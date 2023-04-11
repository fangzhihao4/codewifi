package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusMenuConfigModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusMenuConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class VerystatusMenuConfigMapper {
    private final DSLContext context;
    public static final Byte STATUS_IN = 1;
    public static final Byte STATUS_OFF = 2;

    public List<VerystatusMenuConfigModel> getAllConfig(){
        Condition condition = VerystatusMenuConfig.VERYSTATUS_MENU_CONFIG.STATUS.eq(STATUS_IN);
        return context.select(VerystatusMenuConfig.VERYSTATUS_MENU_CONFIG.fields())
                .from(VerystatusMenuConfig.VERYSTATUS_MENU_CONFIG)
                .where(condition)
                .orderBy(VerystatusMenuConfig.VERYSTATUS_MENU_CONFIG.SORT.asc())
                .fetchInto(VerystatusMenuConfigModel.class);
    }
}
