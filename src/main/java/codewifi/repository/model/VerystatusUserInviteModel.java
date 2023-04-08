package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VerystatusUserInviteModel {
    Integer id;
    String userNo;
    String parentUserNo;
    Integer type;
    Integer isReward;
    BigDecimal coin;
    LocalDate createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
