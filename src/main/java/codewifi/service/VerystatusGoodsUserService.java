package codewifi.service;

import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.very.VerystatusGoodsInfoRequest;
import codewifi.request.very.VerystatusGoodsMoreRequest;
import codewifi.request.very.VerystatusPayGoodsRequest;
import codewifi.request.very.VerystatusUserGoodsRequest;
import codewifi.response.very.VerystatusGoodsMoreResponse;
import codewifi.response.very.VerystatusGoodsUserInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VerystatusGoodsUserService {
    VerystatusGoodsUserInfoResponse getUserGoods(VerystatusUserModel verystatusUserModel, VerystatusPayGoodsRequest verystatusPayGoodsRequest);

    List<VerystatusGoodsUserInfoResponse> getUserGoodsList(VerystatusUserModel verystatusUserModel, VerystatusGoodsInfoRequest verystatusGoodsInfoRequest);

    VerystatusGoodsUserInfoResponse getUserPayContent(VerystatusUserModel verystatusUserModel, VerystatusPayGoodsRequest verystatusPayGoodsRequest);

    VerystatusGoodsMoreResponse getGoodsMore(VerystatusUserModel verystatusUserModel, VerystatusGoodsMoreRequest verystatusGoodsMoreRequest);
}
