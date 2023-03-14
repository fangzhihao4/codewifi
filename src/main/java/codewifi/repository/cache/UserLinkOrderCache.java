package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.UserLinkOrderMapper;
import codewifi.repository.model.UserLinkOrderModel;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserLinkOrderCache {
    public final RedissonService redissonService;
    public final UserLinkOrderMapper userLinkOrderMapper;

    public void addLinkScan(UserLinkOrderModel userLinkWifiModel){
        userLinkOrderMapper.addLinkOrder(userLinkWifiModel);
        RBucket<UserLinkOrderModel> bucket = redissonService.getBucket(RedisKeyConstants.LINK_ORDER_BY_LINK_NO + userLinkWifiModel.getLinkNo(), UserLinkOrderModel.class);
        bucket.set(userLinkWifiModel, RedisKeyConstants.EXPIRE_BY_THIRTY_MINUTE_SECONDS, TimeUnit.SECONDS);
    }


    public void addLinkOrder(UserLinkOrderModel userLinkWifiModel){
        userLinkOrderMapper.addLinkOrder(userLinkWifiModel);
        RBucket<UserLinkOrderModel> bucket = redissonService.getBucket(RedisKeyConstants.LINK_ORDER_BY_LINK_NO + userLinkWifiModel.getLinkNo(), UserLinkOrderModel.class);
        bucket.set(userLinkWifiModel, RedisKeyConstants.EXPIRE_BY_THIRTY_MINUTE_SECONDS, TimeUnit.SECONDS);
    }

    public UserLinkOrderModel getCacheLink(String linkNo){
        RBucket<UserLinkOrderModel> bucket = redissonService.getBucket(RedisKeyConstants.LINK_ORDER_BY_LINK_NO + linkNo, UserLinkOrderModel.class);
        return bucket.get();
    }

    public UserLinkOrderModel updateCacheLink(UserLinkOrderModel userLinkWifiModel){
        RBucket<UserLinkOrderModel> bucket = redissonService.getBucket(RedisKeyConstants.LINK_ORDER_BY_LINK_NO + userLinkWifiModel.getLinkNo(), UserLinkOrderModel.class);
        bucket.set(userLinkWifiModel, RedisKeyConstants.EXPIRE_BY_THIRTY_MINUTE_SECONDS, TimeUnit.SECONDS);
        return userLinkWifiModel;
    }

    public UserLinkOrderModel getUserIsSuccessLinkWifi(String userNo, String wifiNo){
        return  userLinkOrderMapper.getLinkUserWifi(userNo,wifiNo);
    }
}
