package codewifi.repository.cache;


import cn.hutool.core.codec.Base64;
import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.UserMapper;
import codewifi.repository.mapper.VerystatusUserMapper;
import codewifi.repository.model.UserModel;
import codewifi.repository.model.VerystatusUserModel;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.jooq.generated.tables.User;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

@Service
@AllArgsConstructor
public class UserLoginCache {
    public final RedissonService redissonService;
    private final UserMapper userMapper;
    private final VerystatusUserMapper verystatusUserMapper;


    public void setRedisUserToken(String token, UserModel userModel, Integer expireTime) {
        RBucket<UserModel> bucket = redissonService.getBucket(RedisKeyConstants.USER_TOKEN + token, UserModel.class);
        bucket.set(userModel,expireTime, TimeUnit.SECONDS );
    }

    public UserModel getUserByToken(String token){
        RBucket<UserModel> bucket = redissonService.getBucket(RedisKeyConstants.USER_TOKEN + token, UserModel.class);
        return bucket.get();
    }

    public UserModel getUserByOpenid(String openid){
        return userMapper.getByOpenid(openid);
    }

    public UserModel addUserByWx(UserModel userModel){
        return userMapper.addUser(userModel);
    }







    public void setVerystatusRedisUserToken(String token, VerystatusUserModel verystatusUserModel, Integer expireTime) {
        RBucket<VerystatusUserModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_USER_TOKEN + token, VerystatusUserModel.class);
        bucket.set(verystatusUserModel,expireTime, TimeUnit.SECONDS );
    }

    public VerystatusUserModel getVerystatusUserByToken(String token){
        RBucket<VerystatusUserModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_USER_TOKEN + token, VerystatusUserModel.class);
        return bucket.get();
    }

    public VerystatusUserModel getVerystatusUserByOpenId(String openid){
        return verystatusUserMapper.getByOpenid(openid);
    }

    public VerystatusUserModel addVerystatusUserByWx(VerystatusUserModel verystatusUserModel){
        return verystatusUserMapper.addUser(verystatusUserModel);
    }
}
