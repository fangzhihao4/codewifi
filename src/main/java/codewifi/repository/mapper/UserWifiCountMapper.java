package codewifi.repository.mapper;

import codewifi.repository.model.UserWifiCountModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.UserWifiCount;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class UserWifiCountMapper {
    private final DSLContext context;

    public void addWifiCount(UserWifiCountModel userWifiCountModel){
        context.insertInto(UserWifiCount.USER_WIFI_COUNT,
                UserWifiCount.USER_WIFI_COUNT.WIFI_NO,
                UserWifiCount.USER_WIFI_COUNT.PROFIT,
                UserWifiCount.USER_WIFI_COUNT.SCAN_CODE_NUM,
                UserWifiCount.USER_WIFI_COUNT.LINK_NUM,
                UserWifiCount.USER_WIFI_COUNT.LINK_USER,
                UserWifiCount.USER_WIFI_COUNT.CREATE_DATE,
                UserWifiCount.USER_WIFI_COUNT.CREATE_TIME,
                UserWifiCount.USER_WIFI_COUNT.UPDATE_TIME
        )
                .values(
                        userWifiCountModel.getWifiNo(),
                        BigDecimal.ZERO,
                        0,
                        0,
                        0,
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
    }

    public UserWifiCountModel getByUserNo(String wifiNo) {
        Condition condition = UserWifiCount.USER_WIFI_COUNT.WIFI_NO.eq(wifiNo);
        return context.select(UserWifiCount.USER_WIFI_COUNT.fields())
                .from(UserWifiCount.USER_WIFI_COUNT)
                .where(condition)
                .limit(1)
                .fetchOneInto(UserWifiCountModel.class);
    }

    public void addProfit(String wifiNo, BigDecimal profit){
        Condition condition = UserWifiCount.USER_WIFI_COUNT.WIFI_NO.eq(wifiNo);
        context.update(UserWifiCount.USER_WIFI_COUNT)
                .set(UserWifiCount.USER_WIFI_COUNT.PROFIT, UserWifiCount.USER_WIFI_COUNT.PROFIT.add(profit))
                .set(UserWifiCount.USER_WIFI_COUNT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void addScanCodeNum(String wifiNo){
        Condition condition = UserWifiCount.USER_WIFI_COUNT.WIFI_NO.eq(wifiNo);
        context.update(UserWifiCount.USER_WIFI_COUNT)
                .set(UserWifiCount.USER_WIFI_COUNT.SCAN_CODE_NUM, UserWifiCount.USER_WIFI_COUNT.SCAN_CODE_NUM.add(1))
                .set(UserWifiCount.USER_WIFI_COUNT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void addLinkNum(String wifiNo){
        Condition condition = UserWifiCount.USER_WIFI_COUNT.WIFI_NO.eq(wifiNo);
        context.update(UserWifiCount.USER_WIFI_COUNT)
                .set(UserWifiCount.USER_WIFI_COUNT.LINK_NUM, UserWifiCount.USER_WIFI_COUNT.LINK_NUM.add(1))
                .set(UserWifiCount.USER_WIFI_COUNT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void addLinkUser(String wifiNo){
        Condition condition = UserWifiCount.USER_WIFI_COUNT.WIFI_NO.eq(wifiNo);
        context.update(UserWifiCount.USER_WIFI_COUNT)
                .set(UserWifiCount.USER_WIFI_COUNT.LINK_USER, UserWifiCount.USER_WIFI_COUNT.LINK_USER.add(1))
                .set(UserWifiCount.USER_WIFI_COUNT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }


}
