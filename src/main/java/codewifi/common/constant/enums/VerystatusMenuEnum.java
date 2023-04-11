package codewifi.common.constant.enums;

import lombok.Getter;

@Getter
public enum VerystatusMenuEnum {

    STAR(1,"星座运势");

    private final Integer menuType;
    private final String name;

    VerystatusMenuEnum(Integer menuType,  String name) {
        this.menuType = menuType;
        this.name = name;
    }
}
