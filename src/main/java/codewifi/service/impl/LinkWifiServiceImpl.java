package codewifi.service.impl;

import codewifi.annotation.exception.ReturnException;
import codewifi.common.constant.LogConstant;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.enums.LinkOrderTypeEnum;
import codewifi.common.constant.enums.NoNameEnum;
import codewifi.repository.cache.UserCreateWifiCache;
import codewifi.repository.cache.UserLinkOrderCache;
import codewifi.repository.cache.UserWifiCountCache;
import codewifi.repository.cache.WxCodeSceneCache;
import codewifi.repository.model.UserCreateWifiModel;
import codewifi.repository.model.UserLinkOrderModel;
import codewifi.repository.model.UserModel;
import codewifi.repository.model.WxCodeSceneModel;
import codewifi.request.wifi.CodeSceneRequest;
import codewifi.request.wifi.LinkWifiRequest;
import codewifi.response.wifi.CodeSceneResponse;
import codewifi.service.LinkWifiService;
import codewifi.utils.IdUtils;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class LinkWifiServiceImpl implements LinkWifiService {
    private static final LogUtil logUtil = LogUtil.getLogger(LinkWifiServiceImpl.class);
    private static final String V2 = "LinkWifiServiceImpl";

    private final UserCreateWifiCache userCreateWifiCache;
    private final WxCodeSceneCache wxCodeSceneCache;
    private final IdUtils idUtils;
    private final UserLinkOrderCache userLinkOrderCache;
    private final UserWifiCountCache userWifiCountCache;


    @Override
    public void linkWIfiInfo(UserModel userModel, LinkWifiRequest linkWifiRequest) {
        String v3 = "linkWifi";
        long subTime = System.currentTimeMillis() - linkWifiRequest.getLinkTime();
        if ((subTime < 0) || (subTime > 30000)) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "完成视频通知", linkWifiRequest, null);
            return;
        }
        UserLinkOrderModel userLinkOrderModel = userLinkOrderCache.getCacheLink(linkWifiRequest.getLinkNo());
        if (Objects.isNull(userLinkOrderModel)) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "扫码过期了或没有扫码", linkWifiRequest, null);
            return;
        }
        if (LinkOrderTypeEnum.WATCH_VIDEO_FINISH.getType().equals(linkWifiRequest.getType())
                &&  LinkOrderTypeEnum.WATCH_VIDEO_FINISH.getType().equals(userLinkOrderModel.getType())
        ) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "链接wifi状态值不对", linkWifiRequest, null);
            return;
        }
        if (LinkOrderTypeEnum.onLinkList().contains(linkWifiRequest.getType()) && !userLinkOrderModel.getType().equals(LinkOrderTypeEnum.SCAN_CODE.getType())) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "不是扫码中上报了视频关闭或视频完成", linkWifiRequest, null);
            return;
        }
        userLinkOrderModel.setType(linkWifiRequest.getType());
        //增加记录
        userLinkOrderCache.addLinkOrder(userLinkOrderModel);

        //链接成功
        if (LinkOrderTypeEnum.LINK_WIFI_FINISH.getType().equals(linkWifiRequest.getType())) {
            userWifiCountCache.addLinkNum(userLinkOrderModel.getWifiNo()); //增加链接成功次数

            //用户之前没有链接过 增加连接人数
            UserLinkOrderModel userLinkOrderModelSuccess = userLinkOrderCache.getUserIsSuccessLinkWifi(userModel.getUserNo(), userLinkOrderModel.getWifiNo());
            if (Objects.isNull(userLinkOrderModelSuccess)) {
                userWifiCountCache.addLinkUserNum(userLinkOrderModel.getWifiNo());
            }
        }

        //看完视频给收益

    }

    @Override
    public CodeSceneResponse getInfoByCodeScene(CodeSceneRequest codeSceneRequest, UserModel userModel) {
        String v3 = "getInfoByCodeScene";
        WxCodeSceneModel wxCodeSceneModel = wxCodeSceneCache.getByScene(codeSceneRequest.getSceneId());
        if (Objects.isNull(wxCodeSceneModel)) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "没有查询到这个分享id", codeSceneRequest, userModel.getUserNo());
            throw new ReturnException(ReturnEnum.NO_FUND_SCENE);
        }
        UserCreateWifiModel userCreateWifiModel = userCreateWifiCache.getWifiInfo(wxCodeSceneModel.getWifiNo());
        if (Objects.isNull(userCreateWifiModel)) {
            logUtil.infoBug(LogConstant.V1, V2, v3, "没有这个wifi信息", wxCodeSceneModel, codeSceneRequest);
            throw new ReturnException(ReturnEnum.NO_FUND_WIFI_INFO);
        }
        String linkNo = String.valueOf(idUtils.getByDayId(NoNameEnum.LINK_NO_HEADER));
        CodeSceneResponse codeSceneResponse = new CodeSceneResponse();
        codeSceneResponse.setType(1);
        codeSceneResponse.setLinkNo(linkNo);
        BeanUtils.copyProperties(userCreateWifiModel, codeSceneResponse, CodeSceneResponse.class);

        UserLinkOrderModel userLinkOrderModel = new UserLinkOrderModel();
        userLinkOrderModel.setLinkNo(linkNo);
        userLinkOrderModel.setUserNo(userModel.getUserNo());
        userLinkOrderModel.setType(LinkOrderTypeEnum.SCAN_CODE.getType());
        userLinkOrderModel.setWifiNo(userCreateWifiModel.getWifiNo());
        userLinkOrderModel.setWifiName(userCreateWifiModel.getName());
        userLinkOrderModel.setWifiAddress(userCreateWifiModel.getAddress());

        //增加扫码记录
        userLinkOrderCache.addLinkOrder(userLinkOrderModel);

        //增加wifi扫码次数
        userWifiCountCache.addScanCodeNum(userCreateWifiModel.getWifiNo());
        return codeSceneResponse;
    }

}
