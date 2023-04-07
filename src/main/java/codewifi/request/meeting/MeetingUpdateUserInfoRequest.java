package codewifi.request.meeting;

import lombok.Data;

@Data
public class MeetingUpdateUserInfoRequest {
    String keyName; //键名
    String valueName; //键值
}
