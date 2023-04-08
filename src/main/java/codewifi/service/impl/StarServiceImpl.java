package codewifi.service.impl;

import codewifi.annotation.exception.ReturnException;
import codewifi.common.constant.ReturnEnum;
import codewifi.common.constant.enums.HoroscopeEnum;
import codewifi.repository.cache.UserStarCache;
import codewifi.repository.model.UserModel;
import codewifi.repository.model.UserStarRecordModel;
import codewifi.request.wifi.StarFortuneRequest;
import codewifi.response.wifi.StarResponse;
import codewifi.sdk.sdkHoroscope.HoroscopeSdkService;
import codewifi.sdk.sdkHoroscope.response.HoroscopeSdkResponse;
import codewifi.service.StarService;
import codewifi.utils.LogUtil;
import lombok.AllArgsConstructor;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StarServiceImpl implements StarService {
    private static final LogUtil logUtil = LogUtil.getLogger(StarServiceImpl.class);

    private static final String V1 = "user";
    private static final String V2 = "LoginServiceImpl";

    private final UserStarCache userStarCache;
    private final HoroscopeSdkService horoscopeSdkService;

    @Override
    public StarResponse getStarContent(StarFortuneRequest request, UserModel userModel) {
        String v3 = "getStarContent";
        String time = request.getTime();
        String star = request.getStar();

        HoroscopeEnum timeHoroscopeEnum = HoroscopeEnum.getTimeByType(time);
        HoroscopeEnum starHoroscopeEnum = HoroscopeEnum.getStarByType(star);
        if (Objects.isNull(timeHoroscopeEnum) || Objects.isNull(starHoroscopeEnum)){
            logUtil.infoBug(V1, V2, v3, "查询星座运势参数错误", request, null);
            throw new ReturnException(ReturnEnum.STAR_FORTUNE_PARAMS_ERROR);
        }
        StarResponse starResponse = new StarResponse();
        LocalDate localDate = LocalDate.now();

        UserStarRecordModel userStarRecordModel = userStarCache.getUserByFortune(userModel.getUserNo(),timeHoroscopeEnum.getType(), starHoroscopeEnum.getType(), localDate);
        if (Objects.nonNull(userStarRecordModel)){
            starResponse.setContent(userStarRecordModel.getContent());
            return starResponse;
        }

        String content = getContent(timeHoroscopeEnum.getType(),starHoroscopeEnum.getType(), localDate);
        if (Objects.isNull(content)){
            logUtil.infoBug(V1, V2, v3, "查询星座运势参数错误", request, null);
            throw new ReturnException(ReturnEnum.GET_STAR_SDK_RESPONSE_ERROR);
        }

        starResponse.setContent(content);
        userStarCache.addFortune(userModel.getUserNo(),timeHoroscopeEnum,starHoroscopeEnum,localDate,content);
        return starResponse;
    }

    public String getContent(String time, String star, LocalDate localDate ){
        String content = userStarCache.getStarContent(time,star, localDate);
        if (Objects.nonNull(content)){
            return content;
        }

        HoroscopeSdkResponse horoscopeInfo = horoscopeSdkService.getHoroscopeInfo(time, star);
        String checkContent = checkAndContent(horoscopeInfo);
        userStarCache.setStarContent(time,star,localDate,checkContent);
        return checkContent;
    }

    public String checkAndContent(HoroscopeSdkResponse horoscopeInfo){
        String content = "";
        if (!StringUtils.isEmpty(horoscopeInfo.getData().getTitle())){
            content = content + "\n标题: " + horoscopeInfo.getData().getTitle();
        }
        if (!StringUtils.isEmpty(horoscopeInfo.getData().getType())){
            content = content + "\n类型: " + horoscopeInfo.getData().getType();
        }
        if (!StringUtils.isEmpty(horoscopeInfo.getData().getTime())){
            content = content + "\n更新时间: " + horoscopeInfo.getData().getTime();
        }

        if (Objects.nonNull(horoscopeInfo.getData().getTodo())){
            content = content + "\n动作: ";
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getTodo().getYi())){
                content = content + "\n&nbsp; 宜做: " + horoscopeInfo.getData().getTodo().getYi();
            }
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getTodo().getYi())){
                content = content + "\n&nbsp; 忌做: " + horoscopeInfo.getData().getTodo().getJi();
            }
        }

        if (Objects.nonNull(horoscopeInfo.getData().getFortune())){
            content = content + "\n运势: ";
            if (Objects.nonNull(horoscopeInfo.getData().getFortune().getAll())){
                content = content + "\n&nbsp; 综合运势: " + horoscopeInfo.getData().getFortune().getAll();
            }
            if (Objects.nonNull(horoscopeInfo.getData().getFortune().getLove())){
                content = content + "\n&nbsp; 爱情运势: " + horoscopeInfo.getData().getFortune().getLove();
            }
            if (Objects.nonNull(horoscopeInfo.getData().getFortune().getWork())){
                content = content + "\n&nbsp; 学业工作: " + horoscopeInfo.getData().getFortune().getWork();
            }
            if (Objects.nonNull(horoscopeInfo.getData().getFortune().getMoney())){
                content = content + "\n&nbsp; 财富运势: " + horoscopeInfo.getData().getFortune().getMoney();
            }
            if (Objects.nonNull(horoscopeInfo.getData().getFortune().getHealth())){
                content = content + "\n&nbsp; 健康运势: " + horoscopeInfo.getData().getFortune().getHealth();
            }
        }

        if (Objects.nonNull(horoscopeInfo.getData().getIndex())){
            content = content + "\n指数: ";
            if (Objects.nonNull(horoscopeInfo.getData().getIndex().getAll())){
                content = content + "\n&nbsp; 综合指数: " + horoscopeInfo.getData().getIndex().getAll();
            }
            if (Objects.nonNull(horoscopeInfo.getData().getIndex().getLove())){
                content = content + "\n&nbsp; 爱情指数: " + horoscopeInfo.getData().getIndex().getLove();
            }
            if (Objects.nonNull(horoscopeInfo.getData().getIndex().getWork())){
                content = content + "\n&nbsp; 学业工作: " + horoscopeInfo.getData().getIndex().getWork();
            }
            if (Objects.nonNull(horoscopeInfo.getData().getIndex().getMoney())){
                content = content + "\n&nbsp; 财富指数: " + horoscopeInfo.getData().getIndex().getMoney();
            }
            if (Objects.nonNull(horoscopeInfo.getData().getIndex().getHealth())){
                content = content + "\n&nbsp; 健康指数: " + horoscopeInfo.getData().getIndex().getHealth();
            }
        }

        if (Objects.nonNull(horoscopeInfo.getData().getLuckynumber())){
            content = content + "\n幸运数字: " + horoscopeInfo.getData().getLuckynumber();
        }
        if (!StringUtils.isEmpty(horoscopeInfo.getData().getLuckycolor())){
            content = content + "\n幸运颜色: " + horoscopeInfo.getData().getLuckycolor();
        }
        if (!StringUtils.isEmpty(horoscopeInfo.getData().getLuckyconstellation())){
            content = content + "\n速配星座: " + horoscopeInfo.getData().getLuckyconstellation();
        }
        if (!StringUtils.isEmpty(horoscopeInfo.getData().getBadconstellation())){
            content = content + "\n提防星座: " + horoscopeInfo.getData().getBadconstellation();
        }
        if (!StringUtils.isEmpty(horoscopeInfo.getData().getShortcomment())){
            content = content + "\n短评: " + horoscopeInfo.getData().getShortcomment();
        }
        if (!StringUtils.isEmpty(horoscopeInfo.getData().getShortcomment())){
            content = content + "\n短评: " + horoscopeInfo.getData().getShortcomment();
        }


        if (Objects.nonNull(horoscopeInfo.getData().getFortunetext())){
            content = content + "\n运势解析: ";
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getFortunetext().getAll())){
                content = content + "\n&nbsp; ♉综合运势: " + horoscopeInfo.getData().getFortunetext().getAll();
            }
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getFortunetext().getLove())){
                content = content + "\n&nbsp; ♉爱情运势: " + horoscopeInfo.getData().getFortunetext().getLove();
            }
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getFortunetext().getWork())){
                content = content + "\n&nbsp; ♉学业工作: " + horoscopeInfo.getData().getFortunetext().getWork();
            }
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getFortunetext().getMoney())){
                content = content + "\n&nbsp; ♉财富运势: " + horoscopeInfo.getData().getFortunetext().getMoney();
            }
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getFortunetext().getHealth())){
                content = content + "\n&nbsp; ♉健康运势: " + horoscopeInfo.getData().getFortunetext().getHealth();
            }
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getFortunetext().getDecompression())){
                content = content + "\n&nbsp; ♉解压秘诀: " + horoscopeInfo.getData().getFortunetext().getDecompression();
            }
            if (!StringUtils.isEmpty(horoscopeInfo.getData().getFortunetext().getOpenluck())){
                content = content + "\n&nbsp; ♉开运秘诀: " + horoscopeInfo.getData().getFortunetext().getOpenluck();
            }
        }

        return content;
    }
}
