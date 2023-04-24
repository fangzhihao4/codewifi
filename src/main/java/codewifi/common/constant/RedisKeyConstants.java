package codewifi.common.constant;

import lombok.Getter;

@Getter
public class RedisKeyConstants {

	public static final int NO_EXPIRE_TIME = -1;

	public static final int EXPIRE_BY_FIVE_SECONDS = 5;
	
	public static final int EXPIRE_BY_TEN_SECONDS = 10;

	public static final int EXPIRE_BY_FIVE_MINUTE_SECONDS = 120;

	public static final int EXPIRE_BY_THIRTY_MINUTE_SECONDS = 300;

	public static final int EXPIRE_BY_TEN_MINUTE = 600;

	public static final int EXPIRE_BY_ONE_HOUR = 3600;

	public static final int EXPIRE_BY_TWO_HOUR = 7200;

	public static final int EXPIRE_BY_TREE_HOUR = 10800;

	public static final int EXPIRE_BY_DAY_SECONDS = 86400;

	public static final int EXPIRE_BY_WEEK_SECONDS = 86400 * 7;

	public static final int EXPIRE_BY_MONTH_SECONDS = 86400 * 30;

	public static final int EXPIRE_ONE_MONTH_BY_DAY = 32;

	public static final int EXPIRE_BY_WEEK_DAY = 7;


	public static final String HEADER = "codewifi:";

	public static final String USER_HEADER = "";
	public static final String USER_TOKEN = HEADER + "token:";


	public static final String WX_ACCESS_TOKEN = "wx:access:token";


	public static final String USER_GROUP_LIST = HEADER + "user:group:list:";
	public static final String WIFI_INFO = HEADER + "wifi:info:";
	public static final String USER_WIFI_NO_LIST = HEADER + "user:wifi:no:list";
	public static final String USER_BY_INVITE_INFO = HEADER + "user:by:invite:";
	public static final String USER_BY_SUB_INFO = HEADER + "user:by:sub:";
	public static final String USER_PROFIT = HEADER + "user:profit:";
	public static final String USER_WIFI_COUNT = HEADER + "user:wifi:count";
	public static final String USER_LINK_TICKET = HEADER + "user:link:ticket:";

	public static final String USER_WIFI_FIRST_PAGE = HEADER + "user:wifi:page:1:";
	public static final String USER_INVITE_PROFIT = HEADER + "user:invite:profit:page:";

	public static final String WX_CODE_SCENE = HEADER + "wx:code:scene:";
	public static final String LINK_USER_SORED = HEADER + "link:user:sort";
	public static final String LINK_USER_HASH = HEADER + "link:user:hash";

	public static final String LINK_ORDER_BY_LINK_NO = HEADER + "link:order:";


	public static final String LOCAL_HEADER = "codewifi:lock:";

	public static final String LOCAL_USER_LINK_TICKET = LOCAL_HEADER + "user:link:ticket:";
	public static final String LOCAL_USER_CREATE_WIFI = LOCAL_HEADER + "user:create:wifi:";





	public static final String VERY_STATUS = "very:";
	public static final String VERY_STATUS_SYSTEM_MENU = VERY_STATUS + "system:menu"; //系统菜单
	public static final String VERY_STATUS_SYSTEM_GOODS = VERY_STATUS + "system:goods:"; //系统商品

	public static final String VERY_STATUS_USER_TOKEN = VERY_STATUS + "user:token:" ; //用户token

	public static final String VERY_STATUS_USER_WALLET = VERY_STATUS + "user:wallet:info:" ;//用户钱包
	public static final String VERY_STATUS_USER_VIDEO = VERY_STATUS + "user:video:type:";//用户看视频
	public static final String VERY_STATUS_USER_GOODS = VERY_STATUS + "user:goods:"; //用户商品信息
	public static final String STAR_FORTUNE_STRING = VERY_STATUS + "third:star:all:"; //星座运势
	public static final String VERY_STATUS_HOT_HU_PU_HASH = VERY_STATUS +  "third:hot:hupu:hash"; //热榜虎扑
	public static final String VERY_STATUS_HOT_ZHI_HU_HASH = VERY_STATUS +  "third:hot:zhihu:hash"; //热榜知乎
	public static final String VERY_STATUS_HOT_36_KE_HASH = VERY_STATUS +  "third:hot:36ke:hash"; //热榜36氪
	public static final String VERY_STATUS_HOT_BAI_DU_HASH = VERY_STATUS +  "third:hot:baidu:hash"; //热榜百度
	public static final String VERY_STATUS_HOT_BILI_HASH = VERY_STATUS +  "third:hot:bili:hash"; //热榜bilibili
	public static final String VERY_STATUS_HOT_TIE_BA_HASH = VERY_STATUS +  "third:hot:tieba:hash"; //热榜bilibili
	public static final String VERY_STATUS_HOT_WEIBO_HASH = VERY_STATUS +  "third:hot:weibo:hash"; //热榜微博
	public static final String VERY_STATUS_HOT_DOU_YIN_HASH = VERY_STATUS +  "third:hot:douyin:hash"; //热榜抖音
	public static final String VERY_STATUS_HOT_HISTORY_HASH = VERY_STATUS +  "third:hot:history:hash"; //历史上的今天

	public static final String VERY_STATUS_WORK_SAO_HUA_RATE = VERY_STATUS + "third:work:sao:hua:rate"; //骚话查询数据库概率
	public static final String VERY_STATUS_WORK_QING_HUA_RATE = VERY_STATUS + "third:work:qing:hua:rate"; //情话话查询数据库概率

	public static final String VERY_STATUS_IMG_DONG_MAN_RATE = VERY_STATUS + "third:img:dong:man:rate"; //动漫二次元图片
	public static final String VERY_STATUS_IMG_TAO_GIRL_RATE = VERY_STATUS + "third:img:tao:girl:rate"; //淘宝卖家秀
	public static final String VERY_STATUS_IMG_MOBIL_GIRL_RATE = VERY_STATUS + "third:img:mobil:girl:rate"; //美女图
	public static final String VERY_STATUS_IMG_LOL_SPIN_RATE = VERY_STATUS + "third:img:lol:spin:rate"; //lol高清壁纸
	public static final String VERY_STATUS_IMG_HEAD_AVATAR_RATE = VERY_STATUS + "third:img:head:avatar:rate"; //随机头像
	public static final String VERY_STATUS_IMG_HEAD_NAN_RATE = VERY_STATUS + "third:img:head:nan:rate"; //随机头像男
	public static final String VERY_STATUS_IMG_HEAD_NV_RATE = VERY_STATUS + "third:img:head:nv:rate"; //随机头像女
	public static final String VERY_STATUS_IMG_HEAD_DM_RATE = VERY_STATUS + "third:img:head:dm:rate"; //随机头像动漫
	public static final String VERY_STATUS_IMG_HEAD_JW_RATE = VERY_STATUS + "third:img:head:jw:rate"; //随机头像景物



	public static final String VERY_LOCK = "very:lock:";
	public static final String VERY_STATUS_LOCK_USER_VIDEO = VERY_LOCK +  "user:goods:video:";//看视频的缓存
	public static final String VERY_STATUS_LOCK_USER_GOODS_DAY_START = VERY_LOCK + "user:goods:day:start"; //商品每天第一次更新
	public static final String VERY_STATUS_LOCK_USER_WALLET = VERY_LOCK + "user:wallet:change:";//金币钱包增减锁
	public static final String VERY_STATUS_LOCK_USER_INFO_UPDATE = VERY_LOCK + "user:info:update:"; //用户信息更新



}
