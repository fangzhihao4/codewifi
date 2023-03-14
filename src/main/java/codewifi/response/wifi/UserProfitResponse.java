package codewifi.response.wifi;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserProfitResponse {
    BigDecimal yesterdayMoney;
    BigDecimal accountMoney;
    BigDecimal withdrawalMoney;
}
