package codewifi.controller;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.common.constant.ReturnEnum;
import codewifi.repository.model.UserModel;
import codewifi.request.wifi.CodeSceneRequest;
import codewifi.request.wifi.LinkTicketRequest;
import codewifi.request.wifi.LinkWifiRequest;
import codewifi.request.wifi.WifiNoRequest;
import codewifi.response.wifi.CodeSceneResponse;
import codewifi.response.wifi.LinkTicketResponse;
import codewifi.response.wifi.LinkWifiInfoResponse;
import codewifi.service.LinkWifiService;
import codewifi.service.UserLoginCommonService;
import codewifi.service.UserProfitService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/link")
@AllArgsConstructor
public class LinkWifiController {

    private static final LogUtil logUtil = LogUtil.getLogger(LinkWifiController.class);

    private static final String V1 = "wifi";
    private static final String V2 = "LinkWifiController";

    private final LinkWifiService linkWifiService;
    private final UserLoginCommonService userLoginCommonService;

    @RequestMapping("/link")
    public Response<ReturnEnum> finishWatch(@ProRequestBody @Valid LinkWifiRequest linkWifiRequest, @Token String token) {
        logUtil.info(V1,V2,"link","请求日志",linkWifiRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        linkWifiService.linkWIfiInfo(userModel,linkWifiRequest);
        logUtil.info(V1,V2,"link","返回日志",linkWifiRequest,null);
        return Response.data(ReturnEnum.SUCCESS);
    }


    @RequestMapping("/scene")
    public Response<CodeSceneResponse> codeScene(@ProRequestBody @Valid CodeSceneRequest codeSceneRequest, @Token String token) {
        logUtil.info(V1,V2,"codeScene","请求日志",codeSceneRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        CodeSceneResponse codeSceneResponse = linkWifiService.getInfoByCodeScene(codeSceneRequest, userModel);
        logUtil.info(V1,V2,"codeScene","返回日志",codeSceneRequest,codeSceneResponse);
        return Response.data(codeSceneResponse);
    }



}
