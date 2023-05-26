package codewifi.service.impl;


import codewifi.repository.cache.VerystatusCouponCache;
import codewifi.repository.model.VerystatusCouponModel;
import codewifi.request.very.VerystatusCouponListRequest;
import codewifi.response.very.VerystatusCouponInfoResponse;
import codewifi.response.very.VerystatusCouponListResponse;
import codewifi.service.VerystatusCouponService;
import codewifi.utils.DateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VerystatusCouponServiceImpl implements VerystatusCouponService {

    private final VerystatusCouponCache verystatusCouponCache;

    @Override
    public VerystatusCouponListResponse getList(VerystatusCouponListRequest request) {
        VerystatusCouponListResponse response = new VerystatusCouponListResponse();
        List<VerystatusCouponModel> list = verystatusCouponCache.getUserGoodsList(request.getType());
        if (list.isEmpty()){
            response.setList(new ArrayList<>());
            return response;
        }
        List<VerystatusCouponInfoResponse> infoList = new ArrayList<>();
        for (VerystatusCouponModel verystatusCouponModel : list){
            VerystatusCouponInfoResponse infoResponse = new VerystatusCouponInfoResponse();
            infoResponse.setAppId(verystatusCouponModel.getAppid());
            infoResponse.setPage(verystatusCouponModel.getPage());
            infoResponse.setCover(verystatusCouponModel.getCover());
            infoResponse.setTitle(verystatusCouponModel.getTitle());
            infoResponse.setSubTitle(verystatusCouponModel.getSubTitle());
            infoResponse.setDesc(verystatusCouponModel.getDesc());
            String startTime = DateTimeUtil.localDateTimeToDateString(verystatusCouponModel.getStartTime(), "yyyy-MM-dd");
            String endTime = DateTimeUtil.localDateTimeToDateString(verystatusCouponModel.getEndTime(),"yyyy-MM-dd");
            infoResponse.setStartTime(startTime);
            infoResponse.setEndTime(endTime);
            infoList.add(infoResponse);
        }
        response.setList(infoList);
        return response;
    }
}
