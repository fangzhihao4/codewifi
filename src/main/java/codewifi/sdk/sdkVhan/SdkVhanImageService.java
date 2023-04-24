package codewifi.sdk.sdkVhan;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SdkVhanImageService {
    public static final String taoImg = "https://api.vvhan.com/api/tao";
    public static final String mobilGirl = "https://api.vvhan.com/api/mobil.girl";
    public static final String lolSpin = "https://api.vvhan.com/api/lolskin";
    public static final String dongMan = "https://api.vvhan.com/api/acgimg";
    public static final String headAvatar = "https://api.vvhan.com/api/avatar";
    public static final String headNan = "https://api.vvhan.com/api/avatar?class=nan";
    public static final String headNv = "https://api.vvhan.com/api/avatar?type=nv";
    public static final String headDm = "https://api.vvhan.com/api/avatar?type=dm";
    public static final String headJw = "https://api.vvhan.com/api/avatar?type=jw";
    public String getTaoImage(){
        try {
            return resHttpImg(taoImg);
        }catch (Exception e){
            return null;
        }
    }

    public String getMobilGirlImage(){
        try {
            return resHttpImg(mobilGirl);
        }catch (Exception e){
            return null;
        }
    }

    public String getLolSpinImage(){
        try {
            return resHttpImg(lolSpin);
        }catch (Exception e){
            return null;
        }
    }

    public String getHeadAvatar(){
        try {
            return resHttpImg(headAvatar);
        }catch (Exception e){
            return null;
        }
    }

    public String getHeadNan(){
        try {
            return resHttpImg(headNan);
        }catch (Exception e){
            return null;
        }
    }

    public String getHeadNv(){
        try {
            return resHttpImg(headNv);
        }catch (Exception e){
            return null;
        }
    }

    public String getHeadDm(){
        try {
            return resHttpImg(headDm);
        }catch (Exception e){
            return null;
        }
    }

    public String getHeadJw(){
        try {
            return resHttpImg(headJw);
        }catch (Exception e){
            return null;
        }
    }

    public String getDongMan(){
        try {
            return resHttpImg(dongMan);
        }catch (Exception e){
            return null;
        }
    }

    public String resHttpImg(String imageUrl)throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        return connection.getHeaderField("location");
    }


}
