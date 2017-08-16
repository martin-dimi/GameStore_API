package models;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordMapper implements ResultSetMapper<Record> {

    @Override
    public Record map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        return new Record(
                resultSet.getInt("id"),
                resultSet.getInt("game_id"),
                resultSet.getInt("saleDate"),
                resultSet.getInt("price")
        );

    }
}
