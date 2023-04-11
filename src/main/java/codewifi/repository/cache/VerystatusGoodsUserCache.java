package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.repository.mapper.VerystatusGoodsUserMapper;
import codewifi.repository.model.VerystatusGoodsModel;
import codewifi.repository.model.VerystatusGoodsUserModel;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class VerystatusGoodsUserCache {
    public final RedissonService redissonService;
    private final VerystatusGoodsCache verystatusGoodsCache;
    private final VerystatusGoodsUserMapper verystatusGoodsUserMapper;

    public void  delRedisUserGoods(String userNo, Integer goodsSku, LocalDate today){
        String redisKey = RedisKeyConstants.VERY_STATUS_USER_GOODS + userNo + ":" + goodsSku + ":" + today;
        RBucket<VerystatusGoodsUserCo> bucket = redissonService.getBucket(redisKey, VerystatusGoodsUserCo.class);
        bucket.delete();
    }

    public VerystatusGoodsUserCo getUserGoods(String userNo, Integer goodsSku, LocalDate today){
        String redisKey = RedisKeyConstants.VERY_STATUS_USER_GOODS + userNo + ":" + goodsSku + ":" + today;
        RBucket<VerystatusGoodsUserCo> bucket = redissonService.getBucket(redisKey, VerystatusGoodsUserCo.class);
        VerystatusGoodsUserCo verystatusGoodsUserCo = bucket.get();
        if(Objects.nonNull(verystatusGoodsUserCo)){
            return verystatusGoodsUserCo;
        }

        VerystatusGoodsModel verystatusGoodsModel = verystatusGoodsCache.getByGoodsSku(goodsSku);
        if (Objects.isNull(verystatusGoodsModel)){
            return null;
        }

        VerystatusGoodsUserModel verystatusGoodsUserModel = verystatusGoodsUserMapper.getInfo(goodsSku, userNo);
        verystatusGoodsUserCo = getCheckUserGoods(userNo, verystatusGoodsModel, verystatusGoodsUserModel, today);
        bucket.set(verystatusGoodsUserCo,RedisKeyConstants.EXPIRE_BY_TWO_HOUR,TimeUnit.SECONDS);
        return verystatusGoodsUserCo;
    }

    public VerystatusGoodsUserCo getCheckUserGoods(String userNo,VerystatusGoodsModel verystatusGoodsModel,VerystatusGoodsUserModel verystatusGoodsUserModel, LocalDate today ){
        if (Objects.isNull(verystatusGoodsUserModel)){
            return addUserGoods(userNo,verystatusGoodsModel);
        }
        if (verystatusGoodsUserModel.getCreateDate().equals(today)){
            return dayInfoUserGoods(verystatusGoodsModel,verystatusGoodsUserModel);
        }
        return dayStartUserGoods(verystatusGoodsModel,verystatusGoodsUserModel);
    }


    public VerystatusGoodsUserCo addUserGoods(String userNo,VerystatusGoodsModel verystatusGoodsModel){
        VerystatusGoodsUserModel verystatusGoodsUserModel = new VerystatusGoodsUserModel();

        verystatusGoodsUserModel.setGoodsSku(verystatusGoodsModel.getGoodsSku());
        verystatusGoodsUserModel.setUserNo(userNo);
        verystatusGoodsUserModel.setPriceType(verystatusGoodsModel.getPriceType());
        verystatusGoodsUserModel.setVideoFinish(0);
        verystatusGoodsUserModel.setFinishTimes(0);
        verystatusGoodsUserModel.setIsFinish(1);
        verystatusGoodsUserModel.setShowType(verystatusGoodsModel.getShowType());
        verystatusGoodsUserModel.setUseNum(0);
        verystatusGoodsUserModel.setContent("");
        verystatusGoodsUserModel.setContentImg("");

        VerystatusGoodsUserCo verystatusGoodsUserCo = newVerystatusGoodsUserCo(verystatusGoodsModel);

        String lockKey = RedisKeyConstants.VERY_STATUS_LOCK_USER_GOODS_DAY_START + userNo + verystatusGoodsModel.getGoodsSku();
        RLock linkLock = redissonService.getLock(lockKey);
        try {
            if (!linkLock.tryLock(RedisKeyConstants.EXPIRE_BY_FIVE_SECONDS, 0, TimeUnit.MINUTES)) {
                return verystatusGoodsUserCo;
            }
        } catch (InterruptedException exception) {
            return verystatusGoodsUserCo;
        }
        verystatusGoodsUserMapper.insertInfo(verystatusGoodsUserModel);
        return verystatusGoodsUserCo;
    }

    public VerystatusGoodsUserCo dayStartUserGoods(VerystatusGoodsModel verystatusGoodsModel,VerystatusGoodsUserModel verystatusGoodsUserModel){
        verystatusGoodsUserModel.setGoodsSku(verystatusGoodsModel.getGoodsSku());
        verystatusGoodsUserModel.setPriceType(verystatusGoodsModel.getPriceType());
        verystatusGoodsUserModel.setVideoFinish(0);
        verystatusGoodsUserModel.setFinishTimes(0);
        verystatusGoodsUserModel.setIsFinish(1);
        verystatusGoodsUserModel.setShowType(verystatusGoodsModel.getShowType());
        verystatusGoodsUserModel.setUseNum(0);
        verystatusGoodsUserModel.setContent("");
        verystatusGoodsUserModel.setContentImg("");

        verystatusGoodsUserMapper.updateInfo(verystatusGoodsUserModel);

        return newVerystatusGoodsUserCo(verystatusGoodsModel);
    }

    public VerystatusGoodsUserCo newVerystatusGoodsUserCo(VerystatusGoodsModel verystatusGoodsModel){
        VerystatusGoodsUserCo verystatusGoodsUserCo = new VerystatusGoodsUserCo();
        verystatusGoodsUserCo.setGoodsSku(verystatusGoodsModel.getGoodsSku());
        verystatusGoodsUserCo.setPriceType(verystatusGoodsModel.getPriceType());
        verystatusGoodsUserCo.setFreeNum(verystatusGoodsModel.getFreeNum());
        verystatusGoodsUserCo.setCoin(verystatusGoodsModel.getCoin());
        verystatusGoodsUserCo.setVideoNum(verystatusGoodsModel.getVideoNum());
        verystatusGoodsUserCo.setUserVideoFinish(0);
        verystatusGoodsUserCo.setUserFinishTime(0);
        verystatusGoodsUserCo.setRepeatTime(verystatusGoodsModel.getRepeatTime());
        verystatusGoodsUserCo.setUserIsFinish(1);
        verystatusGoodsUserCo.setShowType(verystatusGoodsModel.getShowType());
        verystatusGoodsUserCo.setContent("");
        verystatusGoodsUserCo.setContentImg("");
        verystatusGoodsUserCo.setUserUseNum(0);

        return verystatusGoodsUserCo;
    }

    public VerystatusGoodsUserCo dayInfoUserGoods(VerystatusGoodsModel verystatusGoodsModel,VerystatusGoodsUserModel verystatusGoodsUserModel){
        VerystatusGoodsUserCo verystatusGoodsUserCo = new VerystatusGoodsUserCo();
        verystatusGoodsUserCo.setGoodsSku(verystatusGoodsModel.getGoodsSku());
        verystatusGoodsUserCo.setPriceType(verystatusGoodsModel.getPriceType());
        verystatusGoodsUserCo.setFreeNum(verystatusGoodsModel.getFreeNum());
        verystatusGoodsUserCo.setCoin(verystatusGoodsModel.getCoin());
        verystatusGoodsUserCo.setVideoNum(verystatusGoodsModel.getVideoNum());
        verystatusGoodsUserCo.setUserVideoFinish(verystatusGoodsUserModel.getVideoFinish());
        verystatusGoodsUserCo.setUserFinishTime(verystatusGoodsUserModel.getFinishTimes());
        verystatusGoodsUserCo.setRepeatTime(verystatusGoodsModel.getRepeatTime());
        verystatusGoodsUserCo.setUserIsFinish(verystatusGoodsUserModel.getIsFinish());
        verystatusGoodsUserCo.setShowType(verystatusGoodsModel.getShowType());
        verystatusGoodsUserCo.setContent(verystatusGoodsUserModel.getContent());
        verystatusGoodsUserCo.setContentImg(verystatusGoodsUserModel.getContentImg());
        verystatusGoodsUserCo.setUserUseNum(verystatusGoodsUserModel.getUseNum());

        return verystatusGoodsUserCo;
    }

    public void updateUserGoods(VerystatusGoodsUserModel verystatusGoodsUserModel){
        verystatusGoodsUserMapper.updateInfo(verystatusGoodsUserModel);
        delRedisUserGoods(verystatusGoodsUserModel.getUserNo(),verystatusGoodsUserModel.getGoodsSku(),LocalDate.now());
    }


}
