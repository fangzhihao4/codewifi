package codewifi.utils;

public final class ByteUtil {

	private ByteUtil() {
	}

	public static final byte TRUE = 1;

	public static final byte FALSE = 0;

	public static boolean toBool(final byte value) {
		return value != FALSE;
	}

	public static byte toByte(final boolean value) {
		return value ? TRUE : FALSE;
	}

}
