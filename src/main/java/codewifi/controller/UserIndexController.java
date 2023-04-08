package codewifi.controller;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.repository.model.UserModel;
import codewifi.request.wifi.UserProfitRequest;
import codewifi.response.wifi.UserIndexResponse;
import codewifi.service.UserLoginCommonService;
import codewifi.service.UserProfitService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/index")
@AllArgsConstructor
public class UserIndexController {
    private static final LogUtil logUtil = LogUtil.getLogger(UserIndexController.class);

    private static final String V1 = "wifi";
    private static final String V2 = "UserIndexController";

    private final UserProfitService userProfitService;
    private final UserLoginCommonService userLoginCommonService;

    @RequestMapping("/index")
    public Response<UserIndexResponse> getIndex(@ProRequestBody @Valid UserProfitRequest userProfitRequest, @Token String token) {
        logUtil.info(V1,V2,"getIndex","请求日志",userProfitRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        UserIndexResponse userIndexResponse = userProfitService.getUserIndex(userModel,userProfitRequest);
        logUtil.info(V1,V2,"getIndex","返回日志",userProfitRequest,userIndexResponse);
        return Response.data(userIndexResponse);
    }
}
