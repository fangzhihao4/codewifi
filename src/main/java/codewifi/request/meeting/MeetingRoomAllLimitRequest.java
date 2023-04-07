package codewifi.request.meeting;

import lombok.Data;

@Data
public class MeetingRoomAllLimitRequest {
    Integer type; //1 全部最小人数限制 2全部最大人数限制  3全部审核开关
    Integer isUse; //1全部关闭 2全部开启
}
