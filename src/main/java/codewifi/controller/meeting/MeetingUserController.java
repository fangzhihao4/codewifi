package codewifi.controller.meeting;

import codewifi.annotation.ProRequestBody;
import codewifi.common.Response;
import codewifi.request.meeting.MeetingUserLoginRequest;
import codewifi.response.meeting.MeetingUserLoginResponse;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/meeting")
@AllArgsConstructor
public class MeetingUserController {
    private static final LogUtil logUtil = LogUtil.getLogger(MeetingUserController.class);

    private static final String V1 = "user";
    private static final String V2 = "LoginController";

    /**
     * 微信登录
     * @param request
     * @return
     */
    @RequestMapping("/user/code")
    public Response<MeetingUserLoginResponse> wxLogin(@Valid @ProRequestBody MeetingUserLoginRequest request) {
        String v3 = "userLogin";
        logUtil.info(V1,V2,v3,"请求日志",request,null);
        logUtil.info(V1,V2,v3,"返回日志",request,null);
        return Response.data(null);
    }
}
