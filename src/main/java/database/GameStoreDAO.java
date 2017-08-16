package database;

import models.Game;
import models.GameMapper;
import models.Record;
import models.RecordMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface GameStoreDAO {

    @SqlUpdate("insert into games (id, name, genre, releaseDate, stock, price)" +
            " values (:id, :name, :genre, :releaseDate, :stock, :price)")
    int insertGame(@BindBean Game game);

    @SqlUpdate("INSERT INTO records (id, game_id, saleDate, price)" +
            " values (:id, :game_id, :saleDate, :price)")
    int insertRecord(@BindBean Record record);

    @SqlQuery("SELECT * FROM games")
    @Mapper(GameMapper.class)
    List<Game> getGames();

    @SqlQuery("SELECT * FROM records")
    @Mapper(RecordMapper.class)
    List<Record> getRecords();

    @SqlQuery("SELECT * FROM games WHERE id=:id")
    @Mapper(GameMapper.class)
    Game getGame(@Bind("id") int id);

    @SqlQuery("SELECT * FROM records WHERE id=:id")
    @Mapper(RecordMapper.class)
    Record getRecord(@Bind("id") int id);

    @SqlUpdate("UPDATE games SET stock = stock - 1 WHERE id=:id")
    int reduceGameStock(@Bind("id") int id);

    @SqlUpdate("DELETE FROM games WHERE id=:id")
    int deleteGame(@Bind("id") int id);

    @SqlUpdate("DELETE FROM records WHERE id=:id")
    int deleteRecord(@Bind("id") int id);

    void close();

}
