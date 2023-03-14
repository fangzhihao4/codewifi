package codewifi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Component
public class JsonUtil {

	@Resource
	private ObjectMapper objectMapper;

	public <T> T fromJsonObject(JsonNode content, Class<T> valueType) {
		if (content == null) {
			return null;
		}
		try {
			return objectMapper.readValue(content.toString(), valueType);
		}
		catch (JsonProcessingException e) {
			return null;
		}
	}

	public <T> T fromJsonString(String content, Class<T> valueType) {
		if (content == null) {
			return null;
		}
		try {
			return objectMapper.readValue(content, valueType);
		}
		catch (JsonProcessingException e) {
			return null;

		}
	}

	public JsonNode toJsonObject(String input) {
		try {
			return objectMapper.readTree(input);
		}
		catch (JsonProcessingException e) {
			return null;

		}
	}

	public <T> JsonNode toJsonObject(T value) {
		if (value == null) {
			return null;
		}
		return objectMapper.convertValue(value, JsonNode.class);
	}

	public String writeValueAsString(Object object) {
		if (Objects.isNull(object)) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

}
