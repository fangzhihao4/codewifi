package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusCouponModel;
import codewifi.repository.model.VerystatusGoodsModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusCoupon;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class VerystatusCouponMapper {
    private final DSLContext context;
    public static final Integer STATUS_IN = 1;

    public List<VerystatusCouponModel> getList(Integer type){
        Condition condition = VerystatusCoupon.VERYSTATUS_COUPON.STATUS.eq(STATUS_IN)
                .and(VerystatusCoupon.VERYSTATUS_COUPON.TYPE.eq(type))
                .and(VerystatusCoupon.VERYSTATUS_COUPON.START_TIME.le(LocalDateTime.now()))
                .and(VerystatusCoupon.VERYSTATUS_COUPON.END_TIME.ge(LocalDateTime.now()));
        return context.select(VerystatusCoupon.VERYSTATUS_COUPON.fields())
                .from(VerystatusCoupon.VERYSTATUS_COUPON)
                .where(condition)
                .orderBy(VerystatusCoupon.VERYSTATUS_COUPON.SORT.asc())
                .fetchInto(VerystatusCouponModel.class);
    }
}
