package codewifi.response.very;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerystatusGoodsUserInfoResponse {
    Integer goodsSku;//商品sku
    Integer priceType; //使用类型 使用类型1仅金币 2仅观看视频  3金币或视频 4免费
    BigDecimal coin;//金币值
    Integer freeTotalNum; //可免费总数
    Integer freeUseNum;//已经使用免费总数
    Integer videoNeed;//需要观看视频次数
    Integer videFinish;//已经观看视频次数
    Integer isFinish; //今天是否可继续  1未完成可继续  2已完成不可继续
    Integer showType;//显示类型 1文本内容 2图片  3文本加图片 4显示部分
    String content;
    String contentImg;
    Object other;
}
