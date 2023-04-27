package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusGoodsContentModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.generated.tables.VerystatusGoodsContent;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class VerystatusGoodsContentMapper {
    private final DSLContext context;

    public VerystatusGoodsContentModel getInfo(Integer goodsSku){
        Condition condition = VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.GOODS_SKU.eq(goodsSku);
        return context.select(VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.fields())
                .from(VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT)
                .where(condition)
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOneInto(VerystatusGoodsContentModel.class);
    }

    public VerystatusGoodsContentModel getInfoByContent(Integer goodsSku, String content){
        String goodsNo = content.substring(0,Math.min(499,content.length()));
        Condition condition = VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.GOODS_SKU.eq(goodsSku)
                .and(VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.GOODS_NO.eq(goodsNo));
        return context.select(VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.fields())
                .from(VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT)
                .where(condition)
                .limit(1)
                .fetchOneInto(VerystatusGoodsContentModel.class);
    }

    public VerystatusGoodsContentModel insertInfo(VerystatusGoodsContentModel verystatusGoodsContentModel){
        context.insertInto(VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT,
                VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.GOODS_SKU,
                VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.GOODS_NO,
                VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.CONTENT,
                VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.CREATE_DATE,
                VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.CREATE_TIME,
                VerystatusGoodsContent.VERYSTATUS_GOODS_CONTENT.UPDATE_TIME
        )
                .values(
                        verystatusGoodsContentModel.getGoodsSku(),
                        StringUtils.isEmpty(verystatusGoodsContentModel.getGoodsNo()) ? verystatusGoodsContentModel.getContent().substring(0,Math.min(499,verystatusGoodsContentModel.getContent().length())) : verystatusGoodsContentModel.getGoodsNo(),
                        verystatusGoodsContentModel.getContent(),
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).returning().fetchOne();
        return verystatusGoodsContentModel;
    }
}
