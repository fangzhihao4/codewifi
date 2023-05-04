package codewifi.sdk.weather.response;

import lombok.Data;

@Data
public class WeatherDataResponse {
    public String fxDate;//预报日期

    public String sunrise;//日出时间

    public String sunset;//日落时间

    public String moonrise;//月升时间

    public String moonset;//月落时间

    public String moonPhase;//月相名称

    public String moonPhaseIcon;//月相图标代码，图标可通过天气状况和图标下载

    public String tempMax;//预报当天最高温度

    public String tempMin;//预报当天最低温度

    public String iconDay;//预报白天天气状况的图标代码，图标可通过天气状况和图标下载

    public String textDay;//预报白天天气状况文字描述，包括阴晴雨雪等天气状态的描述

    public String iconNight;//预报夜间天气状况的图标代码，图标可通过天气状况和图标下载

    public String textNight;//预报晚间天气状况文字描述，包括阴晴雨雪等天气状态的描述

    public String wind360Day;//预报白天风向360角度

    public String windDirDay;//预报白天风向

    public String windScaleDay;//预报白天风力等级

    public String windSpeedDay;//预报白天风速，公里/小时

    public String wind360Night;//预报夜间风向360角度

    public String windDirNight;//预报夜间当天风向

    public String windScaleNight;//预报夜间风力等级

    public String windSpeedNight;//预报夜间风速，公里/小时

    public String precip;//预报当天总降水量，默认单位：毫米

    public String uvIndex;//紫外线强度指数

    public String humidity;//相对湿度，百分比数值

    public String pressure;//大气压强，默认单位：百帕

    public String vis;//能见度，默认单位：公里

    public String cloud;//云量，百分比数值。可能为空
}
