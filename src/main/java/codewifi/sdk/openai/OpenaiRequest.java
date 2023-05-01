package codewifi.sdk.openai;

import lombok.Data;

import java.util.List;

@Data
public class OpenaiRequest {
    String model;
    List<Messages> messages;

    @Data
    public static class Messages{
        String role;
        String content;
    }
}
