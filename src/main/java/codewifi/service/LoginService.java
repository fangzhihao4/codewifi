package codewifi.service;

import codewifi.request.user.UserLoginRequest;
import codewifi.response.user.UserLoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    UserLoginResponse pwdLogin(UserLoginRequest request);

    UserLoginResponse wxLogin(String code);
}
