package codewifi.service.impl;


import codewifi.annotation.exception.ReturnException;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.enums.LoginTypeEnums;
import codewifi.repository.cache.UserLoginCache;
import codewifi.repository.model.UserModel;
import codewifi.repository.model.VerystatusUserModel;
import codewifi.service.UserLoginCommonService;
import codewifi.utils.IdUtils;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserLoginCommonServiceImpl implements UserLoginCommonService {
    private static final LogUtil logUtil = LogUtil.getLogger(UserLoginCommonServiceImpl.class);

    private static final String V1 = "user";
    private static final String V2 = "UserLoginCommonServiceImpl";
    private final UserLoginCache userLoginCache;
    private final IdUtils idUtils;

    @Override
    public String setTokenByUserInfo(LoginTypeEnums loginTypeEnums, UserModel userModel) {
        String token = idUtils.getToken(userModel.getUserNo());
        userLoginCache.setRedisUserToken(token, userModel, loginTypeEnums.getOutTime());
        return token;
    }

    @Override
    public UserModel getUserModelByToken(String token) {
        UserModel userModel = userLoginCache.getUserByToken(token);
        if (Objects.isNull(userModel)) {
            logUtil.info("登录token过期", token);
            throw new ReturnException(ReturnEnum.TOKEN_ERROR);
        }
        return userModel;
    }

    @Override
    public String setVerystatusTokenByUserInfo(LoginTypeEnums loginTypeEnums, VerystatusUserModel verystatusUserModel) {
        String token = idUtils.getToken(verystatusUserModel.getUserNo());
        userLoginCache.setVerystatusRedisUserToken(token, verystatusUserModel, loginTypeEnums.getOutTime());
        return token;
    }

    @Override
    public VerystatusUserModel getVerystatusUserModelByToken(String token) {
        VerystatusUserModel verystatusUserModel = userLoginCache.getVerystatusUserByToken(token);
        if (Objects.isNull(verystatusUserModel)) {
            logUtil.info("登录token过期", token);
            throw new ReturnException(ReturnEnum.TOKEN_ERROR);
        }
        return verystatusUserModel;
    }
}
