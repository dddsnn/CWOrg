package cworg.replay;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonWriter;

import org.apache.catalina.fileupload.ByteArrayOutputStream;

public class ReplayMaker {
	public static void main(String[] args) {
		JsonObject firstBlock = makeFirstBlock(501504024);
		JsonArray secondBlock =
				makeSecondBlock(Instant.now(), makeTeam1(), makeTeam2());
		byte[] firstBlockBytes = null;
		byte[] secondBlockBytes = null;
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			JsonWriter w = Json.createWriter(os);
			w.write(firstBlock);
			firstBlockBytes = os.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			JsonWriter w = Json.createWriter(os);
			w.write(secondBlock);
			secondBlockBytes = os.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (OutputStream os =
				new FileOutputStream("/home/dddsnn/test.wotreplay")) {
			// magic number
			writeLittleEndInt(os, 288633362);
			// num of blocks
			writeLittleEndInt(os, 2);
			// first block
			writeLittleEndInt(os, firstBlockBytes.length);
			os.write(firstBlockBytes);
			// second block
			writeLittleEndInt(os, secondBlockBytes.length);
			os.write(secondBlockBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Map<Long, Long> makeTeam1() {
		Map<Long, Long> res = new HashMap<>(15, 1.0f);
		// zer0
		res.put(501504024L, 9489L);
		res.put(500359296L, 9489L);
		res.put(503577582L, 9489L);
		res.put(500538976L, 9489L);
		res.put(511006172L, 7169L);
		res.put(500111530L, 7169L);
		res.put(505261143L, 7169L);
		res.put(500812951L, 7169L);
		res.put(507936657L, 7249L);
		res.put(500226168L, 7169L);
		res.put(500739231L, 7169L);
		res.put(502424058L, 7169L);
		res.put(507572264L, 7169L);
		res.put(507875739L, 7169L);
		res.put(500506363L, 9489L);
		return res;
	}

	private static Map<Long, Long> makeTeam2() {
		Map<Long, Long> res = new HashMap<>(15, 1.0f);
		// fame
		res.put(500057617L, 7169L);
		res.put(503045600L, 7169L);
		res.put(500549947L, 7169L);
		res.put(502467332L, 7169L);
		res.put(502182060L, 7169L);
		res.put(513245847L, 7169L);
		res.put(500910204L, 7169L);
		res.put(505167907L, 10785L);
		res.put(500374070L, 10785L);
		res.put(501077600L, 10785L);
		res.put(500274824L, 10785L);
		res.put(500125424L, 10785L);
		res.put(500916751L, 10785L);
		res.put(503425775L, 10785L);
		res.put(500475485L, 10785L);
		return res;
	}

	private static void writeLittleEndInt(OutputStream stream, int i)
			throws IOException {
		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.putInt(i);
		stream.write(buf.array());
	}

	private static JsonObject makeFirstBlock(long recordingPlayerId) {
		JsonObjectBuilder firstBlockBuilder = Json.createObjectBuilder();
		firstBlockBuilder.add("playerID", recordingPlayerId).add("mapName",
				"14_siegfried_line");
		JsonObject firstBlock = firstBlockBuilder.build();
		return firstBlock;
	}

	private static JsonArray makeSecondBlock(Instant battleStartTime,
			Map<Long, Long> team1, Map<Long, Long> team2) {
		// second block
		JsonObjectBuilder firstObjectBuilder = Json.createObjectBuilder();
		JsonObjectBuilder commonInfoBuilder = Json.createObjectBuilder();
		commonInfoBuilder.add("bonusType", 5).add("vehLockMode", 1)
				.add("arenaCreateTime", battleStartTime.getEpochSecond())
				.add("duration", 400).add("winnerTeam", 0)
				.add("finishReason", 1);
		JsonObject commonInfo = commonInfoBuilder.build();

		JsonObjectBuilder vehiclesInfoBuilder = Json.createObjectBuilder();
		int i = 0;
		for (Map.Entry<Long, Long> e : team1.entrySet()) {
			JsonObjectBuilder vehicleBuilder = Json.createObjectBuilder();
			vehicleBuilder.add("accountDBID", e.getKey())
					.add("typeCompDescr", e.getValue()).add("team", 1);
			vehiclesInfoBuilder
					.add(Integer.toString(i), vehicleBuilder.build());
			i++;
		}
		for (Map.Entry<Long, Long> e : team2.entrySet()) {
			JsonObjectBuilder vehicleBuilder = Json.createObjectBuilder();
			vehicleBuilder.add("accountDBID", e.getKey())
					.add("typeCompDescr", e.getValue()).add("team", 2);
			vehiclesInfoBuilder
					.add(Integer.toString(i), vehicleBuilder.build());
			i++;
		}
		JsonObject vehiclesInfo = vehiclesInfoBuilder.build();
		firstObjectBuilder.add("arenaUniqueID", 0).add("common", commonInfo)
				.add("vehicles", vehiclesInfo);
		JsonObject firstObject = firstObjectBuilder.build();
		JsonObjectBuilder secondObjectBuilder = Json.createObjectBuilder();
		for (Map.Entry<String, JsonValue> e : vehiclesInfo.entrySet()) {
			JsonObjectBuilder isAliveBuilder = Json.createObjectBuilder();
			isAliveBuilder.add("isAlive", false);
			secondObjectBuilder.add(e.getKey(), isAliveBuilder.build());
		}
		JsonObject secondObject = secondObjectBuilder.build();
		JsonArrayBuilder secondBlockBuilder = Json.createArrayBuilder();
		secondBlockBuilder.add(firstObject).add(secondObject);
		JsonArray secondBlock = secondBlockBuilder.build();
		return secondBlock;
	}
}
