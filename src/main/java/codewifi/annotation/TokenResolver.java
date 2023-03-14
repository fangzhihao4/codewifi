package codewifi.annotation;



import codewifi.annotation.exception.ReturnException;
import codewifi.common.constant.ReturnEnum;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import javax.annotation.Nullable;
import java.util.List;

public class TokenResolver extends AbstractMessageConverterMethodArgumentResolver {

    public TokenResolver(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Token.class);//绑定注解
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Authorization-Token");
        if (null == token || "".equals(token)) {
            throw new ReturnException(ReturnEnum.TOKEN_IS_NULL.getCode(), ReturnEnum.TOKEN_IS_NULL.getMsg());
        }
        return token;
    }

}
