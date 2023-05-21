package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDate;

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
    LocalDate startDate;
    LocalDate endDate;
    Integer sort;
    Integer status;
}
