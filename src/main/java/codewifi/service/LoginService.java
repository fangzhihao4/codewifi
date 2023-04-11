package codewifi.service;

import codewifi.request.user.UserLoginRequest;
import codewifi.response.user.UserLoginResponse;
import codewifi.response.user.VerystatusUserLoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    UserLoginResponse pwdLogin(UserLoginRequest request);

    UserLoginResponse wxLogin(String code);

    VerystatusUserLoginResponse wxVerystatusLogin(String code);
}
