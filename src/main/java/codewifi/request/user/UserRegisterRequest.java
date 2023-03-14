package codewifi.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UserRegisterRequest {
    @NotBlank(message = "缺失用户名")
    String username;

    @NotBlank(message = "缺失密码")
    String password;

    @NotBlank(message = "缺失重复密码")
    String forPassword;
}
