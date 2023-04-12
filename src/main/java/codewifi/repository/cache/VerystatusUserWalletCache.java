package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.VerystatusUserWalletMapper;
import codewifi.repository.model.VerystatusUserWalletModel;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class VerystatusUserWalletCache {
    public final RedissonService redissonService;
    private final VerystatusUserWalletMapper verystatusUserWalletMapper;

    public VerystatusUserWalletModel addUserWallet(VerystatusUserWalletModel verystatusUserWalletModel){
        verystatusUserWalletMapper.add(verystatusUserWalletModel);
        setUserWallet(verystatusUserWalletModel.getUserNo(),verystatusUserWalletModel);
        return verystatusUserWalletModel;
    }

    public void setUserWallet(String userNo, VerystatusUserWalletModel verystatusUserWalletModel){
        RBucket<VerystatusUserWalletModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_USER_WALLET + userNo, VerystatusUserWalletModel.class);
        bucket.set(verystatusUserWalletModel,RedisKeyConstants.EXPIRE_BY_ONE_HOUR, TimeUnit.SECONDS );
    }

    public VerystatusUserWalletModel getUserWallet(String userNo){
        RBucket<VerystatusUserWalletModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_USER_WALLET + userNo, VerystatusUserWalletModel.class);
        VerystatusUserWalletModel verystatusUserWalletModel = bucket.get();
        if (Objects.nonNull(verystatusUserWalletModel)){
            return verystatusUserWalletModel;
        }

        verystatusUserWalletModel = verystatusUserWalletMapper.getUserWallet(userNo);
        if (Objects.isNull(verystatusUserWalletModel)){
            return null;
        }
        setUserWallet(userNo,verystatusUserWalletModel);
        return verystatusUserWalletModel;
    }

    public void delRedisUserWallet(String userNo){
        RBucket<VerystatusUserWalletModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_USER_WALLET + userNo, VerystatusUserWalletModel.class);
        bucket.delete();
    }

    /**
     * 变更金币
     * @param changeType true 增加  false 扣减
     * @param userNo
     * @param changeCoin
     * @return
     */
    public boolean changeUserCoin(boolean changeType, String userNo, BigDecimal changeCoin){
        RLock rLock = redissonService.getLock(RedisKeyConstants.VERY_STATUS_LOCK_USER_WALLET + userNo);
        try {
            if (!rLock.tryLock(5, 5, TimeUnit.SECONDS))
                return false;
        }catch (InterruptedException ignored) {
            return false;
        }
        // 执行业务
        try {
            if (!changeType){
                changeCoin = changeCoin.multiply(BigDecimal.valueOf(-1));
            }
            verystatusUserWalletMapper.changeCoin(userNo,changeCoin);
            delRedisUserWallet(userNo);
            return true;
        }
        catch (Exception e) {
            return false;
        }
        finally {
            if (rLock.isLocked())
                rLock.unlock();
        }
    }
}
