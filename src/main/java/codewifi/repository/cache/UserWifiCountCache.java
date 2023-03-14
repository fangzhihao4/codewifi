package codewifi.repository.cache;

import codewifi.repository.mapper.UserWifiCountMapper;
import codewifi.repository.model.UserWifiCountModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class UserWifiCountCache {
    private final UserWifiCountMapper userWifiCountMapper;

    public void addCount(UserWifiCountModel userWifiCountModel){
        userWifiCountMapper.addWifiCount(userWifiCountModel);
    }

    public void addScanCodeNum(String wifiNo){
        userWifiCountMapper.addScanCodeNum(wifiNo);
    }

    public void addProfit(String wifiNo, BigDecimal profit){
        userWifiCountMapper.addProfit(wifiNo,profit);
    }

    public void addLinkNum(String wifiNo){
        userWifiCountMapper.addLinkNum(wifiNo);
    }

    public void addLinkUserNum(String wifiNo){
        userWifiCountMapper.addLinkUser(wifiNo);
    }

    public UserWifiCountModel getInfoByWifiNo(String wifiNo){
        return userWifiCountMapper.getByUserNo(wifiNo);
    }
}
