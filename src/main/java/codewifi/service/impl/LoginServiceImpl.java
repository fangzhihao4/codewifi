package codewifi.service.impl;


import codewifi.annotation.exception.ReturnException;
import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.VerystatusConstants;
import codewifi.common.constant.enums.LoginTypeEnums;
import codewifi.common.constant.enums.NoNameEnum;
import codewifi.common.constant.enums.VerystatusUserHeadEnum;
import codewifi.repository.cache.UserLoginCache;
import codewifi.repository.cache.UserProfitCache;
import codewifi.repository.cache.VerystatusUserWalletCache;
import codewifi.repository.mapper.UserMapper;
import codewifi.repository.mapper.VerystatusAdviseMapper;
import codewifi.repository.mapper.VerystatusUserMapper;
import codewifi.repository.model.UserModel;
import codewifi.repository.model.VerystatusAdviseModel;
import codewifi.repository.model.VerystatusUserModel;
import codewifi.repository.model.VerystatusUserWalletModel;
import codewifi.request.user.UserLoginRequest;
import codewifi.request.user.WxUserHeadUpRequest;
import codewifi.request.user.WxUserNicknameUpRequest;
import codewifi.request.very.VerystatusAdviseRequest;
import codewifi.response.user.UserLoginResponse;
import codewifi.response.user.VerystatusUserLoginResponse;
import codewifi.sdk.wxApi.WxApiService;
import codewifi.sdk.wxApi.response.WechatJsCode2SessionResponse;
import codewifi.service.UserLoginCommonService;
import codewifi.service.LoginService;
import codewifi.utils.IdUtils;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private static final LogUtil logUtil = LogUtil.getLogger(LoginServiceImpl.class);

    private static final String V1 = "user";
    private static final String V2 = "LoginServiceImpl";
    private final UserMapper userMapper;
    private final IdUtils idUtils;
    private final UserLoginCommonService userLoginCommonService;
    private final WxApiService wxApiService;
    private final UserLoginCache userLoginCache;
    private final UserProfitCache userProfitCache;
    private final RedissonService redissonService;
    private final VerystatusUserWalletCache verystatusUserWalletCache;
    private final VerystatusAdviseMapper verystatusAdviseMapper;


    private final String appId = "wx83a48cee270a3de9";
    private final String appSecret = "26deaa40cb1bd4d0c928ddef4f4a5a21";

    private final String very_status_appId = "wxf0dda273a342bda7";
    private final String very_status_appSecret = "5fad85edc3057a07191c390f2fa4ce83";

    private final String all_know_appId = "wx2f25e52fdbe43d0e";
    private final String all_know_appSecret = "a619da65d8960155c5f6ff89a827f06c";

    @Override
    public UserLoginResponse pwdLogin(UserLoginRequest request) {
        String v3 = "pwdLogin";
        UserModel userModel = userMapper.getByUserNo(request.getUserNo());
        if (Objects.isNull(userModel)) {
            logUtil.infoWarn(V1, V2, v3, ReturnEnum.FAILURE.getLogMsg(), request, null);
            throw new ReturnException(ReturnEnum.FAILURE);
        }

//        String token = idUtils.getToken(userModel.getUserNo());
//        userLoginCommonService.setTokenByUserInfo(LoginTypeEnums.PWD,userModel);
//
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        String token = userLoginCommonService.setTokenByUserInfo(LoginTypeEnums.PWD, userModel);

        userLoginResponse.setUserNo(userModel.getUserNo());
        userLoginResponse.setToken(token);
//
        return userLoginResponse;
    }

    @Transactional
    @Override
    public UserLoginResponse wxLogin(String code) {
        String v3 = "wxLogin";
        if (StringUtils.isEmpty(code)) {
            logUtil.infoBug(V1, V2, v3, "wx登录缺失code", code, null);
            throw new ReturnException(ReturnEnum.TOKEN_CODE_ERROR);
        }

        WechatJsCode2SessionResponse wechatJsCode2SessionResponse = wxApiService.snsJsCode2Session(code, appId, appSecret);
        if (Objects.isNull(wechatJsCode2SessionResponse) || StringUtils.isEmpty(wechatJsCode2SessionResponse.getOpenid())) {
            logUtil.infoBug(V1, V2, v3, "wx登录返回空数据", code, wechatJsCode2SessionResponse);
            throw new ReturnException(ReturnEnum.WX_LOGIN_FAIL);
        }
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        UserModel userModel = userLoginCache.getUserByOpenid(wechatJsCode2SessionResponse.getOpenid());
        if (Objects.isNull(userModel)) {
            //新用户
            userModel = new UserModel();
            String userNo = String.valueOf(idUtils.getByDayId(NoNameEnum.USER_NO_HEADER));
            userModel.setUserNo(userNo);
            userModel.setNickname("微信用户" + userNo.substring(0, 6));
            userModel.setOpenid(wechatJsCode2SessionResponse.getOpenid());
            userModel.setType(UserMapper.WX_REGISTER);
            userModel.setHeadImgUrl(UserMapper.HEADER_DEFAULT);
            userLoginCache.addUserByWx(userModel);
            userProfitCache.createUserProfit(userNo);
        }
        String token = userLoginCommonService.setTokenByUserInfo(LoginTypeEnums.WX, userModel);
        userLoginResponse.setNickname(userModel.getNickname());
        userLoginResponse.setHeaderImg(userModel.getHeadImgUrl());
        userLoginResponse.setUserNo(userModel.getUserNo());
        userLoginResponse.setToken(token);
        return userLoginResponse;
    }

    @Transactional
    @Override
    public VerystatusUserLoginResponse wxVerystatusLogin(String code, Integer type) {
        String v3 = "wxLogin";
        if (StringUtils.isEmpty(code)) {
            logUtil.infoBug(V1, V2, v3, "wx登录缺失code", code, null);
            throw new ReturnException(ReturnEnum.TOKEN_CODE_ERROR);
        }

        String appid = very_status_appId;
        String appSecret = very_status_appSecret;
        byte typeReg = VerystatusUserMapper.WX_REGISTER;
        if (Objects.nonNull(type) && (type == 2)){
            typeReg = VerystatusUserMapper.WX_ALL_REGISTER;
            appid = all_know_appId;
            appSecret = all_know_appSecret;
        }

        WechatJsCode2SessionResponse wechatJsCode2SessionResponse = wxApiService.snsJsCode2Session(code, appid, appSecret);
        if (Objects.isNull(wechatJsCode2SessionResponse) || StringUtils.isEmpty(wechatJsCode2SessionResponse.getOpenid())) {
            logUtil.infoBug(V1, V2, v3, "wx登录返回空数据", code, wechatJsCode2SessionResponse);
            throw new ReturnException(ReturnEnum.WX_LOGIN_FAIL);
        }
        VerystatusUserLoginResponse verystatusUserLoginResponse = new VerystatusUserLoginResponse();
        verystatusUserLoginResponse.setIsFirst(VerystatusConstants.no_first_register_user);
        VerystatusUserModel verystatusUserModel = userLoginCache.getVerystatusUserByOpenId(wechatJsCode2SessionResponse.getOpenid());
        VerystatusUserWalletModel verystatusUserWalletModel = new VerystatusUserWalletModel();

        if (Objects.nonNull(verystatusUserModel)){
            verystatusUserWalletModel = verystatusUserWalletCache.getUserWallet(verystatusUserModel.getUserNo());
        }

        if (Objects.isNull(verystatusUserModel)) {
            //新用户
            verystatusUserModel = new VerystatusUserModel();
            String userNo = String.valueOf(idUtils.getByDayId(NoNameEnum.VERY_STATUS_USER_NO_HEADER));
            verystatusUserModel.setUserNo(userNo);
            verystatusUserModel.setNickname("微信用户" + userNo.substring(userNo.length() - 6));
            verystatusUserModel.setOpenid(wechatJsCode2SessionResponse.getOpenid());
            verystatusUserModel.setType(typeReg);
            verystatusUserModel.setHeadImgUrl(VerystatusUserMapper.HEADER_DEFAULT);
            verystatusUserModel.setHeadType(VerystatusUserMapper.HEAD_TYPE_HTTPS);
            userLoginCache.addVerystatusUserByWx(verystatusUserModel);

            //增加钱包
            verystatusUserWalletModel.setUserNo(userNo);
            verystatusUserWalletModel.setCoin(VerystatusConstants.first_register_send_coin);
            verystatusUserWalletCache.addUserWallet(verystatusUserWalletModel);

            verystatusUserLoginResponse.setIsFirst(VerystatusConstants.is_first_register_user);
        }

        String token = userLoginCommonService.setVerystatusTokenByUserInfo(LoginTypeEnums.WX, verystatusUserModel);
        verystatusUserLoginResponse.setNickname(verystatusUserModel.getNickname());
        verystatusUserLoginResponse.setHeaderImg(verystatusUserModel.getHeadImgUrl());
        verystatusUserLoginResponse.setHeaderType(verystatusUserModel.getHeadType().intValue());
        verystatusUserLoginResponse.setUserNo(verystatusUserModel.getUserNo());
        verystatusUserLoginResponse.setCoin(verystatusUserWalletModel.getCoin());
        verystatusUserLoginResponse.setGem(BigDecimal.valueOf(0));
        verystatusUserLoginResponse.setToken(token);
        return verystatusUserLoginResponse;
    }

    @Override
    public void wxVerystatusUpHead(String token, VerystatusUserModel userModel, WxUserHeadUpRequest wxUserHeadUpRequest) {
        String v3 = "wxVerystatusUpHead";
        if (!VerystatusUserMapper.HEAD_TYPE_LIST.contains(wxUserHeadUpRequest.getHeadType())){
            logUtil.infoBug(V1, V2, v3, "头像类型错误", wxUserHeadUpRequest, null);
            throw new ReturnException(ReturnEnum.TOKEN_CODE_ERROR);
        }
        if (VerystatusUserMapper.HEAD_TYPE_BASE.equals(wxUserHeadUpRequest.getHeadType())
                && Objects.isNull(VerystatusUserHeadEnum.getHeadEnum(wxUserHeadUpRequest.getPageUrl()))){
            logUtil.infoBug(V1, V2, v3, "头像地址太长", wxUserHeadUpRequest, null);
            throw new ReturnException(ReturnEnum.HEAD_LENGTH_TOO_MAX);
        }
        RLock rLock = redissonService.getLock(RedisKeyConstants.VERY_STATUS_LOCK_USER_INFO_UPDATE + userModel.getUserNo());
        try {
            if (rLock.tryLock(0, 5, TimeUnit.SECONDS)){
                logUtil.infoBug(V1, V2, v3, "头像-用户信息更新锁在进行中", wxUserHeadUpRequest, null);
                throw new ReturnException(ReturnEnum.USER_INFO_UP_ING);
            }
        }catch (InterruptedException ignored) {
            logUtil.infoBug(V1, V2, v3, "头像更新锁异常", wxUserHeadUpRequest, null);
            throw new ReturnException(ReturnEnum.USER_HEAD_UP_LOCK_ERROR);
        }
        // 执行业务
        try {
            userLoginCache.updateVerystatusHead(userModel.getUserNo(),token,wxUserHeadUpRequest.getHeadType(),wxUserHeadUpRequest.getPageUrl());
        }
        catch (Exception e) {
            logUtil.infoBug(V1, V2, v3, "头像更新异常", wxUserHeadUpRequest, null);
            throw new ReturnException(ReturnEnum.USER_HEAD_UP_ERROR);
        }
        finally {
            if (rLock.isLocked())
                rLock.unlock();
        }
    }

    @Override
    public void wxVerystatusUpNickname(String token, VerystatusUserModel userModel, WxUserNicknameUpRequest wxUserNicknameUpRequest) {
        String v3 = "wxVerystatusUpNickname";
        RLock rLock = redissonService.getLock(RedisKeyConstants.VERY_STATUS_LOCK_USER_INFO_UPDATE + userModel.getUserNo());
        try {
            if (rLock.tryLock(0, 5, TimeUnit.SECONDS)){
                logUtil.infoBug(V1, V2, v3, "昵称用户信息更新锁在进行中", wxUserNicknameUpRequest, null);
                throw new ReturnException(ReturnEnum.USER_INFO_UP_ING);
            }
        }catch (InterruptedException ignored) {
            logUtil.infoBug(V1, V2, v3, "昵称更新锁异常", wxUserNicknameUpRequest, null);
            throw new ReturnException(ReturnEnum.USER_NICKNAME_LOCK_UP_ERROR);
        }
        // 执行业务
        try {
            userLoginCache.updateVerystatusNickname(userModel.getUserNo(),token, wxUserNicknameUpRequest.getNickname());
        }
        catch (Exception e) {
            logUtil.infoBug(V1, V2, v3, "昵称更新异常", wxUserNicknameUpRequest, null);
            throw new ReturnException(ReturnEnum.USER_NICKNAME_UP_ERROR);
        }
        finally {
            if (rLock.isLocked())
                rLock.unlock();
        }
    }

    @Override
    public void addAdvise(VerystatusUserModel userModel, VerystatusAdviseRequest request) {
        String v3 = "wxVerystatusUpNickname";
        RLock rLock = redissonService.getLock(RedisKeyConstants.VERY_STATUS_LOCK_USER_ADVISE_UPDATE + userModel.getUserNo());
        try {
            if (!rLock.tryLock(0, 10, TimeUnit.SECONDS)){
                logUtil.infoBug(V1, V2, v3, "用户意见信息更新锁在进行中", request, null);
                throw new ReturnException(ReturnEnum.USER_INFO_UP_ING);
            }
        }catch (InterruptedException ignored) {
            logUtil.infoBug(V1, V2, v3, "用户意见更新锁异常", request, null);
            throw new ReturnException(ReturnEnum.USER_NICKNAME_LOCK_UP_ERROR);
        }
        // 执行业务
        try {
            VerystatusAdviseModel verystatusAdviseModel = new VerystatusAdviseModel();
            verystatusAdviseModel.setTitle(request.getTitle());
            verystatusAdviseModel.setContent(request.getContent());
            verystatusAdviseModel.setAnswer("");
            verystatusAdviseModel.setUserNo(userModel.getUserNo());
            verystatusAdviseMapper.addAdvise(verystatusAdviseModel);
        }
        catch (Exception e) {
            logUtil.infoBug(V1, V2, v3, "用户意见更新异常", request, null);
            throw new ReturnException(ReturnEnum.USER_NICKNAME_UP_ERROR);
        }
        finally {
            if (rLock.isLocked())
                rLock.unlock();
        }
    }
}
