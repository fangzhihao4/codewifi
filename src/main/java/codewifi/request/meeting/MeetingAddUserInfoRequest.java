package codewifi.request.meeting;

import lombok.Data;

@Data
public class MeetingAddUserInfoRequest {
    String  companyNo;
    String  department; //部门
    String  username;//用户名
    String  phone; //电话号码
}
