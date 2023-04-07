package codewifi.response.meeting;

import lombok.Data;

@Data
public class MeetingUserLoginResponse {
    private String token;
    private String userNo;
    private String nickname;
    private String headImg;
}
