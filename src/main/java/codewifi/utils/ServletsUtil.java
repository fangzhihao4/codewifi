package codewifi.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public final class ServletsUtil {

	private ServletsUtil() {
	}

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		}
		return requestAttributes.getRequest();
	}

	public static String getHeader(String headerName) {
		HttpServletRequest request = getRequest();
		if (null == request) {
			return null;
		}

		return request.getHeader(headerName);
	}

	public static Map<String, String> getHeaders() {
		Map<String, String> headerMaps = new HashMap<String, String>();
		for (Enumeration<String> enu = getRequest().getHeaderNames(); enu.hasMoreElements();) {
			String name = enu.nextElement();
			headerMaps.put(name, getRequest().getHeader(name));
		}
		return headerMaps;
	}

	public static HttpServletResponse getResponse() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		}
		return requestAttributes.getResponse();
	}

	public static String getIpAddr() {
		return getIpAddr(getRequest());
	}

	public static String getIpAddr(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && ip.trim().length() != 0 && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			}
			else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (ip != null && ip.trim().length() != 0 && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

}
