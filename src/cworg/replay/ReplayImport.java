package cworg.replay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;

@Local
public interface ReplayImport {
	ReplayBattle getBattleFromReplay(File replayFile) throws ReplayException;

	JSONArray getJSONs(File f) throws FileNotFoundException, IOException,
			ReplayException;
}
