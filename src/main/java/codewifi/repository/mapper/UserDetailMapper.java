package codewifi.repository.mapper;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserDetailMapper {
    private final DSLContext context;

//    public void addUserDetail(UserDetailModel userDetailModel){
//        context.insertInto(UserDetail.USER_DETAIL,
//                UserDetail.USER_DETAIL.USER_NO,
//                UserDetail.USER_DETAIL.REGISTER_IP,
//                UserDetail.USER_DETAIL.LOGIN_LAST_IP,
//                UserDetail.USER_DETAIL.LOGIN_LAST_TIME,
//                UserDetail.USER_DETAIL.LOGIN_END_TIME,
//                UserDetail.USER_DETAIL.CREATE_TIME,
//                UserDetail.USER_DETAIL.UPDATE_TIME
//        )
//                .values(
//                        userDetailModel.getUserNo(),
//                        userDetailModel.getRegisterIp(),
//                        userDetailModel.getLoginLastIp(),
//                        userDetailModel.getLoginLastTime(),
//                        userDetailModel.getLoginEndTime(),
//                        userDetailModel.getCreateTime(),
//                        userDetailModel.getUpdateTime()
//                ) .returning().fetchOne();
//    }
}
