package codewifi.service;

import codewifi.repository.model.UserModel;
import codewifi.request.wifi.UserInviteRequest;
import codewifi.request.wifi.UserProfitRequest;
import codewifi.response.wifi.UserIndexResponse;
import codewifi.response.wifi.UserInviteResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserProfitService {
    UserIndexResponse getUserIndex(UserModel userModel, UserProfitRequest userProfitRequest);

    UserInviteResponse getUserInvite(UserModel userModel, UserInviteRequest userInviteRequest);
}
