package codewifi.annotation;
import codewifi.common.IdGenerate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class XTransactionIdAspect {

	@Autowired
	IdGenerate idGenerate;

	/** 在切点之前织入 */
	@Before("@within(XTransactionId) || @annotation(XTransactionId)")
	public void doBefore() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

		String traceId = request.getHeader("x-transaction-id");

		if (StringUtils.isEmpty(traceId)) {
			// xTransactionId 未传可自己生成
			traceId = idGenerate.generateTraceIdId();
		}

		MDC.put("xTransactionId", traceId);
	}

}
