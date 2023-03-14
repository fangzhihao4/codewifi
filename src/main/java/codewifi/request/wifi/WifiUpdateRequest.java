package codewifi.request.wifi;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class WifiUpdateRequest {

    @NotBlank(message = "缺失wifiNo")
    @Size(min = 2, max = 45, message = "wifiNo长度有误")
    String wifiNo;

    @NotBlank(message = "缺失title")
    @Size(min = 2, max = 45, message = "标题名字长度有误")
    String title;

    @NotBlank(message = "缺失address")
    @Size(min = 2, max = 1000, message = "地址长度有误")
    String address;

    @NotBlank(message = "缺失name")
    @Size(min = 2, max = 45, message = "名称长度有误")
    String name;

    @NotBlank(message = "缺失password")
    @Size(min = 2, max = 45, message = "密码长度有误")
    String password;
}
