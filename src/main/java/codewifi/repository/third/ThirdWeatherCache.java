package codewifi.repository.third;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.co.ThirdWeatherCityCo;
import codewifi.repository.co.ThirdWeatherCo;
import codewifi.sdk.weather.WeatherService;
import codewifi.sdk.weather.response.WeatherCityDataResponse;
import codewifi.sdk.weather.response.WeatherDataResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class ThirdWeatherCache {
    private final RedissonService redissonService;
    private final WeatherService weatherService;
    private final JsonUtil jsonUtil;

    public List<ThirdWeatherCityCo> getByCityName(String name) {
        String redisKey = RedisKeyConstants.VERY_WEATHER_CITY_RESPONSE + name;
        RBucket<String> bucket = redissonService.getBucket(redisKey);
        String content = bucket.get();
        if (StringUtils.isNotEmpty(content)) {
            List<ThirdWeatherCityCo> cityCos = JSON.parseArray(content, ThirdWeatherCityCo.class);
            if (Objects.nonNull(cityCos)) {
                return cityCos;
            }
        }
        List<WeatherCityDataResponse> cityByName = weatherService.getCityByName(name);
        if (Objects.isNull(cityByName) || cityByName.isEmpty()) {
            return null;
        }
        List<ThirdWeatherCityCo> userWifiInfoResponseList = BeanCopyUtils.copyListProperties(cityByName, ThirdWeatherCityCo::new);
        bucket.set(jsonUtil.writeValueAsString(userWifiInfoResponseList), RedisKeyConstants.EXPIRE_BY_MONTH_SECONDS, TimeUnit.SECONDS);
        return userWifiInfoResponseList;
    }

    public WeatherResponse getByCityId(String id) {
        String redisKey = RedisKeyConstants.VERY_WEATHER_WEATHER_RESPONSE + id;
        RBucket<WeatherResponse> bucket = redissonService.getBucket(redisKey, WeatherResponse.class);
        WeatherResponse weatherResponse = bucket.get();
        if (Objects.nonNull(weatherResponse)) {
            return weatherResponse;
        }
        weatherResponse = weatherService.getWeather(id);
        if (Objects.isNull(weatherResponse)) {
            return null;
        }
        long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());

        bucket.set(weatherResponse, time, TimeUnit.SECONDS);
        return weatherResponse;
    }

    public ThirdWeatherCo getContent(WeatherResponse weatherResponse) {
        if (Objects.isNull(weatherResponse)) {
            return null;
        }
        ThirdWeatherCo thirdWeatherCo = new ThirdWeatherCo();
        thirdWeatherCo.setFxLink(weatherResponse.getFxLink());
        List<ThirdWeatherCo.DayInfo> list = new ArrayList<>();
        for (WeatherDataResponse weatherDataResponse : weatherResponse.getDaily()) {
            ThirdWeatherCo.DayInfo dayInfo = new ThirdWeatherCo.DayInfo();
            dayInfo.setFxDate(weatherDataResponse.getFxDate());
            String message = "白天: " + weatherDataResponse.getTextDay() + "  夜间: " + weatherDataResponse.getTextNight() + "\n\n";

            if (StringUtils.isNotEmpty(weatherDataResponse.getSunrise()) && StringUtils.isNotEmpty(weatherDataResponse.getSunset())) {
                message = message + "日出时间: " + weatherDataResponse.getSunrise()
                        + "     日落时间: " + weatherDataResponse.getSunset();
            }

            if (StringUtils.isNotEmpty(weatherDataResponse.getMoonPhase())
                    && StringUtils.isNotEmpty(weatherDataResponse.getMoonrise())
                    && StringUtils.isNotEmpty(weatherDataResponse.getMoonset())
            ) {
                message = message
                        + "\n月相名称: " + weatherDataResponse.getMoonPhase()
                        + "     月升时间:" + weatherDataResponse.getMoonrise()
                        + "     月落时间:" + weatherDataResponse.getMoonset();
            }

            if (StringUtils.isNotEmpty(weatherDataResponse.getTempMax()) && StringUtils.isNotEmpty(weatherDataResponse.getTempMin())) {
                message = message
                        + "\n最高温度: " + weatherDataResponse.getTempMax()
                        + "  最低温度:" + weatherDataResponse.getTempMin();
            }

            if (StringUtils.isNotEmpty(weatherDataResponse.getWind360Day())
                    && StringUtils.isNotEmpty(weatherDataResponse.getWindDirDay())
                    && StringUtils.isNotEmpty(weatherDataResponse.getWindScaleDay())
                    && StringUtils.isNotEmpty(weatherDataResponse.getWindSpeedDay())
            ){
                message = message
                        + "\n白天风向角度: "+ weatherDataResponse.getWind360Day()
                        + "     白天风向: " + weatherDataResponse.getWindDirDay()
                        + "     白天风力等级: " + weatherDataResponse.getWindScaleDay()
                        + "     白天风速: " + weatherDataResponse.getWindSpeedDay();
            }

            if (StringUtils.isNotEmpty(weatherDataResponse.getWind360Night())
                && StringUtils.isNotEmpty(weatherDataResponse.getWindDirNight())
                    && StringUtils.isNotEmpty(weatherDataResponse.getWindScaleNight())
                    && StringUtils.isNotEmpty(weatherDataResponse.getWindSpeedNight())
            ){
                message = message
                        + "\n夜晚风向角度: "+ weatherDataResponse.getWind360Night()
                        + "     夜间风向: " + weatherDataResponse.getWindDirNight()
                        + "     夜间风力等级: " + weatherDataResponse.getWindScaleNight()
                        + "     夜间风速: " + weatherDataResponse.getWindSpeedNight();
            }

            if (StringUtils.isNotEmpty(weatherDataResponse.getPrecip())){
                message = message + "\n紫外线强度指数: " + weatherDataResponse.getPrecip();
            }
            if (StringUtils.isNotEmpty(weatherDataResponse.getHumidity())){
                message = message + "\n相对湿度: " + weatherDataResponse.getHumidity();
            }
            if (StringUtils.isNotEmpty(weatherDataResponse.getPressure())){
                message = message + "\n大气压强: " + weatherDataResponse.getPressure();
            }
            if (StringUtils.isNotEmpty(weatherDataResponse.getVis())){
                message = message + "\n能见度: " + weatherDataResponse.getVis();
            }
            if (StringUtils.isNotEmpty(weatherDataResponse.getCloud())){
                message = message + "\n云量：" + weatherDataResponse.getCloud();
            }

            dayInfo.setContent(message);
            list.add(dayInfo);
        }
        thirdWeatherCo.setDayInfo(list);
        return thirdWeatherCo;
    }
}
