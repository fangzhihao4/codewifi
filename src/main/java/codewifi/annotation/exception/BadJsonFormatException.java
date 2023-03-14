package codewifi.annotation.exception;

public class BadJsonFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadJsonFormatException() {
		super();
	}

	public BadJsonFormatException(String message) {
		super(message);
	}

	public BadJsonFormatException(Throwable throwable) {
		super(throwable);
	}

	public BadJsonFormatException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
