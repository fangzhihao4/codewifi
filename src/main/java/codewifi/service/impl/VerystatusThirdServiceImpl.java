package codewifi.service.impl;

import codewifi.annotation.exception.ReturnException;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.enums.VerystatusGoodsEnum;
import codewifi.common.constant.enums.thrid.HoroscopeEnum;
import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.request.very.VerystatusPayGoodsRequest;
import codewifi.response.wifi.StarResponse;
import codewifi.service.VerystatusThirdService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class VerystatusThirdServiceImpl implements VerystatusThirdService {
    private static final LogUtil logUtil = LogUtil.getLogger(VerystatusThirdServiceImpl.class);

    private static final String V1 = "very";
    private static final String V2 = "VerystatusThirdServiceImpl";


    private final ThirdStarService thirdStarService;

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

        return false;
    }

    public boolean starContent(HoroscopeEnum timeHoroscopeEnum,VerystatusGoodsUserCo verystatusGoodsUserCo,VerystatusPayGoodsRequest verystatusPayGoodsRequest){
        String v3 = "starContent";
        HoroscopeEnum horoscopeEnum = HoroscopeEnum.getStarByType(verystatusPayGoodsRequest.getParamFirst());
        if (Objects.isNull(horoscopeEnum)){
            logUtil.infoWarn(V1,V2,v3,"商品类型错误", verystatusGoodsUserCo,verystatusPayGoodsRequest);
            throw new ReturnException(ReturnEnum.GOODS_PARAMS_ERROR);
        }
        StarResponse starResponse = thirdStarService.getStarContent(timeHoroscopeEnum.getType(), horoscopeEnum.getType());
        if (Objects.isNull(starResponse)) {
            return false;
        }
        verystatusGoodsUserCo.setContent(starResponse.getContent());
        return true;
    }



}
