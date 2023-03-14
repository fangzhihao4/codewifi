package codewifi.repository.mapper;

import codewifi.repository.model.WxCodeSceneModel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.WxCodeScene;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class WxCodeSceneMapper {
    private final DSLContext context;
    public final static Integer wifi_type = 1;

    public WxCodeSceneModel getById(Integer sceneId) {
        Condition condition = WxCodeScene.WX_CODE_SCENE.ID.eq(sceneId);
        return context.select(WxCodeScene.WX_CODE_SCENE.fields())
                .from(WxCodeScene.WX_CODE_SCENE)
                .where(condition)
                .limit(1)
                .fetchOneInto(WxCodeSceneModel.class);
    }

    public WxCodeSceneModel addScene(WxCodeSceneModel wxCodeSceneModel){
       Integer id = context.insertInto(WxCodeScene.WX_CODE_SCENE,
                WxCodeScene.WX_CODE_SCENE.TYPE,
                WxCodeScene.WX_CODE_SCENE.USER_NO,
                WxCodeScene.WX_CODE_SCENE.WIFI_NO,
                WxCodeScene.WX_CODE_SCENE.CREATE_DATE,
                WxCodeScene.WX_CODE_SCENE.CREATE_TIME,
                WxCodeScene.WX_CODE_SCENE.UPDATE_TIME,
                WxCodeScene.WX_CODE_SCENE.IMG_BASE
        ).values(
                wxCodeSceneModel.getType(),
                wxCodeSceneModel.getUserNo(),
                wxCodeSceneModel.getWifiNo(),
                LocalDate.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
               StringUtils.isEmpty(wxCodeSceneModel.getImgBase()) ? "" : wxCodeSceneModel.getImgBase()
        ).returning().fetchOne().getId();
       wxCodeSceneModel.setId(id);
       return wxCodeSceneModel;
    }
}
