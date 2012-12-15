package cworg;

import java.io.File;
import java.util.Vector;

import cworg.replay.ReplayBattle;

public interface UICallback {
	void setSelectedClan(Clan c);

	Clan getSelectedClan();

	void createProject(String name);

	void saveProject(File f);

	void loadProject(File selectedFile);

	void addClan(Clan c) throws IllegalOperationException;

	public void addPlayer(String name) throws IllegalOperationException,
			IllegalArgumentException;

	void setDisplayedTanks(Vector<TankType> arties);

	boolean hasProject();

	void addReplayBattle(ReplayBattle replayBattle);
}
