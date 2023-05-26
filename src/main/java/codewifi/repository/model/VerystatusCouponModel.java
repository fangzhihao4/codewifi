package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerystatusCouponModel {
    Integer id;
    Integer type;
    String appid;
    String page;
    String cover;
    String title;
    String subTitle;
    String desc;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Integer sort;
    Integer status;
}
