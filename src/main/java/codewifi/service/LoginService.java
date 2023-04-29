package codewifi.service;

import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.user.UserLoginRequest;
import codewifi.request.user.WxUserHeadUpRequest;
import codewifi.request.user.WxUserNicknameUpRequest;
import codewifi.request.very.VerystatusAdviseRequest;
import codewifi.response.user.UserLoginResponse;
import codewifi.response.user.VerystatusUserLoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    UserLoginResponse pwdLogin(UserLoginRequest request);

    UserLoginResponse wxLogin(String code);

    VerystatusUserLoginResponse wxVerystatusLogin(String code);

    void wxVerystatusUpHead(String token, VerystatusUserModel verystatusUserModel,WxUserHeadUpRequest wxUserHeadUpRequest);

    void wxVerystatusUpNickname(String token,VerystatusUserModel verystatusUserModel,WxUserNicknameUpRequest wxUserNicknameUpRequest);

    void addAdvise(VerystatusUserModel verystatusUserModel, VerystatusAdviseRequest request);
}
