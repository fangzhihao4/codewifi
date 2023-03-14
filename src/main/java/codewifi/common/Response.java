package codewifi.common;



import codewifi.common.constant.ReturnEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.ui.ModelMap;

/**
 * @description: Response通用响应
 * @author: Michael
 * @date: 2021/1/4
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

	private int code;

	private T data;

	private String msg;

	/**
	 * 返回Response
	 * @param flag 成功状态
	 * @return Response
	 */
	public static <T> Response<T> status(boolean flag) {
		return flag ? success("SUCCESS") : fail("FAILURE");
	}

	/**
	 * 返回Response
	 * @param data 数据
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> data(T data) {
		return data(data, "SUCCESS");
	}

	/**
	 * 返回Response
	 * @param data 数据
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> data(T data, String msg) {
		return data(ReturnEnum.SUCCESS.getCode(), data, msg);
	}

	/**
	 * 返回Response
	 * @param code 状态码
	 * @param data 数据
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> data(int code, T data, String msg) {
		return new Response<>(code, data, data == null ? "无数据" : msg);
	}

	/**
	 * 返回Response
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> success(String msg) {
		return new Response<>(ReturnEnum.SUCCESS, msg == null ? ReturnEnum.SUCCESS.getMsg() : msg);
	}

	/**
	 * 返回Response
	 * @param ReturnEnum 业务代码
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> success(ReturnEnum ReturnEnum) {
		return new Response<>(ReturnEnum);
	}

	/**
	 * 返回Response
	 * @param ReturnEnum 业务代码
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> success(ReturnEnum ReturnEnum, T data) {
		return new Response<>(ReturnEnum, data);
	}

	/**
	 * 返回Response
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> fail(String msg) {
		return new Response<>(ReturnEnum.FAILURE, msg == null ? ReturnEnum.FAILURE.getMsg() : msg);
	}

	/**
	 * 返回Response
	 * @param code 状态码
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> fail(int code, String msg) {
		return new Response<>(code, null, msg);
	}

	/**
	 * 返回Response
	 * @param resultCode 业务代码
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> fail(ReturnEnum resultCode) {
		return new Response<>(resultCode);
	}

	/**
	 * 返回Response
	 * @param resultCode 业务代码
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> fail(ReturnEnum resultCode, String msg) {
		return new Response<>(resultCode, msg);
	}

	/**
	 * 返回Response
	 * @param resultCode 业务代码
	 * @param <T> T 泛型标记
	 * @return Response
	 */
	public static <T> Response<T> fail(ReturnEnum resultCode, String msg, T data) {
		return new Response<>(resultCode, data, msg == null ? resultCode.getMsg() : msg);
	}

	private Response(ReturnEnum resultCode) {
		this(resultCode, null, resultCode.getMsg());
	}

	private Response(ReturnEnum resultCode, String msg) {
		this(resultCode, null, msg);
	}

	private Response(ReturnEnum resultCode, T data) {
		this(resultCode, data, resultCode.getMsg());
	}

	private Response(ReturnEnum resultCode, T data, String msg) {
		this(resultCode.getCode(), data, msg);
	}

	public static String errorView(ReturnEnum ReturnEnum, String data, ModelMap modelMap) {
		modelMap.put("code", ReturnEnum.getCode());
		modelMap.put("msg", ReturnEnum.getMsg());
		modelMap.put("data", data);
		return "error/error";
	}

}
