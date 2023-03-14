package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserLinkOrderModel {
    Integer id;
    String linkNo;
    String userNo;
    Byte type;
    String wifiNo;
    String wifiName;
    String wifiAddress;
    LocalDate createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
