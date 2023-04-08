package codewifi.controller;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.repository.model.UserModel;
import codewifi.request.wifi.StarFortuneRequest;
import codewifi.response.wifi.StarResponse;
import codewifi.service.StarService;
import codewifi.service.UserLoginCommonService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/star")
@AllArgsConstructor
public class StarController {

    private static final LogUtil logUtil = LogUtil.getLogger(StarController.class);

    private static final String V1 = "wifi";
    private static final String V2 = "StarController";

    private final UserLoginCommonService userLoginCommonService;
    private final StarService starService;

    @RequestMapping("/fortune")
    public Response<StarResponse> finishWatch(@ProRequestBody @Valid StarFortuneRequest starFortuneRequest, @Token String token) {
        logUtil.info(V1,V2,"fortune","请求日志",starFortuneRequest,token);
        UserModel userModel = userLoginCommonService.getUserModelByToken(token);
        StarResponse starResponse = starService.getStarContent(starFortuneRequest,userModel);
        logUtil.info(V1,V2,"fortune","返回日志",starFortuneRequest,starResponse);
        return Response.data(starResponse);
    }
}
