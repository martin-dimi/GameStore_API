package database;

import models.Game;
import models.Record;

import java.util.List;

public interface GameStoreService {
    public List<Game> getGames();
    public List<Record> getRecords();
    public Game getGame(int id);
    public Record getRecord(int id);
    public void insertGame(Game game);
    public void insertRecord(Record record);
    public void deleteGame(int id);
    public void deleteRecord(int id);
    public String getSales(int from, int to);
    public void saleGame(int id, int price);
}
