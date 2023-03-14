package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.WxCodeSceneMapper;
import codewifi.repository.model.WxCodeSceneModel;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class WxCodeSceneCache {
    public final RedissonService redissonService;
    public final WxCodeSceneMapper wxCodeSceneMapper;

    public WxCodeSceneModel getByScene(Integer scene){
        RBucket<WxCodeSceneModel> bucket = redissonService.getBucket(RedisKeyConstants.WX_CODE_SCENE + scene, WxCodeSceneModel.class);
        WxCodeSceneModel wxCodeSceneModel = bucket.get();
        if (Objects.nonNull(wxCodeSceneModel)){
            return wxCodeSceneModel;
        }
        wxCodeSceneModel = wxCodeSceneMapper.getById(scene);
        if (Objects.isNull(wxCodeSceneModel)){
            return null;
        }
        bucket.set(wxCodeSceneModel, RedisKeyConstants.EXPIRE_BY_TWO_HOUR, TimeUnit.SECONDS);
        return wxCodeSceneModel;
    }

    public WxCodeSceneModel addCodeScene(WxCodeSceneModel wxCodeSceneModel) {
        return wxCodeSceneMapper.addScene(wxCodeSceneModel);
    }
}
