package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserProfitModel {
    Integer id;
    String userNo;
    BigDecimal yesterdayMoney;
    BigDecimal allMoney;
    BigDecimal accountMoney;
    BigDecimal withdrawalMoney;
    Integer withdrawalTimes;
    LocalDate createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
