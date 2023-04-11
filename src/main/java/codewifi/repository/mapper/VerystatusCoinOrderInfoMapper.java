package codewifi.repository.mapper;

import codewifi.repository.model.UserCreateWifiModel;
import codewifi.repository.model.VerystatusCoinOrderInfoModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.UserCreateWifi;
import org.jooq.generated.tables.VerystatusCoinOrderInfo;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class VerystatusCoinOrderInfoMapper {
    private final DSLContext context;

    public void addOrder(VerystatusCoinOrderInfoModel verystatusCoinOrderInfoModel){
        context.insertInto(VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.INFO_NO,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.ORDER_NO,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.USER_NO,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.CHANGE_TYPE,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.SOURCE,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.TITLE,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.SHOW_TYPE,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.CONTENT_IMG,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.CONTENT,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.CREATE_DATE,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.CREATE_TIME,
                VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.UPDATE_TIME
        )
                .values(
                        verystatusCoinOrderInfoModel.getInfoNo(),
                        verystatusCoinOrderInfoModel.getOrderNo(),
                        verystatusCoinOrderInfoModel.getUserNo(),
                        verystatusCoinOrderInfoModel.getChangeType().byteValue(),
                        verystatusCoinOrderInfoModel.getSource(),
                        verystatusCoinOrderInfoModel.getTitle(),
                        verystatusCoinOrderInfoModel.getShowType().byteValue(),
                        verystatusCoinOrderInfoModel.getContentImg(),
                        verystatusCoinOrderInfoModel.getContent(),
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
    }

    public VerystatusCoinOrderInfoModel getInfo(String infoNo){
        Condition condition = VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.INFO_NO.eq(infoNo);
        return context.select(VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO.fields())
                .from(VerystatusCoinOrderInfo.VERYSTATUS_COIN_ORDER_INFO)
                .where(condition)
                .limit(1)
                .fetchOneInto(VerystatusCoinOrderInfoModel.class);
    }
}
