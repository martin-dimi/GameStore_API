package database;

import models.Game;
import models.GameMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface GameDAO {

    @SqlUpdate("insert into games (id, name, genre, releaseDate, stock, price)" +
            " values (:id, :name, :genre, :releaseDate, :stock, :price)")
    int insertGame(@BindBean Game game);

    @SqlQuery("SELECT * FROM games")
    @Mapper(GameMapper.class)
    List<Game> getGames();

    @SqlQuery("SELECT * FROM games WHERE id=:id")
    @Mapper(GameMapper.class)
    Game getGame(@Bind("id") int id);

    @SqlUpdate("UPDATE games SET stock = stock - 1 WHERE id=:id")
    int reduceGameStock(@Bind("id") int id);

    @SqlUpdate("DELETE FROM games WHERE id=:id")
    int deleteGame(@Bind("id") int id);

    void close();
}
