package codewifi.annotation;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ScheduledProperties {

    public final static String SYNC_ONE_HOUR = "0 0 0/1 * * ?";

    public final static String SYNC_ONE_MIN = "0 0/1 * * * ?";

}
