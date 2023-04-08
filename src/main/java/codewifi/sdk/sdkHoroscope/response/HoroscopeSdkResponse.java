package codewifi.sdk.sdkHoroscope.response;

import lombok.Data;

@Data
public class HoroscopeSdkResponse {
    Boolean success;

    DataInfo data;

    @Data
    public static class DataInfo{
        String title; //标题

        String type;//类型

        String time;//更新时间

        Todo todo;//动作

        Fortune fortune;//运势

        Index index; //指数

        String luckycolor;//幸运颜色

        Integer luckynumber;//幸运数字

        String luckyconstellation;//速配星座

        String badconstellation;//提防星座

        String shortcomment;//短评

        FortuneText fortunetext; //运势解析
    }

    @Data
    public static class Todo{
        String yi; //宜做
        String ji; //忌做
    }

    @Data
    public static class Fortune{ //运势
        Integer all; //综合运势

        Integer love; //爱情运势

        Integer work; //学业工作

        Integer money;//财富运势

        Integer health; //健康运势"
    }

    @Data
    public static class Index{ //指数
        String all; //综合指数

        String love;//爱情指数

        String work;//学业工作

        String money;//财富指数

        String health;//健康指数

    }

    @Data
    public static class  FortuneText{
        String all; //综合运势

        String love; //爱情运势

        String work; //学业工作

        String money; //财富运势

        String health; //健康运势

        String decompression;//解压秘诀

        String openluck;//开运秘诀
    }
}
