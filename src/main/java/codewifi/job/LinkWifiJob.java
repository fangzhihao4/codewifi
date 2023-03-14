package codewifi.job;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.redisson.api.RDeque;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class LinkWifiJob {
    private static final LogUtil logUtil = LogUtil.getLogger(LinkWifiJob.class);
    private static final String V2 = "LinkWifiJob";
    private final Executor executor;
    private final RedissonService redissonService;

    @Async
    public void msgHandle(){
        for (int i = 1; i<= 10; i++){
            executor.execute(this::pollMsg);
        }
    }

    public void pollMsg(){
        while (true){
//            try {
//                RDeque<String> msgString = redissonService.getDeque(RedisKeyConstants.LINK_USER_SORED,String.class);
//                String message = msgString.pollFirst();
//                if (Objects.isNull(message)){
//                    TimeUnit.SECONDS.sleep(2);
//                    continue;
//                }else{
//                    messageService.getMessage(message);
//                }
//            }catch (Exception e){
//                logUtil.error(v1, v2, "pollMsg", ExceptionUtils.getStackTrace(e),
//                        null, null);
//            }

        }

    }
}
