package codewifi.service;

import codewifi.repository.model.UserModel;
import codewifi.request.wifi.CodeSceneRequest;
import codewifi.request.wifi.LinkWifiRequest;
import codewifi.request.wifi.WifiNoRequest;
import codewifi.response.wifi.CodeSceneResponse;
import codewifi.response.wifi.LinkTicketResponse;
import codewifi.response.wifi.LinkWifiInfoResponse;
import codewifi.response.wifi.UserWifiInfoResponse;
import org.springframework.stereotype.Service;

@Service
public interface LinkWifiService {

    void linkWIfiInfo(UserModel userModel,LinkWifiRequest linkWifiRequest);

    CodeSceneResponse getInfoByCodeScene(CodeSceneRequest codeSceneRequest, UserModel userModel);


}
