package codewifi.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class StringChangeUtil {
    public static String getStringBetween(String content, String start, String end){
        String contentStart = StringUtils.substringBeforeLast(content,end);
        return StringUtils.substringAfter(contentStart,start);
    }


    public static String getNoStringBetween(String content, String start, String end){
        String contentStart =  StringUtils.substringBeforeLast(content,start);
        String contentEnd =  StringUtils.substringAfter(content,end);
        return  contentStart + contentEnd;
    }

    public static String getStartLastEndStart(String content, String start, String end){
        String contentStart = StringUtils.substringAfterLast(content,start);
        return StringUtils.substringBefore(contentStart,end);
    }

    public static String getStartEndStart(String content, String start, String end){
        String contentStart = StringUtils.substringAfter(content,start);
        return StringUtils.substringBefore(contentStart,end);
    }

    public static Integer stringToInteger(String str){
        if (Objects.isNull(str)){
            return null;
        }
        try {
            return Integer.parseInt(str);
        }catch (Exception e){
            return null;
        }
    }

    public static int strToInt(String str){
        if (Objects.isNull(str)){
            return 0;
        }
        try {
            return Integer.parseInt(str.trim());
        }catch (Exception e){
            return 0;
        }
    }
}
