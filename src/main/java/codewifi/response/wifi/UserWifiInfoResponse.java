package codewifi.response.wifi;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserWifiInfoResponse {
    String wifiNo;
    String title;
    String address;
    String name;
    String password;
    BigDecimal profit;
    Integer linkNum;
    Integer linkUser;
    Integer scanCodeNum;
    Integer freeNum;
    String imgUrl;
}
