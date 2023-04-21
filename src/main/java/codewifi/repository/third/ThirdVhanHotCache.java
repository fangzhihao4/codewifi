package codewifi.repository.third;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.common.constant.enums.VerystatusGoodsEnum;
import codewifi.repository.co.ThirdVhanHotCo;
import codewifi.sdk.sdkVhan.SdkVhanHotService;
import codewifi.sdk.sdkVhan.response.HotHuPuSdkResponse;
import codewifi.utils.LogUtil;
import codewifi.utils.StringChangeUtil;
import lombok.AllArgsConstructor;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ThirdVhanHotCache {
    private static final LogUtil logUtil = LogUtil.getLogger(ThirdVhanHotCache.class);

    private static final String V1 = "user";
    private static final String V2 = "ThirdStarService";

    private final RedissonService redissonService;
    private final SdkVhanHotService sdkVhanHotService;

    public List<ThirdVhanHotCo> getHotList(Integer goodsSku){
        if (VerystatusGoodsEnum.HOT_HU_PU.getGoodsSku().equals(goodsSku)){
            return getHuPu();
        }
        return new ArrayList<>();
    }
    
    public List<ThirdVhanHotCo> getHuPu(){
        String redisKey = RedisKeyConstants.VERY_STATUS_HOT_HU_PU_HASH;
        RMap<Integer, ThirdVhanHotCo> rMap = redissonService.getMap(redisKey, ThirdVhanHotCo.class);
        if (Objects.nonNull(rMap) && !rMap.isEmpty()){
            return (List<ThirdVhanHotCo>) rMap.values();
        }

        HotHuPuSdkResponse huPu = sdkVhanHotService.getHuPu();
        if (Objects.isNull(huPu)){
            logUtil.infoError("虎扑热榜查询为空");
            return new ArrayList<>();
        }
        List<ThirdVhanHotCo> listHuPu = new ArrayList<>();
        int maxStarInt = 1;
        for (HotHuPuSdkResponse.Info info : huPu.getData()){
            int starInt = StringChangeUtil.strToInt(info.getHot());
            maxStarInt = Math.max(maxStarInt,starInt);

            ThirdVhanHotCo thirdVhanHotCo = new ThirdVhanHotCo();
            thirdVhanHotCo.setIndex(info.getIndex());
            thirdVhanHotCo.setTitle(info.getTitle());
            thirdVhanHotCo.setPic(info.getPic());
            thirdVhanHotCo.setMobilUrl(info.getMobilUrl());
            thirdVhanHotCo.setHot(info.getHot());
            thirdVhanHotCo.setHotInt(starInt);
            listHuPu.add(thirdVhanHotCo);
        }

        for (ThirdVhanHotCo hotCo : listHuPu){
            int star = BigDecimal.valueOf(hotCo.getHotInt()).multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(maxStarInt), 2, RoundingMode.HALF_UP).intValue();
            hotCo.setStar(star);
        }

        Map<Integer, ThirdVhanHotCo> inMap = listHuPu.stream()
                .collect(Collectors.toMap(ThirdVhanHotCo::getIndex, s -> s, (k1 , k2) -> k2));
        if (inMap.isEmpty()){
            return new ArrayList<>();
        }
        rMap.putAll(inMap);
        return listHuPu;
    }



}
