package codewifi.repository.mapper;

import codewifi.common.constant.enums.LinkOrderTypeEnum;
import codewifi.repository.model.UserLinkOrderModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.UserLinkOrder;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Repository
@AllArgsConstructor
public class UserLinkOrderMapper {

    private final DSLContext context;

    public UserLinkOrderModel getLinkUserWifi(String userNo, String wifiNo) {
        Condition condition = UserLinkOrder.USER_LINK_ORDER.USER_NO.eq(userNo)
                .and(UserLinkOrder.USER_LINK_ORDER.WIFI_NO.eq(wifiNo))
                .and(UserLinkOrder.USER_LINK_ORDER.TYPE.eq(LinkOrderTypeEnum.LINK_WIFI_FINISH.getType()));
        return context.select(UserLinkOrder.USER_LINK_ORDER.fields())
                .from(UserLinkOrder.USER_LINK_ORDER)
                .where(condition)
                .limit(1)
                .fetchOneInto(UserLinkOrderModel.class);
    }

    public void addLinkOrder(UserLinkOrderModel userLinkWifiModel) {
        context.insertInto(UserLinkOrder.USER_LINK_ORDER,
                UserLinkOrder.USER_LINK_ORDER.LINK_NO,
                UserLinkOrder.USER_LINK_ORDER.USER_NO,
                UserLinkOrder.USER_LINK_ORDER.TYPE,
                UserLinkOrder.USER_LINK_ORDER.WIFI_NO,
                UserLinkOrder.USER_LINK_ORDER.WIFI_NAME,
                UserLinkOrder.USER_LINK_ORDER.WIFI_ADDRESS,
                UserLinkOrder.USER_LINK_ORDER.CREATE_DATE,
                UserLinkOrder.USER_LINK_ORDER.CREATE_TIME,
                UserLinkOrder.USER_LINK_ORDER.UPDATE_TIME
        )
                .values(
                        userLinkWifiModel.getLinkNo(),
                        userLinkWifiModel.getUserNo(),
                        userLinkWifiModel.getType(),
                        userLinkWifiModel.getWifiNo(),
                        userLinkWifiModel.getWifiName(),
                        userLinkWifiModel.getWifiAddress(),
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
    }
}
