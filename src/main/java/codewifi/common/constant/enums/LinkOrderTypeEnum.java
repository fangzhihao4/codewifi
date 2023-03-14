package codewifi.common.constant.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum LinkOrderTypeEnum {
    SCAN_CODE((byte)1, "扫码"),
    WATCH_VIDEO_OFF((byte)2, "看视频关闭"),
    WATCH_VIDEO_FINISH((byte)3, "看视频完成"),
    LINK_WIFI_START((byte)4, "点击wifi链接"),
    LINK_WIFI_FINISH((byte)5, "wifi链接成功"),
    LINK_WIFI_FAIL((byte)6, "wifi链接失败");

    private final Byte type;
    private final String name;

    LinkOrderTypeEnum(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static List<Byte> onLinkList(){
        return Arrays.asList(WATCH_VIDEO_OFF.getType(),WATCH_VIDEO_FINISH.getType());
    }

    public static LinkOrderTypeEnum linkOrderTypeEnum(Byte code) {
        LinkOrderTypeEnum[] changeHandleEnums = values();
        for (LinkOrderTypeEnum changeHandleEnum : changeHandleEnums) {
            if (changeHandleEnum.getType().equals(code)) {
                return changeHandleEnum;
            }
        }
        return null;
    }

}
