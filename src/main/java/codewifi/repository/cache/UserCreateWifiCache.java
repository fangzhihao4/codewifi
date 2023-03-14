package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.LogConstant;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.UserCreateWifiMapper;
import codewifi.repository.model.UserCreateWifiModel;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserCreateWifiCache {
    private static final LogUtil logUtil = LogUtil.getLogger(UserCreateWifiCache.class);
    private static final String V2 = "UserCreateWifiCache";

    public final RedissonService redissonService;
    public final JsonUtil jsonUtil;
    public final Integer pageSize = 8;

    public final UserCreateWifiMapper userCreateWifiMapper;

    public List<UserCreateWifiModel> getWifiPage(String userNo, int page){
        if (page > 1){
            return userCreateWifiMapper.getUserWifiList(userNo, page, pageSize);
        }
        RBucket<String> bucket = redissonService.getBucket(RedisKeyConstants.USER_WIFI_FIRST_PAGE + userNo, String.class);
        String listString = bucket.get();
        List<String> listWifiNo = new ArrayList<>();
        List<UserCreateWifiModel> wifiModelList = new ArrayList<>();
        if (StringUtils.isNotEmpty(listString)){
            try {
                listWifiNo = JSON.parseArray(listString,String.class);
                for (String wifiNo : listWifiNo){
                    UserCreateWifiModel userCreateWifiModel = getWifiInfo(wifiNo);
                    wifiModelList.add(userCreateWifiModel);
                }
                return wifiModelList;
            }catch (Exception e){
                logUtil.infoBug(LogConstant.V1, V2, "getWifiPage", "wifino信息转json失败", listWifiNo, userNo );
                bucket.delete();
            }
        }
        wifiModelList = userCreateWifiMapper.getUserWifiList(userNo, page, pageSize);
        if (wifiModelList.isEmpty()){
            return wifiModelList;
        }
        for (UserCreateWifiModel userCreateWifiModel : wifiModelList){
            RBucket<UserCreateWifiModel> bucketWifi = redissonService.getBucket(RedisKeyConstants.WIFI_INFO + userCreateWifiModel.getWifiNo(), UserCreateWifiModel.class);
            if (Objects.nonNull(bucket.get())){
                continue;
            }
            bucketWifi.set(userCreateWifiModel, RedisKeyConstants.EXPIRE_BY_TREE_HOUR, TimeUnit.SECONDS);
        }
        return wifiModelList;
    }

    public UserCreateWifiModel getWifiInfo(String wifiNo) {
        RBucket<UserCreateWifiModel> bucket = redissonService.getBucket(RedisKeyConstants.WIFI_INFO + wifiNo, UserCreateWifiModel.class);

        UserCreateWifiModel userCreateWifiModel = bucket.get();
        if (Objects.nonNull(userCreateWifiModel)) {
            return userCreateWifiModel;
        }
        userCreateWifiModel = userCreateWifiMapper.getByWifiNo(wifiNo);
        if (Objects.isNull(userCreateWifiModel)) {
            return null;
        }
        bucket.set(userCreateWifiModel, RedisKeyConstants.EXPIRE_BY_TREE_HOUR, TimeUnit.SECONDS);
        return userCreateWifiModel;
    }

    public UserCreateWifiModel getWifiDetail(String wifiNo){
        return userCreateWifiMapper.getByWifiNo(wifiNo);
    }


    public void addWifi(UserCreateWifiModel userCreateWifiModel){
        userCreateWifiMapper.addWifi(userCreateWifiModel);
        delRedisWifiPage(userCreateWifiModel.getUserNo());
    }

    public void updateWifi(UserCreateWifiModel userCreateWifiModel){
        userCreateWifiMapper.updateWifiInfo(userCreateWifiModel);
        delRedisWifi(userCreateWifiModel.getWifiNo());
    }

    public void updateWifiImg(UserCreateWifiModel userCreateWifiModel){
        userCreateWifiMapper.updateWifiInfoImg(userCreateWifiModel);
    }

    public void addWifiTimes(String userNo,  String wifiNo, Integer addTimes){
        userCreateWifiMapper.addWifiTimes(userNo,wifiNo,addTimes);
        delRedisWifi(wifiNo);
    }

    public void updateWifiFree(String userNo,  String wifiNo){
        userCreateWifiMapper.updateWifiFree(userNo,wifiNo);
        delRedisWifi(wifiNo);
    }

    public void subWifiTimes(String userNo,  String wifiNo){
        userCreateWifiMapper.subWifiTimes(userNo, wifiNo);
        delRedisWifi(wifiNo);
    }

    public void updateWifiAllFree(String userNo){
        userCreateWifiMapper.updateWifiAllFree(userNo);
        delRedisWifi(userNo);
    }

    public void delWifi(String userNo,String wifiNo){
        userCreateWifiMapper.delWifi(wifiNo);
        delRedisWifi(wifiNo);
        delRedisWifiPage(userNo);
    }

    public void delRedisWifi(String wifiNo){
        RBucket<UserCreateWifiModel> bucket = redissonService.getBucket(RedisKeyConstants.WIFI_INFO + wifiNo, UserCreateWifiModel.class);
        bucket.delete();
    }

    public void delRedisWifiPage(String userNo){
        RBucket<String> bucket = redissonService.getBucket(RedisKeyConstants.USER_WIFI_FIRST_PAGE + userNo, String.class);
        bucket.delete();
    }


}
