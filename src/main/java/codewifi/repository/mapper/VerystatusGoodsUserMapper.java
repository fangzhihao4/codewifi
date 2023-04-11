package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusGoodsModel;
import codewifi.repository.model.VerystatusGoodsUserModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusGoodsUser;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class VerystatusGoodsUserMapper {
    public static final Integer NO_FINISH = 1;
    public static final Integer IS_FINISH = 2;

    private final DSLContext context;

    public VerystatusGoodsUserModel getInfo(Integer goodsSku, String userNo){
        Condition condition = VerystatusGoodsUser.VERYSTATUS_GOODS_USER.GOODS_SKU.eq(goodsSku)
                .and(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USER_NO.eq(userNo));
        return context.select(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.fields())
                .from(VerystatusGoodsUser.VERYSTATUS_GOODS_USER)
                .where(condition)
                .limit(1)
                .fetchOneInto(VerystatusGoodsUserModel.class);
    }

    public VerystatusGoodsUserModel insertInfo(VerystatusGoodsUserModel verystatusGoodsUserModel){
        context.insertInto(VerystatusGoodsUser.VERYSTATUS_GOODS_USER,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USER_NO,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CREATE_DATE,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.GOODS_SKU,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.PRICE_TYPE,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.VIDEO_FINISH,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.FINISH_TIMES,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.IS_FINISH,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.SHOW_TYPE,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT_IMG,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USE_NUM,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CREATE_TIME,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.UPDATE_TIME
        )
                .values(
                        verystatusGoodsUserModel.getUserNo(),
                        LocalDate.now(),
                        verystatusGoodsUserModel.getGoodsSku(),
                        verystatusGoodsUserModel.getPriceType().byteValue(),
                        verystatusGoodsUserModel.getVideoFinish(),
                        verystatusGoodsUserModel.getFinishTimes(),
                        verystatusGoodsUserModel.getIsFinish().byteValue(),
                        verystatusGoodsUserModel.getShowType().byteValue(),
                        verystatusGoodsUserModel.getContentImg(),
                        verystatusGoodsUserModel.getContent(),
                        verystatusGoodsUserModel.getUseNum(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
        return verystatusGoodsUserModel;
    }


    public VerystatusGoodsUserModel updateInfo(VerystatusGoodsUserModel verystatusGoodsUserModel){
        Condition condition = VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USER_NO.eq(verystatusGoodsUserModel.getUserNo())
                .and(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.GOODS_SKU.eq(verystatusGoodsUserModel.getGoodsSku()));
        context.update(VerystatusGoodsUser.VERYSTATUS_GOODS_USER)
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CREATE_DATE, verystatusGoodsUserModel.getCreateDate())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.PRICE_TYPE, verystatusGoodsUserModel.getPriceType().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.VIDEO_FINISH, verystatusGoodsUserModel.getVideoFinish())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.FINISH_TIMES, verystatusGoodsUserModel.getFinishTimes())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.IS_FINISH, verystatusGoodsUserModel.getIsFinish().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.SHOW_TYPE, verystatusGoodsUserModel.getShowType().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT_IMG, verystatusGoodsUserModel.getContentImg())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT, verystatusGoodsUserModel.getContent())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USE_NUM, verystatusGoodsUserModel.getUseNum())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
        return verystatusGoodsUserModel;
    }
}
