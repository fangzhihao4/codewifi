package codewifi.request.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class WxUserHeadUpRequest {
    @NotNull(message = "头像类型不能为空")
    Byte headType;
    @Size(min = 1, max = 1000, message = "长度需大于1个字符小于1000个字符")
    @NotNull(message = "头像信息不能为空")
    String pageUrl;
}
