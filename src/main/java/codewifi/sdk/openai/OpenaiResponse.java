package codewifi.sdk.openai;

import lombok.Data;

import java.util.List;

@Data
public class OpenaiResponse {
    String id;
//    String object;
    Long created;
    String model;
    List<ChoicesInfo> choices;


    @Data
    public static class Usage{
        String  prompt_tokens;
        String completion_tokens;
        String total_tokens;
    }

    @Data
    public static class ChoicesInfo{
        String finish_reason;
        Integer index;
        Message message;
    }

    @Data
    public static class Message{
        String role;
        String content;
    }
}
