package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusCoinOrderModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusCoinOrder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class VerystatusCoinOrderMapper {
    private final DSLContext context;
    public static final Integer COIN_SUB = 1;
    public static final Integer COIN_ADD = 2;
    public static final Integer FREE = 3;//免费使用
    public static final Integer VIDEO = 4;//观看的视频
    public static final Integer VIDEO_FINISH = 5;//观看完成


    public void addOrder(VerystatusCoinOrderModel verystatusCoinOrderModel){
        context.insertInto(VerystatusCoinOrder.VERYSTATUS_COIN_ORDER,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.ORDER_NO,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.USER_NO,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.OLD_COIN,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.NEW_COIN,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.USE_COIN,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.CHANGE_TYPE,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.SOURCE,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.DESCRIPTION,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.STATUS,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.CREATE_DATE,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.CREATE_TIME,
                VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.UPDATE_TIME
        )
                .values(
                        verystatusCoinOrderModel.getOrderNo(),
                        verystatusCoinOrderModel.getUserNo(),
                        verystatusCoinOrderModel.getOldCoin(),
                        verystatusCoinOrderModel.getNewCoin(),
                        verystatusCoinOrderModel.getUseCoin(),
                        verystatusCoinOrderModel.getChangeType().byteValue(),
                        verystatusCoinOrderModel.getSource(),
                        verystatusCoinOrderModel.getDescription(),
                        (byte)1,
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
    }

    public List<VerystatusCoinOrderModel> getUserPage(String userNo, int page, int pageSize) {
        Condition condition = VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.USER_NO.eq(userNo);
        return context.select(VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.fields())
                .from(VerystatusCoinOrder.VERYSTATUS_COIN_ORDER)
                .where(condition)
                .orderBy(VerystatusCoinOrder.VERYSTATUS_COIN_ORDER.ID.desc())
                .limit((page - 1) * pageSize, pageSize)
                .fetchInto(VerystatusCoinOrderModel.class);
    }

}
