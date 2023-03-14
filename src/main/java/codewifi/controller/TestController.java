package codewifi.controller;

import codewifi.common.Response;
import codewifi.sdk.wxApi.WxApiService;
import codewifi.utils.FileUtil;
import codewifi.utils.QiniuYunUtil;
import com.UpYun;
import com.upyun.UpException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
    private final WxApiService wxApiService;
    // 运行前先设置好以下三个参数
    private static final String BUCKET_NAME = "image-wifi-com";
    private static final String OPERATOR_NAME = "xiaji";
    private static final String OPERATOR_PWD = "MIbINY9Qil1NjGUVOImXrBQnHdQEzuQA";


    @RequestMapping("/test")
    public Response<String> generateId(@RequestBody String body) {
        return Response.data("success");
    }
    @RequestMapping("/qrcode")
    public Response<String> qrcode(@RequestBody String body) throws IOException, UpException {
//        String accessToken = wxApiService.getAccessToken("wx83a48cee270a3de9","26deaa40cb1bd4d0c928ddef4f4a5a21");
//        if (Objects.isNull(accessToken)){
//            return Response.data("null accessToken");
//        }
//        byte[] qrcode = wxApiService.getUnlimitedQRCode(1, accessToken);
//        String dirUrl = System.getProperty("user.dir");
//        String fileUrl = dirUrl + "/" + UUID.randomUUID().toString() + ".png";
//        FileUtil.inputByte(qrcode, fileUrl);
        String fileUrl = "/Users/fangzhihao/nginx/www/zhihao/java/codewifi/26e467d3-0cdb-40c4-b3f7-3cade7a2486f.png";
////        File.write(fileUrl,qrcode);
        UpYun upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
//        java.io.File file = new File(fileUrl);
        boolean b = upyun.writeFile("/wifi/image/"+ System.currentTimeMillis() + ".png", fileUrl);
        return Response.data(String.valueOf(b));
    }

    @RequestMapping("/qiniu")
    public Response<String> qiniu(@RequestBody String body) throws IOException, UpException {

        String fileUrl = "/Users/fangzhihao/nginx/www/zhihao/java/codewifi/26e467d3-0cdb-40c4-b3f7-3cade7a2486f.png";
////        File.write(fileUrl,qrcode);
//        java.io.File file = new File(fileUrl);
        QiniuYunUtil.writeFileByFile(System.currentTimeMillis() + ".png",fileUrl);
        return Response.data("success");
    }
}
