package codewifi.repository.mapper;

import codewifi.repository.model.CommonDbModel;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.Objects;


@Repository
@AllArgsConstructor
public class CommonMapper {
    private final DSLContext context;

    public CommonDbModel getPartition(String tableName, String partitionTableName){
        String sql = "SELECT  partition_name part,partition_expression expr,partition_description descr, table_rows FROM information_schema.partitions WHERE table_schema = SCHEMA() AND table_name = 'tableName' AND partition_name = 'partitionTableName' limit 1;"
                .replace("tableName", tableName)
                .replace("partitionTableName", partitionTableName);

        Record withRecord = context.fetchOne(sql);
        if (Objects.nonNull(withRecord)) {
            return withRecord.into(CommonDbModel.class);
        }
        return null;
    }

    public void addPartition(String tableName,String partitionTableName, String dateString){
        String sql = "alter table tableName add partition (partition partitionTableName values less than (to_days('pushPartition')));"
                .replace("tableName", tableName)
                .replace("partitionTableName", partitionTableName)
                .replace("pushPartition",dateString);
        context.fetchOne(sql);
    }


    public void delPartition(String tableName, String partitionTableName){
        String sql = "alter table tableName drop partition partitionTableName;"
                .replace("tableName", tableName)
                .replace("partitionTableName", partitionTableName);
        context.fetchOne(sql);
    }
}
