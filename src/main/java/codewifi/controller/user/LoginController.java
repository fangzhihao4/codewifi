package codewifi.controller.user;

import codewifi.annotation.ProRequestBody;
import codewifi.common.Response;
import codewifi.request.user.UserLoginRequest;
import codewifi.request.user.WxLoginCodeRequest;
import codewifi.response.user.UserLoginResponse;
import codewifi.service.LoginService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/login")
@AllArgsConstructor
public class LoginController {

    private static final LogUtil logUtil = LogUtil.getLogger(LoginController.class);

    private static final String V1 = "user";
    private static final String V2 = "LoginController";
    private final LoginService loginService;

    @RequestMapping("/pwd")
    public Response<UserLoginResponse> userLogin(@Valid @ProRequestBody UserLoginRequest request) {
        String v3 = "userLogin";
        logUtil.info(V1,V2,v3,"请求日志",request,null);
        UserLoginResponse userLoginResponse = loginService.pwdLogin(request);
        logUtil.info(V1,V2,v3,"返回日志",request,userLoginResponse);

        return Response.data(userLoginResponse);
    }

    @RequestMapping("/code")
    public Response<UserLoginResponse> wxLogin(@Valid @ProRequestBody WxLoginCodeRequest request) {
        String v3 = "userLogin";
        logUtil.info(V1,V2,v3,"请求日志",request,null);
        UserLoginResponse userLoginResponse = loginService.wxLogin(request.getCode());
        logUtil.info(V1,V2,v3,"返回日志",request,userLoginResponse);

        return Response.data(userLoginResponse);
    }




}
