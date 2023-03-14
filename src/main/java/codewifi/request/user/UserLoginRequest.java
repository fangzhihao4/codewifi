package codewifi.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UserLoginRequest {

    @NotBlank(message = "缺失userNo")
    private String userNo;
}
