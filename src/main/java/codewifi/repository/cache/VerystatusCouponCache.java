package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.repository.mapper.VerystatusCouponMapper;
import codewifi.repository.model.VerystatusCouponModel;
import codewifi.repository.model.VerystatusGoodsModel;
import codewifi.utils.JsonUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class VerystatusCouponCache {

    public final RedissonService redissonService;
    public final VerystatusCouponMapper verystatusCouponMapper;
    public final JsonUtil jsonUtil;
    public List<VerystatusCouponModel> getUserGoodsList(Integer type){
        RBucket<String> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_COUPON_LIST_BY_TYPE + type, String.class);
        String resString =  bucket.get();
        if (Objects.nonNull(resString)){
            return JSON.parseArray(resString,VerystatusCouponModel.class);
        }
        List<VerystatusCouponModel> dbList = verystatusCouponMapper.getList(type);
        if (Objects.isNull(dbList) || dbList.isEmpty()){
            return new ArrayList<>();
        }

        String dbString = jsonUtil.writeValueAsString(dbList);
        long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        bucket.set(dbString,time, TimeUnit.SECONDS);
        return dbList;
    }

}
