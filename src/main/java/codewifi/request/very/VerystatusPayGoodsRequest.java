package codewifi.request.very;

import lombok.Data;

@Data
public class VerystatusPayGoodsRequest {
    Integer payType;//1金币 2视频 4使用免费
    Integer goodsSku;
    String paramFirst; //参数1
    String paramTwo; //参数2
}
