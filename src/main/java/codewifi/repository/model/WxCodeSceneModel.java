package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WxCodeSceneModel {
    Integer id;

    Integer type;

    String userNo;

    String wifiNo;

    LocalDate createDate;

    LocalDateTime createTime;

    LocalDateTime updateTime;

    String imgBase;
}
