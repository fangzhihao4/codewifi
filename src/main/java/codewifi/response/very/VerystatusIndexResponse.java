package codewifi.response.very;

import lombok.Data;

import java.util.List;

@Data
public class VerystatusIndexResponse {
    Integer isFinishVideo; //是否完成观看视频
    Integer videoFinish; //已经观看次数
    Integer videoTotal; //总需要观看次数

    List<MenuList> menuList;


    @Data
    public static class MenuList{
        String name;
        Integer type;
        String pageUrl;
    }
}
