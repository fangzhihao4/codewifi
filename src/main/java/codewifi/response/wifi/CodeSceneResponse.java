package codewifi.response.wifi;

import lombok.Data;

@Data
public class CodeSceneResponse {
    Integer type; //1 wifi信息
    String linkNo;
    String wifiNo;
    String name;
    String title;
    String password;
    String address;
}
