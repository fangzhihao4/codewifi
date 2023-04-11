package codewifi.repository.cache;

import codewifi.common.RedissonService;
import codewifi.repository.mapper.VerystatusCoinOrderInfoMapper;
import codewifi.repository.mapper.VerystatusCoinOrderMapper;
import codewifi.repository.model.VerystatusCoinOrderInfoModel;
import codewifi.repository.model.VerystatusCoinOrderModel;
import codewifi.utils.IdUtils;
import codewifi.utils.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class VerystatusCoinOrderCache {

    public final RedissonService redissonService;
    public final VerystatusCoinOrderMapper verystatusCoinOrderMapper;
    public final VerystatusCoinOrderInfoMapper verystatusCoinOrderInfoMapper;
    public final IdUtils idUtils;

    public void addOrder(VerystatusCoinOrderModel verystatusCoinOrderModel, VerystatusCoinOrderInfoModel verystatusCoinOrderInfoModel){
        String orderNo = idUtils.getOrderNo();
        String infoNo = orderNo + "001";

        verystatusCoinOrderModel.setOrderNo(orderNo);
        verystatusCoinOrderMapper.addOrder(verystatusCoinOrderModel);

        verystatusCoinOrderInfoModel.setOrderNo(orderNo);
        verystatusCoinOrderInfoModel.setInfoNo(infoNo);
        verystatusCoinOrderInfoMapper.addOrder(verystatusCoinOrderInfoModel);
    }

}
