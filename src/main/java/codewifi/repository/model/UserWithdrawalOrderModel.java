package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserWithdrawalOrderModel {
    Integer id;
    String userNo;
    BigDecimal money;
    Byte status;
    LocalDate createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
