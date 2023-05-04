package codewifi.sdk.weather.response;

import lombok.Data;

import java.util.List;

@Data
public class WeatherCityResponse {
    Integer code;
    List<WeatherCityDataResponse> location;
}
