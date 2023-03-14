package codewifi.common;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdGenerate {

	public String generateTraceIdId() {
		return UUID.randomUUID().toString();
	}

}
