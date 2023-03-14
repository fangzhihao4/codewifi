package codewifi.response.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoResponse {

    Long userNo;

    String username;

    String nickname;

    String headerImg;

    String sex;

    String phone;

    String email;


}
