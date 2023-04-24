package codewifi.service.impl;

import codewifi.common.constant.enums.VerystatusGoodsEnum;
import codewifi.repository.cache.VerystatusMenuConfigCache;
import codewifi.repository.cache.VerystatusGoodsUserCache;
import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.repository.model.VerystatusMenuConfigModel;
import codewifi.repository.model.VerystatusUserModel;
import codewifi.request.very.VerystatusIndexRequest;
import codewifi.response.very.VerystatusIndexResponse;
import codewifi.service.VerystatusIndexService;
import codewifi.utils.BeanCopyUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class VerystatusIndexServiceImpl implements VerystatusIndexService {
    private final VerystatusMenuConfigCache verystatusMenuConfigCache;
    private final VerystatusGoodsUserCache verystatusGoodsUserCache;

    @Override
    public VerystatusIndexResponse getUserIndex(VerystatusIndexRequest verystatusIndexRequest, VerystatusUserModel verystatusUserModel) {
        VerystatusIndexResponse verystatusIndexResponse = new VerystatusIndexResponse();
        String userNo = verystatusUserModel.getUserNo();
        Integer goodsSku = VerystatusGoodsEnum.TO_DAY_VIDEO.getGoodsSku();
        LocalDate today = LocalDate.now();

        VerystatusGoodsUserCo verystatusGoodsUserCo = verystatusGoodsUserCache.getUserGoods(userNo, goodsSku, today);
        verystatusIndexResponse.setIsFinishVideo(verystatusGoodsUserCo.getIsFinish());
        verystatusIndexResponse.setVideoFinish(verystatusGoodsUserCo.getVideoFinish());
        verystatusIndexResponse.setVideoTotal(verystatusGoodsUserCo.getVideoNeed());

        List<VerystatusMenuConfigModel> allConfig = verystatusMenuConfigCache.getAllConfig();
        List<VerystatusIndexResponse.MenuList> menuLists = BeanCopyUtils.copyListProperties(allConfig, VerystatusIndexResponse.MenuList::new);

        verystatusIndexResponse.setMenuList(menuLists);
        return verystatusIndexResponse;
    }
}
