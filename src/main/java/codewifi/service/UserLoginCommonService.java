package codewifi.service;
import codewifi.common.constant.enums.LoginTypeEnums;
import codewifi.repository.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserLoginCommonService {
    String setTokenByUserInfo(LoginTypeEnums loginTypeEnums, UserModel userModel);

    UserModel getUserModelByToken(String token);
}
