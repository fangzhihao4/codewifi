package codewifi.service.impl.third;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.repository.mapper.VerystatusGoodsContentMapper;
import codewifi.repository.model.VerystatusGoodsContentModel;
import codewifi.sdk.sdkVhan.SdkVhanWorkService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.jooq.tools.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ThirdVhanWorkService {
    private static final LogUtil logUtil = LogUtil.getLogger(ThirdVhanWorkService.class);

    private static final String V1 = "very";
    private static final String V2 = "ThirdVhanWorkService";

    private static final Long saoHuaRate = 0L; //使用数据库的概率
    private static final Long qingHuaRate = 0L; //使用数据库的概率
    private static final Long addRate = 1L;
    private static final BigDecimal allBig = BigDecimal.valueOf(10000);

    private final RedissonService redissonService;
    private final VerystatusGoodsContentMapper verystatusGoodsContentMapper;
    private final SdkVhanWorkService sdkVhanWorkService;


    public String getSaoHua(Integer goodsSku){
        int randoms = BigDecimal.valueOf(Math.random()).multiply(allBig).intValue(); //随机的数值
        RAtomicLong rAtomicLong = redissonService.getAtomicLong(RedisKeyConstants.VERY_STATUS_WORK_SAO_HUA_RATE);
        long rate = rAtomicLong.get();

        //随机到的概率 大于查询数据库的概率 查询接口
        if (randoms > rate){
            String message = sdkVhanWorkService.getSaoHuaMessage();
            //查询到值了
            if(!StringUtils.isEmpty(message)){
                VerystatusGoodsContentModel dbInfo = verystatusGoodsContentMapper.getInfoByContent(goodsSku, message);
                rAtomicLong.addAndGet(addRate);

                //数据库有这条数据 增加查询数据库的概率
                if (Objects.nonNull(dbInfo)){
                    return message;
                }
                //数据库没有这条数据 新增到数据库 概率不变化
                dbInfo = new VerystatusGoodsContentModel();
                dbInfo.setContent(message);
                dbInfo.setGoodsSku(goodsSku);
                verystatusGoodsContentMapper.insertInfo(dbInfo);
                return message;
            }
        }
        //查询数据库
        VerystatusGoodsContentModel info = verystatusGoodsContentMapper.getInfo(goodsSku);
        return info.getContent();
    }

    public String getQingHua(Integer goodsSku){
        int randoms = BigDecimal.valueOf(Math.random()).multiply(allBig).intValue(); //随机的数值
        RAtomicLong rAtomicLong = redissonService.getAtomicLong(RedisKeyConstants.VERY_STATUS_WORK_QING_HUA_RATE);
        long rate = rAtomicLong.get();

        //随机到的概率 大于查询数据库的概率 查询接口
        if (randoms > rate){
            String message = sdkVhanWorkService.getQingHuaMessage();
            //查询到值了
            if(!StringUtils.isEmpty(message)){
                VerystatusGoodsContentModel dbInfo = verystatusGoodsContentMapper.getInfoByContent(goodsSku, message);
                rAtomicLong.addAndGet(addRate);

                //数据库有这条数据 增加查询数据库的概率
                if (Objects.nonNull(dbInfo)){
                    return message;
                }
                //数据库没有这条数据 新增到数据库 概率不变化
                dbInfo = new VerystatusGoodsContentModel();
                dbInfo.setContent(message);
                dbInfo.setGoodsSku(goodsSku);
                verystatusGoodsContentMapper.insertInfo(dbInfo);
                return message;
            }
        }
        //查询数据库
        VerystatusGoodsContentModel info = verystatusGoodsContentMapper.getInfo(goodsSku);
        return info.getContent();
    }
}
