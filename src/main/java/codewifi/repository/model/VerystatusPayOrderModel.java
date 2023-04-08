package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VerystatusPayOrderModel {
    Integer id;
    String userNo;
    String orderNo;
    String wxOrderNo;
    BigDecimal price;
    BigDecimal coin;
    String payNo;
    LocalDateTime payOverTime;
    Integer status;
    LocalDate createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
