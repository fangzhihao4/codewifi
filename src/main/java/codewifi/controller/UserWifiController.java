package codewifi.controller;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.common.constant.ReturnEnum;
import codewifi.repository.model.UserModel;
import codewifi.request.wifi.WifiNoRequest;
import codewifi.request.wifi.WifiUpdateRequest;
import codewifi.response.wifi.UserWifiInfoResponse;
import codewifi.service.UserLoginCommonService;
import codewifi.service.WifiInfoService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/wifi")
@AllArgsConstructor
public class UserWifiController {
    private static final LogUtil logUtil = LogUtil.getLogger(UserWifiController.class);

    private static final String V1 = "wifi";
    private static final String V2 = "UserIndexController";

    private final WifiInfoService wifiInfoService;
    private final UserLoginCommonService userLoginCommonService;

    @RequestMapping("/add")
    public Response<ReturnEnum> addWifi(@ProRequestBody @Valid WifiUpdateRequest wifiUpdateRequest, @Token String token) {
        logUtil.info(V1,V2,"addWifi","请求日志",wifiUpdateRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        wifiInfoService.addWifi(userModel,wifiUpdateRequest);
        logUtil.info(V1,V2,"addWifi","返回日志",wifiUpdateRequest,token);
        return Response.data(ReturnEnum.SUCCESS);
    }

    @RequestMapping("/update")
    public Response<ReturnEnum> updateWifi(@ProRequestBody @Valid WifiUpdateRequest wifiUpdateRequest, @Token String token) {
        logUtil.info(V1,V2,"updateWifi","请求日志",wifiUpdateRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        wifiInfoService.updateWifi(userModel,wifiUpdateRequest);
        logUtil.info(V1,V2,"updateWifi","返回日志",wifiUpdateRequest,token);
        return Response.data(ReturnEnum.SUCCESS);
    }

    @RequestMapping("/del")
    public Response<ReturnEnum> delWifi(@ProRequestBody @Valid WifiNoRequest wifiNoRequest, @Token String token) {
        logUtil.info(V1,V2,"delWifi","请求日志",wifiNoRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        wifiInfoService.delWifi(userModel,wifiNoRequest);
        logUtil.info(V1,V2,"delWifi","返回日志",wifiNoRequest,token);
        return Response.data(ReturnEnum.SUCCESS);
    }



    @RequestMapping("/detail")
    public Response<UserWifiInfoResponse> detail(@ProRequestBody WifiNoRequest wifiNoRequest, @Token String token) {
        logUtil.info(V1,V2,"info","请求日志",wifiNoRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        UserWifiInfoResponse userWifiInfoResponse = wifiInfoService.getWifiDetail(userModel, wifiNoRequest);
        logUtil.info(V1,V2,"info","返回日志",wifiNoRequest,token);
        return Response.data(userWifiInfoResponse);
    }
}
