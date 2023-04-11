package codewifi.response.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerystatusUserLoginResponse {
    String userNo;//用户编号
    String token;
    String nickname;
    Integer headerType; //头像类型 1网络地址 2本地地址
    String headerImg;

    BigDecimal coin;//剩余金币
    BigDecimal gem;//宝石
    Integer isFirst;//是否首次登录 1是首次登录 2不是首次登录
}
