package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VerystatusGoodsContentModel {
    Integer id;
    Integer goodsSku;
    String goodsNo;
    String content;
    LocalDate createDate;
}
