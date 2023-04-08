package codewifi.service.impl;

import codewifi.repository.cache.UserCreateWifiCache;
import codewifi.repository.cache.UserInviteProfitCache;
import codewifi.repository.cache.UserProfitCache;
import codewifi.repository.model.UserCreateWifiModel;
import codewifi.repository.model.UserInviteProfitModel;
import codewifi.repository.model.UserModel;
import codewifi.repository.model.UserProfitModel;
import codewifi.request.wifi.UserInviteRequest;
import codewifi.request.wifi.UserProfitRequest;
import codewifi.response.wifi.UserIndexResponse;
import codewifi.response.wifi.UserInviteResponse;
import codewifi.response.wifi.UserProfitResponse;
import codewifi.response.wifi.UserWifiInfoResponse;
import codewifi.service.UserProfitService;
import codewifi.utils.BeanCopyUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserProfitServiceImpl implements UserProfitService {
    private final UserProfitCache userProfitCache;
    private final UserCreateWifiCache userCreateWifiCache;
    private final UserInviteProfitCache userInviteProfitCache;


    @Override
    public UserIndexResponse getUserIndex(UserModel userModel, UserProfitRequest userProfitRequest) {
        UserProfitModel userProfitModel = userProfitCache.getUserProfit(userModel.getUserNo());
        UserIndexResponse userIndexResponse = new UserIndexResponse();
        UserProfitResponse userProfitResponse = new UserProfitResponse();

        //用户的收益信息
        userProfitResponse.setYesterdayMoney(userProfitModel.getYesterdayMoney().setScale(4, RoundingMode.DOWN));
        userProfitResponse.setAccountMoney(userProfitModel.getAccountMoney().setScale(4, RoundingMode.DOWN));
        userProfitResponse.setWithdrawalMoney(userProfitModel.getWithdrawalMoney().setScale(4, RoundingMode.DOWN));
        userIndexResponse.setUserProfit(userProfitResponse);

        //wifi列表
        List<UserCreateWifiModel> userCreateWifiModels = userCreateWifiCache.getWifiPage(userModel.getUserNo(), userProfitRequest.getPage());
        List<UserWifiInfoResponse> userWifiInfoResponseList = BeanCopyUtils.copyListProperties(userCreateWifiModels, UserWifiInfoResponse::new);
//        userWifiInfoResponseList.forEach(s -> s.setImgUrl(YmlContent.upyunHost + s.getImgUrl()));
        userWifiInfoResponseList.forEach(s -> s.setImgUrl("https://img1.baidu.com/it/u=1503611658,1817364920&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500"));
        userIndexResponse.setWifiList(userWifiInfoResponseList);
        userIndexResponse.setWifiPage(userProfitRequest.getPage());
        userIndexResponse.setWifiPageSize(userCreateWifiCache.pageSize);
        return userIndexResponse;
    }

    @Override
    public UserInviteResponse getUserInvite(UserModel userModel, UserInviteRequest userInviteRequest) {
        List<UserInviteProfitModel> list = userInviteProfitCache.getInviteByUser(userModel.getUserNo(), userInviteRequest.getPage());
        if (list.isEmpty()) {
            return new UserInviteResponse();
        }
        List<UserInviteResponse.InviteInfo> inviteInfoList = new ArrayList<>();
        for (UserInviteProfitModel userInviteProfitModel : list) {
            UserInviteResponse.InviteInfo inviteInfo = new UserInviteResponse.InviteInfo();
            inviteInfo.setInviteProfit(userInviteProfitModel.getInviteProfitPrice());
            inviteInfo.setWifiProfit(userInviteProfitModel.getWifiProfitPrice());
            inviteInfo.setRegisterName(userInviteProfitModel.getRegisterName());
            inviteInfoList.add(inviteInfo);
        }
        UserInviteResponse userInviteResponse = new UserInviteResponse();
        userInviteResponse.setPage(userInviteRequest.getPage());
        userInviteResponse.setInviteInfoList(inviteInfoList);
        return userInviteResponse;
    }


}
