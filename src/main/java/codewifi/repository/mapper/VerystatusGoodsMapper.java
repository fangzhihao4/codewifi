package codewifi.repository.mapper;

import codewifi.repository.model.VerystatusCoinOrderModel;
import codewifi.repository.model.VerystatusGoodsModel;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.VerystatusCoinOrder;
import org.jooq.generated.tables.VerystatusGoods;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@AllArgsConstructor
public class VerystatusGoodsMapper {
    public static final Integer price_coin = 1;
    public static final Integer price_video = 2;
    public static final Integer price_coin_video = 3;
    public static final Integer price_free = 4;

    public static final List<Integer> USE_COIN = Arrays.asList(price_coin,price_coin_video);
    public static final List<Integer> USE_VIDEO = Arrays.asList(price_video,price_coin_video);
    private final DSLContext context;

    public VerystatusGoodsModel getInfo(Integer goodsSku){
        Condition condition = VerystatusGoods.VERYSTATUS_GOODS.GOODS_SKU.eq(goodsSku);
        return context.select(VerystatusGoods.VERYSTATUS_GOODS.fields())
                .from(VerystatusGoods.VERYSTATUS_GOODS)
                .where(condition)
                .orderBy(VerystatusGoods.VERYSTATUS_GOODS.ID.desc())
                .limit(1)
                .fetchOneInto(VerystatusGoodsModel.class);
    }
}
