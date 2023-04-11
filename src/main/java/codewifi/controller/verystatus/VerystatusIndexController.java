package codewifi.controller.verystatus;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.common.constant.ReturnEnum;
import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.very.VerystatusIndexRequest;
import codewifi.response.very.VerystatusIndexResponse;
import codewifi.service.UserLoginCommonService;
import codewifi.service.VerystatusIndexService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/very/index")
@AllArgsConstructor
public class VerystatusIndexController {
    private static final LogUtil logUtil = LogUtil.getLogger(VerystatusIndexController.class);

    private static final String V1 = "very";
    private static final String V2 = "VerystatusIndexController";

    private final UserLoginCommonService userLoginCommonService;
    private final VerystatusIndexService verystatusIndexService;

    @RequestMapping("/index")
    public Response<VerystatusIndexResponse> index(@ProRequestBody @Valid VerystatusIndexRequest verystatusIndexRequest, @Token String token) {
        logUtil.info(V1,V2,"index","请求日志",verystatusIndexRequest,token);
        VerystatusUserModel verystatusUserModel = userLoginCommonService.getVerystatusUserModelByToken(token);
        VerystatusIndexResponse verystatusIndexResponse = verystatusIndexService.getUserIndex(verystatusIndexRequest,verystatusUserModel);
        logUtil.info(V1,V2,"index","返回日志",verystatusIndexRequest,verystatusIndexResponse);
        return Response.data(verystatusIndexResponse);
    }
}
