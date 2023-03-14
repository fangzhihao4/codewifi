package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserInviteProfitModel {
    Integer id;
    String parentUserNo;
    String registerUserNo;
    String registerName;
    Byte type;
    BigDecimal wifiProfitPrice;
    BigDecimal inviteProfitPrice;
    LocalDateTime createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
