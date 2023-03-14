package codewifi.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserUpNicknameRequest {

    @Size(min = 3, max = 40, message = "长度需大于3个字符小于40个字符")
    @NotNull(message = "昵称不能为空")
    String nickname;
}
