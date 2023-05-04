package codewifi.sdk.weather.response;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {
    public Integer code;

    public String updateTime;

    public String fxLink;

    public List<WeatherDataResponse> daily;
}
