package codewifi.common.constant.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum HoroscopeEnum {
    TODAY("今天","today"),
    NEXT_DAY("明天","nextday"),
    WEEK("本周","week"),
    MONTH("本月","month"),
    YEAR("今年","year"),
    LOVE("爱情","love"),

    TAURUS("金牛座","taurus"),
    GEMINI("双子座","gemini"),
    CANCER("巨蟹座","cancer"),
    LEO("狮子座","leo"),
    VIRGO("处女座","virgo"),
    LIBRA("天秤座","libra"),
    SCORPIO("天蝎座","scorpio"),
    SAGITTARIUS("射手座","sagittarius"),
    CAPRICORN("摩羯座","capricorn"),
    AQUARIUS("水瓶座","aquarius"),
    PISCES("双鱼座","pisces"),
    ARIES("白羊座","aries");

    HoroscopeEnum(String name, String type) {
        this.name = name;
        this.type = type;
    }

    private final String name;

    private final String type;

    public static String getHoroscopeListTime(String name){
        HoroscopeEnum[] changeHandleEnums = values();
        for (HoroscopeEnum changeHandleEnum : changeHandleEnums) {
            if (changeHandleEnum.getName().equals(name)) {
                return changeHandleEnum.getName();
            }
        }
        return null;
    }


    public static HoroscopeEnum getTimeByType(String name){
        List<HoroscopeEnum> list = Arrays.asList(TODAY,NEXT_DAY,WEEK,MONTH,YEAR,LOVE);
        for (HoroscopeEnum horoscopeEnum : list){
            if (horoscopeEnum.getName().equals(name)){
                return horoscopeEnum;
            }
        }
        return null;
    }

    public static HoroscopeEnum getStarByType(String type){
        List<HoroscopeEnum> list = Arrays.asList(TAURUS,GEMINI,CANCER,LEO,YEAR,VIRGO,LIBRA,SCORPIO,SAGITTARIUS,CAPRICORN,AQUARIUS,PISCES,ARIES);
        for (HoroscopeEnum horoscopeEnum : list){
            if (horoscopeEnum.getName().equals(type)){
                return horoscopeEnum;
            }
        }
        return null;
    }

}
