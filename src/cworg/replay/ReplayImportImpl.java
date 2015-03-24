package cworg.replay;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

import cworg.replay.ReplayBattle.BattleOutcome;
import cworg.replay.ReplayBattle.BattleType;

@Stateless
public class ReplayImportImpl {
	public ReplayBattle getBattleFromReplay(InputStream is)
			throws ReplayException {
		Pair<JsonObject, JsonArray> blocks = this.readTwoBlocks(is);
		JsonObject firstBlock = blocks.a;
		JsonArray secondBlock = blocks.b;
		try {
			JsonObject commonInfo =
					secondBlock.getJsonObject(0).getJsonObject("common");
			String playerId = firstBlock.get("playerID").toString();
			int typeInt = commonInfo.getInt("bonusType");
			BattleType type = this.determineBattleType(typeInt);
			boolean lockingEnabled = commonInfo.getInt("vehLockMode") != 0;
			Instant arenaCreateTime =
					Instant.ofEpochSecond(commonInfo.getJsonNumber(
							"arenaCreateTime").longValue());
			String mapName = firstBlock.getString("mapName");
			double durationDouble =
					commonInfo.getJsonNumber("duration").doubleValue();
			long durationNanos =
					new Double(durationDouble * 1000 * 1000).longValue();
			Duration duration = Duration.ofNanos(durationNanos);
			int winningTeam = commonInfo.getInt("winnerTeam");
			int outcomeInt = commonInfo.getInt("finishReason");
			BattleOutcome outcome = this.determineBattleOutcome(outcomeInt);
			// TODO players
		} catch (ClassCastException | NullPointerException e) {
			throw new ReplayException("Unexpected replay format", e);
		}
		// TODO
		return new ReplayBattle();
	}

	private BattleOutcome determineBattleOutcome(int outcomeInt) {
		BattleOutcome outcome = null;
		switch (outcomeInt) {
		case 1:
			outcome = BattleOutcome.EXTERMINATION;
			break;
		case 2:
			outcome = BattleOutcome.CAPTURE;
			break;
		case 3:
			outcome = BattleOutcome.TIMEOUT;
			break;
		case 4:
			outcome = BattleOutcome.FAILURE;
			break;
		case 5:
			outcome = BattleOutcome.TECHNICAL;
			break;
		default:
			outcome = BattleOutcome.UNKNOWN;
		}
		return outcome;
	}

	private BattleType determineBattleType(int typeInt) {
		BattleType type = null;
		switch (typeInt) {
		case 1:
			type = BattleType.REGULAR;
			break;
		case 2:
			type = BattleType.TRAINING;
			break;
		case 3:
			type = BattleType.COMPANY;
			break;
		case 4:
			type = BattleType.TOURNAMENT;
			break;
		case 5:
			type = BattleType.CW;
			break;
		case 6:
			type = BattleType.TUTORIAL;
			break;
		case 10:
			type = BattleType.STRONGHOLD_SKIRMISH;
			break;
		case 11:
			type = BattleType.STRONGHOLD_BATTLE;
			break;
		default:
			type = BattleType.UNKNOWN;
		}
		return type;
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
