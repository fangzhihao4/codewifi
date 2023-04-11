package codewifi.common.constant;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class VerystatusConstants {
    public static final BigDecimal first_register_send_coin = BigDecimal.valueOf(1000); //首次注册赠送金币
    public static final Integer is_first_register_user = 1; //是抽次注册
    public static final Integer no_first_register_user = 2; //不是抽次注册
}
