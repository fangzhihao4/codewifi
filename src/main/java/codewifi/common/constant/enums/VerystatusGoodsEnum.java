package codewifi.common.constant.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum VerystatusGoodsEnum {
    //使用类型1仅金币 2仅观看视频  3金币或视频 4免费
    //展示类型 0 不展示  1文本展示  2图片展示 3文本和图片展示
    //              商品sku           使用类型  免费使用次数            金币数量    需要观看视频次数    可重复次数   展示类型        说明
    STAR_TODAY      (2, 4, 1,BigDecimal.valueOf(0),  0,  1, 1,"星座今日运势"),
    STAR_NEXT_DAY   (3, 3, 0,BigDecimal.valueOf(10), 1,  1, 1,"星座明日运势"),
    STAR_WEEK       (4, 3, 0,BigDecimal.valueOf(20), 1,  1, 1,"星座本周运势"),
    STAR_MONTH      (5, 3, 0,BigDecimal.valueOf(20), 1,  1, 1,"星座本月运势"),
    STAR_YEAR       (6, 3, 0,BigDecimal.valueOf(30), 1,  1, 1,"星座今年运势"),
    STAR_LOVE       (7, 3, 0,BigDecimal.valueOf(20), 1,  1, 1,"星座爱情运势"),

    WORK_SAO_HUA    (8, 3, 5,BigDecimal.valueOf(2),  1,  999, 1,"骚话"),
    WORK_QING_HUA   (9, 3, 5,BigDecimal.valueOf(2),  1,  999, 1,"情话"),


    TO_DAY_VIDEO    (1, 2, 0,BigDecimal.valueOf(20),10,  1, 0,"每日视频");


    private final Integer goodsSku;
    private final Integer priceType;
    private final Integer freeNum;
    private final BigDecimal coin;
    private final Integer videoNum;
    private final Integer repeatTime;
    private final Integer showType;
    private final String name;

    VerystatusGoodsEnum(Integer goodsSku, Integer priceType, Integer freeNum,BigDecimal coin, Integer videoNum, Integer repeatTime, Integer showType, String name) {
        this.goodsSku = goodsSku;
        this.priceType = priceType;
        this.freeNum = freeNum;
        this.coin = coin;
        this.videoNum = videoNum;
        this.repeatTime = repeatTime;
        this.showType = showType;
        this.name = name;
    }

    public static VerystatusGoodsEnum getGoodsEnum(Integer goodsType) {
        VerystatusGoodsEnum[] verystatusGoodsEnums = values();
        for (VerystatusGoodsEnum verystatusGoodsEnum : verystatusGoodsEnums) {
            if (verystatusGoodsEnum.getGoodsSku().equals(goodsType)) {
                return verystatusGoodsEnum;
            }
        }
        return null;
    }
}
