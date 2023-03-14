package codewifi.service;

import codewifi.repository.model.UserModel;
import codewifi.request.wifi.WifiNoRequest;
import codewifi.request.wifi.WifiUpdateRequest;
import codewifi.response.wifi.UserWifiInfoResponse;
import codewifi.response.wifi.WifiImgResponse;
import org.springframework.stereotype.Service;

@Service
public interface WifiInfoService {

    void updateWifi(UserModel userModel, WifiUpdateRequest wifiUpdateRequest);

    void delWifi(UserModel userModel, WifiNoRequest wifiNoRequest);

    void addWifi(UserModel userModel, WifiUpdateRequest wifiUpdateRequest);

    UserWifiInfoResponse getWifiDetail(UserModel userModel, WifiNoRequest wifiNoRequest);

}
