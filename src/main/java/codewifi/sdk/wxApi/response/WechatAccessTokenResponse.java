package codewifi.sdk.wxApi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WechatAccessTokenResponse {
    @JsonProperty(value = "access_token")
    String accessToken;

    @JsonProperty(value = "expires_in")
    Integer expiresIn;
}
