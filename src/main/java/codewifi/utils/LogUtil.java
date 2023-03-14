package codewifi.utils;


import codewifi.common.constant.DatePatternConstant;
import codewifi.common.constant.LogConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.marker.LogstashMarker;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static net.logstash.logback.marker.Markers.append;

public class LogUtil {
	private final Logger logger;

	private static final DateTimeFormatter logDateTimeFormatter = DateTimeFormatter
			.ofPattern(DatePatternConstant.LOG_DATETIME_PATTERN);

	public static LogUtil getLogger(Class<?> className) {
		Logger logger = LoggerFactory.getLogger(className);
		return new LogUtil(logger);
	}

	private LogUtil(final Logger logger) {
		this.logger = logger;
	}

	public void info(String message) {
		execute(LogConstant.LEVEL4, message, null, null, null);
	}

	public void info(String message, Object request) {
		execute(LogConstant.LEVEL4, message, request, null, null);
	}

	public void info(String message, Object request, Object response) {
		execute(LogConstant.LEVEL4, message, request, response, null);
	}

	public void info(String message, Object request, Object response, Long executeTime) {
		execute(LogConstant.LEVEL4, message, request, response, executeTime);
	}

	public void info(String v1, String v2, String v3, String message) {
		execute(LogConstant.LEVEL4, v1, v2, v3, message, null, null, null);
	}

	public void info(String v1, String v2, String v3, String message, Object request, Object response) {
		execute(LogConstant.LEVEL4, v1, v2, v3, message, request, response, null);
	}

	public void info(String v1, String v2, String v3, String message, Object request, Object response,
			Long executeTime) {
		execute(LogConstant.LEVEL4, v1, v2, v3, message, request, response, executeTime);
	}

	public void infoWarn(String message) {
		execute(LogConstant.LEVEL3, message, null, null, null);
	}

	public void infoWarn(String message, Object request) {
		execute(LogConstant.LEVEL3, message, request, null, null);
	}

	public void infoWarn(String message, Object request, Object response) {
		execute(LogConstant.LEVEL3, message, request, response, null);
	}

	public void infoWarn(String message, Object request, Object response, Long executeTime) {
		execute(LogConstant.LEVEL3, message, request, response, executeTime);
	}

	public void infoWarn(String v1, String v2, String v3, String message, Object request, Object response) {
		execute(LogConstant.LEVEL3, v1, v2, v3, message, request, response, null);
	}

	public void infoWarn(String v1, String v2, String v3, String message, Object request, Object response,
			Long executeTime) {
		execute(LogConstant.LEVEL3, v1, v2, v3, message, request, response, executeTime);
	}

	public void infoBug(String message) {
		execute(LogConstant.LEVEL2, message, null, null, null);
	}

	public void infoBug(String message, Object request) {
		execute(LogConstant.LEVEL2, message, request, null, null);
	}

	public void infoBug(String message, Object request, Object response) {
		execute(LogConstant.LEVEL2, message, request, response, null);
	}

	public void infoBug(String message, Object request, Object response, Long executeTime) {
		execute(LogConstant.LEVEL2, message, request, response, executeTime);
	}

	public void infoBug(String v1, String v2, String v3, String message, Object request, Object response) {
		execute(LogConstant.LEVEL2, v1, v2, v3, message, request, response, null);
	}

	public void infoBug(String v1, String v2, String v3, String message, Object request, Object response,
			Long executeTime) {
		execute(LogConstant.LEVEL2, v1, v2, v3, message, request, response, executeTime);
	}

	public void infoError(String message) {
		execute(LogConstant.LEVEL1, message, null, null, null);
	}

	public void infoError(String message, Object request) {
		execute(LogConstant.LEVEL1, message, request, null, null);
	}

	public void infoError(String message, Object request, Object response) {
		execute(LogConstant.LEVEL1, message, request, response, null);
	}

	public void infoError(String message, Object request, Object response, Long executeTime) {
		execute(LogConstant.LEVEL1, message, request, response, executeTime);
	}

	public void infoError(String v1, String v2, String v3, String message, Object request, Object response) {
		execute(LogConstant.LEVEL1, v1, v2, v3, message, request, response, null);
	}

	public void infoError(String v1, String v2, String v3, String message, Object request, Object response,
			Long executeTime) {
		execute(LogConstant.LEVEL1, v1, v2, v3, message, request, response, executeTime);
	}

	public void error(String message, Object request, Object response) {
		execute(LogConstant.LEVEL0, message, request, response, null);
	}

	public void error(String message, Object request, Object response, Long executeTime) {
		execute(LogConstant.LEVEL0, message, request, response, executeTime);
	}

	public void error(String v1, String v2, String v3, String message, Object request, Object response) {
		execute(LogConstant.LEVEL0, v1, v2, v3, message, request, response, null);
	}

	public void error(String v1, String v2, String v3, String message, Object request, Object response,
			Long executeTime) {
		execute(LogConstant.LEVEL0, v1, v2, v3, message, request, response, executeTime);
	}

	private Marker marker(String v1, String v2, String v3, Object request, Object response, Long executeTime) {
		String requestStr = Objects.isNull(request) ? "" : writeValueAsString(request);
		String responseStr = Objects.isNull(response) ? "" : writeValueAsString(response);
		LogstashMarker marker = append("request_id",
				MDC.get("xTransactionId") == null ? "" : MDC.get("xTransactionId"));
		if (null != executeTime) {
			marker = marker.and(append("execute_time", executeTime));
		}
		if (null != v1) {
			marker = marker.and(append("v1", v1));
		}
		if (null != v2) {
			marker = marker.and(append("v2", v2));
		}
		if (null != v3) {
			marker = marker.and(append("v3", v3));
		}
		marker = marker.and(append("request", requestStr)).and(append("response", responseStr));
		return marker;
	}

	private void execute(int level, String message, Object request, Object response, Long executeTime) {
		execute(level, null, null, null, message, request, response, executeTime);
	}

	private void execute(int level, String v1, String v2, String v3, String message, Object request, Object response,
			Long executeTime) {
		if (response instanceof Exception) {
			response = ExceptionUtils.getStackTrace((Exception) response);
		}
		if (LogConstant.LEVEL4 == level) {
			logger.info(marker(v1, v2, v3, request, response, executeTime), message);
		}
		else if (LogConstant.LEVEL3 == level) {
			logger.info(marker(v1, v2, v3, request, response, executeTime), message);
		}
		else if (LogConstant.LEVEL2 == level) {
			logger.info(marker(v1, v2, v3, request, response, executeTime), message);
		}
		else if (LogConstant.LEVEL1 == level) {
			logger.info(marker(v1, v2, v3, request, response, executeTime), message);
		}
		else if (LogConstant.LEVEL0 == level) {
			logger.error(marker(v1, v2, v3, request, response, executeTime), message);
		}
	}

	public String writeValueAsString(Object object) {
		if (Objects.isNull(object)) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

}
