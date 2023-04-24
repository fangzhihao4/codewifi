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
        String name; //名称
        Integer type; //类型
        String pageUrl; //路径
        String badge; //右上角说明
        String url; //图片路径
    }
}
