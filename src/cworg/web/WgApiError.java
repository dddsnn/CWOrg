package cworg.web;

public class WgApiError extends Exception {
	private static final long serialVersionUID = 1L;

	private int code;
	private String field;
	private String message;
	private String value;

	public WgApiError(int code, String field, String message,
			String value) {
		this.code = code;
		this.field = field;
		this.message = message;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	public String getValue() {
		return value;
	}
}
