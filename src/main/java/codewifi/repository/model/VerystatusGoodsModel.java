package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerystatusGoodsModel {
    Integer id;
    Integer goodsSku;//商品sku
    Integer priceType;//1仅金币 2仅观看视频  3金币或视频
    Integer freeNum;//可免费使用次数
    BigDecimal coin;//使用金币数量
    Integer videoNum;//需要观看视频数量
    Integer repeatTime;//可重复使用次数
    Integer showType;//0不展示 1文本 2图片  3文本加图片
    Byte status;//1使用中 2暂停中
}
