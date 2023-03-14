package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserWifiCountModel {
    String wifiNo;
    BigDecimal profit;
    Integer scanCodeNum;
    Integer linkNum;
    Integer linkUser;
}
