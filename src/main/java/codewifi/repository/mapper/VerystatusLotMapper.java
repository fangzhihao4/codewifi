package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusLotModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusLot;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class VerystatusLotMapper {
    private final DSLContext context;
    public final static Integer type_common = 1;
    public final static Integer type_men = 2;
    public final static Integer type_money = 3;

    public VerystatusLotModel getByDayType(Integer type){
        Condition condition  = VerystatusLot.VERYSTATUS_LOT.TYPE.eq(type);
        return   context.select(VerystatusLot.VERYSTATUS_LOT.fields())
                .from(VerystatusLot.VERYSTATUS_LOT)
                .where(condition)
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOneInto(VerystatusLotModel.class);
    }
}
