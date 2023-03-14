package codewifi.repository.mapper;

import codewifi.repository.model.UserCreateWifiModel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.UserCreateWifi;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserCreateWifiMapper {
    private final DSLContext context;
    public static final byte STATUS_IN = 1;
    public static final byte STATUS_DEL = 2;


    public static Integer LENGTH_MAX = 45;
    public static Integer LENGTH_MIN = 2;

    public List<UserCreateWifiModel> getUserWifiList(String userNo, int page, int pageSize) {
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.USER_NO.eq(userNo)
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        return context.select(UserCreateWifi.USER_CREATE_WIFI.fields())
                .from(UserCreateWifi.USER_CREATE_WIFI)
                .where(condition)
                .orderBy(UserCreateWifi.USER_CREATE_WIFI.ID.desc())
                .limit((page - 1) * pageSize, pageSize)
                .fetchInto(UserCreateWifiModel.class);
    }

    public UserCreateWifiModel getByWifiNo(String wifiNo){
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.WIFI_NO.eq(wifiNo)
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        return context.select(UserCreateWifi.USER_CREATE_WIFI.fields())
                .from(UserCreateWifi.USER_CREATE_WIFI)
                .where(condition)
                .limit(1)
                .fetchOneInto(UserCreateWifiModel.class);
    }

    public void updateWifiInfo(UserCreateWifiModel userCreateWifiModel){
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.USER_NO.eq(userCreateWifiModel.getUserNo())
                .and(UserCreateWifi.USER_CREATE_WIFI.WIFI_NO.eq(userCreateWifiModel.getWifiNo()))
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        context.update(UserCreateWifi.USER_CREATE_WIFI)
                .set(UserCreateWifi.USER_CREATE_WIFI.TITLE, userCreateWifiModel.getTitle())
                .set(UserCreateWifi.USER_CREATE_WIFI.NAME, userCreateWifiModel.getName())
                .set(UserCreateWifi.USER_CREATE_WIFI.PASSWORD, userCreateWifiModel.getPassword())
                .set(UserCreateWifi.USER_CREATE_WIFI.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void updateWifiInfoImg(UserCreateWifiModel userCreateWifiModel){
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.USER_NO.eq(userCreateWifiModel.getUserNo())
                .and(UserCreateWifi.USER_CREATE_WIFI.WIFI_NO.eq(userCreateWifiModel.getWifiNo()))
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        context.update(UserCreateWifi.USER_CREATE_WIFI)
                .set(UserCreateWifi.USER_CREATE_WIFI.IMG_URL, userCreateWifiModel.getImgUrl())
                .set(UserCreateWifi.USER_CREATE_WIFI.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void addWifiTimes(String userNo,  String wifiNo, Integer addTimes){
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.USER_NO.eq(userNo)
                .and(UserCreateWifi.USER_CREATE_WIFI.WIFI_NO.eq(wifiNo))
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        context.update(UserCreateWifi.USER_CREATE_WIFI)
                .set(UserCreateWifi.USER_CREATE_WIFI.FREE_TIMES, UserCreateWifi.USER_CREATE_WIFI.FREE_TIMES.add(addTimes))
                .set(UserCreateWifi.USER_CREATE_WIFI.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }



    public void subWifiTimes(String userNo,  String wifiNo){
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.USER_NO.eq(userNo)
                .and(UserCreateWifi.USER_CREATE_WIFI.WIFI_NO.eq(wifiNo))
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        context.update(UserCreateWifi.USER_CREATE_WIFI)
                .set(UserCreateWifi.USER_CREATE_WIFI.FREE_TIMES, UserCreateWifi.USER_CREATE_WIFI.FREE_TIMES.subtract(1))
                .set(UserCreateWifi.USER_CREATE_WIFI.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void updateWifiFree(String userNo,  String wifiNo){
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.USER_NO.eq(userNo)
                .and(UserCreateWifi.USER_CREATE_WIFI.WIFI_NO.eq(wifiNo))
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        context.update(UserCreateWifi.USER_CREATE_WIFI)
                .set(UserCreateWifi.USER_CREATE_WIFI.FREE_TIMES, -1)
                .set(UserCreateWifi.USER_CREATE_WIFI.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void updateWifiAllFree(String userNo){
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.USER_NO.eq(userNo)
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        context.update(UserCreateWifi.USER_CREATE_WIFI)
                .set(UserCreateWifi.USER_CREATE_WIFI.FREE_TIMES, -1)
                .set(UserCreateWifi.USER_CREATE_WIFI.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void addWifi(UserCreateWifiModel userCreateWifiModel){
        context.insertInto(UserCreateWifi.USER_CREATE_WIFI,
                UserCreateWifi.USER_CREATE_WIFI.WIFI_NO,
                UserCreateWifi.USER_CREATE_WIFI.USER_NO,
                UserCreateWifi.USER_CREATE_WIFI.TITLE,
                UserCreateWifi.USER_CREATE_WIFI.ADDRESS,
                UserCreateWifi.USER_CREATE_WIFI.NAME,
                UserCreateWifi.USER_CREATE_WIFI.PASSWORD,
                UserCreateWifi.USER_CREATE_WIFI.CREATE_DATE,
                UserCreateWifi.USER_CREATE_WIFI.CREATE_TIME,
                UserCreateWifi.USER_CREATE_WIFI.UPDATE_TIME,
                UserCreateWifi.USER_CREATE_WIFI.FREE_TIMES,
                UserCreateWifi.USER_CREATE_WIFI.STATUS
        )
                .values(
                        userCreateWifiModel.getWifiNo(),
                        userCreateWifiModel.getUserNo(),
                        StringUtils.isBlank(userCreateWifiModel.getTitle()) ? userCreateWifiModel.getName() : userCreateWifiModel.getTitle(),
                        userCreateWifiModel.getAddress(),
                        userCreateWifiModel.getName(),
                        userCreateWifiModel.getPassword(),
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        0,
                        STATUS_IN
                ).returning().fetchOne();
    }

    public void delWifi(String wifiNo){
        Condition condition = UserCreateWifi.USER_CREATE_WIFI.WIFI_NO.eq(wifiNo)
                .and(UserCreateWifi.USER_CREATE_WIFI.STATUS.eq(STATUS_IN));
        context.update(UserCreateWifi.USER_CREATE_WIFI)
                .set(UserCreateWifi.USER_CREATE_WIFI.STATUS, STATUS_DEL)
                .set(UserCreateWifi.USER_CREATE_WIFI.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

}
