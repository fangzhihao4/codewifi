package codewifi.request.wifi;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WifiNoRequest {
    @NotBlank(message = "缺少wifiNo")
    String wifiNo;
}
