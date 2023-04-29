package codewifi.sdk.sdkVhan;

import codewifi.common.constant.LogConstant;
import codewifi.sdk.sdkVhan.response.QinghuaApiSdkResponse;
import codewifi.sdk.sdkVhan.response.SaoApiSdkResponse;
import codewifi.utils.HttpClientUtil;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SdkVhanWorkService {
    private final JsonUtil jsonUtil;

    private static final LogUtil logUtil = LogUtil.getLogger(SdkVhanWorkService.class);

    private static final String v2 = "qinghuaSdkServiceImpl";

    private static final String url = "https://api.uomg.com/api/rand.qinghua?format=json";
    private static final String sqoHuaUrl = "https://api.vvhan.com/api/sao?type=json";
    private static final String jokeUrl = "https://api.vvhan.com/api/joke?type=json";

    public QinghuaApiSdkResponse getQinghua() {
        String v3 = "getQinghua";
        String response;
        try {
            response = HttpClientUtil.post(url, "{}");
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "情话出错", url, ExceptionUtils.getStackTrace(e));
            return null;
        }
        QinghuaApiSdkResponse qinghuaApiResponse;
        try {
            qinghuaApiResponse = jsonUtil.fromJsonString(response, QinghuaApiSdkResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询历史今天失败数据转对象失败", null, response);
            return null;
        }
        return qinghuaApiResponse;
    }


    public String getQingHuaMessage(){
        QinghuaApiSdkResponse qinghuaApiResponse = getQinghua();
        if (Objects.isNull(qinghuaApiResponse)){
            return null;
        }
        if (Objects.isNull(qinghuaApiResponse.getCode())){
            return null;
        }
        if (qinghuaApiResponse.getCode() != 1){
            return null;
        }
        return qinghuaApiResponse.getContent();
    }

    public SaoApiSdkResponse getSaohua() {
        String v3 = "getQinghua";
        String response;
        try {
            response = HttpClientUtil.post(sqoHuaUrl, "{}");
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "骚话出错", url, ExceptionUtils.getStackTrace(e));
            return null;
        }
        SaoApiSdkResponse saoApiResponse;
        try {
            saoApiResponse = jsonUtil.fromJsonString(response, SaoApiSdkResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询历史今天失败数据转对象失败", null, response);
            return null;
        }
        return saoApiResponse;
    }

    public String getSaoHuaMessage(){
        SaoApiSdkResponse saoApiResponse = getSaohua();
        if (Objects.isNull(saoApiResponse)){
            return null;
        }
        if (Objects.isNull(saoApiResponse.getSuccess())){
            return null;
        }
        if (!"true".equals(saoApiResponse.getSuccess())){
            return null;
        }
        return saoApiResponse.getIshan();
    }

    public SaoApiSdkResponse getJoke() {
        String v3 = "jokeUrl";
        String response;
        try {
            response = HttpClientUtil.post(jokeUrl, "{}");
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "笑话出错", jokeUrl, ExceptionUtils.getStackTrace(e));
            return null;
        }
        SaoApiSdkResponse saoApiResponse;
        try {
            saoApiResponse = jsonUtil.fromJsonString(response, SaoApiSdkResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询笑话对象失败", null, response);
            return null;
        }
        return saoApiResponse;
    }
}
