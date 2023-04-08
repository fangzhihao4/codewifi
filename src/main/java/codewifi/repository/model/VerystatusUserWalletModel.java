package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerystatusUserWalletModel {
    Integer id;
    String userNo;
    BigDecimal coin;
}
