package codewifi.request.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class WxUserNicknameUpRequest {
    @Size(min = 1, max = 45, message = "长度需大于1个字符小于45个字符")
    @NotNull(message = "头像信息不能为空")
    String nickname;
}
