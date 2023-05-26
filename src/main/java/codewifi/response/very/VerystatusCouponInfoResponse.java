package codewifi.response.very;

import lombok.Data;

@Data
public class VerystatusCouponInfoResponse {
    Integer type;
    String appId;
    String page;
    String cover;
    String title;
    String subTitle;
    String desc;
    String startTime;
    String endTime;
    Integer sort;
}
