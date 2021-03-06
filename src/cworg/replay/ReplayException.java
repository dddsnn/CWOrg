package cworg.replay;

public class ReplayException extends Exception {
	private static final long serialVersionUID = 1L;

	public ReplayException() {
	}

	public ReplayException(String message) {
		super(message);
	}

	public ReplayException(Throwable cause) {
		super(cause);
	}

	public ReplayException(String message, Throwable cause) {
		super(message, cause);
	}
}
