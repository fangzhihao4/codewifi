package codewifi.repository.third;

import codewifi.common.RedissonService;
import codewifi.common.constant.RedisKeyConstants;
import codewifi.common.constant.enums.VerystatusGoodsEnum;
import codewifi.repository.mapper.VerystatusGoodsContentMapper;
import codewifi.repository.model.VerystatusGoodsContentModel;
import codewifi.sdk.sdkVhan.SdkVhanImageService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.jooq.tools.StringUtils;
import org.redisson.api.RAtomicLong;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ThirdVhanImgCache {

    private static final LogUtil logUtil = LogUtil.getLogger(ThirdVhanImgCache.class);

    private static final String V1 = "user";
    private static final String V2 = "ThirdStarService";
    private static final Long addRate = 1L;
    private static final BigDecimal allBig = BigDecimal.valueOf(10000);

    private final RedissonService redissonService;
    private final SdkVhanImageService sdkVhanImageService;
    private final VerystatusGoodsContentMapper verystatusGoodsContentMapper;

    public String getBySku(Integer goodsSku){
        if (VerystatusGoodsEnum.IMG_DONG_MAN.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_DONG_MAN_RATE );
        }
        if (VerystatusGoodsEnum.IMG_TAO_GIRL.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_TAO_GIRL_RATE );
        }
        if (VerystatusGoodsEnum.IMG_MOBIL_GIRL.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_MOBIL_GIRL_RATE );
        }
        if (VerystatusGoodsEnum.IMG_LOL_SPIN.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_LOL_SPIN_RATE );
        }
        if (VerystatusGoodsEnum.IMG_HEAD_AVATAR.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_HEAD_AVATAR_RATE );
        }
        if (VerystatusGoodsEnum.IMG_HEAD_NAN.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_HEAD_NAN_RATE );
        }
        if (VerystatusGoodsEnum.IMG_HEAD_NV.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_HEAD_NV_RATE );
        }
        if (VerystatusGoodsEnum.IMG_HEAD_DM.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_HEAD_DM_RATE );
        }
        if (VerystatusGoodsEnum.IMG_HEAD_JW.getGoodsSku().equals(goodsSku)){
            return commonImg(goodsSku,RedisKeyConstants.VERY_STATUS_IMG_HEAD_JW_RATE );
        }
        return null;
    }


    public String commonImg(Integer goodsSku, String redisKey){
        int randoms = BigDecimal.valueOf(Math.random()).multiply(allBig).intValue(); //随机的数值
        RAtomicLong rAtomicLong = redissonService.getAtomicLong(redisKey);
        long rate = rAtomicLong.get();
        //随机到的概率 大于查询数据库的概率 查询接口
        if (randoms > rate) {
            String imgHttp = getImgUrl(goodsSku);
            //查询到值了
            if (!StringUtils.isEmpty(imgHttp)) {
                VerystatusGoodsContentModel dbInfo = verystatusGoodsContentMapper.getInfoByContent(goodsSku, imgHttp);
                rAtomicLong.addAndGet(addRate);

                //数据库有这条数据 增加查询数据库的概率
                if (Objects.nonNull(dbInfo)) {
                    return imgHttp;
                }
                //数据库没有这条数据 新增到数据库 概率不变化
                dbInfo = new VerystatusGoodsContentModel();
                dbInfo.setContent(imgHttp);
                dbInfo.setGoodsSku(goodsSku);
                verystatusGoodsContentMapper.insertInfo(dbInfo);
                return imgHttp;
            }
        }
        //查询数据库
        VerystatusGoodsContentModel info = verystatusGoodsContentMapper.getInfo(goodsSku);
        return info.getContent();
    }

    public String getImgUrl(Integer goodsSku){
        if (VerystatusGoodsEnum.IMG_DONG_MAN.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getDongMan();
        }
        if (VerystatusGoodsEnum.IMG_TAO_GIRL.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getTaoImage();
        }
        if (VerystatusGoodsEnum.IMG_MOBIL_GIRL.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getMobilGirlImage();
        }
        if (VerystatusGoodsEnum.IMG_LOL_SPIN.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getLolSpinImage();
        }
        if (VerystatusGoodsEnum.IMG_HEAD_AVATAR.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getHeadAvatar();
        }
        if (VerystatusGoodsEnum.IMG_HEAD_NAN.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getHeadNan();
        }
        if (VerystatusGoodsEnum.IMG_HEAD_NV.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getHeadNv();
        }
        if (VerystatusGoodsEnum.IMG_HEAD_DM.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getHeadDm();
        }
        if (VerystatusGoodsEnum.IMG_HEAD_JW.getGoodsSku().equals(goodsSku)){
            return sdkVhanImageService.getHeadJw();
        }
        return  null;
    }

}
