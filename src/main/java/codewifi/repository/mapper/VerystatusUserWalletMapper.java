package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusUserWalletModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusUserWallet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class VerystatusUserWalletMapper {
    private final DSLContext context;

    public void add(VerystatusUserWalletModel verystatusUserWalletModel){
        context.insertInto(VerystatusUserWallet.VERYSTATUS_USER_WALLET,
                VerystatusUserWallet.VERYSTATUS_USER_WALLET.USER_NO,
                VerystatusUserWallet.VERYSTATUS_USER_WALLET.COIN,
                VerystatusUserWallet.VERYSTATUS_USER_WALLET.CREATE_DATE,
                VerystatusUserWallet.VERYSTATUS_USER_WALLET.CREATE_TIME,
                VerystatusUserWallet.VERYSTATUS_USER_WALLET.UPDATE_TIME
        )
                .values(
                        verystatusUserWalletModel.getUserNo(),
                        verystatusUserWalletModel.getCoin(),
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
    }

    public VerystatusUserWalletModel getUserWallet(String userNo) {
        Condition condition = VerystatusUserWallet.VERYSTATUS_USER_WALLET.USER_NO.eq(userNo);
        return context.select(VerystatusUserWallet.VERYSTATUS_USER_WALLET.fields())
                .from(VerystatusUserWallet.VERYSTATUS_USER_WALLET)
                .where(condition)
                .limit(1)
                .fetchOneInto(VerystatusUserWalletModel.class);
    }

    public void changeCoin(String userNo, BigDecimal coin){
        context.update(VerystatusUserWallet.VERYSTATUS_USER_WALLET)
                .set(VerystatusUserWallet.VERYSTATUS_USER_WALLET.COIN, VerystatusUserWallet.VERYSTATUS_USER_WALLET.COIN.add(coin))
                .set(VerystatusUserWallet.VERYSTATUS_USER_WALLET.UPDATE_TIME, LocalDateTime.now())
                .where(VerystatusUserWallet.VERYSTATUS_USER_WALLET.USER_NO.eq(userNo))
                .execute();
    }

}
