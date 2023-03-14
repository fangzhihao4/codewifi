package codewifi.common.constant.enums;

import codewifi.common.constant.RedisKeyConstants;
import lombok.Getter;

@Getter
public enum LoginTypeEnums {


    PWD("pwd", RedisKeyConstants.EXPIRE_BY_MONTH_SECONDS),
    WX("wx",RedisKeyConstants.EXPIRE_BY_TWO_HOUR);

    private final String type;
    private final Integer outTime;

    LoginTypeEnums(String type, int outTime) {
        this.type = type;
        this.outTime = outTime;
    }
}
