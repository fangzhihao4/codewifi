package codewifi.controller.verystatus;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.very.VerystatusGoodsInfoRequest;
import codewifi.request.very.VerystatusIndexRequest;
import codewifi.request.very.VerystatusPayGoodsRequest;
import codewifi.request.very.VerystatusUserGoodsRequest;
import codewifi.response.very.VerystatusGoodsUserInfoResponse;
import codewifi.response.very.VerystatusIndexResponse;
import codewifi.service.UserLoginCommonService;
import codewifi.service.VerystatusGoodsUserService;
import codewifi.service.VerystatusIndexService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/very/goods")
@AllArgsConstructor
public class VerystatusGoodsController {
    private static final LogUtil logUtil = LogUtil.getLogger(VerystatusGoodsController.class);

    private static final String V1 = "very";
    private static final String V2 = "VerystatusGoodsController";

    private final UserLoginCommonService userLoginCommonService;
    private final VerystatusGoodsUserService verystatusGoodsUserService;

    @RequestMapping("/info")
    public Response<VerystatusGoodsUserInfoResponse> info(@ProRequestBody @Valid VerystatusPayGoodsRequest verystatusPayGoodsRequest, @Token String token) {
        logUtil.info(V1,V2,"index","请求日志",verystatusPayGoodsRequest,token);
        VerystatusUserModel verystatusUserModel = userLoginCommonService.getVerystatusUserModelByToken(token);
        VerystatusGoodsUserInfoResponse userGoods = verystatusGoodsUserService.getUserGoods(verystatusUserModel, verystatusPayGoodsRequest);
        logUtil.info(V1,V2,"index","返回日志",verystatusPayGoodsRequest,userGoods);
        return Response.data(userGoods);
    }

    @RequestMapping("/list")
    public Response<List<VerystatusGoodsUserInfoResponse>> list(@ProRequestBody @Valid VerystatusGoodsInfoRequest verystatusGoodsInfoRequest, @Token String token) {
        logUtil.info(V1,V2,"index","请求日志",verystatusGoodsInfoRequest,token);
        VerystatusUserModel verystatusUserModel = userLoginCommonService.getVerystatusUserModelByToken(token);
//        VerystatusGoodsUserInfoResponse userGoods = verystatusGoodsUserService.getUserGoods(verystatusUserModel, verystatusGoodsInfoRequest);
//        logUtil.info(V1,V2,"index","返回日志",verystatusGoodsInfoRequest,userGoods);
        return Response.data(null);
    }

    @RequestMapping("/pay")
    public Response<VerystatusGoodsUserInfoResponse> pay(@ProRequestBody @Valid VerystatusPayGoodsRequest verystatusPayGoodsRequest, @Token String token) {
        logUtil.info(V1,V2,"index","请求日志",verystatusPayGoodsRequest,token);
        VerystatusUserModel verystatusUserModel = userLoginCommonService.getVerystatusUserModelByToken(token);
        VerystatusGoodsUserInfoResponse userGoods = verystatusGoodsUserService.getUserPayContent(verystatusUserModel, verystatusPayGoodsRequest);
        logUtil.info(V1,V2,"index","返回日志",verystatusPayGoodsRequest,userGoods);
        return Response.data(userGoods);
    }
}
