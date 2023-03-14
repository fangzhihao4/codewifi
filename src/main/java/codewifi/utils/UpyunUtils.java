package codewifi.utils;

import com.UpYun;

public class UpyunUtils {
    private static final LogUtil logUtil = LogUtil.getLogger(UpyunUtils.class);
    // 运行前先设置好以下三个参数
    private static final String BUCKET_NAME = "image-wifi-com";
    private static final String OPERATOR_NAME = "xiaji";
    private static final String OPERATOR_PWD = "MIbINY9Qil1NjGUVOImXrBQnHdQEzuQA";

    public static boolean writeFileByByte(String fileName, byte[] fileByte){
        try {
            UpYun upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
            boolean res = upyun.writeFile(fileName, fileByte);
            return res;
        }catch (Exception exception){
            logUtil.infoError("上传文件失败", fileName, exception);
            return false;
        }
//        boolean b = upyun.writeFile("/wifi/image/"+ System.currentTimeMillis() + ".png", fileByte);
    }

    public static boolean writeFileByFile(String fileName, String fileUrl){
        try {
            UpYun upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
            boolean res = upyun.writeFile(fileName, fileUrl);
            return res;
        }catch (Exception exception){
            logUtil.infoError("上传文件失败", fileName, exception);
            return false;
        }
    }
}
