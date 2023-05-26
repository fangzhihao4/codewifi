package codewifi.controller.verystatus;

import codewifi.annotation.ProRequestBody;
import codewifi.annotation.Token;
import codewifi.common.Response;
import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.very.VerystatusCouponListRequest;
import codewifi.response.very.VerystatusCouponListResponse;
import codewifi.service.UserLoginCommonService;
import codewifi.service.VerystatusCouponService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/very/coupon")
@AllArgsConstructor
public class VerystatusCouponController {

    private static final LogUtil logUtil = LogUtil.getLogger(VerystatusCouponController.class);

    private static final String V1 = "very";
    private static final String V2 = "VerystatusCouponController";

    private final UserLoginCommonService userLoginCommonService;
    private final VerystatusCouponService verystatusCouponService;

    @RequestMapping("/list")
    public Response<VerystatusCouponListResponse> info(@ProRequestBody @Valid VerystatusCouponListRequest request, @Token String token) {
        logUtil.info(V1,V2,"index","请求日志",request,token);
        VerystatusUserModel verystatusUserModel = userLoginCommonService.getVerystatusUserModelByToken(token);
        VerystatusCouponListResponse response = verystatusCouponService.getList(request);
        logUtil.info(V1,V2,"index","返回日志",verystatusUserModel,response);
        return Response.data(response);
    }

}
