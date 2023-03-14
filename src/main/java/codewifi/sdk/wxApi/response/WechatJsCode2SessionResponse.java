package codewifi.sdk.wxApi.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WechatJsCode2SessionResponse {
    private int errcode;// errcode

    private String errmsg;// errmsg

    private String openid;// 用户openid

    private String unionid;// 用户unionid

    @JsonProperty(value = "session_key")
    private String sessionKey;// sessionKey

}
