package codewifi.annotation.config;



import codewifi.annotation.ProRequestBodyResolver;
import codewifi.annotation.TokenResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	// 解决跨域
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// List<HttpMessageConverter<?>> converters = new ArrayList<>();
		// // 添加消息转换器
		// converters.add(new MappingJackson2HttpMessageConverter());
		// // 消息转换器与Resolver绑定
		// resolvers.add(new ProRequestBodyResolver(converters));
		// resolvers.add(new CustomerIdResolver((converters)));

		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		// 添加消息转换器
		// converters.add(new MappingJackson2XmlHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
		// 消息转换器与Resolver绑定+
		resolvers.add(new TokenResolver((converters)));

		resolvers.add(new ProRequestBodyResolver(converters));
	}

	/**
	 * 忽略http路径大小写
	 * @param configurer 返回
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer){
		AntPathMatcher matcher = new AntPathMatcher();
		matcher.setCaseSensitive(false);
		configurer.setPathMatcher(matcher);
	}

}
