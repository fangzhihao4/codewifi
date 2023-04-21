package codewifi.response.very;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VerystatusGoodsUserInfoResponse {
    Integer goodsSku;//商品sku
    Integer priceType; //使用类型 使用类型1仅金币 2仅观看视频  3金币或视频 4免费
    Integer freeNum; //免费次数
    BigDecimal coin;//金币值
    Integer videoNum;//需要观看视频次数
    Integer userVideoFinish;//已经观看视频次数
    Integer userFinishTime;//该任务完成次数
    Integer repeatTime;//可以重复次数
    Integer userIsFinish;//是否完成本次
    Integer showType;//显示类型 1文本内容 2图片  3文本加图片
    String content;
    String contentImg;
    Object other;
    Integer userUseNum;//以及使用免费次数
}
