package codewifi.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateTimeUtil {

	private static final String TIME_ZONE = "Asia/Shanghai";

	/**
	 * 秒时间戳转对应的日期格式LocalDateTime
	 * @param timestamp 秒时间戳
	 * @return LocalDateTime
	 */
	public static LocalDateTime timestampToLocalDateTime(long timestamp) {
		Instant instant = Instant.ofEpochSecond(timestamp);
		return LocalDateTime.ofInstant(instant, ZoneId.of(TIME_ZONE));
	}

	/**
	 * 秒时间戳转对应格式的时间字符串
	 * @param timestamp 秒时间戳
	 * @param dateFormat 转换的时间格式
	 * @return 时间格式
	 */
	public static String timestampToDateString(long timestamp, String dateFormat) {
		Instant instant = Instant.ofEpochSecond(timestamp);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(TIME_ZONE));
		return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat));
	}

	/**
	 * localDateTime转对应的dateString
	 * @param localDateTime localDateTime
	 * @param dateFormat dateString的格式
	 * @return DateString
	 */
	public static String localDateTimeToDateString(LocalDateTime localDateTime, String dateFormat) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
		return localDateTime.atZone(ZoneId.of(TIME_ZONE)).format(dateTimeFormatter);
	}

	/**
	 * localDateTime转对应的秒时间戳
	 * @param localDateTime localDateTime
	 * @return 秒时间戳
	 */
	public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.of(TIME_ZONE)).toEpochSecond();
	}

	/**
	 * dateString转对应的localDateTime
	 * @param dateString dateString
	 * @param dateFormat dateString的格式
	 * @return LocalDateTime
	 */
	public static LocalDateTime dateStringToLocalDateTime(String dateString, String dateFormat) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
		return LocalDateTime.parse(dateString, dateTimeFormatter);
	}

	/**
	 * dateString转对应的秒时间戳
	 * @param dateString dateString
	 * @param dateFormat dateString的日期格式
	 * @return 秒时间戳
	 */
	public static Long dateStringToTimestamp(String dateString, String dateFormat) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, dateTimeFormatter);
		return localDateTime.atZone(ZoneId.of(TIME_ZONE)).toEpochSecond();
	}

	// LocalDate -> Date
	public static Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	// LocalDateTime -> Date
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Instant localDateTimeToInstant(LocalDateTime localDateTime) {
		return localDateTime.toInstant(ZoneOffset.of("+8"));
	}

	/**
	 * 当天开始时间
	 * @return
	 */
	public static LocalDateTime dayStartTime() {
		return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
	}

	/**
	 * 当天结束时间
	 * @return
	 */
	public static LocalDateTime dayEndTime() {
		return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
	}

	/**
	 * 本月第一天开始时间
	 * @return
	 */
	public static LocalDateTime monthStartTime() {
		return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())),
				LocalTime.MIN);
	}

	/**
	 * 本月最后一天结束时间
	 * @return
	 */
	public static LocalDateTime monthEndTime() {
		return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())),
				LocalTime.MAX);
	}

}
