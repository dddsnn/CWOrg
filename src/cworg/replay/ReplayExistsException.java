package cworg.replay;

import cworg.data.ReplayBattle;

public class ReplayExistsException extends ReplayException {
	private static final long serialVersionUID = 1L;
	private ReplayBattle replay;

	public ReplayExistsException(ReplayBattle replay) {
		this.replay = replay;
	}

	public ReplayExistsException(String message, ReplayBattle replay) {
		super(message);
		this.replay = replay;
	}

	public ReplayExistsException(Throwable cause, ReplayBattle replay) {
		super(cause);
		this.replay = replay;
	}

	public ReplayExistsException(String message, Throwable cause,
			ReplayBattle replay) {
		super(message, cause);
		this.replay = replay;
	}

	public ReplayBattle getReplay() {
		return replay;
	}
}
