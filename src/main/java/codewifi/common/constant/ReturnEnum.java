package codewifi.common.constant;

import lombok.Getter;

@Getter
public enum ReturnEnum {

	// http code
	SUCCESS(200, "SUCCESS", ""),
	SYSTEM_ERROR(500, "系统错误，请稍后重试。【错误码：500】", "SYSTEM_ERROR"),
	NO_HANDLER_FOUND(404,"404 NOT FOUND【错误码：404】", "404NOTFOUND"),
	FAILURE(400,"FAILURE【错误码：400】", "FAILURE"),

	// public code
	TOKEN_IS_NULL(40005, "token信息错误。【错误码：40005】", "token为空"),
	APP_KEY_FAIL(40010, "系统错误，请稍后重试。【错误码：40010】",
			"APP_KEY_FAIL"),
	CHECK_SIGN_FAIL(40011, "系统错误，请稍后重试。【错误码：40011】", "CHECK_SIGN_FAIL"),

	// system code
	MISSING_PARAMS(4004, "系统错误，请稍后重试。【错误码：4004】", "参数不完整"),
	REQUEST_EXPIRE(4005, "系统错误，请稍后重试。【错误码：4005】",
			"请求已过期"),
	SIGN_ERROR(4006, "系统错误，请稍后重试。【错误码：4006】", "签名错误"),
	VALIDATION_FAILED(4007,
					"系统错误，请稍后重试。【错误码：4007】", "参数校验失败"),
	PARAMS_ERROR(4008, "系统错误，请稍后重试。【错误码：4008】",
							"参数错误"),
	DATABASE_FAILED(4009, "系统错误，请稍后重试。【错误码：4009】", "数据库处理失败"),
	REQUEST_TIME_ERROR(4010,"系统错误，请稍后重试。【错误码：4010】", "数据库处理失败"),

	TOKEN_ERROR(10001,"token错误","token错误"),
	NO_GET_WIFI_INFO(10002,"查询错误【错误码：10002】",""),
	NO_THIS_USER_WIFI(10003,"非法操作【错误码：10003】",""),
	NO_GROUP_NAME(10004,"参数错误【错误码：10004】",""),
	GROUP_NAME_ERROR(10005,"参数错误【错误码：10005】",""),
	NO_THIS_GROUP(10006,"参数错误【错误码：10006】",""),
	GROUP_WIFI_MORE(10007,"组下面还有其他wifi,请先把组wifi移到其他组",""),
	NO_GROUP_NO(10008,"没有groupNo",""),
	LINK_WIFI_TICKET_NO(10009,"ticket过期了",""),
	NO_FUND_WIFI_INFO(10010,"找不到这个wifi",""),
	TOKEN_CODE_ERROR(10011,"缺失code","token错误"),
	WX_LOGIN_FAIL(10012,"微信登录失败",""),
	NO_FUND_SCENE(10013,"未查询到分享信息",""),

	ADD_WIFI_FAST(11000,"创建wifi太快，请稍后再试",""),

	GET_WX_ACCESS_TOKEN_ERROR(12000,"请求微信失败",""),
	GET_WX_QRCODE_ERR(12001,"请求微信失败",""),

	UP_UPYUN_FILE_ERROR(13001,"又拍云失败",""),

	STAR_FORTUNE_PARAMS_ERROR(15001,"查询星座运势参数错误",""),
	GET_STAR_SDK_RESPONSE_ERROR(15002,"查询星座运势错误",""),


	USER_VIDEO_FAST(20001,"视频观看太快",""),
	USER_VIDEO_IS_MAX(20002,"已经达到上限",""),
	USER_VIDEO_ERROR(20002, "观看视频异常", ""),
	NO_FUND_THIS_GOODS(20003,"没有此信息",""),
	FREE_TIME_IS_MAX(20004,"免费次数到达上限",""),
	NOT_USE_TYPE_GOODS(20005,"不支持的方式",""),
	USER_COIN_LESS(20007,"金币不足",""),
	GET_GOODS_CONTENT_FALSE(20008,"查询失败",""),
	NO_FUND_USER_GOODS(20009,"初始化数据异常",""),
	GOODS_PARAMS_ERROR(20010,"查询类型错误",""),

	// business code
	LAST_CODE(10000, "新加定义在此code前,方便不需要每次加code需更改逗号", "");

	ReturnEnum(int code, String msg, String logMsg) {
		this.code = code;
		this.msg = msg;
		this.logMsg = logMsg;
	}

	private int code;

	private String msg;

	private String logMsg;

}
