package codewifi.response.wifi;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserWifiCountResponse {
    Integer linkNum;
    Integer freeTimes;
    BigDecimal profit;
}
