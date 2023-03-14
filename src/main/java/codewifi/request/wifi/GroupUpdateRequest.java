package codewifi.request.wifi;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupUpdateRequest {
    @NotBlank(message = "缺失groupNo")
    String groupNo;
    String name;
}
