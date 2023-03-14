package codewifi.response.wifi;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserInviteResponse {
    Integer page;
    List<InviteInfo> inviteInfoList;

    @Data
    public static class InviteInfo{
        String registerName;
        BigDecimal wifiProfit;
        BigDecimal inviteProfit;
    }

}
