package codewifi.repository.mapper;

import codewifi.repository.model.UserInviteProfitModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.UserInviteProfit;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserInviteProfitMapper {
    private final DSLContext context;
    public static final byte TYPE_USER_WIFI = 1;
    public static final byte TYPE_BUS = 2;

    public void addInvite(UserInviteProfitModel userInviteProfitModel) {
        context.insertInto(UserInviteProfit.USER_INVITE_PROFIT,
                UserInviteProfit.USER_INVITE_PROFIT.PARENT_USER_NO,
                UserInviteProfit.USER_INVITE_PROFIT.REGISTER_USER_NO,
                UserInviteProfit.USER_INVITE_PROFIT.REGISTER_NAME,
                UserInviteProfit.USER_INVITE_PROFIT.TYPE,
                UserInviteProfit.USER_INVITE_PROFIT.WIFI_PROFIT_PRICE,
                UserInviteProfit.USER_INVITE_PROFIT.INVITE_PROFIT_PRICE,
                UserInviteProfit.USER_INVITE_PROFIT.CREATE_DATE,
                UserInviteProfit.USER_INVITE_PROFIT.CREATE_TIME,
                UserInviteProfit.USER_INVITE_PROFIT.UPDATE_TIME
        )
                .values(
                        userInviteProfitModel.getParentUserNo(),
                        userInviteProfitModel.getRegisterUserNo(),
                        userInviteProfitModel.getRegisterName(),
                        userInviteProfitModel.getType(),
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
    }

    public void addInviteWifiTimes(Integer id, BigDecimal addProfit) {
        Condition condition = UserInviteProfit.USER_INVITE_PROFIT.ID.eq(id);
        context.update(UserInviteProfit.USER_INVITE_PROFIT)
                .set(UserInviteProfit.USER_INVITE_PROFIT.WIFI_PROFIT_PRICE, UserInviteProfit.USER_INVITE_PROFIT.WIFI_PROFIT_PRICE.add(addProfit))
                .set(UserInviteProfit.USER_INVITE_PROFIT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void addInviteSubTimes(Integer id, BigDecimal addProfit) {
        Condition condition = UserInviteProfit.USER_INVITE_PROFIT.ID.eq(id);
        context.update(UserInviteProfit.USER_INVITE_PROFIT)
                .set(UserInviteProfit.USER_INVITE_PROFIT.INVITE_PROFIT_PRICE, UserInviteProfit.USER_INVITE_PROFIT.INVITE_PROFIT_PRICE.add(addProfit))
                .set(UserInviteProfit.USER_INVITE_PROFIT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }


    public List<UserInviteProfitModel> getUserWifiListByPage(String userNo, int page, int type) {
        Condition condition = UserInviteProfit.USER_INVITE_PROFIT.PARENT_USER_NO.eq(userNo);
        if (type == 1){
            return context.select(UserInviteProfit.USER_INVITE_PROFIT.fields())
                    .from(UserInviteProfit.USER_INVITE_PROFIT)
                    .where(condition)
                    .orderBy(UserInviteProfit.USER_INVITE_PROFIT.WIFI_PROFIT_PRICE.desc())
                    .limit(page - 1 ,20)
                    .fetchInto(UserInviteProfitModel.class);
        }
        return context.select(UserInviteProfit.USER_INVITE_PROFIT.fields())
                .from(UserInviteProfit.USER_INVITE_PROFIT)
                .where(condition)
                .orderBy(UserInviteProfit.USER_INVITE_PROFIT.INVITE_PROFIT_PRICE.desc())
                .limit(page - 1 ,20)
                .fetchInto(UserInviteProfitModel.class);
    }


    public UserInviteProfitModel getUserParentInfo(String userNo){
            Condition condition = UserInviteProfit.USER_INVITE_PROFIT.REGISTER_USER_NO.eq(userNo)
                    .and(UserInviteProfit.USER_INVITE_PROFIT.TYPE.eq(TYPE_USER_WIFI));
            return context.select(UserInviteProfit.USER_INVITE_PROFIT.fields())
                    .from(UserInviteProfit.USER_INVITE_PROFIT)
                    .where(condition)
                    .limit(1)
                    .fetchOneInto(UserInviteProfitModel.class);
    }

    public UserInviteProfitModel getUserSubInfo(String userNo){
        Condition condition = UserInviteProfit.USER_INVITE_PROFIT.REGISTER_USER_NO.eq(userNo)
                .and(UserInviteProfit.USER_INVITE_PROFIT.TYPE.eq(TYPE_BUS));
        return context.select(UserInviteProfit.USER_INVITE_PROFIT.fields())
                .from(UserInviteProfit.USER_INVITE_PROFIT)
                .where(condition)
                .limit(1)
                .fetchOneInto(UserInviteProfitModel.class);
    }




}
