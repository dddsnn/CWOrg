package cworg.replay;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

@Stateless
public class ReplayImportImpl {
	public ReplayBattle getBattleFromReplay(InputStream is)
			throws ReplayException {
		Pair<JsonObject, JsonArray> blocks = this.readTwoBlocks(is);
		JsonObject firstBlock = blocks.a;
		JsonArray secondBlock = blocks.b;
		// TODO
		return new ReplayBattle();
	}

	private Pair<JsonObject, JsonArray> readTwoBlocks(InputStream is)
			throws ReplayException {
		int magicConstant = 0;
		try {
			magicConstant = readLittleEndInt(is);
		} catch (IOException e) {
			throw new ReplayException("Error reading replay", e);
		}
		if (magicConstant != 288633362)
			throw new ReplayException(
					"Unknown replay format: wrong magic number.");
		// number of json blocks
		int numBlocks = 0;
		try {
			numBlocks = readLittleEndInt(is);
		} catch (IOException e) {
			throw new ReplayException("Error reading replay", e);
		}
		if (numBlocks < 2)
			throw new ReplayException(
					"No post-battle block contained in this replay.");
		try {
			// read length of first block
			readLittleEndInt(is);
		} catch (IOException e) {
			throw new ReplayException("Error reading replay", e);
		}
		JsonReader reader = Json.createReader(is);
		JsonObject firstBlock = null;
		try {
			firstBlock = reader.readObject();
		} catch (JsonException | IllegalStateException e) {
			throw new ReplayException("Error parsing Replay metadata", e);
		}
		try {
			// read length of second block
			readLittleEndInt(is);
		} catch (IOException e) {
			throw new ReplayException("Error reading replay", e);
		}
		JsonArray secondBlock = null;
		try {
			secondBlock = reader.readArray();
		} catch (JsonException | IllegalStateException e) {
			throw new ReplayException("Error parsing Replay metadata", e);
		}
		return new Pair<JsonObject, JsonArray>(firstBlock, secondBlock);
	}

	/**
	 * Reads a 4 byte int from stream in little endian.
	 */
	private int readLittleEndInt(InputStream stream) throws IOException {
		byte[] bytes = new byte[4];
		int bytesRead = stream.read(bytes);
		if (bytesRead != 4) {
			throw new IOException(
					"Not enough bytes in the stream to read the block length");
		}
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		return buf.getInt();
	}

	private class Pair<A, B> {
		public final A a;
		public final B b;

		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}
}
