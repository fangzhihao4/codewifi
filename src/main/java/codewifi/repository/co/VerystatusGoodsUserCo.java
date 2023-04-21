package codewifi.repository.co;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerystatusGoodsUserCo {
    Integer goodsSku;//商品sku
    Integer priceType; //使用类型 使用类型1仅金币 2仅观看视频  3金币或视频 4免费
    BigDecimal coin;//金币值
    Integer freeTotalNum; //免费可用总次数
    Integer freeUseNum; //已经使用总次数
    Integer videoNeed;//需要观看视频次数
    Integer videoFinish; //视频完成次数
    Integer isFinish; // 1今日未完成 2今日已完成
    Integer repeatTotalNum;//可重复次数
    Integer repeatUseNum;//已经重复次数
    Integer showType;//显示类型 1文本内容 2图片  3文本加图片
}
