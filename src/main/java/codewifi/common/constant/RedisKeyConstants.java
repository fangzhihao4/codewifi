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



	public static final String LOCAL_HEADER = "codewifi:lock:";

	public static final String LOCAL_USER_LINK_TICKET = LOCAL_HEADER + "user:link:ticket:";
	public static final String LOCAL_USER_CREATE_WIFI = LOCAL_HEADER + "user:create:wifi:";


	public static final String LINK_USER_SORED = "link:user:sort";
	public static final String LINK_USER_HASH = "link:user:hash";


	public static final String LINK_ORDER_BY_LINK_NO = "link:order:";


}
