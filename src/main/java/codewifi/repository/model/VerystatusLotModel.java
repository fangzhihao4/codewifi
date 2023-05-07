package codewifi.repository.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VerystatusLotModel {
    Byte type;              //1月老灵签    2月老灵签姻缘签  3财神灵签
    String name;            //签名称
    String lots;            //凶吉        凶吉           诗曰
    String titleOne;        //灵签解签     签文           典故
    String titleTwo;        //           缘份指数        米力仙注
    String titleTree;       //           幸福指数        说明
    String titleFour;       //           暧昧指数        求得此签着
    String titleFive;       //           缠绵指数        事宜
    String titleSix;        //           约会成功指数
    String titleSeven;      //           告白成功指数
    String titleEight;      //           复合成功指数
    String titleNine;       //
    String titleTen;        //
    LocalDate createDate;
    LocalDateTime createTime;
    LocalDateTime updateTime;

    Integer isDraw;
}
