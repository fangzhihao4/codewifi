package codewifi.sdk.sdkVhan.response;

import lombok.Data;

import java.util.List;

@Data
public class HotHuPuSdkResponse {
    Boolean success;
    String title;
    String subititle;
    String update_time;
    List<Info> data;

    @Data
    public static class Info{
        Integer index;
        String title;
        String desc;
        String pic;
        String hot;
        String url;
        String mobilUrl;
    }
}
