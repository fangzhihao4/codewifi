package codewifi.service.impl;

import codewifi.annotation.exception.ReturnException;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.enums.VerystatusCoinSourceEnum;
import codewifi.repository.cache.VerystatusCoinOrderCache;
import codewifi.repository.cache.VerystatusGoodsUserCache;
import codewifi.repository.cache.VerystatusUserWalletCache;
import codewifi.repository.co.VerystatusGoodsUserCo;
import codewifi.repository.mapper.VerystatusCoinOrderMapper;
import codewifi.repository.mapper.VerystatusGoodsMapper;
import codewifi.repository.mapper.VerystatusGoodsUserMapper;
import codewifi.repository.model.*;
import codewifi.request.very.VerystatusGoodsInfoRequest;
import codewifi.request.very.VerystatusPayGoodsRequest;
import codewifi.response.very.VerystatusGoodsUserInfoResponse;
import codewifi.service.VerystatusGoodsUserService;
import codewifi.service.VerystatusThirdService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class VerystatusGoodsUserServiceImpl implements VerystatusGoodsUserService {
    private static final LogUtil logUtil = LogUtil.getLogger(VerystatusGoodsUserServiceImpl.class);

    private static final String V1 = "very";
    private static final String V2 = "VerystatusGoodsUserServiceImpl";

    private final VerystatusGoodsUserCache verystatusGoodsUserCache;
    private final VerystatusUserWalletCache verystatusUserWalletCache;
    private final VerystatusGoodsUserMapper verystatusGoodsUserMapper;
    private final VerystatusCoinOrderCache verystatusCoinOrderCache;
    private final VerystatusThirdService verystatusThirdService;


    /**
     * 查询用户商品信息
     * @param userModel 用户信息
     * @param verystatusPayGoodsRequest 商品verystatusPayGoodsRequest
     * @return 商品信息
     */
    @Override
    public VerystatusGoodsUserInfoResponse getUserGoods(VerystatusUserModel userModel, VerystatusPayGoodsRequest verystatusPayGoodsRequest) {
        String v3 = "getUserGoods";
        LocalDate today = LocalDate.now();
        VerystatusGoodsUserCo verystatusGoodsUserCo = verystatusGoodsUserCache.getUserGoods(userModel.getUserNo(), verystatusPayGoodsRequest.getGoodsSku(), today);
        if (Objects.isNull(verystatusGoodsUserCo)){
            logUtil.infoWarn(V1,V2,v3,"找不到这个商品", verystatusPayGoodsRequest, userModel.getUserNo());
            throw new ReturnException(ReturnEnum.NO_FUND_THIS_GOODS);
        }
        if (Objects.nonNull(verystatusPayGoodsRequest.getParamFirst())){
            getGoodsContent(verystatusGoodsUserCo,verystatusPayGoodsRequest);
            if (!StringUtils.isEmpty(verystatusGoodsUserCo.getContent())){
                String content = verystatusGoodsUserCo.getContent();
                int subInt = Math.min(100,content.length() / 4);
                verystatusGoodsUserCo.setContent(content.substring(0, subInt));
            }
        }
        return getResByCo(verystatusGoodsUserCo);
    }

    @Override
    public List<VerystatusGoodsUserInfoResponse> getUserGoodsList(VerystatusUserModel verystatusUserModel, VerystatusGoodsInfoRequest verystatusGoodsInfoRequest) {
        return null;
    }

    /**
     * 商品支付
     * @param userModel 用户信息
     * @param verystatusPayGoodsRequest 支付类型
     * @param goodsSku 商品sku
     * @return 支付结果
     */
    @Override
    @Transactional
    public VerystatusGoodsUserInfoResponse getUserPayContent(VerystatusUserModel userModel, VerystatusPayGoodsRequest verystatusPayGoodsRequest) {
        String v3 = "getUserPayContent";
        LocalDate today = LocalDate.now();
        VerystatusGoodsUserCo verystatusGoodsUserCo = verystatusGoodsUserCache.getUserGoods(userModel.getUserNo(), verystatusPayGoodsRequest.getGoodsSku(), today);
        if (Objects.isNull(verystatusGoodsUserCo)){
            logUtil.infoWarn(V1,V2,v3,"找不到这个商品", verystatusPayGoodsRequest, userModel.getUserNo());
            throw new ReturnException(ReturnEnum.NO_FUND_THIS_GOODS);
        }
        //使用金币
        if (VerystatusGoodsMapper.price_coin.equals(verystatusPayGoodsRequest.getPayType()) && VerystatusGoodsMapper.USE_COIN.contains(verystatusGoodsUserCo.getPriceType())){
            return getByCoin(userModel,verystatusPayGoodsRequest,verystatusGoodsUserCo);
        }
        //使用免费
        if (VerystatusGoodsMapper.price_free.equals(verystatusPayGoodsRequest.getPayType())){
            return getByFree(userModel,verystatusPayGoodsRequest,verystatusGoodsUserCo);
        }
        //使用视频
        if (VerystatusGoodsMapper.price_video.equals(verystatusPayGoodsRequest.getPayType()) && VerystatusGoodsMapper.USE_VIDEO.contains(verystatusGoodsUserCo.getPriceType())){
            return getByVideo(userModel,verystatusPayGoodsRequest,verystatusGoodsUserCo);
        }
        logUtil.infoWarn(V1,V2,v3,"不支持的类型", verystatusGoodsUserCo.getGoodsSku(), verystatusGoodsUserCo);
        throw new ReturnException(ReturnEnum.NOT_USE_TYPE_GOODS);
    }

    /**
     * 金币支付
     * @param userModel 用户信息
     * @param verystatusGoodsUserCo 个人商品信息
     * @return 支付结果
     */
    public VerystatusGoodsUserInfoResponse getByCoin(VerystatusUserModel userModel, VerystatusPayGoodsRequest verystatusPayGoodsRequest, VerystatusGoodsUserCo verystatusGoodsUserCo){
        String v3 = "getByCoin";
        VerystatusUserWalletModel userWallet = verystatusUserWalletCache.getUserWallet(userModel.getUserNo());
        if (Objects.isNull(userWallet) || (userWallet.getCoin().compareTo(verystatusGoodsUserCo.getCoin()) < 0)){
            logUtil.infoWarn(V1,V2,v3,"金币不足", verystatusGoodsUserCo.getGoodsSku(), userModel.getUserNo());
            throw new ReturnException(ReturnEnum.USER_COIN_LESS);
        }
        boolean getInfoRes = getGoodsContent(verystatusGoodsUserCo, verystatusPayGoodsRequest);
        if (!getInfoRes){
            logUtil.infoWarn(V1,V2,v3,"查询相关信息失败", verystatusGoodsUserCo.getGoodsSku(), userModel.getUserNo());
            throw new ReturnException(ReturnEnum.GET_GOODS_CONTENT_FALSE);
        }
        verystatusUserWalletCache.changeUserCoin(false, userModel.getUserNo(), verystatusGoodsUserCo.getCoin());

        VerystatusGoodsUserModel verystatusGoodsUserModel = finishUserGoods(userModel,verystatusGoodsUserCo,VerystatusGoodsMapper.price_coin);

        VerystatusCoinOrderModel verystatusCoinOrderModel = new VerystatusCoinOrderModel();
        verystatusCoinOrderModel.setUserNo(userWallet.getUserNo());
        verystatusCoinOrderModel.setOldCoin(userWallet.getCoin());
        verystatusCoinOrderModel.setNewCoin(userWallet.getCoin().subtract(verystatusGoodsUserCo.getCoin()));
        verystatusCoinOrderModel.setChangeType(VerystatusCoinOrderMapper.COIN_SUB);
        addOrder(userModel,verystatusGoodsUserCo,verystatusCoinOrderModel);

        VerystatusGoodsUserInfoResponse verystatusGoodsUserInfoResponse = getResByCo(verystatusGoodsUserCo);
        verystatusGoodsUserInfoResponse.setUserFinishTime(verystatusGoodsUserModel.getFinishTimes());
        verystatusGoodsUserInfoResponse.setUserIsFinish(verystatusGoodsUserModel.getIsFinish());
        verystatusGoodsUserInfoResponse.setContentImg(verystatusGoodsUserCo.getContentImg());
        verystatusGoodsUserInfoResponse.setContent(verystatusGoodsUserCo.getContent());
        return verystatusGoodsUserInfoResponse;
    }


    /**
     * 免费使用
     * @param userModel 用户信息
     * @param verystatusGoodsUserCo 用户商品信息
     * @return 支付结果
     */
    public VerystatusGoodsUserInfoResponse getByFree(VerystatusUserModel userModel,VerystatusPayGoodsRequest verystatusPayGoodsRequest,VerystatusGoodsUserCo verystatusGoodsUserCo){
        String v3 = "getByFree";
        boolean getInfoRes = getGoodsContent(verystatusGoodsUserCo,verystatusPayGoodsRequest);
        if (!getInfoRes){
            logUtil.infoWarn(V1,V2,v3,"查询相关信息失败", verystatusGoodsUserCo.getGoodsSku(), userModel.getUserNo());
            throw new ReturnException(ReturnEnum.GET_GOODS_CONTENT_FALSE);
        }
        //永久免费
        if (VerystatusGoodsMapper.price_free.equals(verystatusGoodsUserCo.getPriceType())){
            VerystatusCoinOrderModel verystatusCoinOrderModel = new VerystatusCoinOrderModel();
            verystatusCoinOrderModel.setChangeType(VerystatusCoinOrderMapper.FREE);
            addOrder(userModel,verystatusGoodsUserCo,verystatusCoinOrderModel);

            VerystatusGoodsUserInfoResponse verystatusGoodsUserInfoResponse = getResByCo(verystatusGoodsUserCo);
            verystatusGoodsUserInfoResponse.setContentImg(verystatusGoodsUserCo.getContentImg());
            verystatusGoodsUserInfoResponse.setContent(verystatusGoodsUserCo.getContent());
            return verystatusGoodsUserInfoResponse;
        }

        if (verystatusGoodsUserCo.getFreeNum() <= verystatusGoodsUserCo.getUserUseNum()){
            logUtil.infoWarn(V1,V2,v3,"免费次数达到上限", null, verystatusGoodsUserCo);
            throw new ReturnException(ReturnEnum.FREE_TIME_IS_MAX);
        }

        VerystatusGoodsUserModel verystatusGoodsUserModel = finishUserGoods(userModel,verystatusGoodsUserCo,VerystatusGoodsMapper.price_free);

        VerystatusCoinOrderModel verystatusCoinOrderModel = new VerystatusCoinOrderModel();
        verystatusCoinOrderModel.setUserNo(userModel.getUserNo());
        verystatusCoinOrderModel.setChangeType(VerystatusCoinOrderMapper.FREE);
        addOrder(userModel,verystatusGoodsUserCo,verystatusCoinOrderModel);

        VerystatusGoodsUserInfoResponse verystatusGoodsUserInfoResponse = getResByCo(verystatusGoodsUserCo);
        verystatusGoodsUserInfoResponse.setUserFinishTime(verystatusGoodsUserModel.getFinishTimes());
        verystatusGoodsUserInfoResponse.setUserIsFinish(verystatusGoodsUserModel.getIsFinish());
        verystatusGoodsUserInfoResponse.setContentImg(verystatusGoodsUserCo.getContentImg());
        verystatusGoodsUserInfoResponse.setContent(verystatusGoodsUserCo.getContent());
        return verystatusGoodsUserInfoResponse;
    }


    /**
     * 视频支付
     * @param userModel 用户信息
     * @param verystatusGoodsUserCo 用户商品信息
     * @return 支付结果
     */
    public VerystatusGoodsUserInfoResponse getByVideo(VerystatusUserModel userModel,VerystatusPayGoodsRequest verystatusPayGoodsRequest,VerystatusGoodsUserCo verystatusGoodsUserCo){
        String v3 = "getByVideo";
        VerystatusCoinOrderModel verystatusCoinOrderModel = new VerystatusCoinOrderModel();
        VerystatusGoodsUserInfoResponse verystatusGoodsUserInfoResponse = getResByCo(verystatusGoodsUserCo);
        //完成
        if ( (verystatusGoodsUserCo.getUserVideoFinish() + 1) >= verystatusGoodsUserCo.getVideoNum()){
            boolean getInfoRes = getGoodsContent(verystatusGoodsUserCo,verystatusPayGoodsRequest);
            if (!getInfoRes){
                logUtil.infoWarn(V1,V2,v3,"查询相关信息失败", verystatusGoodsUserCo.getGoodsSku(), userModel.getUserNo());
                throw new ReturnException(ReturnEnum.GET_GOODS_CONTENT_FALSE);
            }
            VerystatusGoodsUserModel verystatusGoodsUserModel = addVideoGoods(userModel,verystatusGoodsUserCo);
            verystatusGoodsUserInfoResponse.setUserFinishTime(verystatusGoodsUserModel.getFinishTimes());
            verystatusGoodsUserInfoResponse.setUserIsFinish(verystatusGoodsUserModel.getIsFinish());
            verystatusGoodsUserInfoResponse.setContentImg(verystatusGoodsUserCo.getContentImg());
            verystatusGoodsUserInfoResponse.setContent(verystatusGoodsUserCo.getContent());
            verystatusCoinOrderModel.setChangeType(VerystatusCoinOrderMapper.VIDEO_FINISH);
        }else{
            verystatusCoinOrderModel.setChangeType(VerystatusCoinOrderMapper.VIDEO);
            addVideoGoods(userModel,verystatusGoodsUserCo);
        }

        addOrder(userModel,verystatusGoodsUserCo,verystatusCoinOrderModel);

        verystatusGoodsUserInfoResponse.setUserVideoFinish(verystatusGoodsUserCo.getUserVideoFinish()+1);
        return verystatusGoodsUserInfoResponse;
    }


    /**
     * 更新用户商品视频观看结果
     * @param userModel 用户信息
     * @param verystatusGoodsUserCo 用户商品信息
     * @return 最新结果
     */
    public VerystatusGoodsUserModel addVideoGoods(VerystatusUserModel userModel,VerystatusGoodsUserCo verystatusGoodsUserCo){
        String v3 = "addVideoGoods";
        VerystatusGoodsUserModel verystatusGoodsUserModel = verystatusGoodsUserMapper.getInfo(verystatusGoodsUserCo.getGoodsSku(),userModel.getUserNo());
        if (Objects.isNull(verystatusGoodsUserModel)){
            logUtil.infoWarn(V1,V2,v3,"没有查询到记录", verystatusGoodsUserCo.getGoodsSku(), userModel.getUserNo());
            throw new ReturnException(ReturnEnum.NO_FUND_USER_GOODS);
        }
        int newVideo = verystatusGoodsUserCo.getUserVideoFinish() + 1;
        if (newVideo >= verystatusGoodsUserCo.getVideoNum()){
            verystatusGoodsUserModel.setIsFinish(VerystatusGoodsUserMapper.IS_FINISH);
            verystatusGoodsUserModel.setFinishTimes(verystatusGoodsUserModel.getFinishTimes() + 1);
            verystatusGoodsUserModel.setContentImg(verystatusGoodsUserCo.getContentImg());
            verystatusGoodsUserModel.setContent(verystatusGoodsUserCo.getContent());
        }
        verystatusGoodsUserModel.setVideoFinish(newVideo);
        verystatusGoodsUserCache.updateUserGoods(verystatusGoodsUserModel);
        return verystatusGoodsUserModel;
    }

    /**
     * 完成后更新用户商品
     * @param userModel  用户信息
     * @param verystatusGoodsUserCo 商品信息
     * @param useType 更新类型
     * @return 更新后的商品信息
     */
    public VerystatusGoodsUserModel  finishUserGoods(VerystatusUserModel userModel,VerystatusGoodsUserCo verystatusGoodsUserCo, int useType){
        String v3 = "finishUserGoods";
        VerystatusGoodsUserModel verystatusGoodsUserModel = verystatusGoodsUserMapper.getInfo(verystatusGoodsUserCo.getGoodsSku(),userModel.getUserNo());
        if (Objects.isNull(verystatusGoodsUserModel)){
            logUtil.infoWarn(V1,V2,v3,"没有查询到记录", verystatusGoodsUserCo.getGoodsSku(), userModel.getUserNo());
            throw new ReturnException(ReturnEnum.NO_FUND_USER_GOODS);
        }
        if (VerystatusGoodsMapper.price_free.equals(useType)) {
            verystatusGoodsUserModel.setUseNum(verystatusGoodsUserModel.getUseNum() + 1);
        }
        verystatusGoodsUserModel.setIsFinish(VerystatusGoodsUserMapper.IS_FINISH);
        verystatusGoodsUserModel.setFinishTimes(verystatusGoodsUserModel.getFinishTimes() + 1);
        verystatusGoodsUserModel.setContentImg(verystatusGoodsUserCo.getContentImg());
        verystatusGoodsUserModel.setContent(verystatusGoodsUserCo.getContent());

        verystatusGoodsUserCache.updateUserGoods(verystatusGoodsUserModel);
        return verystatusGoodsUserModel;
    }

    /**
     * 新增订单记录
     * @param userModel 用户信息
     * @param verystatusGoodsUserCo 用户商品信息
     * @param verystatusCoinOrderModel 订单信息
     */
    public void addOrder(VerystatusUserModel userModel, VerystatusGoodsUserCo verystatusGoodsUserCo,VerystatusCoinOrderModel verystatusCoinOrderModel){
        VerystatusCoinSourceEnum verystatusCoinSourceEnum = VerystatusCoinSourceEnum.getGoodsEnum(verystatusGoodsUserCo.getGoodsSku());
        VerystatusCoinOrderInfoModel verystatusCoinOrderInfoModel = new VerystatusCoinOrderInfoModel();

        if (Objects.isNull(verystatusCoinOrderModel.getOldCoin())){
            verystatusCoinOrderModel.setOldCoin(BigDecimal.valueOf(0));
        }
        if (Objects.isNull(verystatusCoinOrderModel.getNewCoin())){
            verystatusCoinOrderModel.setNewCoin(BigDecimal.valueOf(0));
        }
        verystatusCoinOrderModel.setUserNo(userModel.getUserNo());
        verystatusCoinOrderModel.setUseCoin(verystatusGoodsUserCo.getCoin());
        verystatusCoinOrderModel.setSource(verystatusCoinSourceEnum.getSource());
        verystatusCoinOrderModel.setDescription(verystatusCoinSourceEnum.getName());

        verystatusCoinOrderInfoModel.setUserNo(userModel.getUserNo());
        verystatusCoinOrderInfoModel.setSource(verystatusCoinSourceEnum.getSource());
        verystatusCoinOrderInfoModel.setTitle(verystatusCoinSourceEnum.getName());
        verystatusCoinOrderInfoModel.setChangeType(verystatusCoinOrderModel.getChangeType());
        verystatusCoinOrderInfoModel.setShowType(verystatusGoodsUserCo.getShowType());
        verystatusCoinOrderInfoModel.setContentImg(verystatusGoodsUserCo.getContentImg());
        verystatusCoinOrderInfoModel.setContent(verystatusGoodsUserCo.getContent());
        verystatusCoinOrderInfoModel.setSource(verystatusCoinOrderModel.getSource());

        verystatusCoinOrderCache.addOrder(verystatusCoinOrderModel,verystatusCoinOrderInfoModel);
    }


    public VerystatusGoodsUserInfoResponse getResByCo(VerystatusGoodsUserCo verystatusGoodsUserCo){
        VerystatusGoodsUserInfoResponse verystatusGoodsUserInfoResponse = new VerystatusGoodsUserInfoResponse();
        verystatusGoodsUserInfoResponse.setGoodsSku(verystatusGoodsUserCo.getGoodsSku());
        verystatusGoodsUserInfoResponse.setPriceType(verystatusGoodsUserCo.getPriceType());
        verystatusGoodsUserInfoResponse.setFreeNum(verystatusGoodsUserCo.getFreeNum());
        verystatusGoodsUserInfoResponse.setCoin(verystatusGoodsUserCo.getCoin());
        verystatusGoodsUserInfoResponse.setVideoNum(verystatusGoodsUserCo.getVideoNum());
        verystatusGoodsUserInfoResponse.setUserVideoFinish(verystatusGoodsUserCo.getUserVideoFinish());
        verystatusGoodsUserInfoResponse.setUserFinishTime(verystatusGoodsUserCo.getUserFinishTime());
        verystatusGoodsUserInfoResponse.setRepeatTime(verystatusGoodsUserCo.getRepeatTime());
        verystatusGoodsUserInfoResponse.setUserIsFinish(verystatusGoodsUserCo.getUserIsFinish());
        verystatusGoodsUserInfoResponse.setShowType(verystatusGoodsUserCo.getShowType());
        verystatusGoodsUserInfoResponse.setContent(verystatusGoodsUserCo.getContent());
        verystatusGoodsUserInfoResponse.setContentImg(verystatusGoodsUserCo.getContentImg());
        verystatusGoodsUserInfoResponse.setUserUseNum(verystatusGoodsUserCo.getUserUseNum());
        return verystatusGoodsUserInfoResponse;
    }

    public boolean getGoodsContent(VerystatusGoodsUserCo verystatusGoodsUserCo, VerystatusPayGoodsRequest verystatusPayGoodsRequest){
        return verystatusThirdService.getThirdContent(verystatusGoodsUserCo,verystatusPayGoodsRequest);
    }

}
