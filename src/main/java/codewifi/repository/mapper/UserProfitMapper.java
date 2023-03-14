package codewifi.repository.mapper;

import codewifi.repository.model.UserProfitModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.UserProfit;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class UserProfitMapper {
    private final DSLContext context;

    public void addProfit(String userNo) {
        context.insertInto(UserProfit.USER_PROFIT,
                UserProfit.USER_PROFIT.USER_NO,
                UserProfit.USER_PROFIT.ALL_MONEY,
                UserProfit.USER_PROFIT.ACCOUNT_MONEY,
                UserProfit.USER_PROFIT.WITHDRAWAL_MONEY,
                UserProfit.USER_PROFIT.WITHDRAWAL_TIMES,
                UserProfit.USER_PROFIT.CREATE_DATE,
                UserProfit.USER_PROFIT.CREATE_TIME,
                UserProfit.USER_PROFIT.UPDATE_TIME
        )
                .values(
                        userNo,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        0,
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
    }

    public UserProfitModel getUserProfit(String userNo){
        Condition condition = UserProfit.USER_PROFIT.USER_NO.eq(userNo);
        return context.select(UserProfit.USER_PROFIT.fields())
                .from(UserProfit.USER_PROFIT)
                .where(condition)
                .limit(1)
                .fetchOneInto(UserProfitModel.class);
    }

    public void addUserProfit(Integer id, BigDecimal addProfit) {
        Condition condition = UserProfit.USER_PROFIT.ID.eq(id);
        context.update(UserProfit.USER_PROFIT)
                .set(UserProfit.USER_PROFIT.ALL_MONEY, UserProfit.USER_PROFIT.ALL_MONEY.add(addProfit))
                .set(UserProfit.USER_PROFIT.ACCOUNT_MONEY, UserProfit.USER_PROFIT.ACCOUNT_MONEY.add(addProfit))
                .set(UserProfit.USER_PROFIT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void subUserProfit(Integer id, BigDecimal addProfit) {
        Condition condition = UserProfit.USER_PROFIT.ID.eq(id);
        context.update(UserProfit.USER_PROFIT)
                .set(UserProfit.USER_PROFIT.ALL_MONEY, UserProfit.USER_PROFIT.ALL_MONEY.subtract(addProfit))
                .set(UserProfit.USER_PROFIT.ACCOUNT_MONEY, UserProfit.USER_PROFIT.ACCOUNT_MONEY.subtract(addProfit))
                .set(UserProfit.USER_PROFIT.WITHDRAWAL_MONEY, UserProfit.USER_PROFIT.WITHDRAWAL_MONEY.add(addProfit))
                .set(UserProfit.USER_PROFIT.WITHDRAWAL_TIMES, UserProfit.USER_PROFIT.WITHDRAWAL_TIMES.add(addProfit))
                .set(UserProfit.USER_PROFIT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void upYesterdayUserProfit(Integer id, BigDecimal profit) {
        Condition condition = UserProfit.USER_PROFIT.ID.eq(id);
        context.update(UserProfit.USER_PROFIT)
                .set(UserProfit.USER_PROFIT.YESTERDAY_MONEY, profit)
                .set(UserProfit.USER_PROFIT.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }
}
