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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class VerystatusGoodsUserCache {
    public final RedissonService redissonService;
    private final VerystatusGoodsCache verystatusGoodsCache;
    private final VerystatusGoodsUserMapper verystatusGoodsUserMapper;

    /**
     * 删除缓存
     * @param userNo 用户no
     * @param goodsSku 商品sku
     * @param today 时间
     */
    public void  delRedisUserGoods(String userNo, Integer goodsSku, LocalDate today){
        String redisKey = RedisKeyConstants.VERY_STATUS_USER_GOODS + userNo + ":" + goodsSku + ":" + today;
        RBucket<VerystatusGoodsUserCo> bucket = redissonService.getBucket(redisKey, VerystatusGoodsUserCo.class);
        bucket.delete();
    }

    /**
     * 获取单个商品缓存
     * @param userNo 用户信息
     * @param goodsSku 商品sku
     * @param today 事件
     * @return 商品信息
     */
    public VerystatusGoodsUserCo getUserGoods(String userNo, Integer goodsSku, LocalDate today){
        String redisKey = RedisKeyConstants.VERY_STATUS_USER_GOODS + userNo + ":" + goodsSku + ":" + today;
        RBucket<VerystatusGoodsUserCo> bucket = redissonService.getBucket(redisKey, VerystatusGoodsUserCo.class);
        VerystatusGoodsUserCo verystatusGoodsUserCo = bucket.get();
        if(Objects.nonNull(verystatusGoodsUserCo)){
            return verystatusGoodsUserCo;
        }

        //数据库有数据且是当天的
        VerystatusGoodsUserModel verystatusGoodsUserModel = verystatusGoodsUserMapper.getInfo(goodsSku, userNo);
        if (Objects.nonNull(verystatusGoodsUserModel) && today.equals(verystatusGoodsUserModel.getCreateDate())){
            verystatusGoodsUserCo = userGoodToCache(verystatusGoodsUserModel);
            bucket.set(verystatusGoodsUserCo,RedisKeyConstants.EXPIRE_BY_TWO_HOUR,TimeUnit.SECONDS);
            return verystatusGoodsUserCo;
        }

        VerystatusGoodsModel verystatusGoodsModel = verystatusGoodsCache.getByGoodsSku(goodsSku);
        if (Objects.isNull(verystatusGoodsModel)){
            return null;
        }

        //重新初始化
        VerystatusGoodsUserModel verystatusGoodsUserModelStart = goodsToUserGoods(verystatusGoodsModel,today);
        verystatusGoodsUserModelStart.setUserNo(userNo);
        addOrStartUserGoods(today,verystatusGoodsUserModel,verystatusGoodsUserModelStart );
        verystatusGoodsUserCo = userGoodToCache(verystatusGoodsUserModelStart);
        bucket.set(verystatusGoodsUserCo,RedisKeyConstants.EXPIRE_BY_TWO_HOUR,TimeUnit.SECONDS);
        return verystatusGoodsUserCo;
    }


    /**
     * 获取列表
     * @param userNo 用户no
     * @param goodsSku 商品列表
     * @param today 今天
     * @return 列表
     */
    public List<VerystatusGoodsUserCo> getUserGoodsList(String userNo, List<Integer> goodsSku, LocalDate today){
        List<VerystatusGoodsUserCo> goodsList = new ArrayList<>();
        for (Integer goodSku : goodsSku){
            VerystatusGoodsUserCo verystatusGoodsUserCo = getUserGoods(userNo, goodSku, today);
            if (Objects.nonNull(verystatusGoodsUserCo)){
                goodsList.add(verystatusGoodsUserCo);
            }
        }
        return goodsList;
    }


    /**
     * 新增或初始每天数据
     * @param today 日期
     * @param verystatusGoodsUserModel 原来的数据
     * @param verystatusGoodsUserModelStart 新组装的数据
     */
    public void addOrStartUserGoods(LocalDate today, VerystatusGoodsUserModel verystatusGoodsUserModel, VerystatusGoodsUserModel verystatusGoodsUserModelStart){
        String lockKey = RedisKeyConstants.VERY_STATUS_LOCK_USER_GOODS_DAY_START + verystatusGoodsUserModelStart.getUserNo() + verystatusGoodsUserModelStart.getGoodsSku();
        RLock linkLock = redissonService.getLock(lockKey);
        try {
            if (linkLock.tryLock(0, RedisKeyConstants.EXPIRE_BY_FIVE_SECONDS, TimeUnit.MINUTES)) {
                //如果数据库有 且不是当天的
                if (Objects.nonNull(verystatusGoodsUserModel) && !today.equals(verystatusGoodsUserModel.getCreateDate())){
                    verystatusGoodsUserMapper.startDayInfo(verystatusGoodsUserModelStart);
                }

                //数据库没有  新增
                if (Objects.isNull(verystatusGoodsUserModel)){
                    verystatusGoodsUserMapper.insertInfo(verystatusGoodsUserModelStart);
                }
            }
        } catch (InterruptedException ignored) {
            return;
        }finally {
            if (linkLock.isLocked())
                linkLock.unlock();
        }
    }

    /**
     * 商品数据转用户商品数据
     * @param verystatusGoodsModel 商品数据
     * @param today 今天
     * @return 用户商品数据
     */
    public VerystatusGoodsUserModel goodsToUserGoods(VerystatusGoodsModel verystatusGoodsModel, LocalDate today){
        VerystatusGoodsUserModel verystatusGoodsUserModel = new VerystatusGoodsUserModel();
        verystatusGoodsUserModel.setGoodsSku(verystatusGoodsModel.getGoodsSku());
        verystatusGoodsUserModel.setCreateDate(today);
        verystatusGoodsUserModel.setPriceType(verystatusGoodsModel.getPriceType());
        verystatusGoodsUserModel.setCoin(verystatusGoodsModel.getCoin());
        verystatusGoodsUserModel.setFreeTotalNum(verystatusGoodsModel.getFreeNum());
        verystatusGoodsUserModel.setFreeUseNum(0);
        verystatusGoodsUserModel.setVideoNeed(verystatusGoodsModel.getVideoNum());
        verystatusGoodsUserModel.setVideoFinish(verystatusGoodsModel.getVideoNum());
        verystatusGoodsUserModel.setIsFinish(VerystatusGoodsUserMapper.NO_FINISH);
        verystatusGoodsUserModel.setRepeatTotalNum(verystatusGoodsModel.getRepeatTime());
        verystatusGoodsUserModel.setRepeatUseNum(0);
        verystatusGoodsUserModel.setShowType(verystatusGoodsModel.getShowType());
        verystatusGoodsUserModel.setContent("");
        verystatusGoodsUserModel.setContentImg("");
        return verystatusGoodsUserModel;
    }


    /**
     * 用户的商品数据转缓存
     * @param verystatusGoodsUserModel 用户商品数据
     * @return 商品缓存
     */
    public VerystatusGoodsUserCo userGoodToCache(VerystatusGoodsUserModel verystatusGoodsUserModel){
        VerystatusGoodsUserCo verystatusGoodsUserCo = new VerystatusGoodsUserCo();
        verystatusGoodsUserCo.setGoodsSku(verystatusGoodsUserModel.getGoodsSku());
        verystatusGoodsUserCo.setPriceType(verystatusGoodsUserModel.getPriceType());
        verystatusGoodsUserCo.setCoin(verystatusGoodsUserModel.getCoin());
        verystatusGoodsUserCo.setFreeTotalNum(verystatusGoodsUserModel.getFreeTotalNum());
        verystatusGoodsUserCo.setFreeUseNum(verystatusGoodsUserModel.getFreeUseNum());
        verystatusGoodsUserCo.setVideoNeed(verystatusGoodsUserModel.getVideoNeed());
        verystatusGoodsUserCo.setVideoFinish(verystatusGoodsUserModel.getVideoFinish());
        verystatusGoodsUserCo.setIsFinish(verystatusGoodsUserModel.getIsFinish());
        verystatusGoodsUserCo.setRepeatTotalNum(verystatusGoodsUserModel.getRepeatTotalNum());
        verystatusGoodsUserCo.setRepeatUseNum(verystatusGoodsUserModel.getRepeatUseNum());
        verystatusGoodsUserCo.setShowType(verystatusGoodsUserModel.getShowType());
        verystatusGoodsUserCo.setContent(verystatusGoodsUserModel.getContent());
        verystatusGoodsUserCo.setContentImg(verystatusGoodsUserModel.getContentImg());
        return verystatusGoodsUserCo;
    }

    public void updateUserGoods(VerystatusGoodsUserModel verystatusGoodsUserModel){
        verystatusGoodsUserMapper.startDayInfo(verystatusGoodsUserModel);
        delRedisUserGoods(verystatusGoodsUserModel.getUserNo(),verystatusGoodsUserModel.getGoodsSku(),LocalDate.now());
    }

    public void addUserVideo(String userNo,Integer goodSku,LocalDate today){
        verystatusGoodsUserMapper.addVideo(userNo,goodSku);
        delRedisUserGoods(userNo,goodSku,today);
    }


}
