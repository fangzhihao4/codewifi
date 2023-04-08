package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.common.constant.enums.HoroscopeEnum;
import codewifi.repository.mapper.UserStarMapper;
import codewifi.repository.model.UserStarRecordModel;
import codewifi.response.wifi.StarResponse;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserStarCache {
    public final RedissonService redissonService;
    public final UserStarMapper userStarMapper;

    public void addFortune(String userNo,HoroscopeEnum timeHoroscopeEnum, HoroscopeEnum starHoroscopeEnum, LocalDate localDate, String content){
        UserStarRecordModel userStarRecordModel = new UserStarRecordModel();
        userStarRecordModel.setUserNo(userNo);
        userStarRecordModel.setTime(timeHoroscopeEnum.getType());
        userStarRecordModel.setStar(starHoroscopeEnum.getType());
        userStarRecordModel.setContent(content);
        userStarRecordModel.setTimeName(timeHoroscopeEnum.getName());
        userStarRecordModel.setStarName(starHoroscopeEnum.getName());
        setRedisFortune(userNo,timeHoroscopeEnum.getType(),starHoroscopeEnum.getType(),localDate,userStarRecordModel);

        userStarMapper.addWifiCount(userStarRecordModel);
    }

    public void setRedisFortune(String userNo, String time, String star, LocalDate localDate, UserStarRecordModel userStarRecordModel){
        String redisKey = RedisKeyConstants.STAR_FORTUNE_CONTENT + userNo + ":" + time + ":" + star + ":" + localDate;
        RBucket<UserStarRecordModel> bucket = redissonService.getBucket(redisKey, StarResponse.class);
        bucket.set(userStarRecordModel, RedisKeyConstants.EXPIRE_BY_TWO_HOUR, TimeUnit.SECONDS );
    }

    public UserStarRecordModel getUserByFortune(String userNo, String time, String star, LocalDate localDate){
        String redisKey = RedisKeyConstants.STAR_FORTUNE_CONTENT + userNo + ":" + time + ":" + star + ":" + localDate;
        RBucket<UserStarRecordModel> bucket = redissonService.getBucket(redisKey, UserStarRecordModel.class);
        UserStarRecordModel userStarRecordModel = bucket.get();
        if (Objects.nonNull(userStarRecordModel)){
            return userStarRecordModel;
        }

        userStarRecordModel = userStarMapper.getByUserNo(userNo, time, star, localDate);
        if (Objects.isNull(userStarRecordModel)){
            return null;
        }
        setRedisFortune(userNo, time, star, localDate,userStarRecordModel);
        return userStarRecordModel;
    }

    public void setStarContent(String time, String star, LocalDate localDate, String content){
        String redisKey = RedisKeyConstants.STAR_FORTUNE_STRING + time + ":" + star + ":" + localDate;
        RBucket<String> bucket = redissonService.getBucket(redisKey, String.class);
        bucket.set(content, RedisKeyConstants.EXPIRE_BY_DAY_SECONDS, TimeUnit.SECONDS );
    }

    public String getStarContent(String time, String star, LocalDate localDate){
        String redisKey = RedisKeyConstants.STAR_FORTUNE_STRING + time + ":" + star + ":" + localDate;
        RBucket<String> bucket = redissonService.getBucket(redisKey, String.class);
        return bucket.get();
    }

}
