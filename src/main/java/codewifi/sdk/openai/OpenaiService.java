package codewifi.sdk.openai;

import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.request.very.VerystatusPayGoodsRequest;

public interface OpenaiService {
    String getByName(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest);

    String getReport(VerystatusGoodsUserCo verystatusGoodsUserCo,VerystatusPayGoodsRequest verystatusPayGoodsRequest);
}
