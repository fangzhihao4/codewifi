package codewifi.sdk.weather;

import codewifi.common.constant.LogConstant;
import codewifi.repository.third.ThirdVhanWorkCache;
import codewifi.sdk.weather.response.WeatherCityDataResponse;
import codewifi.sdk.weather.response.WeatherCityResponse;
import codewifi.sdk.weather.response.WeatherResponse;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import codewifi.utils.RestTemplateUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService{
    private static final String v2 = "WeatherServiceImpl";
    private static final String cityUrl = "https://geoapi.qweather.com/v2/city/lookup?";
    private static final String key = "3a83a205946d46b6b0585c589cd6b84f";

    private static final String weatherUrl = "https://devapi.qweather.com/v7/weather/3d?";

    private final RestTemplateUtil restTemplateUtil;
    private final JsonUtil jsonUtil;
    private static final LogUtil logUtil = LogUtil.getLogger(WeatherServiceImpl.class);


    @Override
    public List<WeatherCityDataResponse> getCityByName(String name) {
        String v3 = "getCityWeather";
        String cityUrlHost = cityUrl + "key=" + key + "&location=" + name;
        String response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            URI uri = UriComponentsBuilder.fromHttpUrl(cityUrlHost).build().encode().toUri();
            response = restTemplateUtil.get(uri,null,
                    headers,
                    new ParameterizedTypeReference<String>() {
                    });
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询城市出错", cityUrlHost, ExceptionUtils.getStackTrace(e));
            return null;
        }
        if (Objects.isNull(response)){
            return null;
        }
        WeatherCityResponse weatherCityResponse;
        try {
            weatherCityResponse = jsonUtil.fromJsonString(response, WeatherCityResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询城市数据转对象失败", null, response);
            return null;
        }
        if (Objects.isNull(weatherCityResponse)){
            return null;
        }
        if (200 != weatherCityResponse.getCode()){
            return null;
        }
        if (Objects.isNull(weatherCityResponse.getLocation()) || weatherCityResponse.getLocation().isEmpty()){
            return null;
        }

        return weatherCityResponse.getLocation();
    }

    @Override
    public WeatherResponse getWeather(String cityId) {
        String v3 = "getWeather";
        String weatherUrlHost = weatherUrl + "key=" + key + "&location=" + cityId;
        String response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            URI uri = UriComponentsBuilder.fromHttpUrl(weatherUrlHost).build().encode().toUri();
            response = restTemplateUtil.get(uri,null,
                    headers,
                    new ParameterizedTypeReference<String>() {
                    });
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询天气出错", weatherUrlHost, ExceptionUtils.getStackTrace(e));
            return null;
        }
        if (Objects.isNull(response)){
            return null;
        }
        WeatherResponse weatherResponse;
        try {
            weatherResponse = jsonUtil.fromJsonString(response, WeatherResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询天气数据转对象失败", null, response);
            return null;
        }
        if (Objects.isNull(weatherResponse)){
            return null;
        }
        if (Objects.isNull(weatherResponse.getCode())){
            return null;
        }
        if (200 != weatherResponse.getCode()){
            return null;
        }
        if (Objects.isNull(weatherResponse.getDaily()) || weatherResponse.getDaily().isEmpty()){
            return null;
        }
        return weatherResponse;
    }
}
