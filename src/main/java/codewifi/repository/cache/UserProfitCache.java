package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.UserProfitMapper;
import codewifi.repository.model.UserProfitModel;
import codewifi.utils.JsonUtil;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserProfitCache {
    public final RedissonService redissonService;
    public final JsonUtil jsonUtil;

    public final UserProfitMapper userProfitMapper;

    public UserProfitModel getUserProfit(String userNo){
        RBucket<UserProfitModel> bucket = redissonService.getBucket(RedisKeyConstants.USER_PROFIT + userNo, UserProfitModel.class);

        UserProfitModel userProfitModel = bucket.get();
        if (Objects.nonNull(userProfitModel)) {
            return userProfitModel;
        }
        userProfitModel = userProfitMapper.getUserProfit(userNo);
        if (Objects.isNull(userProfitModel)) {
            return null;
        }
        bucket.set(userProfitModel, RedisKeyConstants.EXPIRE_BY_TREE_HOUR, TimeUnit.SECONDS);
        return userProfitModel;
    }

    public void addUserProfit(Integer id, String userNo, BigDecimal addProfit){
        userProfitMapper.addUserProfit(id, addProfit);
        delRedisUserProfit(userNo);
    }

    public void subUserProfit(Integer id, String userNo, BigDecimal addProfit){
        userProfitMapper.subUserProfit(id, addProfit);
        delRedisUserProfit(userNo);
    }

    public void upYesterdayUserProfit(Integer id, String userNo, BigDecimal profit){
        userProfitMapper.upYesterdayUserProfit(id, profit);
        delRedisUserProfit(userNo);
    }

    public void delRedisUserProfit(String userNo){
        RBucket<UserProfitModel> bucket = redissonService.getBucket(RedisKeyConstants.USER_PROFIT + userNo, UserProfitModel.class);
        bucket.delete();
    }

    public void createUserProfit(String userNo){
        userProfitMapper.addProfit(userNo);
    }
}
