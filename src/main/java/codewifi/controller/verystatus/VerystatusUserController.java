package codewifi.controller.verystatus;

import codewifi.annotation.ProRequestBody;
import codewifi.common.Response;
import codewifi.controller.user.LoginController;
import codewifi.request.user.WxLoginCodeRequest;
import codewifi.response.user.UserLoginResponse;
import codewifi.response.user.VerystatusUserLoginResponse;
import codewifi.service.LoginService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/very/user")
@AllArgsConstructor
public class VerystatusUserController {
    private static final LogUtil logUtil = LogUtil.getLogger(VerystatusUserController.class);

    private static final String V1 = "very";
    private static final String V2 = "VerystatusUserController";
    private final LoginService loginService;

    @RequestMapping("/code")
    public Response<VerystatusUserLoginResponse> wxLogin(@Valid @ProRequestBody WxLoginCodeRequest request) {
        String v3 = "userLogin";
        logUtil.info(V1,V2,v3,"请求日志",request,null);
        VerystatusUserLoginResponse verystatusUserLoginResponse = loginService.wxVerystatusLogin(request.getCode());
        logUtil.info(V1,V2,v3,"返回日志",request,verystatusUserLoginResponse);
        return Response.data(verystatusUserLoginResponse);
    }
}
