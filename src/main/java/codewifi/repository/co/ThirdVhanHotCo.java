package codewifi.repository.co;

import lombok.Data;

@Data
public class ThirdVhanHotCo {
    Integer index;//第几个
    String title;//标题
    String desc;//描述
    String pic;//图片
    String hot;//热度
    Integer hotInt;
    Integer star;//热度心心 0-10
    String mobilUrl;//点击url
}
