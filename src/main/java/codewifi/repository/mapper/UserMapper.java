package codewifi.repository.mapper;


import codewifi.repository.model.UserModel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class UserMapper {
    private final DSLContext context;

    public static final String HEADER_DEFAULT = "https://wx.qlogo.cn/mmhead/Q3auHgzwzM7ibiclCric2EtiaDoeNSdAUyRicQxc1OgonEqWZhp9mu5ibhzw/0";

    public static final byte STATUS_IN = 1;
    public static final byte STATUS_DEL = 2;

    public static final Byte WX_REGISTER = 1;
    public static final Byte PWD_REGISTER = 2;

    public static final int USERNAME_LENGTH = 45;
    public static final int PASSWORD_LENGTH = 45;

    public UserModel getByOpenid(String openid) {
        Condition condition = User.USER.OPENID.eq(openid)
                .and(User.USER.STATUS.eq(STATUS_IN));
        return context.select(User.USER.fields())
                .from(User.USER)
                .where(condition)
                .limit(1)
                .fetchOneInto(UserModel.class);
    }

    public UserModel addUser(UserModel userModel) {
        context.insertInto(User.USER,
                User.USER.OPENID,
                User.USER.USER_NO,
                User.USER.TYPE,
                User.USER.UNIONID,
                User.USER.NICKNAME,
                User.USER.HEAD_IMG_URL,
                User.USER.GENDER,
                User.USER.REGION,
                User.USER.STATUS,
                User.USER.CREATE_TIME,
                User.USER.UPDATE_TIME
        )
                .values(
                        StringUtils.isEmpty(userModel.getOpenid()) ? "" : userModel.getOpenid(),
                        userModel.getUserNo(),
                        Objects.isNull(userModel.getType()) ? PWD_REGISTER : userModel.getType(),
                        StringUtils.isEmpty(userModel.getUnionid()) ? "" : userModel.getUnionid(),
                        userModel.getNickname(),
                        StringUtils.isEmpty(userModel.getHeadImgUrl()) ? HEADER_DEFAULT : userModel.getHeadImgUrl(),
                        StringUtils.isEmpty(userModel.getGender()) ? "" : userModel.getGender(),
                        StringUtils.isEmpty(userModel.getRegion()) ? "" : userModel.getRegion(),
                        STATUS_IN,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();

        return userModel;
    }

    public UserModel getByUserNo(String userNo) {
        Condition condition = User.USER.USER_NO.eq(userNo).and(User.USER.STATUS.eq(STATUS_IN));
        return context.select(User.USER.fields())
                .from(User.USER)
                .where(condition)
                .limit(1)
                .fetchOneInto(UserModel.class);
    }

    public void updateNickname(String userNo, String nickname) {
        Condition condition = User.USER.USER_NO.eq(userNo);
        context.update(User.USER)
                .set(User.USER.NICKNAME, nickname)
                .set(User.USER.UPDATE_TIME, LocalDateTime.now())
                .where(condition)
                .execute();
    }

    public List<Map<String, Object>> getInfo(String sql) {
        List<Map<String, Object>> listMap = context.fetch(sql).intoMaps();
        return listMap;
    }
}
