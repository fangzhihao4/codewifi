package codewifi.repository.mapper;

import codewifi.repository.model.UserModel;
import codewifi.repository.model.VerystatusUserModel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.User;
import org.jooq.generated.tables.VerystatusGoodsUser;
import org.jooq.generated.tables.VerystatusUser;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class VerystatusUserMapper {
    private final DSLContext context;

    public static final String HEADER_DEFAULT = "https://wx.qlogo.cn/mmhead/Q3auHgzwzM7ibiclCric2EtiaDoeNSdAUyRicQxc1OgonEqWZhp9mu5ibhzw/0";

    public static final byte STATUS_IN = 1; //使用中
    public static final byte STATUS_DEL = 2; //已删除

    public static final Byte WX_REGISTER = 1; //登录类型
    public static final Byte PWD_REGISTER = 2;

    public static final int USERNAME_LENGTH = 45;
    public static final int PASSWORD_LENGTH = 45;

    public static final Byte HEAD_TYPE_HTTPS = 1; //头像 https路径
    public static final Byte HEAD_TYPE_BASE = 2; //本地头像
    public static final List<Byte> HEAD_TYPE_LIST = Arrays.asList(HEAD_TYPE_HTTPS,HEAD_TYPE_BASE);
    public static final Integer HEAD_MAX_LENGTH = 1000; //头像最大长度



    public VerystatusUserModel getByOpenid(String openid) {
        Condition condition = VerystatusUser.VERYSTATUS_USER.OPENID.eq(openid)
                .and(VerystatusUser.VERYSTATUS_USER.STATUS.eq(STATUS_IN));
        return context.select(VerystatusUser.VERYSTATUS_USER.fields())
                .from(VerystatusUser.VERYSTATUS_USER)
                .where(condition)
                .limit(1)
                .fetchOneInto(VerystatusUserModel.class);
    }

    public void updateUserHead(String userNo,Byte headType, String headUrl){
        Condition condition = VerystatusUser.VERYSTATUS_USER.USER_NO.eq(userNo);
        context.update(VerystatusUser.VERYSTATUS_USER)
                .set(VerystatusUser.VERYSTATUS_USER.HEAD_TYPE, headType)
                .set(VerystatusUser.VERYSTATUS_USER.HEAD_IMG_URL, headUrl)
                .set(VerystatusUser.VERYSTATUS_USER.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public void updateUserNickname(String userNo, String nickname){
        Condition condition = VerystatusUser.VERYSTATUS_USER.USER_NO.eq(userNo);
        context.update(VerystatusUser.VERYSTATUS_USER)
                .set(VerystatusUser.VERYSTATUS_USER.NICKNAME, nickname)
                .set(VerystatusUser.VERYSTATUS_USER.UPDATE_TIME, LocalDateTime.now())
                .where(condition).execute();
    }

    public VerystatusUserModel addUser(VerystatusUserModel verystatusUserModel) {
        context.insertInto(VerystatusUser.VERYSTATUS_USER,
                VerystatusUser.VERYSTATUS_USER.OPENID,
                VerystatusUser.VERYSTATUS_USER.USER_NO,
                VerystatusUser.VERYSTATUS_USER.TYPE,
                VerystatusUser.VERYSTATUS_USER.UNIONID,
                VerystatusUser.VERYSTATUS_USER.NICKNAME,
                VerystatusUser.VERYSTATUS_USER.HEAD_TYPE,
                VerystatusUser.VERYSTATUS_USER.HEAD_IMG_URL,
                VerystatusUser.VERYSTATUS_USER.GENDER,
                VerystatusUser.VERYSTATUS_USER.REGION,
                VerystatusUser.VERYSTATUS_USER.STATUS,
                VerystatusUser.VERYSTATUS_USER.CREATE_DATE,
                VerystatusUser.VERYSTATUS_USER.CREATE_TIME,
                VerystatusUser.VERYSTATUS_USER.UPDATE_TIME
        )
                .values(
                        StringUtils.isEmpty(verystatusUserModel.getOpenid()) ? "" : verystatusUserModel.getOpenid(),
                        verystatusUserModel.getUserNo(),
                        Objects.isNull(verystatusUserModel.getType()) ? PWD_REGISTER : verystatusUserModel.getType(),
                        StringUtils.isEmpty(verystatusUserModel.getUnionid()) ? "" : verystatusUserModel.getUnionid(),
                        verystatusUserModel.getNickname(),
                        HEAD_TYPE_HTTPS,
                        StringUtils.isEmpty(verystatusUserModel.getHeadImgUrl()) ? HEADER_DEFAULT : verystatusUserModel.getHeadImgUrl(),
                        StringUtils.isEmpty(verystatusUserModel.getGender()) ? "" : verystatusUserModel.getGender(),
                        StringUtils.isEmpty(verystatusUserModel.getRegion()) ? "" : verystatusUserModel.getRegion(),
                        STATUS_IN,
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();

        return verystatusUserModel;
    }
}
