package codewifi.request.wifi;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LinkWifiRequest {
    @NotBlank(message = "缺失watchType")
    Byte type; //1完成 2未完成
    @NotBlank(message = "缺少linkTime")
    Long linkTime;
    @NotBlank(message = "缺失wifiNo")
    String wifiNo;
    @NotBlank(message = "缺失linkNo")
    String linkNo;
}
