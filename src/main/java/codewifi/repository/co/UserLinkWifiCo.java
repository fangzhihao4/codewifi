package codewifi.repository.co;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLinkWifiCo {
    String linkNo;
    String linkUserNo;
    String wifiNo;
    String wifiUserNo;
}
