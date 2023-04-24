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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ThirdVhanHotCache {
    private static final LogUtil logUtil = LogUtil.getLogger(ThirdVhanHotCache.class);

    private static final String V1 = "user";
    private static final String V2 = "ThirdStarService";

    private final RedissonService redissonService;
    private final SdkVhanHotService sdkVhanHotService;


    public List<ThirdVhanHotCo> getStarHotList(Integer goodsSku) {
        List<ThirdVhanHotCo> list = getHotList(goodsSku);
        int startPage = 0;
        int endPage = Math.min(11,list.size());
        List<ThirdVhanHotCo> subList = list.stream()
                .sorted(Comparator.comparingInt(ThirdVhanHotCo::getIndex))
                .filter( s -> (s.getIndex() > startPage) && ( s.getIndex()<endPage))
                .collect(Collectors.toList());
        for (ThirdVhanHotCo thirdVhanHotCo : subList){
            thirdVhanHotCo.setDesc("");
        }
        return subList;
    }

    public List<ThirdVhanHotCo> getHistoryStart(){
        List<ThirdVhanHotCo> list = getByHistory();
        int startPage = 0;
        int endPage = Math.min(11,list.size());
        return list.stream()
                .sorted(Comparator.comparingInt(ThirdVhanHotCo::getIndex))
                .filter( s -> (s.getIndex() > startPage) && ( s.getIndex()<endPage))
                .collect(Collectors.toList());
    }

    public List<ThirdVhanHotCo> getByHistoryPage(Integer page) {
        List<ThirdVhanHotCo> list = getByHistory();
        int size = list.size();
        if (size <= 0) {
            return new ArrayList<>();
        }
        int startPage = Math.min(page * 10, size);
        int endPage = Math.min(startPage + 11, size);
        List<ThirdVhanHotCo> subList = list.stream()
                .sorted(Comparator.comparingInt(ThirdVhanHotCo::getIndex))
                .filter( s -> (s.getIndex() > startPage) && ( s.getIndex()<endPage))
                .collect(Collectors.toList());
        for (ThirdVhanHotCo thirdVhanHotCo : subList){
            thirdVhanHotCo.setDesc("");
        }
        return subList;
    }


    public List<ThirdVhanHotCo> getByPage(Integer goodsSku, Integer page) {
        List<ThirdVhanHotCo> list = getHotList(goodsSku);
        int size = list.size();
        if (size <= 0) {
            return new ArrayList<>();
        }
        int startPage = Math.min(page * 10, size);
        int endPage = Math.min(startPage + 11, size);
        List<ThirdVhanHotCo> subList = list.stream()
                .sorted(Comparator.comparingInt(ThirdVhanHotCo::getIndex))
                .filter( s -> (s.getIndex() > startPage) && ( s.getIndex()<endPage))
                .collect(Collectors.toList());
        for (ThirdVhanHotCo thirdVhanHotCo : subList){
            thirdVhanHotCo.setDesc("");
        }
        return subList;
    }

    public List<ThirdVhanHotCo> getHotList(Integer goodsSku) {
        if (VerystatusGoodsEnum.HOT_HU_PU.getGoodsSku().equals(goodsSku)) {
            return getHotByType(SdkVhanHotService.HU_PU);
        }
        if (VerystatusGoodsEnum.HOT_ZHI_HU.getGoodsSku().equals(goodsSku)) {
            return getHotByType(SdkVhanHotService.ZHI_HU);
        }
        if (VerystatusGoodsEnum.HOT_KE_36.getGoodsSku().equals(goodsSku)) {
            return getHotByType(SdkVhanHotService.KE_36);
        }
        if (VerystatusGoodsEnum.HOT_BAI_DU.getGoodsSku().equals(goodsSku)) {
            return getHotByType(SdkVhanHotService.BAI_DU);
        }
        if (VerystatusGoodsEnum.HOT_BI_LI.getGoodsSku().equals(goodsSku)) {
            return getHotByType(SdkVhanHotService.BI_LI);
        }
        if (VerystatusGoodsEnum.HOT_TIE_BA.getGoodsSku().equals(goodsSku)) {
            return getHotByType(SdkVhanHotService.TIE_BA);
        }
        if (VerystatusGoodsEnum.HOT_WEI_BO.getGoodsSku().equals(goodsSku)) {
            return getHotByType(SdkVhanHotService.WEI_BO);
        }
        if (VerystatusGoodsEnum.HOT_DOU_YIN.getGoodsSku().equals(goodsSku)) {
            return getHotByType(SdkVhanHotService.DOU_YIN);
        }
        return new ArrayList<>();
    }

    public List<ThirdVhanHotCo> getHotByType(String type) {
        String redisKey = getRedisKeyByType(type);
        RMap<Integer, ThirdVhanHotCo> rMap = redissonService.getMap(redisKey,Integer.class,ThirdVhanHotCo.class);
        if (Objects.nonNull(rMap) && !rMap.isEmpty()) {
            return new ArrayList<>(rMap.values());
        }

        HotHuPuSdkResponse huPu = sdkVhanHotService.getByType(type);
        if (Objects.isNull(huPu)) {
            logUtil.infoError("虎扑热榜查询为空");
            return new ArrayList<>();
        }
        List<ThirdVhanHotCo> listHuPu = new ArrayList<>();
        int maxStarInt = 1;
        for (HotHuPuSdkResponse.Info info : huPu.getData()) {
            int starInt = StringChangeUtil.strToInt(info.getHot());
            maxStarInt = Math.max(maxStarInt, starInt);

            ThirdVhanHotCo thirdVhanHotCo = new ThirdVhanHotCo();
            thirdVhanHotCo.setIndex(info.getIndex());
            thirdVhanHotCo.setTitle(info.getTitle());
            thirdVhanHotCo.setPic(info.getPic());
            thirdVhanHotCo.setMobilUrl(info.getMobilUrl());
            thirdVhanHotCo.setHot(info.getHot());
            thirdVhanHotCo.setHotInt(starInt);
            listHuPu.add(thirdVhanHotCo);
        }

        for (ThirdVhanHotCo hotCo : listHuPu) {
            int star = BigDecimal.valueOf(hotCo.getHotInt()).multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(maxStarInt), 2, RoundingMode.HALF_UP).intValue();
            hotCo.setStar(star);
        }

        Map<Integer, ThirdVhanHotCo> inMap = listHuPu.stream()
                .collect(Collectors.toMap(ThirdVhanHotCo::getIndex, s -> s, (k1, k2) -> k2));
        if (inMap.isEmpty()) {
            return new ArrayList<>();
        }
        rMap.putAll(inMap);
        rMap.expireAsync(RedisKeyConstants.EXPIRE_BY_ONE_HOUR, TimeUnit.SECONDS);
        return listHuPu;
    }

    public String getRedisKeyByType(String type){
        Map<String,String> map = new HashMap<>();
        map.put(SdkVhanHotService.HU_PU,RedisKeyConstants.VERY_STATUS_HOT_HU_PU_HASH);
        map.put(SdkVhanHotService.ZHI_HU,RedisKeyConstants.VERY_STATUS_HOT_ZHI_HU_HASH);
        map.put(SdkVhanHotService.KE_36,RedisKeyConstants.VERY_STATUS_HOT_36_KE_HASH);
        map.put(SdkVhanHotService.BAI_DU,RedisKeyConstants.VERY_STATUS_HOT_BAI_DU_HASH);
        map.put(SdkVhanHotService.BI_LI,RedisKeyConstants.VERY_STATUS_HOT_BILI_HASH);
        map.put(SdkVhanHotService.TIE_BA,RedisKeyConstants.VERY_STATUS_HOT_TIE_BA_HASH);
        map.put(SdkVhanHotService.WEI_BO,RedisKeyConstants.VERY_STATUS_HOT_WEIBO_HASH);
        map.put(SdkVhanHotService.DOU_YIN,RedisKeyConstants.VERY_STATUS_HOT_DOU_YIN_HASH);
        return map.get(type);
    }



    public List<ThirdVhanHotCo> getByHistory() {
        String redisKey = RedisKeyConstants.VERY_STATUS_HOT_HISTORY_HASH;
        RMap<Integer, ThirdVhanHotCo> rMap = redissonService.getMap(redisKey,Integer.class,ThirdVhanHotCo.class);
        if (Objects.nonNull(rMap) && !rMap.isEmpty()) {
            return new ArrayList<>(rMap.values());
        }

        HotHuPuSdkResponse huPu = sdkVhanHotService.getByType(SdkVhanHotService.HISTORY);
        if (Objects.isNull(huPu)) {
            logUtil.infoError("虎扑历史今天查询为空");
            return new ArrayList<>();
        }
        List<ThirdVhanHotCo> listHuPu = new ArrayList<>();
        int maxStarInt = 1;
        for (HotHuPuSdkResponse.Info info : huPu.getData()) {
            int starInt = StringChangeUtil.strToInt(info.getHot());
            maxStarInt = Math.max(maxStarInt, starInt);

            ThirdVhanHotCo thirdVhanHotCo = new ThirdVhanHotCo();
            thirdVhanHotCo.setIndex(info.getIndex());
            thirdVhanHotCo.setTitle(info.getTitle());
            thirdVhanHotCo.setDesc(info.getDesc());
            thirdVhanHotCo.setMobilUrl(info.getMobilUrl());
            thirdVhanHotCo.setHot(info.getHot());
            thirdVhanHotCo.setHotInt(starInt);
            listHuPu.add(thirdVhanHotCo);
        }

        for (ThirdVhanHotCo hotCo : listHuPu) {
            int star = BigDecimal.valueOf(hotCo.getHotInt()).multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(maxStarInt), 2, RoundingMode.HALF_UP).intValue();
            hotCo.setStar(star);
        }

        Map<Integer, ThirdVhanHotCo> inMap = listHuPu.stream()
                .collect(Collectors.toMap(ThirdVhanHotCo::getIndex, s -> s, (k1, k2) -> k2));
        if (inMap.isEmpty()) {
            return new ArrayList<>();
        }
        rMap.putAll(inMap);
        long time = ChronoUnit.SECONDS.between(LocalDateTime.now(),LocalDate.now().atStartOfDay());
        rMap.expireAsync(time, TimeUnit.SECONDS);
        return listHuPu;
    }


}
