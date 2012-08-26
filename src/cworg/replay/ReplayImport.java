package cworg.replay;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cworg.Clan;
import cworg.Player;
import cworg.Tank;

public class ReplayImport {
	private static ReplayImport instance = new ReplayImport();

	public static ReplayImport getInstance() {
		return instance;
	}

	public Battle getBattleFromReplay(File replayFile)
			throws UnknownReplayFormatException,
			IncompleteReplayException {
		JSONObject preBattle = null;
		JSONArray postBattle = null;
		try {
			JSONArray a = getJSONs(replayFile);
			preBattle = a.getJSONObject(0);
			postBattle = a.getJSONArray(1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.print(preBattle.toString(4));
			System.out.print(postBattle.toString(4));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Battle();
	}

	/**
	 * Attempts to read the first two blocks from the specified replay file
	 * and returns them as {@link JSONObject}/{@link JSONArray} wrapped into
	 * one {@link JSONArray}. The first element in the result is a
	 * JSONObject containing the pre-battle information, the second element
	 * is a JSONArray containing the post-battle information of the replay.
	 * If one of these cannot be succesfully parsed from the file, a
	 * {@link UnknownReplayFormatException} is thrown. More specifically, if
	 * the second block is not present, a {@link IncompleteReplayException}
	 * is thrown.
	 * @param f
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnknownReplayFormatException
	 * @throws IncompleteReplayException
	 */
	private JSONArray getJSONs(File f) throws FileNotFoundException,
			IOException, UnknownReplayFormatException,
			IncompleteReplayException {
		BufferedInputStream bis =
				new BufferedInputStream(new FileInputStream(f));
		// first 8 bytes unknown
		bis.skip(8);
		// first block, pre-battle
		JSONObject preJSON = null;
		JSONArray postJSON = null;
		try {
			String s = new String(readBlock(bis), "US-ASCII");
			preJSON = new JSONObject(s);
		} catch (UnknownReplayFormatException e) {
			throw new UnknownReplayFormatException(
					"Unreasonably large size for the pre-battle information in replay file "
							+ f.getName()
							+ ", assuming unknown format.");
		} catch (JSONException e) {
			throw new UnknownReplayFormatException(
					"Failed to parse replay file "
							+ f.getName()
							+ " because of unknown format.");
		}
		// second block, post-battle
		try {
			String s = new String(readBlock(bis), "US-ASCII");
			postJSON = new JSONArray(s);
		} catch (UnknownReplayFormatException e) {
			throw new IncompleteReplayException(
					"Unreasonably large size for the post-battle information in replay file "
							+ f.getName()
							+ ". Most likely, the post-battle information is not present."
							+ "This happens when you exit the battle before"
							+ "it ends or not all enemies are spotted in fog"
							+ "of war.");
		} catch (JSONException e) {
			throw new IncompleteReplayException(
					"Error parsing the post-battle information"
							+ "in replay file"
							+ f.getName()
							+ ". Most likely, the post-battle information is not present."
							+ "This happens when you exit the battle before"
							+ "it ends or not all enemies are spotted in fog"
							+ "of war.");
		}

		JSONArray a = new JSONArray();
		a.put(preJSON);
		a.put(postJSON);
		return a;
	}

	/**
	 * Reads a block from stream. First reads an int in little endian, then
	 * the number bytes of that int and returns those bytes.
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws UnknownReplayFormatException
	 */
	private byte[] readBlock(InputStream stream) throws IOException,
			UnknownReplayFormatException {
		int len = readLittleEndInt(stream);
		// set a reasonable limit to how much we're gonna read
		if (len > 100000)
			throw new UnknownReplayFormatException();
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
