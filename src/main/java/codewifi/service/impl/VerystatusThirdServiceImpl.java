package codewifi.service.impl;

import codewifi.annotation.exception.ReturnException;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.enums.VerystatusGoodsEnum;
import codewifi.common.constant.enums.thrid.HoroscopeEnum;
import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.repository.third.ThirdVhanStarCache;
import codewifi.repository.third.ThirdVhanWorkCache;
import codewifi.request.very.VerystatusPayGoodsRequest;
import codewifi.sdk.sdkVhan.response.HoroscopeSdkResponse;
import codewifi.service.VerystatusThirdService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class VerystatusThirdServiceImpl implements VerystatusThirdService {
    private static final LogUtil logUtil = LogUtil.getLogger(VerystatusThirdServiceImpl.class);

    private static final String V1 = "very";
    private static final String V2 = "VerystatusThirdServiceImpl";


    private final ThirdVhanStarCache thirdVhanStarCache;
    private final ThirdVhanWorkCache thirdVhanWorkCache;

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

        return false;
    }

    @Override
    public void startGoodsInfo(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest) {
        if (verystatusGoodsUserCo.getGoodsSku().equals(VerystatusGoodsEnum.STAR_TODAY.getGoodsSku())){
            starContent(HoroscopeEnum.TODAY,verystatusGoodsUserCo,verystatusPayGoodsRequest);
        }
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



}
