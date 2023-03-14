package codewifi.utils;

import codewifi.common.constant.LogConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RestTemplateUtil {
	private static final LogUtil logUtil = LogUtil.getLogger(RestTemplateUtil.class);

	private static final String V1 = "wifi";
	private static final String V2 = "RestTemplateUtil";

	@Resource(name = "restTemplateNoSsl")
	private RestTemplate restTemplateNoSsl;

	private static final String v2 = "RestTemplateUtil";

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public <T> T get(String url, Object data, HttpHeaders httpHeaders,
			ParameterizedTypeReference<T> responseTypeParameterizedTypeReference) {
		LocalDateTime start = LocalDateTime.now(ZoneOffset.of("+8"));
		ResponseEntity<T> responseEntity = null;
		String errorMsg = null;
		try {
			responseEntity = restTemplateNoSsl.exchange(url, HttpMethod.GET, new HttpEntity<>(data, httpHeaders),
					responseTypeParameterizedTypeReference);
		}
		catch (Exception e) {
			errorMsg = e.getMessage();
		}
		LocalDateTime end = LocalDateTime.now(ZoneOffset.of("+8"));
		if (responseEntity != null) {
			logUtil.info(LogConstant.V1, v2, "get", "请求成功", logRequest(url, data, start, end),
					responseEntity.getBody());
			return responseEntity.getBody();
		}
		else {
			logUtil.info(LogConstant.V1, v2, "get", "请求失败", logRequest(url, data, start, end), errorMsg);
		}
		return null;
	}

	public <T> T get(URI url, Object data, HttpHeaders httpHeaders,
			ParameterizedTypeReference<T> responseTypeParameterizedTypeReference) {
		LocalDateTime start = LocalDateTime.now(ZoneOffset.of("+8"));
		ResponseEntity<T> responseEntity = null;
		String errorMsg = null;
		try {
			responseEntity = restTemplateNoSsl.exchange(url, HttpMethod.GET, new HttpEntity<>(data, httpHeaders),
					responseTypeParameterizedTypeReference);
		}
		catch (Exception e) {
			errorMsg = e.getMessage();
		}
		LocalDateTime end = LocalDateTime.now(ZoneOffset.of("+8"));
		if (responseEntity != null) {
			logUtil.info(LogConstant.V1, v2,  "get", "请求成功", logRequest(url.getPath(), data, start, end),
					responseEntity.getBody());
			return responseEntity.getBody();
		}
		else {
			logUtil.info(LogConstant.V1, v2,  "get", "请求失败", logRequest(url.getPath(), data, start, end),
					errorMsg);
		}
		return null;
	}

	public <T> T get(String url, Object data, HttpHeaders httpHeaders, Class<T> t) {
		LocalDateTime start = LocalDateTime.now(ZoneOffset.of("+8"));
		ResponseEntity<T> responseEntity = null;
		String errorMsg = null;
		try {
			responseEntity = restTemplateNoSsl.getForEntity(url, t, new HttpEntity<>(data, httpHeaders));
		}
		catch (Exception e) {
			errorMsg = e.getMessage();
		}
		LocalDateTime end = LocalDateTime.now(ZoneOffset.of("+8"));
		if (responseEntity != null) {
			logUtil.info(LogConstant.V1, v2,  "get", "请求成功", logRequest(url, data, start, end),
					responseEntity.getBody());
			return responseEntity.getBody();
		}
		else {
			logUtil.info(LogConstant.V1, v2,  "get", "请求失败", logRequest(url, data, start, end), errorMsg);
		}
		return null;
	}

	public <T> T post(String url, Object data, HttpHeaders httpHeaders,
			ParameterizedTypeReference<T> responseTypeParameterizedTypeReference) {
		LocalDateTime start = LocalDateTime.now(ZoneOffset.of("+8"));
		ResponseEntity<T> responseEntity = null;
		String errorMsg = null;
		try {
			responseEntity = restTemplateNoSsl.exchange(url, HttpMethod.POST, new HttpEntity<>(data, httpHeaders),
					responseTypeParameterizedTypeReference);
		}
		catch (Exception e) {
			errorMsg = e.getMessage();
		}
		LocalDateTime end = LocalDateTime.now(ZoneOffset.of("+8"));
		if (responseEntity != null) {
			logUtil.info(LogConstant.V1, v2,  "post", "请求成功", logRequest(url, data, start, end),
					responseEntity.getBody());
			return responseEntity.getBody();
		}
		else {
			logUtil.info(LogConstant.V1, v2,  "post", "请求失败", logRequest(url, data, start, end), errorMsg);
		}
		return null;
	}

	public <T> T post(String url, Object data, HttpHeaders httpHeaders, Class<T> t) {
		LocalDateTime start = LocalDateTime.now(ZoneOffset.of("+8"));
		ResponseEntity<T> responseEntity = null;
		String errorMsg = null;
		try {
			responseEntity = restTemplateNoSsl.postForEntity(url, new HttpEntity<>(data, httpHeaders), t);
		}
		catch (Exception e) {
			log.error("请求异常", e);
			errorMsg = e.getMessage();
		}
		LocalDateTime end = LocalDateTime.now(ZoneOffset.of("+8"));
		if (responseEntity != null) {
			logUtil.info(LogConstant.V1, v2,  "post", "请求成功", logRequest(url, data, start, end),
					responseEntity.getBody());
			return responseEntity.getBody();
		}
		else {
			logUtil.info(LogConstant.V1, v2,  "post", "请求失败", logRequest(url, data, start, end), errorMsg);
		}
		return null;
	}

	// 接口返回http码
	public HttpStatus postHttpCode(String url, Object data, HttpHeaders httpHeaders) {
		LocalDateTime start = LocalDateTime.now(ZoneOffset.of("+8"));
		ResponseEntity<String> responseEntity = null;
		String errorMsg = null;
		try {
			responseEntity = restTemplateNoSsl.postForEntity(url, new HttpEntity<>(data, httpHeaders),
					String.class);
		}
		catch (Exception e) {
			log.error("请求异常", e);
			errorMsg = e.getMessage();
		}
		LocalDateTime end = LocalDateTime.now(ZoneOffset.of("+8"));
		if (responseEntity != null) {
			logUtil.info(LogConstant.V1, v2, "post", "请求成功", logRequest(url, data, start, end),
					responseEntity.getStatusCode());
			return responseEntity.getStatusCode();
		}
		else {
			logUtil.info(LogConstant.V1, v2,  "post", "请求失败", logRequest(url, data, start, end), errorMsg);
		}
		return null;
	}

	private float getExec(long startTime) {
		long endTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
		return endTime - startTime;
	}

	private <T> Map<String, Object> logRequest(String url, T data, LocalDateTime start, LocalDateTime end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		map.put("data", data);
		map.put("requestTime", dtf.format(start));
		map.put("spendTime", Duration.between(start, end).toMillis() + "ms");
		return map;
	}

}
