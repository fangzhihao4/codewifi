package codewifi.request.meeting;

import lombok.Data;

@Data
public class MeetingAddRoomRequest {
    String roomNo;
    String name; //名称
    String address; //会议位置
    Integer minUserNum; //会议使用最小人数
    Integer isMinUser; // 是否要求达到最小限制
    Integer maxUserNum; //会议使用最多人数
    Integer isMaxUser;//是否要求达到最大限制
    Integer type; //状态 1可用 2维修中  3不可用 4已删除
    Integer isVerify; //是否审核
    Integer beginDay; //提前申请天数
    Integer remark;//说明
}
