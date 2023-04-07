package codewifi.request.meeting;

import lombok.Data;

@Data
public class MeetingAdminUpdateUserRequest {
    String username; //姓名
    String department; //部门
    String phone; //电话号码
    String sort; //排序值
}
