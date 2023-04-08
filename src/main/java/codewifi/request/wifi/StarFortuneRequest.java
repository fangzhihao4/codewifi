package codewifi.request.wifi;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StarFortuneRequest {
    @NotBlank(message = "缺失类型")
    String time;
    @NotBlank(message = "缺失星座")
    String star;
}
