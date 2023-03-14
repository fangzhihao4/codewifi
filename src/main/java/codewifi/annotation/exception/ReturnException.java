package codewifi.annotation.exception;

import codewifi.common.constant.ReturnEnum;
import lombok.Getter;

@Getter
public class ReturnException extends RuntimeException {

	private Integer code;

	private String message;

	public ReturnException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public ReturnException(ReturnEnum returnEnum) {
		super(returnEnum.getMsg());
		this.code = returnEnum.getCode();
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getCode(Integer code) {
		return code;
	}

	@Override
	public String getMessage() {

		return super.getMessage();
	}

}
