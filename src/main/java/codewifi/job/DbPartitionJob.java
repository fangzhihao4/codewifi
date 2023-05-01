package codewifi.job;

import codewifi.annotation.ScheduledProperties;
import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.CommonMapper;
import codewifi.repository.model.CommonDbModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class DbPartitionJob  extends JobBase {
    private final CommonMapper commonMapper;
    private final RedissonService redissonService;

    @Override
    public void taskService() {
        String lockKey = RedisKeyConstants.VERY_STATUS_LOCK_DB_PARTITION;
        RLock linkLock = redissonService.getLock(lockKey);
        try {
            if (linkLock.tryLock(0, RedisKeyConstants.EXPIRE_BY_TWO_HOUR, TimeUnit.MINUTES)) {
               updatePartition();
            }
        } catch (InterruptedException ignored) {
            return;
        }finally {
            if (linkLock.isLocked())
                linkLock.unlock();
        }
    }

    @Override
    public String getCron() {
        return ScheduledProperties.SYNC_ONE_HOUR;
    }

    public void updatePartition(){
        Map<String,String> dbMap = new HashMap<>();
        dbMap.put("vcoinorder", "verystatus_coin_order");
        dbMap.put("vcoinorderinfo", "verystatus_coin_order_info");
        for (Map.Entry<String, String> entry : dbMap.entrySet()) {
            String baseTableName = entry.getKey();
            String tableName = entry.getValue();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
            for (int dayInt = 1; dayInt < 10; dayInt ++ ){
                int plusDay = 31 + dayInt;

                LocalDate delFormatDate = LocalDate.now().plusDays(-plusDay);
                String delDateString = delFormatDate.format(df);
                String delPartitionTableName = baseTableName  + delDateString;
                CommonDbModel commonDbModelDel = commonMapper.getPartition(tableName,delPartitionTableName);
                if (Objects.nonNull(commonDbModelDel)){
                    commonMapper.delPartition(tableName,delPartitionTableName);
                }

                LocalDate addFormatDate = LocalDate.now().plusDays(dayInt);
                String dateString = addFormatDate.format(df);
                String addPartitionTableName = baseTableName  +dateString;
                CommonDbModel commonDbModel = commonMapper.getPartition(tableName,addPartitionTableName);
                if (Objects.isNull(commonDbModel)){
                    commonMapper.addPartition(tableName,addPartitionTableName,addFormatDate.toString());
                }
            }
        }

    }
}
