package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VerystatusCoinOrderModel {
    Integer id;
    String orderNo;
    String userNo;
    BigDecimal oldCoin;
    BigDecimal newCoin;
    BigDecimal useCoin;
    Integer changeType;
    Integer source;
    String description;
    Integer status;
    LocalDate createDate;
    LocalDateTime createTime;
}
