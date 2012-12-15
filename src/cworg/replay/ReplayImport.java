package cworg.replay;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cworg.TankType;

public class ReplayImport {
	private static ReplayImport instance = new ReplayImport();

	public static ReplayImport getInstance() {
		return instance;
	}

	/**
	 * Doesn't check whether it's a CW replay.
	 * @param replayFile
	 * @return
	 * @throws ReplayException
	 */
	public ReplayBattle getBattleFromReplay(File replayFile)
			throws ReplayException {
		// JSONObject preBattle = null;
		JSONArray postBattle = null;
		try {
			JSONArray a = getJSONs(replayFile);
			// preBattle = a.getJSONObject(0);
			postBattle = a.getJSONArray(1);
		} catch (FileNotFoundException e) {
			throw new ReplayException("Replay File " + replayFile.getName()
					+ " not found.");
		} catch (IOException e) {
			throw new ReplayException("Failed to read replay file "
					+ replayFile.getName());
		} catch (JSONException e) {
			throw new ReplayException("Failed to parse replay file "
					+ replayFile.getName());
		}
		Vector<ReplayBattlePlayer> team1 = new Vector<ReplayBattlePlayer>(), team2 =
				new Vector<ReplayBattlePlayer>();
		Calendar arenaCreateTime = Calendar.getInstance();
		try {
			// time
			arenaCreateTime.setTimeInMillis(postBattle.getJSONObject(0)
					.getLong("arenaCreateTime") * 1000);
			// teams
			JSONObject players = postBattle.getJSONObject(1);
			@SuppressWarnings("unchecked")
			Iterator<String> i = players.keys();
			while (i.hasNext()) {
				JSONObject player = players.getJSONObject(i.next());
				String name = player.getString("name");
				String clanTag = player.getString("clanAbbrev");
				String replayTankName = player.getString("vehicleType");
				TankType tank = ReplayAdapter.getLocalType(replayTankName);
				boolean survived = player.getBoolean("isAlive");
				if (player.getInt("team") == 1)
					team1.add(new ReplayBattlePlayer(name, clanTag, tank,
							survived));
				else
					team2.add(new ReplayBattlePlayer(name, clanTag, tank,
							survived));
			}
		} catch (JSONException e) {
			throw new ReplayException(
					"Failed to parse the post-battle information "
							+ "in replay file " + replayFile.getName());
		}
		return new ReplayBattle(team1, team2, arenaCreateTime);
	}

	/**
	 * Attempts to read the first two blocks from the specified replay file and
	 * returns them as {@link JSONObject}/{@link JSONArray} wrapped into one
	 * {@link JSONArray}. The first element in the result is a JSONObject
	 * containing the pre-battle information, the second element is a JSONArray
	 * containing the post-battle information of the replay. If one of these
	 * cannot be succesfully parsed from the file, a {@link ReplayException} is
	 * thrown.
	 * @param f
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ReplayException
	 */
	private JSONArray getJSONs(File f) throws FileNotFoundException,
			IOException, ReplayException {
		BufferedInputStream bis =
				new BufferedInputStream(new FileInputStream(f));
		// constant at the beginning
		int magicConstant = readLittleEndInt(bis);
		if (magicConstant != 288633362)
			throw new ReplayException("Unknown replay format in file "
					+ f.getName() + ": wrong magic number.");
		// number of json blocks
		int numBlocks = readLittleEndInt(bis);
		if (numBlocks < 2)
			throw new ReplayException(
					"No post-battle block contained in this replay. "
							+ "This happens when you exit the battle before it ends "
							+ "or not all enemies are spotted in fog of war.");
		// first block, pre-battle
		JSONObject preJSON = null;
		JSONArray postJSON = null;
		try {
			String s = new String(readBlock(bis), "US-ASCII");
			preJSON = new JSONObject(s);
		} catch (ReplayException e) {
			throw new ReplayException(
					"Unreasonably large size for the pre-battle information in replay file "
							+ f.getName() + ", assuming unknown format.");
		} catch (JSONException e) {
			throw new ReplayException("Failed to parse replay file "
					+ f.getName() + " because of invalid json.");
		}
		// second block, post-battle
		try {
			String s = new String(readBlock(bis), "US-ASCII");
			postJSON = new JSONArray(s);
		} catch (ReplayException e) {
			throw new ReplayException(
					"Unreasonably large size for the post-battle information in replay file "
							+ f.getName() + ", assuming unknown format.");
		} catch (JSONException e) {
			throw new ReplayException(
					"Error parsing the post-battle information"
							+ "in replay file" + f.getName());
		}

		JSONArray a = new JSONArray();
		a.put(preJSON);
		a.put(postJSON);
		return a;
	}

	/**
	 * Reads a block from stream. First reads an int in little endian, then the
	 * number bytes of that int and returns those bytes.
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws ReplayException if the size of the block contained in the first 4
	 *         bytes is unreasonable large
	 */
	private byte[] readBlock(InputStream stream) throws IOException,
			ReplayException {
		int len = readLittleEndInt(stream);
		// set a reasonable limit to how much we're gonna read
		if (len > 500000)
			throw new ReplayException();
		byte[] bytes = new byte[len];
		stream.read(bytes);
		return bytes;
	}

	/**
	 * Reads a 4 byte int from stream in little endian.
	 */
	private int readLittleEndInt(InputStream stream) throws IOException {
		byte[] bytes = new byte[4];
		stream.read(bytes);
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		return buf.getInt();
	}
}
