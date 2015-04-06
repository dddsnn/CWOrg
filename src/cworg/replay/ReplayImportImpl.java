package cworg.replay;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import cworg.data.ReplayBattle.BattleOutcome;
import cworg.data.ReplayBattle.BattleType;
import cworg.replay.ParseReplayResponse.ParseReplayResponsePlayer;

@Stateless
public class ReplayImportImpl implements ReplayImport {
	public ParseReplayResponse parseReplay(InputStream is)
			throws ReplayException {
		Pair<JsonObject, JsonArray> blocks = this.readTwoBlocks(is);
		JsonObject firstBlock = blocks.a;
		JsonArray secondBlock = blocks.b;
		ParseReplayResponse response = null;
		try {
			long arenaId = secondBlock.getJsonObject(0).getInt("arenaUniqueID");
			JsonObject commonInfo =
					secondBlock.getJsonObject(0).getJsonObject("common");
			JsonObject postBattleInfo = secondBlock.getJsonObject(1);
			JsonObject vehiclesInfo =
					secondBlock.getJsonObject(0).getJsonObject("vehicles");
			long recordingPlayerId =
					firstBlock.getJsonNumber("playerID").longValue();
			int typeInt = commonInfo.getInt("bonusType");
			BattleType type = this.getBattleType(typeInt);
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
			BattleOutcome outcome = this.getBattleOutcome(outcomeInt);
			Map<Long, ParseReplayResponsePlayer> team1 = new HashMap<>();
			Map<Long, ParseReplayResponsePlayer> team2 = new HashMap<>();
			for (Map.Entry<String, JsonValue> e : vehiclesInfo.entrySet()) {
				JsonObject info = (JsonObject) e.getValue();
				// weird id used as keys in the replays (maybe tmp ids only
				// valid for that replay?)
				String tmpId = e.getKey();
				long typeCompDescr =
						info.getJsonNumber("typeCompDescr").longValue();
				long tankId = new Long(typeCompDescr & 65535);
				long playerId = info.getJsonNumber("accountDBID").longValue();
				boolean survived =
						postBattleInfo.getJsonObject(tmpId).getBoolean(
								"isAlive");
				int team = info.getInt("team");
				ParseReplayResponsePlayer player =
						new ParseReplayResponsePlayer(tankId, survived);
				if (team == 1) {
					team1.put(playerId, player);
				} else if (team == 2) {
					team2.put(playerId, player);
				} else {
					throw new ReplayException(
							"Error parsing replay: one of the players doesn't "
									+ "belong to either team");
				}
			}
			response =
					new ParseReplayResponse(arenaId, recordingPlayerId, type,
							lockingEnabled, arenaCreateTime, mapName, duration,
							winningTeam, outcome, team1, team2);
		} catch (ClassCastException | NullPointerException e) {
			throw new ReplayException("Unexpected replay format", e);
		}
		return response;
	}

	private BattleOutcome getBattleOutcome(int outcomeInt) {
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

	private BattleType getBattleType(int typeInt) {
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
		String blockString = null;
		try {
			blockString = readBlock(is);
		} catch (IOException e) {
			throw new ReplayException("Error reading replay", e);
		}
		JsonReader reader = Json.createReader(new StringReader(blockString));
		JsonObject firstBlock = null;
		try {
			firstBlock = reader.readObject();
		} catch (JsonException | IllegalStateException e) {
			throw new ReplayException("Error parsing Replay metadata", e);
		}
		try {
			blockString = readBlock(is);
		} catch (IOException e) {
			throw new ReplayException("Error reading replay", e);
		}
		// create a new reader to read a new object/array
		reader = Json.createReader(new StringReader(blockString));
		JsonArray secondBlock = null;
		try {
			secondBlock = reader.readArray();
		} catch (JsonException | IllegalStateException e) {
			throw new ReplayException("Error parsing Replay metadata", e);
		}
		return new Pair<JsonObject, JsonArray>(firstBlock, secondBlock);
	}

	private static String readBlock(InputStream is) throws IOException {
		int len = readLittleEndInt(is);
		byte[] buf = new byte[len];
		is.read(buf, 0, len);
		return new String(buf, StandardCharsets.US_ASCII);
	}

	/**
	 * Reads a 4 byte int from stream in little endian.
	 */
	private static int readLittleEndInt(InputStream stream) throws IOException {
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
