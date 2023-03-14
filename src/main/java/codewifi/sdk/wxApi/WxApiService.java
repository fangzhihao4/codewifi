package codewifi.sdk.wxApi;

import codewifi.sdk.wxApi.response.WechatJsCode2SessionResponse;
import org.springframework.stereotype.Service;

@Service
public interface WxApiService {
    WechatJsCode2SessionResponse snsJsCode2Session(String jsCode, String appId, String appSecret);

    String getAccessToken(String appId, String appSecret);

    byte[] getUnlimitedQRCode(Integer scene, String accessToken);
}
