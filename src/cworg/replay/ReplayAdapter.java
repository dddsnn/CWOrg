package cworg.replay;

import java.util.TreeMap;

import cworg.TankType;

public class ReplayAdapter {
	private static class NameInfo {
		private static NameInfo instance = new NameInfo();

		public static NameInfo getInstance() {
			return instance;
		}

		private TreeMap<String, TankType> map = new TreeMap<String, TankType>();

		private NameInfo() {
			map.put("ussr:IS-7", TankType.IS_7);
			map.put("ussr:IS-4", TankType.IS_4);
			map.put("ussr:IS8", TankType.IS_8);
			map.put("ussr:ST_I", TankType.ST_I);
			map.put("ussr:T62A", TankType.T_62A);
			map.put("ussr:T-54", TankType.T_54);
			map.put("ussr:T-44", TankType.T_44);
			map.put("ussr:T_50_2", TankType.T_50_2);
			map.put("ussr:Object268", TankType.OBJECT_268);
			map.put("ussr:Object_704", TankType.OBJECT_704);
			map.put("ussr:ISU-152", TankType.ISU_152);
			map.put("ussr:Object_261", TankType.OBJECT_261);
			map.put("ussr:Object_212", TankType.OBJECT_212);
			map.put("ussr:SU-14", TankType.SU_14);
			map.put("ussr:S-51", TankType.S_51);
			map.put("ussr:SU-8", TankType.SU_8);
			map.put("usa:T110", TankType.T110E5);
			map.put("usa:M103", TankType.M103);
			map.put("usa:M48A1", TankType.M48);
			map.put("usa:M46_Patton", TankType.M46);
			map.put("usa:Pershing", TankType.PERSHING);
			map.put("usa:M24_Chaffee", TankType.CHAFFEE);
			map.put("usa:T110E3", TankType.T110E3);
			map.put("usa:T110E4", TankType.T110E4);
			map.put("usa:T30", TankType.T30);
			map.put("usa:T28_Prototype", TankType.T28_P);
			map.put("usa:T95", TankType.T95);
			map.put("usa:T28", TankType.T28);
			map.put("usa:T92", TankType.T92);
			map.put("usa:M40M43", TankType.M40_M43);
			map.put("usa:M12", TankType.M12);
			map.put("usa:M41", TankType.M41);
			map.put("germany:Maus", TankType.MAUS);
			map.put("germany:E-100", TankType.E_100);
			map.put("germany:E-75", TankType.E_75);
			map.put("germany:VK4502P", TankType.VK4502B);
			map.put("germany:E50_Ausf_M", TankType.E_50_M);
			map.put("germany:E-50", TankType.E_50);
			map.put("germany:Panther_II", TankType.PANTHER_2);
			map.put("germany:VK2801", TankType.VK2801);
			map.put("germany:JagdPz_E100", TankType.JPZ_E_100);
			map.put("germany:JagdTiger", TankType.JAGDTIGER);
			map.put("germany:Ferdinand", TankType.FERDINAND);
			map.put("germany:G_E", TankType.GW_TYP_E);
			map.put("germany:G_Tiger", TankType.GW_TIGER);
			map.put("germany:G_Panther", TankType.GW_PANTHER);
			map.put("germany:Hummel", TankType.HUMMEL);
			map.put("france:F10_AMX_50B", TankType.AMX_50B);
			map.put("france:AMX_50_120", TankType.AMX_50_120);
			map.put("france:Bat_Chatillon25t", TankType.BATCHAT);
			map.put("france:Lorraine40t", TankType.LORRAINE);
			map.put("france:AMX_13_90", TankType.AMX_13_90);
			map.put("france:AMX50_Foch", TankType.AMX_50_FOCH);
			map.put("france:AMX_50Fosh_155", TankType.AMX_50_FOCH_155);
		}

		public TankType getLocalType(String replayTankName) {
			return map.get(replayTankName);
		}
	}

	public static TankType getLocalType(String replayTankName) {
		TankType type = NameInfo.getInstance().getLocalType(replayTankName);
		if (type == null) {
			System.err.println("Unknown Tank: " + replayTankName);
			return TankType.UNKNOWN;
		}
		return type;
	}
}
