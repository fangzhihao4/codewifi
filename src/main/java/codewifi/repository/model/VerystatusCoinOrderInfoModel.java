package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VerystatusCoinOrderInfoModel {
    Integer id;
    String infoNo;
    String orderNo;
    String userNo;
    Integer changeType;
    Integer source;
    String title;
    String content;
    LocalDate createDate;
    LocalDate createTime;
    LocalDate updateTime;
}
