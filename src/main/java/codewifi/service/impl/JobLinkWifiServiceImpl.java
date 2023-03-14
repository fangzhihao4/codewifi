package codewifi.service.impl;

import codewifi.common.RedissonService;
import codewifi.common.constant.LogConstant;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.cache.UserCreateWifiCache;
import codewifi.repository.cache.UserInviteProfitCache;
import codewifi.repository.cache.UserProfitCache;
import codewifi.repository.cache.UserWifiCountCache;
import codewifi.repository.co.UserLinkWifiCo;
import codewifi.repository.mapper.UserMoneyOrderMapper;
import codewifi.repository.model.*;
import codewifi.service.JobLinkWifiService;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class JobLinkWifiServiceImpl implements JobLinkWifiService {
    private final RedissonService redissonService;
    private static final LogUtil logUtil = LogUtil.getLogger(JobLinkWifiServiceImpl.class);
    private static final String V2 = "LinkWifiServiceImpl";
    private static final BigDecimal oneProfit = BigDecimal.valueOf(0.1); //观看一个视频得到的收益
    private static final BigDecimal profit = BigDecimal.valueOf(0.04); //wifi创建的用户得到收益
    private static final BigDecimal parentProfit = BigDecimal.valueOf(0.007); //wifi创建的用户上级得到收益
    private static final BigDecimal subProfit = BigDecimal.valueOf(0.003); //wifi创建的上上级得到收益


    private final JsonUtil jsonUtil;

    private final UserCreateWifiCache userCreateWifiCache;
    private final UserInviteProfitCache userInviteProfitCache;
    private final UserProfitCache userProfitCache;
    private final UserMoneyOrderMapper userMoneyOrderMapper;
    private final UserWifiCountCache userWifiCountCache;

    @Override
    public void pollLinkWifi(String message) {
        String v3 = "pollLinkWifi";
        RMap<String, LocalDateTime> map = redissonService.getMap(RedisKeyConstants.LINK_USER_HASH);
        map.put(message, LocalDateTime.now());
        if (StringUtils.isEmpty(message)) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "消费链接wifi数据控", message, null);
            return;
        }
        UserLinkWifiCo userLinkWifiCo;
        try {
            userLinkWifiCo = jsonUtil.fromJsonString(message, UserLinkWifiCo.class);
        } catch (Exception e) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "消费链接wifi转对象失败", message, e);
            return;
        }
        if (Objects.isNull(userLinkWifiCo)) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "消费链接wifi转对象为空", message, null);
            return;
        }
        if (Objects.isNull(userLinkWifiCo.getWifiNo())
                || Objects.isNull(userLinkWifiCo.getLinkUserNo())
                || Objects.isNull(userLinkWifiCo.getLinkNo())
                || Objects.isNull(userLinkWifiCo.getWifiUserNo())
        ) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "消费链接wifi数据错误", message, null);
            return;
        }
        handleLinkWifi(userLinkWifiCo);
        map.fastRemove(message);
    }

    public void handleLinkWifi(UserLinkWifiCo userLinkWifiCo) {
        String v3 = "handleLinkWifi";
        UserCreateWifiModel userCreateWifiModel = userCreateWifiCache.getWifiInfo(userLinkWifiCo.getWifiNo());
        if (Objects.isNull(userCreateWifiModel)) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "没有查询到wifi信息", userLinkWifiCo, null);
            return;
        }
        addWifiUserProfit(userLinkWifiCo, userCreateWifiModel); //增加创建这个wifi用户的的收益
        addWifiProfit(userCreateWifiModel, profit); //增加这个wifi累计的收益
        parentUserProfit(userCreateWifiModel, userLinkWifiCo);//增加上级收益
        subUserProfit(userCreateWifiModel, userLinkWifiCo);//增加上上级收益
    }

    /**
     * 增加这个wifi用户的收益
     *
     * @param userLinkWifiCo      用户链接wifi信息
     * @param userCreateWifiModel wifi信息
     */
    public void addWifiUserProfit(UserLinkWifiCo userLinkWifiCo, UserCreateWifiModel userCreateWifiModel) {
        String v3 = "addWifiUserProfit";
        BigDecimal addProfit = profit;
        UserProfitModel userProfit = userProfitCache.getUserProfit(userCreateWifiModel.getUserNo());
        if (Objects.isNull(userProfit)) {
            logUtil.error(LogConstant.V1, V2, v3, "没有查询到用户利润信息", userCreateWifiModel.getUserNo(), null);
            return;
        }
        userProfitCache.addUserProfit(userProfit.getId(), userCreateWifiModel.getUserNo(), addProfit);
        UserMoneyOrderModel userMoneyOrderModel = new UserMoneyOrderModel();
        userMoneyOrderModel.setUserNo(userCreateWifiModel.getUserNo());
        userMoneyOrderModel.setPrice(oneProfit);
        userMoneyOrderModel.setUserProfit(addProfit);
        userMoneyOrderModel.setType((byte) 1);
        userMoneyOrderModel.setWifiNo(userCreateWifiModel.getWifiNo());
        userMoneyOrderModel.setWifiUserNo(userCreateWifiModel.getUserNo());
        userMoneyOrderModel.setLinkId(userLinkWifiCo.getLinkNo());
        userMoneyOrderModel.setLinkUserNo(userLinkWifiCo.getLinkUserNo());
        userMoneyOrderModel.setAddTime(0);

        addMoneyOrder(userMoneyOrderModel);
    }

    /**
     * 增加这个wifi的收益
     *
     * @param userCreateWifiModel wifi信息
     * @param addProfit           增加的收益
     */
    public void addWifiProfit(UserCreateWifiModel userCreateWifiModel, BigDecimal addProfit) {
        String v3 = "addWifiProfit";
        UserWifiCountModel userWifiCountModel = userWifiCountCache.getInfoByWifiNo(userCreateWifiModel.getWifiNo());
        if (Objects.isNull(userWifiCountModel)) {
            logUtil.error(LogConstant.V1, V2, v3, "没有查询到wifi统计内容", userCreateWifiModel.getUserNo(), null);
            return;
        }
        userWifiCountCache.addProfit(userCreateWifiModel.getWifiNo(), addProfit);
    }

    /**
     * 增加这个wifi用户的上级的收益
     *
     * @param userCreateWifiModel wifi信息
     * @param userLinkWifiCo      链接wifi信息
     */
    public void parentUserProfit(UserCreateWifiModel userCreateWifiModel, UserLinkWifiCo userLinkWifiCo) {
        String v3 = "parentUserProfit";
        BigDecimal addProfit = parentProfit;
        //查询是否有上级 没有不加
        UserInviteProfitModel userInviteProfitModel = userInviteProfitCache.getUserByInvite(userCreateWifiModel.getUserNo());
        if (Objects.isNull(userInviteProfitModel)) {
            return;
        }
        UserProfitModel userProfit = userProfitCache.getUserProfit(userInviteProfitModel.getParentUserNo());
        if (Objects.isNull(userProfit)) {
            logUtil.error(LogConstant.V1, V2, v3, "没有查询到上级用户利润信息", userInviteProfitModel.getParentUserNo(), null);
            return;
        }
        //增加上级的收益
        userProfitCache.addUserProfit(userProfit.getId(), userInviteProfitModel.getParentUserNo(), addProfit);

        UserMoneyOrderModel userMoneyOrderModel = new UserMoneyOrderModel();
        userMoneyOrderModel.setUserNo(userInviteProfitModel.getParentUserNo());
        userMoneyOrderModel.setPrice(BigDecimal.ZERO);
        userMoneyOrderModel.setUserProfit(addProfit);
        userMoneyOrderModel.setType((byte) 2);
        userMoneyOrderModel.setWifiNo(userCreateWifiModel.getWifiNo());
        userMoneyOrderModel.setWifiUserNo(userCreateWifiModel.getUserNo());
        userMoneyOrderModel.setLinkId(userLinkWifiCo.getLinkNo());
        userMoneyOrderModel.setLinkUserNo(userLinkWifiCo.getLinkUserNo());
        userMoneyOrderModel.setAddTime(0);

        //增加上级的好友wifi产生的利润流水
        addMoneyOrder(userMoneyOrderModel);

        //增加上级的这个好友wifi产品的利润
        userInviteProfitCache.addParentWifiProfit(userInviteProfitModel, addProfit);
    }

    /**
     * 增加这个wifi用户上级的上级的收益
     *
     * @param userCreateWifiModel wifi信息
     * @param userLinkWifiCo      链接wifi信息
     */
    public void subUserProfit(UserCreateWifiModel userCreateWifiModel, UserLinkWifiCo userLinkWifiCo) {
        String v3 = "subUserProfit";
        BigDecimal addProfit = subProfit;
        //查询上级的上级 没有返回
        UserInviteProfitModel userInviteProfitModel = userInviteProfitCache.getUserSub(userCreateWifiModel.getUserNo());
        if (Objects.isNull(userInviteProfitModel)) {
            return;
        }
        UserProfitModel userProfit = userProfitCache.getUserProfit(userInviteProfitModel.getParentUserNo());
        if (Objects.isNull(userProfit)) {
            logUtil.error(LogConstant.V1, V2, v3, "没有查询到用户利润信息", userInviteProfitModel.getParentUserNo(), null);
            return;
        }
        //增加上级的上级的收益
        userProfitCache.addUserProfit(userProfit.getId(), userInviteProfitModel.getParentUserNo(), addProfit);

        UserMoneyOrderModel userMoneyOrderModel = new UserMoneyOrderModel();
        userMoneyOrderModel.setUserNo(userInviteProfitModel.getParentUserNo());
        userMoneyOrderModel.setType((byte) 3);
        userMoneyOrderModel.setPrice(BigDecimal.ZERO);
        userMoneyOrderModel.setUserProfit(addProfit);
        userMoneyOrderModel.setWifiNo(userCreateWifiModel.getWifiNo());
        userMoneyOrderModel.setWifiUserNo(userCreateWifiModel.getUserNo());
        userMoneyOrderModel.setLinkId(userLinkWifiCo.getLinkNo());
        userMoneyOrderModel.setLinkUserNo(userLinkWifiCo.getLinkUserNo());
        userMoneyOrderModel.setAddTime(0);

        //增加上级的上级 下级的下级wifi产生的收益流水
        addMoneyOrder(userMoneyOrderModel);

        //查询上级的上级 的下级 即wifi用户的上级与上级与上级的关系
        UserInviteProfitModel userInviteProfitModelParent = userInviteProfitCache.getUserByInvite(userCreateWifiModel.getUserNo());
        if (Objects.isNull(userInviteProfitModelParent)) {
            logUtil.error(LogConstant.V1, V2, v3, "没有查到上级邀请，但是有上上级邀请", userInviteProfitModel, userLinkWifiCo);
            return;
        }

        //增加上级给上级的上级产生的 上级的下级用户wifi提供的收益
        userInviteProfitCache.addParentInviteProfit(userInviteProfitModel, addProfit);
        //增加该wifi用户提供给上级的上级的收益
        userInviteProfitCache.addSubWifiProfit(userInviteProfitModel, addProfit);
    }

    public void addMoneyOrder(UserMoneyOrderModel userMoneyOrderModel) {
        userMoneyOrderMapper.addMoneyOrder(userMoneyOrderModel);
    }

}
