package codewifi.repository.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class UserDetailModel {
    Integer id;
    Long userNo;
    String registerIp;
    String loginLastIp;
    LocalDateTime loginLastTime;
    LocalDateTime loginEndTime;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
