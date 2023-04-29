package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusAdviseModel;
import codewifi.repository.model.VerystatusCoinOrderInfoModel;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusAdvise;
import org.jooq.generated.tables.VerystatusCoinOrderInfo;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class VerystatusAdviseMapper {

    private final DSLContext context;

    public void addAdvise(VerystatusAdviseModel verystatusAdviseModel){
        context.insertInto(VerystatusAdvise.VERYSTATUS_ADVISE,
                VerystatusAdvise.VERYSTATUS_ADVISE.USER_NO,
                VerystatusAdvise.VERYSTATUS_ADVISE.TITLE,
                VerystatusAdvise.VERYSTATUS_ADVISE.CONTENT,
                VerystatusAdvise.VERYSTATUS_ADVISE.ANSWER,
                VerystatusAdvise.VERYSTATUS_ADVISE.CREATE_DATE,
                VerystatusAdvise.VERYSTATUS_ADVISE.CREATE_TIME,
                VerystatusAdvise.VERYSTATUS_ADVISE.UPDATE_TIME
        )
                .values(
                        verystatusAdviseModel.getUserNo(),
                        verystatusAdviseModel.getTitle(),
                        verystatusAdviseModel.getContent(),
                        verystatusAdviseModel.getAnswer(),
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
    }
}
