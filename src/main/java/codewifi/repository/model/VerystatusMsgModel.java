package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VerystatusMsgModel {
    String userNo;
    String title;
    String content;
    String contentImg;
    Integer pageType;
    String pageUrl;
    LocalDate createDate;
}
