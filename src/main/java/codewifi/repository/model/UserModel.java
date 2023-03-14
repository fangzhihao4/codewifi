package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserModel {
    Integer id;
    String userNo;
    String openid;
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
