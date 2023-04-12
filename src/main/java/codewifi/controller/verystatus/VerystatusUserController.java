package codewifi.controller.verystatus;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.common.constant.ReturnEnum;
import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.user.WxLoginCodeRequest;
import codewifi.request.user.WxUserHeadUpRequest;
import codewifi.request.user.WxUserNicknameUpRequest;
import codewifi.response.user.VerystatusUserLoginResponse;
import codewifi.service.LoginService;
import codewifi.service.UserLoginCommonService;
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
    private final UserLoginCommonService userLoginCommonService;

    @RequestMapping("/code")
    public Response<VerystatusUserLoginResponse> wxLogin(@Valid @ProRequestBody WxLoginCodeRequest request) {
        String v3 = "userLogin";
        logUtil.info(V1, V2, v3, "请求日志", request, null);
        VerystatusUserLoginResponse verystatusUserLoginResponse = loginService.wxVerystatusLogin(request.getCode());
        logUtil.info(V1, V2, v3, "返回日志", request, verystatusUserLoginResponse);
        return Response.data(verystatusUserLoginResponse);
    }

    @RequestMapping("/head")
    public Response<ReturnEnum> upHeadImg(@Valid @ProRequestBody WxUserHeadUpRequest request, @Token String token) {
        String v3 = "upHeadImg";
        logUtil.info(V1, V2, v3, "请求日志", request, null);
        VerystatusUserModel verystatusUserModel = userLoginCommonService.getVerystatusUserModelByToken(token);
        loginService.wxVerystatusUpHead(token, verystatusUserModel, request);
        logUtil.info(V1, V2, v3, "返回日志", request, request);
        return Response.data(ReturnEnum.SUCCESS);
    }

    @RequestMapping("/nickname")
    public Response<ReturnEnum> upNickname(@Valid @ProRequestBody WxUserNicknameUpRequest request, @Token String token) {
        String v3 = "upNickname";
        logUtil.info(V1, V2, v3, "请求日志", request, null);
        VerystatusUserModel verystatusUserModel = userLoginCommonService.getVerystatusUserModelByToken(token);
        loginService.wxVerystatusUpNickname(token, verystatusUserModel, request);
        logUtil.info(V1, V2, v3, "返回日志", request, request);
        return Response.data(ReturnEnum.SUCCESS);
    }
}
