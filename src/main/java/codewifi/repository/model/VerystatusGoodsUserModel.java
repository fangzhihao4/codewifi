package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VerystatusGoodsUserModel {
    Integer id;
    String userNo;
    LocalDate createDate;
    Integer goodsSku; //商品sku
    Integer priceType; //1仅金币 2仅观看视频  3金币或视频
    Integer videoFinish;//完成观看视频的次数
    Integer finishTimes;//完成该商品次数
    Integer isFinish; //1未完成 2已完成
    Integer showType;//1文本内容 2图片  3文本加图片
    Integer useNum;//已经使用免费次数
    String contentImg;
    String content;
}
