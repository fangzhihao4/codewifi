package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.co.UserLinkWifiCo;
import codewifi.utils.JsonUtil;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.redisson.api.RDeque;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LinkWIfiRedisCache {
    private static final LogUtil logUtil = LogUtil.getLogger(LinkWIfiRedisCache.class);
    private static final String V2 = "UserCreateWifiCache";

    public final RedissonService redissonService;
    public final JsonUtil jsonUtil;
    public final Integer pageSize = 20;

    public void addLinkRecord(UserLinkWifiCo userLinkWifiCo){
        RDeque<String> msgString = redissonService.getDeque(RedisKeyConstants.LINK_USER_SORED,String.class);
        msgString.add(jsonUtil.writeValueAsString(userLinkWifiCo));
    }
}
