package codewifi.service;

import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.request.very.VerystatusPayGoodsRequest;
import org.springframework.stereotype.Service;

@Service
public interface VerystatusThirdService {
    boolean getThirdContent(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest);

}
