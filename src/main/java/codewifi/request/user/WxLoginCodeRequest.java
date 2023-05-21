package codewifi.request.user;

import lombok.Data;

@Data
public class WxLoginCodeRequest {
    String code;
    Integer type;
}
