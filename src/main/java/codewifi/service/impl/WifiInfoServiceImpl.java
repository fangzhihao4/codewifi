package codewifi.service.impl;

import codewifi.annotation.exception.ReturnException;
import codewifi.common.RedissonService;
import codewifi.common.YmlContent;
import codewifi.common.constant.LogConstant;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.enums.NoNameEnum;
import codewifi.repository.cache.UserCreateWifiCache;
import codewifi.repository.cache.UserWifiCountCache;
import codewifi.repository.mapper.WxCodeSceneMapper;
import codewifi.repository.model.UserCreateWifiModel;
import codewifi.repository.model.UserModel;
import codewifi.repository.model.UserWifiCountModel;
import codewifi.repository.model.WxCodeSceneModel;
import codewifi.request.wifi.WifiNoRequest;
import codewifi.request.wifi.WifiUpdateRequest;
import codewifi.response.wifi.UserWifiInfoResponse;
import codewifi.sdk.wxApi.WxApiService;
import codewifi.service.WifiInfoService;
import codewifi.utils.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class WifiInfoServiceImpl implements WifiInfoService {
    private static final LogUtil logUtil = LogUtil.getLogger(WifiInfoServiceImpl.class);
    private static final String V2 = "WifiInfoServiceImpl";

    private final IdUtils idUtils;

    private final UserCreateWifiCache userCreateWifiCache;
    private final RedissonService redissonService;
    private final WxApiService wxApiService;
    private final WxCodeSceneMapper wxCodeSceneMapper;
    private final UserWifiCountCache userWifiCountCache;

    @Override
    public void updateWifi(UserModel userModel, WifiUpdateRequest wifiUpdateRequest) {
        String v3 = "updateWifi";
        UserCreateWifiModel userCreateWifiModel = checkWifi(userModel.getUserNo(), wifiUpdateRequest.getWifiNo(), v3);

        userCreateWifiModel.setAddress(wifiUpdateRequest.getAddress());
        userCreateWifiModel.setName(wifiUpdateRequest.getName());
        userCreateWifiModel.setTitle(wifiUpdateRequest.getTitle());
        userCreateWifiModel.setPassword(wifiUpdateRequest.getPassword());
        userCreateWifiCache.updateWifi(userCreateWifiModel);
    }


    @Override
    public void delWifi(UserModel userModel, WifiNoRequest wifiNoRequest) {
        String v3 = "delWifi";
        checkWifi(userModel.getUserNo(), wifiNoRequest.getWifiNo(), v3);
        userCreateWifiCache.delWifi(userModel.getUserNo(), wifiNoRequest.getWifiNo());
    }


    @Transactional
    @Override
    public void addWifi(UserModel userModel, WifiUpdateRequest wifiUpdateRequest) {
        String v3 = "addWifi";
        RLock linkLock = redissonService.getLock(RedisKeyConstants.LOCAL_USER_CREATE_WIFI + userModel.getUserNo());

        try {
            if (!linkLock.tryLock(RedisKeyConstants.EXPIRE_BY_FIVE_SECONDS, 0, TimeUnit.MINUTES)) {
                logUtil.infoBug(LogConstant.V1, V2, v3, "用户创建wifi太快", wifiUpdateRequest, null);
                throw new ReturnException(ReturnEnum.ADD_WIFI_FAST);
            }
        } catch (InterruptedException exception) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "用户创建wifi太快异常", exception, null);
            throw new ReturnException(ReturnEnum.ADD_WIFI_FAST);
        }

        UserCreateWifiModel userCreateWifiModel = new UserCreateWifiModel();
        userCreateWifiModel.setUserNo(userModel.getUserNo());
        userCreateWifiModel.setTitle(wifiUpdateRequest.getTitle());
        userCreateWifiModel.setPassword(wifiUpdateRequest.getPassword());
        userCreateWifiModel.setName(wifiUpdateRequest.getName());
        userCreateWifiModel.setAddress(wifiUpdateRequest.getAddress());
        userCreateWifiModel.setWifiNo(String.valueOf(idUtils.getByDayId(NoNameEnum.WIFI_NO_HEADER)));
        userCreateWifiCache.addWifi(userCreateWifiModel);

        UserWifiCountModel userWifiCountModel = new UserWifiCountModel();
        userWifiCountModel.setWifiNo(userCreateWifiModel.getWifiNo());
        userWifiCountCache.addCount(userWifiCountModel);
    }


    @Override
    public UserWifiInfoResponse getWifiDetail(UserModel userModel, WifiNoRequest wifiNoRequest) {
        String v3 = "getImg";
        UserCreateWifiModel userCreateWifiModel = userCreateWifiCache.getWifiDetail(wifiNoRequest.getWifiNo());
        if (Objects.isNull(userCreateWifiModel)) {
            logUtil.infoWarn(LogConstant.V1, V2, v3, "没有查询到wifi信息", wifiNoRequest, userModel.getUserNo());
            throw new ReturnException(ReturnEnum.NO_GET_WIFI_INFO);
        }
        if (!userCreateWifiModel.getUserNo().equals(userModel.getUserNo())) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "不是本人的wifi信息", wifiNoRequest, userModel.getUserNo());
            throw new ReturnException(ReturnEnum.NO_THIS_USER_WIFI);
        }
        UserWifiInfoResponse userWifiInfoResponse = new UserWifiInfoResponse();


        UserWifiCountModel userWifiCountModel = userWifiCountCache.getInfoByWifiNo(wifiNoRequest.getWifiNo());
        if (Objects.nonNull(userWifiCountModel)) {
            userWifiInfoResponse.setLinkNum(userWifiCountModel.getLinkNum());
            userWifiInfoResponse.setLinkUser(userWifiCountModel.getLinkUser());
            userWifiInfoResponse.setProfit(userWifiCountModel.getProfit());
            userWifiInfoResponse.setScanCodeNum(userWifiCountModel.getScanCodeNum());
        }

        if (StringUtils.isNotEmpty(userCreateWifiModel.getImgUrl())){
            userCreateWifiModel.setImgUrl(YmlContent.upyunHost + userCreateWifiModel.getImgUrl());
//            userCreateWifiModel.setImgUrl("https://img1.baidu.com/it/u=1503611658,1817364920&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500");

            BeanUtils.copyProperties(userCreateWifiModel, userWifiInfoResponse);
            return userWifiInfoResponse;
        }
        String accessToken = wxApiService.getAccessToken(YmlContent.wxAPpi,YmlContent.wxSecret);
        if (Objects.isNull(accessToken)){
            logUtil.infoError(LogConstant.V1,V2,v3,"请求微信accessToken失败", accessToken, "");
            throw new ReturnException(ReturnEnum.GET_WX_ACCESS_TOKEN_ERROR);
        }
        WxCodeSceneModel wxCodeSceneModel = new WxCodeSceneModel();
        wxCodeSceneModel.setWifiNo(wifiNoRequest.getWifiNo());
        wxCodeSceneModel.setUserNo(userModel.getUserNo());
        wxCodeSceneModel.setType(WxCodeSceneMapper.wifi_type);

        wxCodeSceneModel = wxCodeSceneMapper.addScene(wxCodeSceneModel);
        byte[] qrcode = wxApiService.getUnlimitedQRCode(wxCodeSceneModel.getId(), accessToken);
        if (Objects.isNull(qrcode)){
            logUtil.infoError(LogConstant.V1,V2,v3,"生成微信二维码失败", accessToken, "");
            throw new ReturnException(ReturnEnum.GET_WX_QRCODE_ERR);
        }
        String upUrl = YmlContent.upyunFileUrl + "mm" + wxCodeSceneModel.getId() + "-" + UUID.randomUUID().toString() + ".png";
        Boolean upRes = QiniuYunUtil.writeFIleByByte(upUrl,qrcode);
        if (Boolean.FALSE.equals(upRes)){
            logUtil.infoError(LogConstant.V1,V2,v3,"又拍云上传失败", upUrl, "");
            throw new ReturnException(ReturnEnum.UP_UPYUN_FILE_ERROR);
        }
        userCreateWifiModel.setImgUrl(upUrl);
        userCreateWifiCache.updateWifiImg(userCreateWifiModel);

        BeanUtils.copyProperties(userCreateWifiModel,userWifiInfoResponse);
        userWifiInfoResponse.setImgUrl(YmlContent.upyunHost + userCreateWifiModel.getImgUrl());
        return userWifiInfoResponse;
    }


    public UserCreateWifiModel checkWifi(String userNo, String wifiNo, String v3) {
        UserCreateWifiModel userCreateWifiModel = userCreateWifiCache.getWifiInfo(wifiNo);
        if (Objects.isNull(userCreateWifiModel)) {
            logUtil.infoWarn(LogConstant.V1, V2, v3, "没有查询到wifi信息", wifiNo, userNo);
            throw new ReturnException(ReturnEnum.NO_GET_WIFI_INFO);
        }
        if (!userCreateWifiModel.getUserNo().equals(userNo)) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "不是本人的wifi信息", wifiNo, userNo);
            throw new ReturnException(ReturnEnum.NO_THIS_USER_WIFI);
        }
        return userCreateWifiModel;
    }


}
