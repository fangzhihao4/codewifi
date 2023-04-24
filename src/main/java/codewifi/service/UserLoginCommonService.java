package codewifi.service;
import codewifi.common.constant.enums.LoginTypeEnums;
import codewifi.repository.model.UserModel;
import codewifi.repository.model.VerystatusUserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserLoginCommonService {
    String setTokenByUserInfo(LoginTypeEnums loginTypeEnums, UserModel userModel);

    UserModel getUserModelByToken(String token);

    String setVerystatusTokenByUserInfo(LoginTypeEnums loginTypeEnums, VerystatusUserModel verystatusUserModel);

    VerystatusUserModel getVerystatusUserModelByToken(String token);

}
