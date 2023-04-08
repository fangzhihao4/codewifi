package codewifi.repository.mapper;

import codewifi.repository.model.UserStarRecordModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
//import org.jooq.generated.tables.UserStarRecord;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class UserStarMapper {
    private final DSLContext context;

    public void addWifiCount(UserStarRecordModel userStarRecordModel){
//        context.insertInto(UserStarRecord.USER_STAR_RECORD,
//                UserStarRecord.USER_STAR_RECORD.USER_NO,
//                UserStarRecord.USER_STAR_RECORD.TIME,
//                UserStarRecord.USER_STAR_RECORD.STAR,
//                UserStarRecord.USER_STAR_RECORD.CONTENT,
//                UserStarRecord.USER_STAR_RECORD.TIME_NAME,
//                UserStarRecord.USER_STAR_RECORD.STAR_NAME,
//                UserStarRecord.USER_STAR_RECORD.CREATE_DATE,
//                UserStarRecord.USER_STAR_RECORD.CREATE_TIME,
//                UserStarRecord.USER_STAR_RECORD.UPDATE_TIME
//        )
//                .values(
//                        userStarRecordModel.getUserNo(),
//                        userStarRecordModel.getTime(),
//                        userStarRecordModel.getStar(),
//                        userStarRecordModel.getContent(),
//                        userStarRecordModel.getTimeName(),
//                        userStarRecordModel.getStarName(),
//                        LocalDate.now(),
//                        LocalDateTime.now(),
//                        LocalDateTime.now()
//                ).returning().fetchOne();
        return;
    }
    public UserStarRecordModel getByUserNo(String userNo, String time, String star, LocalDate localDate) {
//        Condition condition = UserStarRecord.USER_STAR_RECORD.USER_NO.eq(userNo)
//                .and(UserStarRecord.USER_STAR_RECORD.TIME.eq(time))
//                .and(UserStarRecord.USER_STAR_RECORD.STAR.eq(star))
//                .and(UserStarRecord.USER_STAR_RECORD.CREATE_DATE.eq(localDate));
//        return context.select(UserStarRecord.USER_STAR_RECORD.fields())
//                .from(UserStarRecord.USER_STAR_RECORD)
//                .where(condition)
//                .limit(1)
//                .fetchOneInto(UserStarRecordModel.class);
        return null;
    }
}
