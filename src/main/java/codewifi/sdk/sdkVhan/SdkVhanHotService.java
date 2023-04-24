package codewifi.sdk.sdkVhan;

import codewifi.common.constant.LogConstant;
import codewifi.sdk.sdkVhan.response.HotHuPuSdkResponse;
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

    private static final LogUtil logUtil = LogUtil.getLogger(SdkVhanHotService.class);

    private static final String v2 = "qinghuaSdkServiceImpl";

    public static final String HU_PU = "huPu";
    public static final String ZHI_HU = "zhihuHot";
    public static final String KE_36 = "36Ke";
    public static final String BAI_DU = "baiduRD";
    public static final String BI_LI = "bili";
    public static final String TIE_BA = "baiduRY";
    public static final String WEI_BO = "wbHot";
    public static final String DOU_YIN = "douyinHot";
    public static final String HISTORY = "history";

    private static final String hu_pu_host = "https://api.vvhan.com/api/hotlist?type=huPu";
    private static final String zhi_hu_host = "https://api.vvhan.com/api/hotlist?type=zhihuHot";

    public HotHuPuSdkResponse getHuPu(){
        String v3 = "getHuPu";
        String response;
        try {
            response = HttpClientUtil.post(hu_pu_host, "{}");
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "虎扑热榜出错", hu_pu_host, ExceptionUtils.getStackTrace(e));
            return null;
        }
        HotHuPuSdkResponse huPuSdkResponse;
        try {
            huPuSdkResponse = jsonUtil.fromJsonString(response, HotHuPuSdkResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询虎扑失败数据转对象失败", null, response);
            return null;
        }
        if (Objects.isNull(huPuSdkResponse)){
            return null;
        }
        if (Boolean.TRUE.equals(!huPuSdkResponse.getSuccess())){
            return null;
        }
        if (Objects.isNull(huPuSdkResponse.getData())){
            return null;
        }
        return huPuSdkResponse;
    }

    public HotHuPuSdkResponse getByType(String type){
        String v3 = "getZhiHu";
        String response;
        try {
            response = HttpClientUtil.post("https://api.vvhan.com/api/hotlist?type=" + type, "{}");
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "知乎热榜出错", "https://api.vvhan.com/api/hotlist?type=" + type, ExceptionUtils.getStackTrace(e));
            return null;
        }
        HotHuPuSdkResponse huPuSdkResponse;
        try {
            huPuSdkResponse = jsonUtil.fromJsonString(response, HotHuPuSdkResponse.class);
        }
        catch (Exception e) {
            logUtil.infoError(LogConstant.V1, v2, v3, "查询热榜失败数据转对象失败", null, response);
            return null;
        }
        if (Objects.isNull(huPuSdkResponse)){
            return null;
        }
        if (Boolean.TRUE.equals(!huPuSdkResponse.getSuccess())){
            return null;
        }
        if (Objects.isNull(huPuSdkResponse.getData())){
            return null;
        }
        return huPuSdkResponse;
    }
}
