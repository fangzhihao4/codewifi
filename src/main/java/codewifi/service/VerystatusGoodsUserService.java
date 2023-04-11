package codewifi.service;

import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.very.VerystatusPayGoodsRequest;
import codewifi.response.very.VerystatusGoodsUserInfoResponse;
import org.springframework.stereotype.Service;

@Service
public interface VerystatusGoodsUserService {
    VerystatusGoodsUserInfoResponse getUserGoods(VerystatusUserModel verystatusUserModel, VerystatusPayGoodsRequest verystatusPayGoodsRequest);

    VerystatusGoodsUserInfoResponse getUserPayContent(VerystatusUserModel verystatusUserModel, VerystatusPayGoodsRequest verystatusPayGoodsRequest);

}
