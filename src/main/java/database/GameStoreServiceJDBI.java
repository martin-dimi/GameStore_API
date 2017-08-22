package database;

import models.Game;
import models.Record;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import scala.Tuple2;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.apache.spark.sql.functions.col;

public class GameStoreServiceJDBI implements GameStoreService{

    private final String url = "jdbc:mysql://localhost:3306/games?useSSL=false";
    private final String user = "admin";
    private final String pass = "admin";

    private GameDAO gameDAO;
    private RecordDAO recordDAO;
    private Handle handle;
    private SparkSession spark;
    private Properties properties;

    private void connect(){
        DBI dbi = new DBI(url, user, pass);
        handle = dbi.open();
        gameDAO = handle.attach(GameDAO.class);
        recordDAO = handle.attach(RecordDAO.class);
    }

    private void connectSpark(){
        properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", pass);

        spark = SparkSession.builder()
                .appName("gameStore")
                .master("local")
                .getOrCreate();
    }

    @Override
    public List<Game> getGames() {
        connect();

        List<Game> games = gameDAO.getGames();
        handle.close();

        return games;
    }

    @Override
    public List<Record> getRecords() {
        connect();

        List<Record> records = recordDAO.getRecords();
        handle.close();

        return records;
    }

    @Override
    public Game getGame(int id) {
        connect();

        Game game = gameDAO.getGame(id);
        handle.close();

        return game;
    }

    @Override
    public Record getRecord(int id) {
        connect();

        Record record = recordDAO.getRecord(id);
        handle.close();

        return record;
    }

    @Override
    public void insertGame(Game game) {
        connect();
        gameDAO.insertGame(game);
        handle.close();
    }

    @Override
    public void insertRecord(Record record) {
        connect();
        recordDAO.insertRecord(record);
        handle.close();
    }

    @Override
    public void deleteGame(int id) {
        connect();
        gameDAO.deleteGame(id);
        handle.close();
    }

    @Override
    public void deleteRecord(int id) {
        connect();
        recordDAO.deleteRecord(id);
        handle.close();
    }

    @Override
    public String getSales(int from, int to) {
        connectSpark();

        Dataset<Record> recordsSet = spark.read()
                .jdbc(url, "records", properties)
                .as(Encoders.bean(Record.class))
                .filter(col("saleDate").between(from, to));

        Map<Integer, String> gameIdPair= spark.read()
                .jdbc(url, "games", properties)
                .select("id", "name")
                .toJavaRDD().mapToPair(row -> new Tuple2<>(row.getInt(0), row.getString(1)))
                .collectAsMap();

        JavaPairRDD<String, Tuple2<Integer, Integer>> recordPair = recordsSet.toJavaRDD()
                .mapToPair(record -> new Tuple2<>(
                   gameIdPair.get(record.getGame_id()),
                   new Tuple2<>(1, record.getPrice())))
                .reduceByKey((sale1, sale2) -> new Tuple2<>(
                        sale1._1 + sale2._1,    //number of sales
                        sale2._2 + sale2._2     //revenue
                ));

        String salesJSON = null;
        try{
            salesJSON = new ObjectMapper().writeValueAsString(recordPair.collectAsMap())
                    .replaceAll("_1", "numberOfSales")
                    .replaceAll("_2", "revenue");
        }catch (Exception e){
            e.printStackTrace();
        }

        return salesJSON;
    }

    @Override
    public void saleGame(int id, int price) {
        connect();
        handle.begin();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        gameDAO.reduceGameStock(id);
        recordDAO.insertRecord(new Record(id, currentYear, price));

        handle.commit();
        handle.close();
    }
}
