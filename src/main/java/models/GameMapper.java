package models;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements ResultSetMapper<Game> {

    @Override
    public Game map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        return new Game(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("genre"),
                resultSet.getInt("stock"),
                resultSet.getInt("price"),
                resultSet.getInt("releaseDate")
        );

    }
}
