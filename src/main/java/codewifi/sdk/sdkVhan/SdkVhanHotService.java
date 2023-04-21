package codewifi.sdk.sdkVhan;

import codewifi.common.constant.LogConstant;
import codewifi.sdk.sdkVhan.response.HotHuPuSdkResponse;
import codewifi.sdk.sdkVhan.response.QinghuaApiSdkResponse;
import codewifi.utils.HttpClientUtil;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SdkVhanHotService {
    private final JsonUtil jsonUtil;

    private static final LogUtil logUtil = LogUtil.getLogger(SdkVhanWorkService.class);

    private static final String v2 = "qinghuaSdkServiceImpl";

    private static final String hu_pu = "https://api.vvhan.com/api/hotlist?type=huPu";

    public HotHuPuSdkResponse getHuPu(){
        String v3 = "getHuPu";
        String response;
        try {
            response = HttpClientUtil.post(hu_pu, "{}");
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "虎扑热榜出错", hu_pu, ExceptionUtils.getStackTrace(e));
            return null;
        }
        HotHuPuSdkResponse huPuSdkResponse;
        try {
            huPuSdkResponse = jsonUtil.fromJsonString(response, HotHuPuSdkResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询历史今天失败数据转对象失败", null, response);
            return null;
        }
        if (Objects.isNull(huPuSdkResponse)){
            return null;
        }
        if (Objects.isNull(huPuSdkResponse.getSubititle()) || Boolean.TRUE.equals(!huPuSdkResponse.getSuccess())){
            return null;
        }
        if (Objects.isNull(huPuSdkResponse.getData())){
            return null;
        }
        return huPuSdkResponse;
    }
}
