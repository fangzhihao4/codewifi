package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.UserInviteProfitMapper;
import codewifi.repository.model.UserInviteProfitModel;
import codewifi.utils.JsonUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserInviteProfitCache {
    public final RedissonService redissonService;
    public final JsonUtil jsonUtil;

    public final UserInviteProfitMapper userInviteProfitMapper;

    public UserInviteProfitModel getUserByInvite(String userNo){
        RBucket<UserInviteProfitModel> bucket = redissonService.getBucket(RedisKeyConstants.USER_BY_INVITE_INFO + userNo, UserInviteProfitModel.class);

        UserInviteProfitModel userInviteProfitModel = bucket.get();
        if (Objects.nonNull(userInviteProfitModel)) {
            return userInviteProfitModel;
        }
        userInviteProfitModel = userInviteProfitMapper.getUserParentInfo(userNo);
        if (Objects.isNull(userInviteProfitModel)) {
            return null;
        }
        bucket.set(userInviteProfitModel, RedisKeyConstants.EXPIRE_BY_TREE_HOUR, TimeUnit.SECONDS);
        return userInviteProfitModel;
    }

    public UserInviteProfitModel getUserSub(String userNo){
        RBucket<UserInviteProfitModel> bucket = redissonService.getBucket(RedisKeyConstants.USER_BY_SUB_INFO + userNo, UserInviteProfitModel.class);

        UserInviteProfitModel userInviteProfitModel = bucket.get();
        if (Objects.nonNull(userInviteProfitModel)) {
            return userInviteProfitModel;
        }
        userInviteProfitModel = userInviteProfitMapper.getUserSubInfo(userNo);
        if (Objects.isNull(userInviteProfitModel)) {
            return null;
        }
        bucket.set(userInviteProfitModel, RedisKeyConstants.EXPIRE_BY_TREE_HOUR, TimeUnit.SECONDS);
        return userInviteProfitModel;
    }

    public List<UserInviteProfitModel> getInviteByUser(String userNo, int page){
        if (page > 1){
            return userInviteProfitMapper.getUserWifiListByPage(userNo,page,2);
        }
        RBucket<String> bucket = redissonService.getBucket(RedisKeyConstants.USER_INVITE_PROFIT + userNo, String.class);
        String jsonString = bucket.get();
        if (StringUtils.isNotEmpty(jsonString)){
            try {
                return JSON.parseArray(jsonString,UserInviteProfitModel.class);
            }catch (Exception e){

            }
        }
        List<UserInviteProfitModel> list = userInviteProfitMapper.getUserWifiListByPage(userNo,page,2);
        if (list.isEmpty()){
            return list;
        }
        String redisString = jsonUtil.writeValueAsString(list);
        bucket.set(redisString,RedisKeyConstants.EXPIRE_BY_FIVE_MINUTE_SECONDS,TimeUnit.SECONDS);
        return list;
    }

    /**
     * 增加该wifi用户给上级产生的wifi收益
     * @param userInviteProfitModel 直接邀请关系信息
     * @param addProfit 利润
     */
    public void addParentWifiProfit(UserInviteProfitModel userInviteProfitModel, BigDecimal addProfit){
        userInviteProfitMapper.addInviteWifiTimes(userInviteProfitModel.getId(), addProfit);
    }

    /**
     * 增加上级给上级的上级产生的 上级的下级用户wifi提供的收益
     * @param userInviteProfitModel 直接邀请关系信息
     * @param addProfit 利润
     */
    public void addParentInviteProfit(UserInviteProfitModel userInviteProfitModel, BigDecimal addProfit){
        userInviteProfitMapper.addInviteSubTimes(userInviteProfitModel.getId(), addProfit);

    }

    /**
     * 增加该wifi用户提供给上级的上级的收益
     * @param userInviteProfitModel 下下级邀请关系
     * @param addProfit 利润
     */
    public void addSubWifiProfit(UserInviteProfitModel userInviteProfitModel, BigDecimal addProfit){
        userInviteProfitMapper.addInviteSubTimes(userInviteProfitModel.getId(), addProfit);

    }
}
