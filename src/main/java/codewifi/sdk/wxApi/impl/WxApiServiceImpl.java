package codewifi.sdk.wxApi.impl;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.sdk.wxApi.WxApiService;
import codewifi.sdk.wxApi.response.WechatAccessTokenResponse;
import codewifi.sdk.wxApi.response.WechatJsCode2SessionResponse;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import codewifi.utils.RestTemplateUtil;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class WxApiServiceImpl implements WxApiService {
    private static final LogUtil logUtil = LogUtil.getLogger(WxApiServiceImpl.class);

    private static final String V1 = "wx";
    private static final String V2 = "RestTemplateUtil";

    private static final String wxHost = "https://api.weixin.qq.com";

    private final RestTemplateUtil restTemplateUtil;
    private final JsonUtil jsonUtil;
    private final RedissonService redissonService;

    @Override
    public WechatJsCode2SessionResponse snsJsCode2Session(String jsCode, String appId, String appSecret) {
        String v3 = "snsJsCode2Session";
        String url = wxHost + "/sns/jscode2session" + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + jsCode + "&grant_type=authorization_code";

        String response;
        try{
            HttpHeaders headers = new HttpHeaders();
            response =  restTemplateUtil.get(url,
                    null,
                    headers,
                    new ParameterizedTypeReference<String>() {
                    });
        }catch (Exception e){
            logUtil.infoError(V1,V2,v3,"请求wx的jscode2session接口异常",url, e);
            return null;
        }
        WechatJsCode2SessionResponse wechatJsCode2SessionResponse;
        try{
            wechatJsCode2SessionResponse = jsonUtil.fromJsonString(response, WechatJsCode2SessionResponse.class );
        }catch (Exception e){
            logUtil.infoError(V1,V2,v3,"请求wx的jscode2session数据返回异常",e, response);
            return null;
        }
        if (Objects.isNull(wechatJsCode2SessionResponse)){
            logUtil.infoError(V1,V2,v3,"请求wx的jscode2session数据为空",url, response);
            return null;
        }
        if (wechatJsCode2SessionResponse.getErrcode() != 0){
            logUtil.infoError(V1,V2,v3,"请求wx的jscode2session数据报错",url, response);
            return null;
        }

        logUtil.info(V1,V2,v3,"请求wx的jscode2session数据成功",url, wechatJsCode2SessionResponse);
        return wechatJsCode2SessionResponse;
    }

    @Override
    public String getAccessToken(String appId, String appSecret) {
        String v3 = "getAccessToken";
        RBucket<String> bucket = redissonService.getBucket(RedisKeyConstants.WX_ACCESS_TOKEN, String.class);
        String accessToken = bucket.get();
        if (Objects.nonNull(accessToken)){
            return accessToken;
        }
        String url = wxHost + "/cgi-bin/token" + "?appid=" + appId + "&secret=" + appSecret  + "&grant_type=client_credential";
        String response;
        try{
            HttpHeaders headers = new HttpHeaders();
            response =  restTemplateUtil.get(url,
                    null,
                    headers,
                    new ParameterizedTypeReference<String>() {
                    });
        }catch (Exception e){
            logUtil.infoError(V1,V2,v3,"请求wx的access_token接口异常",url, e);
            return null;
        }
        WechatAccessTokenResponse wechatAccessTokenResponse;
        try {
            wechatAccessTokenResponse = jsonUtil.fromJsonString(response, WechatAccessTokenResponse.class);
        }catch (Exception e){
            logUtil.infoError(V1,V2,v3,"请求wx的access_token数据转json异常",e, response);
            return null;
        }
        if (Objects.isNull(wechatAccessTokenResponse)){
            logUtil.infoError(V1,V2,v3,"请求wx的access_token数据为空",url, response);
            return null;
        }
        if (Objects.isNull(wechatAccessTokenResponse.getAccessToken()) || Objects.isNull(wechatAccessTokenResponse.getExpiresIn())){
            logUtil.infoError(V1,V2,v3,"请求wx的access_token数据有错误",url, response);
            return null;
        }
        bucket.set(wechatAccessTokenResponse.getAccessToken(),wechatAccessTokenResponse.getExpiresIn(), TimeUnit.SECONDS);
        return wechatAccessTokenResponse.getAccessToken();
    }

    @Override
    public byte[] getUnlimitedQRCode(Integer scene, String accessToken) {
        String v3 = "snsJsCode2Session";
        String url = wxHost + "/wxa/getwxacodeunlimit?access_token=" + accessToken;

        Map<String,Object> map = new HashMap<>();
        map.put("scene", String.valueOf(scene));
        map.put("check_path", false);
        map.put("env_version","release");
        map.put("page","pages/link/link/index");

        byte[] response;
        try{
            HttpHeaders headers = new HttpHeaders();
            response =  restTemplateUtil.post(url,
                    jsonUtil.writeValueAsString(map),
                    headers,
                    new ParameterizedTypeReference<byte[]>() {
                    });
        }catch (Exception e){
            logUtil.infoError(V1,V2,v3,"请求wx的jscode2session接口异常",url, e);
            return null;
        }
        return response;
    }

}
