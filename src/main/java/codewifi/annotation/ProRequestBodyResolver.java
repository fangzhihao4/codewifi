package codewifi.annotation;

import com.alibaba.fastjson.JSON;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ProRequestBodyResolver extends AbstractMessageConverterMethodArgumentResolver {

	private static final Set<HttpMethod> SUPPORTED_METHODS = EnumSet.of(HttpMethod.POST, HttpMethod.PUT,
			HttpMethod.PATCH);

	private static final Object NO_VALUE = new Object();

	public ProRequestBodyResolver(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(ProRequestBody.class);// 绑定注解
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		// 调用AbstractMessageConverterMethodArgumentResolver类中readWithMessageConverters方法
		// Object arg = readWithMessageConverters(webRequest, parameter,
		// parameter.getNestedGenericParameterType());
		String className = parameter.getParameterType().getName();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String bodyString = (String) request.getAttribute("bodyString");
		if (bodyString == null) {
			bodyString = BodyString.getBodyStringFromRequest(request);
			request.setAttribute("bodyString", bodyString);
			if ("".equals(bodyString)) {
				bodyString = "{}";
			}
		}
		Object arg = JSON.parseObject(bodyString, Class.forName(className));

		String name = Conventions.getVariableNameForParameter(parameter);
		if (binderFactory != null) {
			WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);
			if (arg != null) {
				validateIfApplicable(binder, parameter);
				if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
					throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
				}
			}
			if (mavContainer != null) {
				mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
			}
		}

		return adaptArgumentIfNecessary(arg, parameter);

	}

}
