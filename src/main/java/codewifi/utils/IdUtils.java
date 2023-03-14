package codewifi.utils;


import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.common.constant.enums.NoNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.api.RAtomicLong;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class IdUtils {
    @Resource
    RedissonService redissonService;

    public long getByDayId(NoNameEnum noNameEnum){
        String localDayString = LocalDate.now().toString();
        String dayString = localDayString.replace("-","");

        String headerString = dayString.substring(2);
        RAtomicLong rAtomicLong = redissonService.getAtomicLong(dayString);
        int addNum = BigDecimal.valueOf(Math.random()).multiply(BigDecimal.valueOf(10)).intValue() + 1;
        long lastLong = rAtomicLong.getAndAdd(addNum);
        rAtomicLong.expire(86400, TimeUnit.SECONDS);

        String endString = String.format("%03d",lastLong);
        String idString = Long.parseLong(headerString) * 4 + endString;
        return noNameEnum.getNoHeader() + Long.parseLong(idString);
    }

    public String getToken(String userNo){
        String ticketString = userNo + System.currentTimeMillis();
        return DigestUtils.md5Hex(ticketString);
    }

    public long getByMonthId(){
        String localDayString = LocalDate.now().toString();
        String dayString = localDayString.replace("-","");

        String headerString = dayString.substring(2, dayString.length() - 2);
        RAtomicLong rAtomicLong = redissonService.getAtomicLong(dayString);
        int addNum = BigDecimal.valueOf(Math.random()).multiply(BigDecimal.valueOf(10)).intValue() + 1;
        long lastLong = rAtomicLong.getAndAdd(addNum);
        rAtomicLong.expire(30, TimeUnit.DAYS);

        String idString = Long.parseLong(headerString) + String.valueOf(lastLong);
        return Long.parseLong(idString);
    }
}
