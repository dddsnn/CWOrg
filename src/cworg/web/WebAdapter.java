package cworg.web;

import java.util.TreeMap;

import cworg.data.TankType;

public class WebAdapter {
	private static class NameInfo {
		private static NameInfo instance = new NameInfo();

		public static NameInfo getInstance() {
			return instance;
		}

		private TreeMap<String, TankType> map = new TreeMap<String, TankType>();

		private NameInfo() {
			map.put("IS-7", TankType.IS_7);
			map.put("IS-4", TankType.IS_4);
			map.put("IS8", TankType.IS_8);
			map.put("ST_I", TankType.ST_I);
			map.put("T62A", TankType.T_62A);
			map.put("T-54", TankType.T_54);
			map.put("T-44", TankType.T_44);
			map.put("T_50_2", TankType.T_50_2);
			map.put("Object268", TankType.OBJECT_268);
			map.put("Object_704", TankType.OBJECT_704);
			map.put("ISU-152", TankType.ISU_152);
			map.put("Object_261", TankType.OBJECT_261);
			map.put("Object_212", TankType.OBJECT_212);
			map.put("SU-14", TankType.SU_14);
			map.put("S-51", TankType.S_51);
			map.put("SU-8", TankType.SU_8);
			map.put("T110", TankType.T110E5);
			map.put("M103", TankType.M103);
			map.put("M48A1", TankType.M48);
			map.put("M46_Patton", TankType.M46);
			map.put("Pershing", TankType.PERSHING);
			map.put("M24_Chaffee", TankType.CHAFFEE);
			map.put("T110E3", TankType.T110E3);
			map.put("T110E4", TankType.T110E4);
			map.put("T30", TankType.T30);
			map.put("T28_Prototype", TankType.T28_P);
			map.put("T95", TankType.T95);
			map.put("T28", TankType.T28);
			map.put("T92", TankType.T92);
			map.put("M40M43", TankType.M40_M43);
			map.put("M12", TankType.M12);
			map.put("M41", TankType.M41);
			map.put("Maus", TankType.MAUS);
			map.put("E-100", TankType.E_100);
			map.put("E-75", TankType.E_75);
			map.put("VK4502P", TankType.VK4502B);
			map.put("E50_Ausf_M", TankType.E_50_M);
			map.put("E-50", TankType.E_50);
			map.put("Panther_II", TankType.PANTHER_2);
			map.put("VK2801", TankType.VK2801);
			map.put("JagdPz_E100", TankType.JPZ_E_100);
			map.put("JagdTiger", TankType.JAGDTIGER);
			map.put("Ferdinand", TankType.FERDINAND);
			map.put("G_E", TankType.GW_TYP_E);
			map.put("G_Tiger", TankType.GW_TIGER);
			map.put("G_Panther", TankType.GW_PANTHER);
			map.put("Hummel", TankType.HUMMEL);
			map.put("F10_AMX_50B", TankType.AMX_50B);
			map.put("AMX_50_120", TankType.AMX_50_120);
			map.put("Bat_Chatillon25t", TankType.BATCHAT);
			map.put("Lorraine40t", TankType.LORRAINE);
			map.put("AMX_13_90", TankType.AMX_13_90);
			map.put("AMX50_Foch", TankType.AMX_50_FOCH);
			map.put("AMX_50Fosh_155", TankType.AMX_50_FOCH_155);
		}

		public TankType getLocalType(String remoteTankName) {
			return map.get(remoteTankName);
		}
	}

	public static TankType getLocalType(String remoteTankName) {
		TankType type = NameInfo.getInstance().getLocalType(remoteTankName);
		if (type == null) {
			System.err.println("Unknown Tank: " + remoteTankName);
			return TankType.UNKNOWN;
		}
		return type;
	}
}
