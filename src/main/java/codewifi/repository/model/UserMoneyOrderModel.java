package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserMoneyOrderModel {
    Integer id;
    String userNo;
    Byte type;
    BigDecimal price;
    BigDecimal userProfit;
    String wifiNo;
    String wifiUserNo;
    LocalDate createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    Integer addTime;
    String linkId;
    String linkUserNo;
}
