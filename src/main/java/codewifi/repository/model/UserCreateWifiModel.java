package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserCreateWifiModel {
    Integer id;
    String wifiNo;
    String userNo;
    String title;
    String address;
    String name;
    String password;
    LocalDate createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    Integer freeTimes;
    Byte status;
    String imgUrl;
}
