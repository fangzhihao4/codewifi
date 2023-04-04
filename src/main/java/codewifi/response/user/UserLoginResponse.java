package codewifi.response.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginResponse {
    String userNo;
    String token;
    String nickname;
    String headerImg;
}
