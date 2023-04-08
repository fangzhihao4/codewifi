package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerystatusUserModel {
    Integer id;
    String openid;
    String userNo;
    Byte type;
    String unionid;
    String nickname;
    String headImgUrl;
    String gender;
    String region;
    Byte status;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
