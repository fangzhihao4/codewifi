package codewifi.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VerystatusGoodsUserModel {
    Integer id;
    String userNo;
    LocalDate createDate;
    Integer goodsSku; //商品sku
    Integer priceType; //1仅金币 2仅观看视频  3金币或视频 4免费
    BigDecimal coin;//需要金币数量
    Integer freeTotalNum; //可以免费次数
    Integer freeUseNum; //已经使用免费次数
    Integer videoNeed; //需要观看视频次数
    Integer videoFinish;//已经观看视频的次数
    Integer isFinish; //今天是否完成 1未完成 2已完成
    Integer repeatTotalNum; //可重复次数
    Integer repeatUseNum; //已经重复次数
    Integer showType;//1文本内容 2图片  3文本加图片
    String contentImg;
    String content;
}
