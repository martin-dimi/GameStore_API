package database;

import models.Record;
import models.RecordMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface RecordDAO {

    @SqlUpdate("INSERT INTO records (id, game_id, saleDate, price)" +
            " values (:id, :game_id, :saleDate, :price)")
    int insertRecord(@BindBean Record record);


    @SqlQuery("SELECT * FROM records")
    @Mapper(RecordMapper.class)
    List<Record> getRecords();


    @SqlQuery("SELECT * FROM records WHERE id=:id")
    @Mapper(RecordMapper.class)
    Record getRecord(@Bind("id") int id);

    @SqlUpdate("DELETE FROM records WHERE id=:id")
    int deleteRecord(@Bind("id") int id);

    void close();
}
