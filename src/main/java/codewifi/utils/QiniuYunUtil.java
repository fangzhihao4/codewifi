package codewifi.utils;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Md5;

import java.nio.charset.StandardCharsets;

public class QiniuYunUtil {
    public static String uploadUrl = "/wifi/img";

    public static void writeFileByFile(String fileName, String fileUrl){
//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "JVGcjYeI2ErVRgSaANvYS2DwUw6zd_uVmGVTsw3z";
        String secretKey = "HClU3ZnsTzeOkrjdMGmYlL9NNyymWCIspyKuu3GK";
        String bucket = "gunyigun";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "/wifi/img/" + fileName;
        String localFilePath = fileUrl;
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "/wifi/img/"+ System.currentTimeMillis() + ".png";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    public static boolean writeFIleByByte(String fileName, byte[] uploadBytes){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "JVGcjYeI2ErVRgSaANvYS2DwUw6zd_uVmGVTsw3z";
        String secretKey = "HClU3ZnsTzeOkrjdMGmYlL9NNyymWCIspyKuu3GK";
        String bucket = "gunyigun";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key =  fileName;


        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
