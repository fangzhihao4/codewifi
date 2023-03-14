package codewifi.repository.cache;


import cn.hutool.core.codec.Base64;
import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.UserMapper;
import codewifi.repository.model.UserModel;
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
    private static final String HEX_STRING = "0123456789abcdef";
    private static final char[] HEX_CHARS = HEX_STRING.toCharArray();


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



    public static void main(String[] args) {
//        String username = "dzymt";
        String username = "123";

        String password = "dzymt@2022";
//        String password = "testsign";
        String method = "GetTicketTypes";


        TreeMap<String,Object> bodyMap = new TreeMap<>();
//        bodyMap.put("PageIndex",1);
//        bodyMap.put("PageSize",20);
//        bodyMap.put("TicketTypeId","14242c56-d538-48fe-8bb6-3777d897a6b3");

        String requestBodyString = JSON.toJSONString(bodyMap);

//        String requestBody = "eyJQYWdlSW5kZXgiOjEsIlBhZ2VTaXplIjoyMCwiVGlja2V0VHlwZUlkIjoiMTQyNDJjNTYtZDUzOC00OGZlLThiYjYtMzc3N2Q4OTdhNmIzIn0=";
        String requestBody = Base64.encode(requestBodyString.getBytes(StandardCharsets.UTF_8));
        String signBefore = username + method + requestBody + password;

//        String signBefore = "123GetTicketTypeseyJQYWdlSW5kZXgiOjEsIlBhZ2VTaXplIjoyMCwiVGlja2V0VHlwZUlkIjoiMTQyNDJjNTYtZDUzOC00OGZlLThiYjYtMzc3N2Q4OTdhNmIzIn0=testsign";
        String md5 = md5(signBefore);

        System.out.println(requestBody);
        System.out.println(md5);
    }

    public static String md5(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(data.getBytes(StandardCharsets.UTF_8));
        return toHexString(md.digest());
    }
    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte x : b) {
            int hi = (x & 0xf0) >> 4;
            int lo = x & 0x0f;
            sb.append(HEX_CHARS[hi]);
            sb.append(HEX_CHARS[lo]);
        }
        return sb.toString().trim();
    }
}
