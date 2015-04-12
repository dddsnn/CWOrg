package cworg.replay;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class ReplayMaker {
	public static void main(String[] args) {
		JsonObject firstBlock = makeFirstBlock(501504024);

		JsonArray secondBlock =
				makeSecondBlock(Instant.now(), makeTeam1(), makeTeam2());
	}

	private static Map<Long, Long> makeTeam2() {
		Map<Long, Long> res = new HashMap<>(15, 1.0f);
		// TODO Auto-generated method stub
		return null;
	}

	private static Map<Long, Long> makeTeam1() {
		Map<Long, Long> res = new HashMap<>(15, 1.0f);
		return null;
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
					.add("typeCompDescr", e.getValue()).add("team", 0);
			vehiclesInfoBuilder
					.add(Integer.toString(i), vehicleBuilder.build());
			i++;
		}
		for (Map.Entry<Long, Long> e : team2.entrySet()) {
			JsonObjectBuilder vehicleBuilder = Json.createObjectBuilder();
			vehicleBuilder.add("accountDBID", e.getKey())
					.add("typeCompDescr", e.getValue()).add("team", 1);
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
