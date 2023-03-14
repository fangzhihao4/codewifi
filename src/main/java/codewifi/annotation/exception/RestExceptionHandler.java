package codewifi.annotation.exception;



import codewifi.common.Response;
import codewifi.common.constant.LogConstant;
import codewifi.common.constant.ReturnEnum;
import codewifi.utils.LogUtil;
import codewifi.utils.ServletsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {


	private static final LogUtil logUtil = LogUtil.getLogger(RestExceptionHandler.class);

	/**
	 * 业务异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ReturnException.class)
	public Response handelReturnException(ReturnException e) {
		logUtil.error(e.getMessage(), e, null);
		return Response.fail(e.getCode(), e.getMessage());
	}

	/**
	 * 常见的请求异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class,
			MissingPathVariableException.class, TypeMismatchException.class, HttpMessageNotReadableException.class,
			HttpMessageNotWritableException.class, HttpMediaTypeNotAcceptableException.class,
			ServletRequestBindingException.class, ConversionNotSupportedException.class,
			MissingServletRequestPartException.class, AsyncRequestTimeoutException.class })
	public Response handleServletException(Exception e) {
		Response<Object> error = Response.fail(ReturnEnum.SYSTEM_ERROR);
		logUtil.error(LogConstant.V1, e.getClass().getName(), "handleNotFoundException",e.getMessage(), ServletsUtil.getRequest().getParameterMap(), error);
		return error;
	}

	/**
	 * 请求路径错误异常单独处理，返回404
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public Response handleNotFoundException(NoHandlerFoundException e) {
		Response<Object> error = Response.fail(ReturnEnum.NO_HANDLER_FOUND);
		logUtil.error(LogConstant.V1, e.getRequestURL(), "handleNotFoundException", e.getMessage(),ServletsUtil.getRequest().getParameterMap(), error);
		return error;
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		String parameterName = e.getParameterName();
		String response = "入参【" + parameterName + "】缺失 ";
		Response<Object> error = Response.fail(ReturnEnum.PARAMS_ERROR, response);
		logUtil.infoWarn( LogConstant.V1, ServletsUtil.getRequest().getRequestURI(),
					"handleMissingServletRequestParameterException", "参数校验失败",
					ServletsUtil.getRequest().getParameterMap(), error);

		return error;
	}

	@ExceptionHandler(value = BindException.class)
	public Response handleBindException(BindException e) {
		String bindingResult = wrapperBindingResult(e.getBindingResult());
		Response<Object> response = Response.fail(ReturnEnum.PARAMS_ERROR.getCode(), bindingResult);
		logUtil.infoWarn(LogConstant.V1, ServletsUtil.getRequest().getRequestURI(), "handleBindException",
					"参数绑定失败", ServletsUtil.getRequest().getParameterMap(), response);

		return response;
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public Response handleValidException(MethodArgumentNotValidException e) {
		String bindingResult = wrapperBindingResult(e.getBindingResult());
		Response<Object> response = Response.fail(ReturnEnum.PARAMS_ERROR.getCode(), bindingResult);
		logUtil.infoWarn(LogConstant.V1,
					e.getParameter().getExecutable().getDeclaringClass().getSimpleName(),
					e.getParameter().getExecutable().getName(), "参数校验失败", ServletsUtil.getRequest().getParameterMap(),
					response);
		return response;
	}

	/**
	 * 其他异常同一返回500
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public Response exceptionHandler(Exception e) {
		logUtil.error( LogConstant.V1, ServletsUtil.getRequest().getRequestURI(),
				"exceptionHandler", e.getMessage(), ServletsUtil.getRequest().getParameterMap(),
				ExceptionUtils.getStackTrace(e));

	return Response.fail(ReturnEnum.SYSTEM_ERROR);
	}


	private String wrapperBindingResult(BindingResult bindingResult) {
		StringBuilder msg = new StringBuilder();

		for (ObjectError error : bindingResult.getAllErrors()) {
			msg.append(", ");
			if (error instanceof FieldError) {
				msg.append(((FieldError) error).getField()).append(": ");
			}
			msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
		}
		return msg.substring(2);
	}

}
