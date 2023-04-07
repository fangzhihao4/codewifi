package codewifi.request.meeting;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingApplyRequest {
    String reservationNo;
    String name;
    String roomNo;
    LocalDateTime startTime;
    LocalDateTime updateTime;
    String description;
    List<String> userNoList;
}
