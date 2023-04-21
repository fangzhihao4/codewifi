package codewifi.sdk.sdkVhan;

import codewifi.common.constant.LogConstant;
import codewifi.sdk.sdkVhan.response.HoroscopeSdkResponse;
import codewifi.utils.HttpClientUtil;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class HoroscopeSdkService {
    private static final LogUtil logUtil = LogUtil.getLogger(HoroscopeSdkService.class);

    private static final String v2 = "HoroscopeSdkServiceImpl";
    private final JsonUtil jsonUtil;


    private static final String httpsUrl = "https://api.vvhan.com/api/horoscope?time=%s&type=%s";

    public HoroscopeSdkResponse getHoroscopeInfo(String time, String type) {
        String v3 = "getHoroscope";
        String url = String.format(httpsUrl,time,type);
        String response;
        try {
            response = HttpClientUtil.post(url, "{}");
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询星座数据出错", url, ExceptionUtils.getStackTrace(e));
            return null;
        }
        HoroscopeSdkResponse horoscopeSdkResponse;
        try {
            horoscopeSdkResponse = jsonUtil.fromJsonString(response, HoroscopeSdkResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询星座数据转对象失败", null, response);
            return null;
        }
        if (Objects.isNull(horoscopeSdkResponse)){
            return null;
        }
        if (!Boolean.TRUE.equals(horoscopeSdkResponse.getSuccess())){
            return null;
        }
        if (Objects.isNull(horoscopeSdkResponse.getData())){
            return null;
        }
        return horoscopeSdkResponse;
    }

}
