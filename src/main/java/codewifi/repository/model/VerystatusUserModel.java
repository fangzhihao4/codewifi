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
    Byte headType; //头像类型 1网络地址 2本地地址
    String headImgUrl; //头像值
    String gender;
    String region;
    Byte status;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
