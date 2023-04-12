package codewifi.common.constant.enums;

import lombok.Getter;

@Getter
public enum VerystatusUserHeadEnum {
    HEAD_ONE("11","");

    private final String headNo;
    private final String name;

    VerystatusUserHeadEnum(String headNo, String name) {
        this.headNo = headNo;
        this.name = name;
    }

    public static VerystatusUserHeadEnum getHeadEnum(String headNo) {
        VerystatusUserHeadEnum[] verystatusUserHeadEnumList = values();
        for (VerystatusUserHeadEnum verystatusUserHeadEnum : verystatusUserHeadEnumList) {
            if (verystatusUserHeadEnum.getHeadNo().equals(headNo)) {
                return verystatusUserHeadEnum;
            }
        }
        return null;
    }
}
