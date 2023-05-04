package codewifi.repository.third;
import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.co.ThirdWeatherCityCo;
import codewifi.sdk.weather.WeatherService;
import codewifi.sdk.weather.response.WeatherCityDataResponse;
import codewifi.sdk.weather.response.WeatherResponse;
import codewifi.utils.BeanCopyUtils;
import codewifi.utils.JsonUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class ThirdWeatherCache {
    private final RedissonService redissonService;
    private final WeatherService weatherService;
    private final JsonUtil jsonUtil;

    public List<ThirdWeatherCityCo> getByCityName(String name){
        String redisKey = RedisKeyConstants.VERY_WEATHER_CITY_RESPONSE + name;
        RBucket<String> bucket = redissonService.getBucket(redisKey);
        String content = bucket.get();
        if (StringUtils.isNotEmpty(content)){
            List<ThirdWeatherCityCo> cityCos = JSON.parseArray(content, ThirdWeatherCityCo.class);
            if (Objects.nonNull(cityCos)){
                return cityCos;
            }
        }
        List<WeatherCityDataResponse> cityByName = weatherService.getCityByName(name);
        if (Objects.isNull(cityByName) ||  cityByName.isEmpty()){
            return null;
        }
        List<ThirdWeatherCityCo> userWifiInfoResponseList = BeanCopyUtils.copyListProperties(cityByName, ThirdWeatherCityCo::new);
        bucket.set(jsonUtil.writeValueAsString(userWifiInfoResponseList), RedisKeyConstants.EXPIRE_BY_MONTH_SECONDS, TimeUnit.SECONDS);
        return userWifiInfoResponseList;
    }

    public WeatherResponse getByCityId(String id){
        String redisKey = RedisKeyConstants.VERY_WEATHER_WEATHER_RESPONSE + id;
        RBucket<WeatherResponse> bucket = redissonService.getBucket(redisKey, WeatherResponse.class);
        WeatherResponse weatherResponse = bucket.get();
        if (Objects.nonNull(weatherResponse)){
            return weatherResponse;
        }
        weatherResponse = weatherService.getWeather(id);
        if (Objects.isNull(weatherResponse)){
            return null;
        }
        long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());

        bucket.set(weatherResponse,time, TimeUnit.SECONDS);
        return weatherResponse;
    }
}
