package codewifi.controller;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.repository.model.UserModel;
import codewifi.request.wifi.UserInviteRequest;
import codewifi.response.wifi.UserInviteResponse;
import codewifi.service.UserLoginCommonService;
import codewifi.service.UserProfitService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/invite")
@AllArgsConstructor
public class UserInviteController {
    private static final LogUtil logUtil = LogUtil.getLogger(UserInviteController.class);

    private static final String V1 = "wifi";
    private static final String V2 = "UserInviteController";

    private final UserProfitService userProfitService;
    private final UserLoginCommonService userLoginCommonService;

    @RequestMapping("/list")
    public Response<UserInviteResponse> getInvite(@ProRequestBody @Valid UserInviteRequest userInviteRequest, @Token String token) {
        logUtil.info(V1,V2,"getInvite","请求日志",userInviteRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        UserInviteResponse userInvite = userProfitService.getUserInvite(userModel, userInviteRequest);
        logUtil.info(V1,V2,"getInvite","返回日志",userInviteRequest,userInvite);
        return Response.data(userInvite);
    }
}
