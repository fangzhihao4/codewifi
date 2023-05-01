package codewifi.service.impl;

import codewifi.annotation.exception.ReturnException;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.enums.VerystatusGoodsEnum;
import codewifi.common.constant.enums.thrid.HoroscopeEnum;
import codewifi.repository.co.ThirdVhanHotCo;
import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.repository.third.ThirdVhanHotCache;
import codewifi.repository.third.ThirdVhanImgCache;
import codewifi.repository.third.ThirdVhanStarCache;
import codewifi.repository.third.ThirdVhanWorkCache;
import codewifi.request.very.VerystatusGoodsMoreRequest;
import codewifi.request.very.VerystatusPayGoodsRequest;
import codewifi.sdk.openai.OpenaiService;
import codewifi.sdk.sdkVhan.response.HoroscopeSdkResponse;
import codewifi.service.VerystatusThirdService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class VerystatusThirdServiceImpl implements VerystatusThirdService {
    private static final LogUtil logUtil = LogUtil.getLogger(VerystatusThirdServiceImpl.class);

    private static final String V1 = "very";
    private static final String V2 = "VerystatusThirdServiceImpl";


    private final ThirdVhanStarCache thirdVhanStarCache;
    private final ThirdVhanWorkCache thirdVhanWorkCache;
    private final ThirdVhanHotCache thirdVhanHotCache;
    private final ThirdVhanImgCache thirdVhanImgCache;
    private final OpenaiService openaiService;

    @Override
    public boolean getThirdContent(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest) {
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.STAR_TODAY.getGoodsSku())){
            return starContent(HoroscopeEnum.TODAY,verystatusGoodsUserCo,verystatusPayGoodsRequest);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.STAR_NEXT_DAY.getGoodsSku())){
            return starContent(HoroscopeEnum.NEXT_DAY,verystatusGoodsUserCo,verystatusPayGoodsRequest);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.STAR_WEEK.getGoodsSku())){
            return starContent(HoroscopeEnum.WEEK,verystatusGoodsUserCo,verystatusPayGoodsRequest);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.STAR_MONTH.getGoodsSku())){
            return starContent(HoroscopeEnum.MONTH,verystatusGoodsUserCo,verystatusPayGoodsRequest);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.STAR_YEAR.getGoodsSku())){
            return starContent(HoroscopeEnum.YEAR,verystatusGoodsUserCo,verystatusPayGoodsRequest);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.STAR_LOVE.getGoodsSku())){
            return starContent(HoroscopeEnum.LOVE,verystatusGoodsUserCo,verystatusPayGoodsRequest);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.WORK_SAO_HUA.getGoodsSku())){
            return workSaoHua(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.WORK_QING_HUA.getGoodsSku())){
            return workQingHua(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.WORK_JOKE.getGoodsSku())){
            return workJoke(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.HOT_HU_PU.getGoodsSku())){
            return hotAll(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.HOT_ZHI_HU.getGoodsSku())){
            return hotAll(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.HOT_KE_36.getGoodsSku())){
            return hotAll(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.HOT_BAI_DU.getGoodsSku())){
            return hotAll(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.HOT_BI_LI.getGoodsSku())){
            return hotAll(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.HOT_TIE_BA.getGoodsSku())){
            return hotAll(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.HOT_WEI_BO.getGoodsSku())){
            return hotAll(verystatusGoodsUserCo);
        }
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.HOT_DOU_YIN.getGoodsSku())){
            return hotAll(verystatusGoodsUserCo);
        }
        if ((verystatusGoodsUserCo.getGoodsSku() >= VerystatusGoodsEnum.IMG_DONG_MAN.getGoodsSku()) &&
                (verystatusGoodsUserCo.getGoodsSku() <= VerystatusGoodsEnum.IMG_HEAD_JW.getGoodsSku())
        ){
            return imgAll(verystatusGoodsUserCo);
        }
        if (VerystatusGoodsEnum.HISTORY_TO_DAY.getGoodsSku().equals(verystatusGoodsUserCo.getGoodsSku())){
            return historyAll(verystatusGoodsUserCo);
        }
        if (VerystatusGoodsEnum.CALENDAR_MO_YU.getGoodsSku().equals(verystatusGoodsUserCo.getGoodsSku())){
            return calendar(verystatusGoodsUserCo);
        }
        if (VerystatusGoodsEnum.CALENDAR_ZHI_CHANG.getGoodsSku().equals(verystatusGoodsUserCo.getGoodsSku())){
            return calendar(verystatusGoodsUserCo);
        }

        if ((verystatusGoodsUserCo.getGoodsSku() >= VerystatusGoodsEnum.OPENAI_USER_NAME.getGoodsSku()) &&
                (verystatusGoodsUserCo.getGoodsSku() <= VerystatusGoodsEnum.OPENAI_MAX.getGoodsSku())
        ){
            return openai(verystatusGoodsUserCo,verystatusPayGoodsRequest);
        }

        return false;
    }

    @Override
    public boolean startGoodsInfo(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest) {
      return getThirdContent(verystatusGoodsUserCo,verystatusPayGoodsRequest);
        //        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.STAR_TODAY.getGoodsSku())){
//            starContent(HoroscopeEnum.TODAY,verystatusGoodsUserCo,verystatusPayGoodsRequest);
//        }
    }

    @Override
    public Object thirdPage(VerystatusGoodsMoreRequest verystatusGoodsMoreRequest) {
        if (VerystatusGoodsEnum.HOT_HU_PU.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return hotAllPage(verystatusGoodsMoreRequest);
        }
        if (VerystatusGoodsEnum.HOT_ZHI_HU.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return hotAllPage(verystatusGoodsMoreRequest);
        }
        if (VerystatusGoodsEnum.HOT_KE_36.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return hotAllPage(verystatusGoodsMoreRequest);
        }
        if (VerystatusGoodsEnum.HOT_BAI_DU.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return hotAllPage(verystatusGoodsMoreRequest);
        }
        if (VerystatusGoodsEnum.HOT_BI_LI.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return hotAllPage(verystatusGoodsMoreRequest);
        }
        if (VerystatusGoodsEnum.HOT_TIE_BA.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return hotAllPage(verystatusGoodsMoreRequest);
        }
        if (VerystatusGoodsEnum.HOT_WEI_BO.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return hotAllPage(verystatusGoodsMoreRequest);
        }
        if (VerystatusGoodsEnum.HOT_DOU_YIN.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return hotAllPage(verystatusGoodsMoreRequest);
        }
        if (VerystatusGoodsEnum.HISTORY_TO_DAY.getGoodsSku().equals(verystatusGoodsMoreRequest.getGoodsSku())){
            return historyAllPage(verystatusGoodsMoreRequest);
        }
        return null;
    }

    public boolean starContent(HoroscopeEnum timeHoroscopeEnum,VerystatusGoodsUserCo verystatusGoodsUserCo,VerystatusPayGoodsRequest verystatusPayGoodsRequest){
        String v3 = "starContent";
        HoroscopeEnum horoscopeEnum = HoroscopeEnum.getStarByType(verystatusPayGoodsRequest.getParamFirst());
        if (Objects.isNull(horoscopeEnum)){
            logUtil.infoWarn(V1,V2,v3,"商品类型错误", verystatusGoodsUserCo,verystatusPayGoodsRequest);
            throw new ReturnException(ReturnEnum.GOODS_PARAMS_ERROR);
        }
        HoroscopeSdkResponse.DataInfo dataInfo = thirdVhanStarCache.getStarContent(timeHoroscopeEnum.getType(), horoscopeEnum.getType());
        if (Objects.isNull(dataInfo)) {
            return false;
        }
        verystatusGoodsUserCo.setOther(dataInfo);
        return true;
    }

    public boolean workSaoHua(VerystatusGoodsUserCo verystatusGoodsUserCo){
        String content = thirdVhanWorkCache.getSaoHua(verystatusGoodsUserCo.getGoodsSku());
        if (StringUtils.isEmpty(content)){
            return false;
        }
        verystatusGoodsUserCo.setContent(content);
        return true;
    }

    public boolean workQingHua(VerystatusGoodsUserCo verystatusGoodsUserCo){
        String content = thirdVhanWorkCache.getQingHua(verystatusGoodsUserCo.getGoodsSku());
        if (StringUtils.isEmpty(content)){
            return false;
        }
        verystatusGoodsUserCo.setContent(content);
        return true;
    }

    public boolean workJoke(VerystatusGoodsUserCo verystatusGoodsUserCo){
        String content = thirdVhanWorkCache.getJoke(verystatusGoodsUserCo.getGoodsSku());
        if (StringUtils.isEmpty(content)){
            return false;
        }
        verystatusGoodsUserCo.setContent(content);
        return true;
    }

    public boolean hotAll(VerystatusGoodsUserCo verystatusGoodsUserCo){
        List<ThirdVhanHotCo> content = thirdVhanHotCache.getStarHotList(verystatusGoodsUserCo.getGoodsSku());
        if (Objects.isNull(content) || content.isEmpty()){
            return false;
        }
        verystatusGoodsUserCo.setOther(content);
        return true;
    }

    public Object hotAllPage(VerystatusGoodsMoreRequest verystatusGoodsMoreRequest){
        List<ThirdVhanHotCo> content = thirdVhanHotCache.getByPage(verystatusGoodsMoreRequest.getGoodsSku(), verystatusGoodsMoreRequest.getPage());
        if (Objects.isNull(content) || content.isEmpty()){
            return false;
        }
        return content;
    }

    public boolean historyAll(VerystatusGoodsUserCo verystatusGoodsUserCo){
        List<ThirdVhanHotCo> content = thirdVhanHotCache.getHistoryStart();
        if (Objects.isNull(content) || content.isEmpty()){
            return false;
        }
        verystatusGoodsUserCo.setOther(content);
        return true;
    }

    public Object historyAllPage(VerystatusGoodsMoreRequest verystatusGoodsMoreRequest){
        ThirdVhanHotCo content = thirdVhanHotCache.getByHistoryPage(verystatusGoodsMoreRequest.getPage());
        if (Objects.isNull(content)){
            return false;
        }
        return content;
    }

    public boolean imgAll(VerystatusGoodsUserCo verystatusGoodsUserCo) {
        String imageHttp = thirdVhanImgCache.getBySku(verystatusGoodsUserCo.getGoodsSku());
        if (Objects.isNull(imageHttp)){
            return false;
        }
        verystatusGoodsUserCo.setContentImg(imageHttp);
        return true;
    }


    public boolean calendar(VerystatusGoodsUserCo verystatusGoodsUserCo) {
        String imageHttp = thirdVhanImgCache.getCalendar(verystatusGoodsUserCo.getGoodsSku());
        if (Objects.isNull(imageHttp)){
            return false;
        }
        verystatusGoodsUserCo.setContentImg(imageHttp);
        return true;
    }

    public boolean openai(VerystatusGoodsUserCo verystatusGoodsUserCo,VerystatusPayGoodsRequest verystatusPayGoodsRequest){
        if (VerystatusGoodsEnum.OPENAI_USER_NAME.getGoodsSku().equals(verystatusGoodsUserCo.getGoodsSku())){
            String content = openaiService.getByName(verystatusGoodsUserCo,verystatusPayGoodsRequest);
            if (StringUtils.isEmpty(content)){
                return false;
            }
            verystatusGoodsUserCo.setContent(content);
            return true;
        }
        List<Integer> reportList = Arrays.asList(VerystatusGoodsEnum.OPENAI_REPORT_DAY.getGoodsSku(),VerystatusGoodsEnum.OPENAI_REPORT_WEEK.getGoodsSku(),VerystatusGoodsEnum.OPENAI_REPORT_ALL.getGoodsSku());
        if (reportList.contains(verystatusGoodsUserCo.getGoodsSku())){
            String content = openaiService.getReport(verystatusGoodsUserCo,verystatusPayGoodsRequest);
            if (StringUtils.isEmpty(content)){
                return false;
            }
            verystatusGoodsUserCo.setContent(content);
            return true;
        }
        return false;
    }

}
