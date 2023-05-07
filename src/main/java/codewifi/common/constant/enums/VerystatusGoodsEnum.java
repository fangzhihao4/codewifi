package codewifi.common.constant.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum VerystatusGoodsEnum {
    //使用类型1仅金币 2仅观看视频  3金币或视频 4免费
    //展示类型 0 不展示  1文本展示  2图片展示 3文本和图片展示
    //                             商品sku   使用类型  免费使用次数            金币数量    需要观看视频次数     可重复次数      展示类型        说明

    //星座
    STAR_TODAY          (1002, 4, 1,BigDecimal.valueOf(0),  0,  1, 1,"星座今日运势"),
    STAR_NEXT_DAY       (1003, 4, 0,BigDecimal.valueOf(10), 1,  1, 1,"星座明日运势"),
    STAR_WEEK           (1004, 4, 0,BigDecimal.valueOf(20), 1,  1, 1,"星座本周运势"),
    STAR_MONTH          (1005, 4, 0,BigDecimal.valueOf(20), 1,  1, 1,"星座本月运势"),
    STAR_YEAR           (1006, 4, 0,BigDecimal.valueOf(30), 1,  1, 1,"星座今年运势"),
    STAR_LOVE           (1007, 4, 0,BigDecimal.valueOf(20), 1,  1, 1,"星座爱情运势"),

    //随机一言
    WORK_SAO_HUA        (1101, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"骚话"),
    WORK_QING_HUA       (1102, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"情话"),
    WORK_JOKE           (1103, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"笑话"),

    //热榜热搜
    HOT_HU_PU           (1301, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"虎扑热榜"),
    HOT_ZHI_HU          (1302, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"知乎热榜"),
    HOT_KE_36           (1303, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"ke36热榜"),
    HOT_BAI_DU          (1304, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"百度热榜"),
    HOT_BI_LI           (1305, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"哔哩哔哩热榜"),
    HOT_TIE_BA          (1306, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"贴吧热榜"),
    HOT_WEI_BO          (1307, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"微博热榜"),
    HOT_DOU_YIN         (1307, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"抖音热榜"),

    //随机一图
    IMG_DONG_MAN        (1401, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"动漫"),
    IMG_MOBIL_GIRL      (1402, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"美女图"),
    IMG_TAO_GIRL        (1403, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"淘宝买家秀"),
    IMG_LOL_SPIN        (1404, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"lol壁纸"),
    IMG_HEAD_AVATAR     (1405, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"头像"),
    IMG_HEAD_NAN        (1406, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"男头像"),
    IMG_HEAD_NV         (1407, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"女头像"),
    IMG_HEAD_DM         (1408, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"动漫头像"),
    IMG_HEAD_JW         (1409, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"景物头像"),

    //历史今天
    HISTORY_TO_DAY      (1501, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"历史的今天"),

    //日历图
    CALENDAR_MO_YU      (1601, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"摸鱼人日历"),
    CALENDAR_ZHI_CHANG  (1602, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"职场人日历"),

    //openai
    OPENAI_USER_NAME    (1701, 3, 5,BigDecimal.valueOf(2),  1,  999, 1,"AI姓名解析"),
    OPENAI_REPORT       (1702, 3, 10,BigDecimal.valueOf(2),  1,  999, 1,"AI日报生成"),

    OPENAI_MAX          (1799, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"默认不返回"),


    WEATHER_CITY        (1801, 4, 10,BigDecimal.valueOf(2),  1,  999, 1,"天气预报"),

    LOT_COMMON          (1901, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"月老灵签"),
    LOT_MAN             (1902, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"月老灵签姻缘签"),
    LOT_MONEY           (1903, 4, 5,BigDecimal.valueOf(2),  1,  999, 1,"财神灵签"),



    TO_DAY_VIDEO    (1, 4, 0,BigDecimal.valueOf(20),10,  1, 0,"每日视频");


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
