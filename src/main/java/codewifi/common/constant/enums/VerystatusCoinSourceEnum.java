package codewifi.common.constant.enums;

import lombok.Getter;

@Getter
public enum VerystatusCoinSourceEnum {
    TO_DAY_VIDEO(1, VerystatusGoodsEnum.TO_DAY_VIDEO.getGoodsSku(), 2,"每日视频获得收益"),


    IS_DEFAULT(0, 0, 3,"默认");
    private final Integer source;
    private final Integer goodsSku;
    private final Integer changeType;//1扣钱 2加钱 3不变化
    private final String name;

    VerystatusCoinSourceEnum(Integer source, Integer goodsSku, Integer changeType , String name) {
        this.source = source;
        this.goodsSku = goodsSku;
        this.changeType = changeType;
        this.name = name;
    }

    public static VerystatusCoinSourceEnum getGoodsEnum(Integer goodsSku) {
        VerystatusCoinSourceEnum[] verystatusCoinSourceEnums = values();
        for (VerystatusCoinSourceEnum verystatusCoinSourceEnum : verystatusCoinSourceEnums) {
            if (verystatusCoinSourceEnum.getGoodsSku().equals(goodsSku)) {
                return verystatusCoinSourceEnum;
            }
        }
        return VerystatusCoinSourceEnum.IS_DEFAULT;
    }
}
