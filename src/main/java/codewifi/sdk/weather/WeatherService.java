package codewifi.sdk.weather;

import codewifi.sdk.weather.response.WeatherCityDataResponse;
import codewifi.sdk.weather.response.WeatherResponse;

import java.util.List;

public interface WeatherService {
    List<WeatherCityDataResponse> getCityByName(String name);

    WeatherResponse getWeather(String cityId);
}
