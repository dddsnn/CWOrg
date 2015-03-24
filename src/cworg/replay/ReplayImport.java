package cworg.replay;

import java.io.InputStream;

import javax.ejb.Local;

@Local
public interface ReplayImport {
	ParseReplayResponse parseReplay(InputStream is)
			throws ReplayException;
}
