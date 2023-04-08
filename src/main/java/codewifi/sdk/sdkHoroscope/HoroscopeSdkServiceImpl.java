package codewifi.sdk.sdkHoroscope;

import codewifi.common.constant.LogConstant;
import codewifi.sdk.sdkHoroscope.response.HoroscopeSdkResponse;

import codewifi.utils.HttpClientUtil;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class HoroscopeSdkServiceImpl implements HoroscopeSdkService {

    private static final LogUtil logUtil = LogUtil.getLogger(HoroscopeSdkServiceImpl.class);

    private static final String v2 = "HoroscopeSdkServiceImpl";
    private final JsonUtil jsonUtil;


    private static final String httpsUrl = "https://api.vvhan.com/api/horoscope?time=%s&type=%s";



//    public String getMsg(String timeStr, String typeStr){
//        HoroscopeSdkResponse horoscopeSdkResponse = getHoroscopeInfo(timeStr,typeStr);
//        if (Objects.isNull(horoscopeSdkResponse)){
//            return null;
//        }
//        if (!Boolean.TRUE.equals(horoscopeSdkResponse.getSuccess())){
//            return "查询星座信息出错";
//        }
//        String returnDes = "\n【"+ horoscopeSdkResponse.getData().getTitle() + "】"
//                + "\n【综合运势】" + "运势值:" + horoscopeSdkResponse.getData().getFortune().getAll()
//                    + " " + horoscopeSdkResponse.getData().getFortunetext().getAll()
//                + "\n【爱情运势】" + "运势值:" + horoscopeSdkResponse.getData().getFortune().getLove()
//                    + " " + horoscopeSdkResponse.getData().getFortunetext().getLove()
//                + "\n【学业工作】" + "运势值:" + horoscopeSdkResponse.getData().getFortune().getWork()
//                    + " " + horoscopeSdkResponse.getData().getFortunetext().getWork()
//                + "\n【财富运势】" + "运势值:" + horoscopeSdkResponse.getData().getFortune().getMoney()
//                    + " " + horoscopeSdkResponse.getData().getFortunetext().getMoney()
//                + "\n【健康运势】" + horoscopeSdkResponse.getData().getFortunetext().getHealth()
//                + "\n" + "健康指数:" + horoscopeSdkResponse.getData().getIndex().getHealth()
//                    + ", 商谈指数:" + horoscopeSdkResponse.getData().getIndex().getDiscuss()
//                + "\n \n"
//                + "♉幸运颜色:" + horoscopeSdkResponse.getData().getLuckycolor()
//                + "\n♉幸运数字:" + horoscopeSdkResponse.getData().getLuckynumber()
//                + "\n♉速配星座:" + horoscopeSdkResponse.getData().getLuckyconstellation()
//                + "\n短评:" + horoscopeSdkResponse.getData().getShortcomment();
//
//        return returnDes;
//    }

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
        return horoscopeSdkResponse;
    }

}
