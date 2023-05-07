package codewifi.repository.co;

import lombok.Data;

import java.util.List;

@Data
public class ThirdWeatherCo {
    String fxLink;
    List<DayInfo> dayInfo;

    @Data
    public static class DayInfo{
        String fxDate;
        String content;
    }
}
