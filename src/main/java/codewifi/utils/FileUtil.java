package codewifi.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {

    public static String downloadFromInternet(String fileUrl, String fileName, String savePath){
        try {
            URLConnection connection = new URL(fileUrl).openConnection();

            //设置超时时间 20秒
            connection.setConnectTimeout(20 * 1000);

            //防止屏蔽程序抓取而返回403错误
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = connection.getInputStream();
            //获取字节数组
            byte[] byteArray = readInputStream(inputStream);
            if (byteArray == null){
                return null;
            }

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()){
                saveDir.mkdir();
            }

            File file = new File(fileName + File.separator + saveDir);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray);
            fos.close();
            inputStream.close();

            return fileName + File.separator + saveDir;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String inputByte(byte[] inputByte, String savePath){
        try {

            //获取字节数组
            byte[] byteArray = inputByte;
            if (byteArray == null){
                return null;
            }

            //文件保存位置
//            File saveDir = new File(savePath);
//            if (!saveDir.exists()){
//                saveDir.mkdir();
//            }

            File file = new File(savePath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray);
            fos.close();

            return savePath;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从输入流中获取字节数组
     * @param inputStream input stream
     * @return byte array
     */
    public static byte[] readInputStream(InputStream inputStream){
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1){
                bos.write(buffer,0, len);
            }
            bos.close();
            return bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
