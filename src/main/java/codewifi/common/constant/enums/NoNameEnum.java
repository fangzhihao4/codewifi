package codewifi.common.constant.enums;

import lombok.Getter;

@Getter
public enum NoNameEnum {
    USER_NO_HEADER(10),
    WIFI_NO_HEADER(80),
    LINK_NO_HEADER(90);

    private final Integer noHeader;

    NoNameEnum(Integer noHeader) {
        this.noHeader = noHeader;
    }
}
