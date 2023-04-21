package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusGoodsUserModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusGoodsUser;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class VerystatusGoodsUserMapper {
    public static final Integer NO_FINISH = 1;
    public static final Integer IS_FINISH = 2;

    private final DSLContext context;

    /**
     * 查询单个详情
     * @param goodsSku 商品sku
     * @param userNo 用户no
     * @return 单个详情
     */
    public VerystatusGoodsUserModel getInfo(Integer goodsSku, String userNo){
        Condition condition = VerystatusGoodsUser.VERYSTATUS_GOODS_USER.GOODS_SKU.eq(goodsSku)
                .and(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USER_NO.eq(userNo));
        return context.select(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.fields())
                .from(VerystatusGoodsUser.VERYSTATUS_GOODS_USER)
                .where(condition)
                .limit(1)
                .fetchOneInto(VerystatusGoodsUserModel.class);
    }

    /**
     * 查询列表
     * @param goodsSku 商品sku
     * @param userNo 用户no
     * @return 列表
     */
    public List<VerystatusGoodsUserModel> getList(List<Integer> goodsSku, String userNo){
        Condition condition = VerystatusGoodsUser.VERYSTATUS_GOODS_USER.GOODS_SKU.in(goodsSku)
                .and(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USER_NO.eq(userNo));
        return context.select(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.fields())
                .from(VerystatusGoodsUser.VERYSTATUS_GOODS_USER)
                .where(condition)
                .fetchInto(VerystatusGoodsUserModel.class);
    }

    /**
     * 新增数据
     * @param verystatusGoodsUserModel 组合商品信息
     * @return 结果
     */
    public VerystatusGoodsUserModel insertInfo(VerystatusGoodsUserModel verystatusGoodsUserModel){
        context.insertInto(VerystatusGoodsUser.VERYSTATUS_GOODS_USER,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USER_NO,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CREATE_DATE,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.GOODS_SKU,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.PRICE_TYPE,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.COIN,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.FREE_TOTAL_NUM,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.FREE_USE_NUM,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.VIDEO_NEED,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.VIDEO_FINISH,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.IS_FINISH,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.REPEAT_TOTAL_NUM,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.REPEAT_USE_NUM,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.SHOW_TYPE,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT_IMG,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CREATE_TIME,
                VerystatusGoodsUser.VERYSTATUS_GOODS_USER.UPDATE_TIME
        )
                .values(
                        verystatusGoodsUserModel.getUserNo(),
                        LocalDate.now(),
                        verystatusGoodsUserModel.getGoodsSku(),
                        verystatusGoodsUserModel.getPriceType().byteValue(),
                        verystatusGoodsUserModel.getCoin(),
                        verystatusGoodsUserModel.getFreeTotalNum(),
                        verystatusGoodsUserModel.getFreeUseNum(),
                        verystatusGoodsUserModel.getVideoNeed(),
                        verystatusGoodsUserModel.getVideoFinish(),
                        verystatusGoodsUserModel.getIsFinish().byteValue(),
                        verystatusGoodsUserModel.getRepeatTotalNum(),
                        verystatusGoodsUserModel.getRepeatUseNum(),
                        verystatusGoodsUserModel.getShowType().byteValue(),
                        verystatusGoodsUserModel.getContentImg(),
                        verystatusGoodsUserModel.getContent(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
        return verystatusGoodsUserModel;
    }


    /**
     *
     * @param verystatusGoodsUserModel
     * @return
     */
    public VerystatusGoodsUserModel updateInfo(VerystatusGoodsUserModel verystatusGoodsUserModel){
        Condition condition = VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USER_NO.eq(verystatusGoodsUserModel.getUserNo())
                .and(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.GOODS_SKU.eq(verystatusGoodsUserModel.getGoodsSku()));
        context.update(VerystatusGoodsUser.VERYSTATUS_GOODS_USER)
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CREATE_DATE, verystatusGoodsUserModel.getCreateDate())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.PRICE_TYPE, verystatusGoodsUserModel.getPriceType().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.VIDEO_FINISH, verystatusGoodsUserModel.getVideoFinish())
//                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.FINISH_TIMES, verystatusGoodsUserModel.getFinishTimes())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.IS_FINISH, verystatusGoodsUserModel.getIsFinish().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.SHOW_TYPE, verystatusGoodsUserModel.getShowType().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT_IMG, verystatusGoodsUserModel.getContentImg())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT, verystatusGoodsUserModel.getContent())
//                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USE_NUM, verystatusGoodsUserModel.getUseNum())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
        return verystatusGoodsUserModel;
    }

    /**
     *
     * @param verystatusGoodsUserModel
     * @return
     */
    public VerystatusGoodsUserModel startDayInfo(VerystatusGoodsUserModel verystatusGoodsUserModel){
        Condition condition = VerystatusGoodsUser.VERYSTATUS_GOODS_USER.USER_NO.eq(verystatusGoodsUserModel.getUserNo())
                .and(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.GOODS_SKU.eq(verystatusGoodsUserModel.getGoodsSku()));

        context.update(VerystatusGoodsUser.VERYSTATUS_GOODS_USER)
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CREATE_DATE, verystatusGoodsUserModel.getCreateDate())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.PRICE_TYPE, verystatusGoodsUserModel.getPriceType().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.COIN, verystatusGoodsUserModel.getCoin())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.FREE_TOTAL_NUM, verystatusGoodsUserModel.getFreeTotalNum())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.FREE_USE_NUM, verystatusGoodsUserModel.getFreeUseNum())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.VIDEO_NEED, verystatusGoodsUserModel.getVideoNeed())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.VIDEO_FINISH, verystatusGoodsUserModel.getVideoFinish())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.IS_FINISH, verystatusGoodsUserModel.getIsFinish().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.REPEAT_TOTAL_NUM, verystatusGoodsUserModel.getRepeatTotalNum())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.REPEAT_USE_NUM, verystatusGoodsUserModel.getRepeatUseNum())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.SHOW_TYPE, verystatusGoodsUserModel.getShowType().byteValue())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT_IMG, verystatusGoodsUserModel.getContentImg())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.CONTENT, verystatusGoodsUserModel.getContent())
                .set(VerystatusGoodsUser.VERYSTATUS_GOODS_USER.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
        return verystatusGoodsUserModel;
    }
}
