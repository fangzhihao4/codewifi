package codewifi.repository.mapper;

import codewifi.repository.model.UserMoneyOrderModel;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.UserMoneyOrder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class UserMoneyOrderMapper {
    private final DSLContext context;
    public static final Byte SELF_WIFI = 1;
    public static final Byte FRIEND_WIFI = 2;
    public static final Byte FRIEND_INVITE = 3;
    public static final Byte ADD_WIFI_LINK = 4;

    public void addMoneyOrder(UserMoneyOrderModel userLinkWifiModel) {
        context.insertInto(UserMoneyOrder.USER_MONEY_ORDER,
                UserMoneyOrder.USER_MONEY_ORDER.USER_NO,
                UserMoneyOrder.USER_MONEY_ORDER.TYPE,
                UserMoneyOrder.USER_MONEY_ORDER.PRICE,
                UserMoneyOrder.USER_MONEY_ORDER.USER_PROFIT,
                UserMoneyOrder.USER_MONEY_ORDER.WIFI_NO,
                UserMoneyOrder.USER_MONEY_ORDER.WIFI_USER_NO,
                UserMoneyOrder.USER_MONEY_ORDER.CREATE_DATE,
                UserMoneyOrder.USER_MONEY_ORDER.CREATE_TIME,
                UserMoneyOrder.USER_MONEY_ORDER.UPDATE_TIME,
                UserMoneyOrder.USER_MONEY_ORDER.ADD_TIME
        )
                .values(
                        userLinkWifiModel.getUserNo(),
                        userLinkWifiModel.getType(),
                        userLinkWifiModel.getPrice(),
                        userLinkWifiModel.getUserProfit(),
                        userLinkWifiModel.getWifiNo(),
                        userLinkWifiModel.getWifiUserNo(),
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        userLinkWifiModel.getAddTime()
                ).returning().fetchOne();
    }
}
