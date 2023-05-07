package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.VerystatusLotMapper;
import codewifi.repository.model.VerystatusLotModel;
import codewifi.utils.JsonUtil;
import jodd.util.StringUtil;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class VerystatusLotCache {
    public final RedissonService redissonService;
    public final VerystatusLotMapper verystatusLotMapper;
    public final JsonUtil jsonUtil;

    public String getUserCommon(String userNo, Integer drawType) {
        RBucket<VerystatusLotModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_LOT_USER_COMMON + userNo, VerystatusLotModel.class);
        VerystatusLotModel verystatusLotModel = bucket.get();
        if (Objects.isNull(verystatusLotModel)) {
            verystatusLotModel = verystatusLotMapper.getByDayType(VerystatusLotMapper.type_common);
            if (Objects.isNull(verystatusLotModel)) {
                return "";
            }
            verystatusLotModel.setIsDraw(1);
            long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());

            bucket.set(verystatusLotModel, time, TimeUnit.SECONDS);
        }
        String name = verystatusLotModel.getName();
        String drawLog = verystatusLotModel.getLots();
        String title = verystatusLotModel.getTitleOne();
        String message = "你抽中了 " + name +
                "\n凶吉:" + drawLog +
                "\n签文:" + title;

        if (drawType == 2 || (2 == verystatusLotModel.getIsDraw())) {
            message = message + "\n\n";
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleTwo())) {
                message = message + "解签:" + verystatusLotModel.getTitleTwo();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleTree())) {
                message = message + "\n注释:" + verystatusLotModel.getTitleTree();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleFour())) {
                message = message + "\n白话解析:" + verystatusLotModel.getTitleFour();
            }
            if (verystatusLotModel.getIsDraw() != 2) {
                verystatusLotModel.setIsDraw(2);
                long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());

                bucket.set(verystatusLotModel, time, TimeUnit.SECONDS);
            }
            return message;
        }

        return message;
    }

    public String getUserMan(String userNo, Integer drawType) {
        RBucket<VerystatusLotModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_LOT_USER_MAN + userNo, VerystatusLotModel.class);
        VerystatusLotModel verystatusLotModel = bucket.get();
        if (Objects.isNull(verystatusLotModel)) {
            verystatusLotModel = verystatusLotMapper.getByDayType(VerystatusLotMapper.type_men);
            if (Objects.isNull(verystatusLotModel)) {
                return "";
            }
            verystatusLotModel.setIsDraw(1);
            long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());

            bucket.set(verystatusLotModel, time, TimeUnit.SECONDS);
        }
        String name = verystatusLotModel.getName();
        String drawLog = verystatusLotModel.getLots();
        String title = verystatusLotModel.getTitleOne();
        String message = "你抽中了 " + name +
                "\n凶吉:" + drawLog +
                "\n签文:" + title;

        if ((drawType == 2) || (2 == verystatusLotModel.getIsDraw())) {
            message = message + "\n\n";
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleTwo())) {
                message = message + "\n缘份指数:" + verystatusLotModel.getTitleTwo();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleTree())) {
                message = message + "\n幸福指数:" + verystatusLotModel.getTitleTree();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleFour())) {
                message = message + "\n暧昧指数:" + verystatusLotModel.getTitleFour();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleFive())) {
                message = message + "\n缠绵指数:" + verystatusLotModel.getTitleFive();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleSix())) {
                message = message + "\n约会成功指数:" + verystatusLotModel.getTitleSix();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleSeven())) {
                message = message + "\n告白成功指数:" + verystatusLotModel.getTitleSeven();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleEight())) {
                message = message + "\n复合成功指数:" + verystatusLotModel.getTitleEight();
            }
            if (verystatusLotModel.getIsDraw() != 2) {
                verystatusLotModel.setIsDraw(2);
                long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());

                bucket.set(verystatusLotModel, time, TimeUnit.SECONDS);
            }
            return message;
        }

        return message;
    }

    public String getUserMoney(String userNo, Integer drawType) {
        RBucket<VerystatusLotModel> bucket = redissonService.getBucket(RedisKeyConstants.VERY_LOT_USER_MONEY + userNo, VerystatusLotModel.class);
        VerystatusLotModel verystatusLotModel = bucket.get();
        if (Objects.isNull(verystatusLotModel)) {
            verystatusLotModel = verystatusLotMapper.getByDayType(VerystatusLotMapper.type_money);
            if (Objects.isNull(verystatusLotModel)) {
                return "";
            }
            verystatusLotModel.setIsDraw(1);
            long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());

            bucket.set(verystatusLotModel, time, TimeUnit.SECONDS);
        }
        String name = verystatusLotModel.getName();
        String drawLog = verystatusLotModel.getLots();

        String message = "你抽中了 " + name +
                "\n签文:" + drawLog +
                "\n注释:" + verystatusLotModel.getTitleTwo();

        if ((drawType == 2) || (2 == verystatusLotModel.getIsDraw())) {
            message = message + "\n\n";
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleTwo())) {
                message = message + "\n米力仙注:" + verystatusLotModel.getTitleTwo();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleTree())) {
                message = message + "\n\n解签:" + verystatusLotModel.getTitleTree();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleFour())) {
                message = message + "\n\n得签着:" + verystatusLotModel.getTitleFour();
            }
            if (StringUtil.isNotBlank(verystatusLotModel.getTitleFive())) {
                message = message + "\n\n事宜:" + verystatusLotModel.getTitleFive();
            }
            if (verystatusLotModel.getIsDraw() != 2) {
                verystatusLotModel.setIsDraw(2);
                long time = ChronoUnit.SECONDS.between(LocalDate.now().atStartOfDay(), LocalDateTime.now());

                bucket.set(verystatusLotModel, time, TimeUnit.SECONDS);
            }
            return message;
        }

        return message;
    }
}
