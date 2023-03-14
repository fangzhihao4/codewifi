package codewifi.response.wifi;

import lombok.Data;

import java.util.List;

@Data
public class UserIndexResponse {
    UserProfitResponse userProfit;
    Integer wifiTotal;
    Integer wifiPage;
    Integer wifiPageSize;
    List<UserWifiInfoResponse> wifiList;
}
