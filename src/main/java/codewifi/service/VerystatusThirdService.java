package codewifi.service;

import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.request.very.VerystatusGoodsMoreRequest;
import codewifi.request.very.VerystatusPayGoodsRequest;
import org.springframework.stereotype.Service;

@Service
public interface VerystatusThirdService {
    boolean getThirdContent(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest);

    void startGoodsInfo(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest);

    Object thirdPage(VerystatusGoodsMoreRequest verystatusGoodsMoreRequest);
}
