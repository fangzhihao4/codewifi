package codewifi.service;

import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.request.very.VerystatusGoodsMoreRequest;
import codewifi.request.very.VerystatusPayGoodsRequest;
import org.springframework.stereotype.Service;

@Service
public interface VerystatusThirdService {
    boolean getThirdContent(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest, String userNo);

    boolean startGoodsInfo(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest, String userNo);

    Object thirdPage(VerystatusGoodsMoreRequest verystatusGoodsMoreRequest);
}
