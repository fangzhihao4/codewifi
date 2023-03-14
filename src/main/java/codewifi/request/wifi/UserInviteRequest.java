package codewifi.request.wifi;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UserInviteRequest {
    @Min(value = 1, message = "页面错误")
    Integer page;
}
