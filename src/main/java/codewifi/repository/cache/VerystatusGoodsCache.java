package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.common.constant.enums.VerystatusGoodsEnum;
import codewifi.repository.mapper.VerystatusGoodsMapper;
import codewifi.repository.model.VerystatusGoodsModel;
import codewifi.utils.JsonUtil;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class VerystatusGoodsCache {
    public final RedissonService redissonService;
    public final VerystatusGoodsMapper verystatusGoodsMapper;
    public final JsonUtil jsonUtil;

    public VerystatusGoodsModel getByGoodsSku(Integer goodsSku){
        RBucket<VerystatusGoodsModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_SYSTEM_GOODS + goodsSku, VerystatusGoodsModel.class);
        VerystatusGoodsModel verystatusGoodsModel = bucket.get();
        if (Objects.nonNull(verystatusGoodsModel)){
            return verystatusGoodsModel;
        }
        verystatusGoodsModel = verystatusGoodsMapper.getInfo(goodsSku);
        if (Objects.isNull(verystatusGoodsModel)){
            verystatusGoodsModel = new VerystatusGoodsModel();
            VerystatusGoodsEnum verystatusGoodsEnum = VerystatusGoodsEnum.getGoodsEnum(goodsSku);
            if (Objects.isNull(verystatusGoodsEnum)){
                return null;
            }
            verystatusGoodsModel.setGoodsSku(goodsSku);
            verystatusGoodsModel.setPriceType(verystatusGoodsEnum.getPriceType());
            verystatusGoodsModel.setFreeNum(verystatusGoodsEnum.getFreeNum());
            verystatusGoodsModel.setCoin(verystatusGoodsEnum.getCoin());
            verystatusGoodsModel.setVideoNum(verystatusGoodsEnum.getVideoNum());
            verystatusGoodsModel.setRepeatTime(verystatusGoodsEnum.getRepeatTime());
            verystatusGoodsModel.setShowType(verystatusGoodsEnum.getShowType());
        }
        bucket.set(verystatusGoodsModel,RedisKeyConstants.EXPIRE_BY_ONE_HOUR, TimeUnit.SECONDS);
        return verystatusGoodsModel;
    }
}
