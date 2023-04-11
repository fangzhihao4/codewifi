package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.VerystatusMenuConfigMapper;
import codewifi.repository.model.VerystatusMenuConfigModel;
import codewifi.utils.JsonUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.jooq.tools.StringUtils;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class VerystatusMenuConfigCache {
    public final RedissonService redissonService;
    public final VerystatusMenuConfigMapper verystatusMenuConfigMapper;
    public final JsonUtil jsonUtil;

    public List<VerystatusMenuConfigModel> getAllConfig(){
        RBucket<String> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_SYSTEM_MENU, String.class);
        String listStr = bucket.get();
        if (!StringUtils.isEmpty(listStr)){
            List<VerystatusMenuConfigModel> list = JSON.parseArray(listStr,VerystatusMenuConfigModel.class);
            if (!list.isEmpty()){
                return list;
            }
        }
        List<VerystatusMenuConfigModel> dbList = verystatusMenuConfigMapper.getAllConfig();
        if (dbList.isEmpty()){
            return new ArrayList<>();
        }
        String jsonStr = jsonUtil.writeValueAsString(dbList);
        setAllMenu(jsonStr);
        return dbList;
    }

    public void setAllMenu(String listStr){
        RBucket<String> bucket = redissonService.getBucket(RedisKeyConstants.VERY_STATUS_SYSTEM_MENU, String.class);
        bucket.set(listStr,RedisKeyConstants.EXPIRE_BY_ONE_HOUR, TimeUnit.SECONDS );
    }
}
