package cworg;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TreeMap;

enum TankClass {
	UNKNOWN, HEAVY, MEDIUM, LIGHT, TD, SPG
}

enum TankNation {
	UNKNOWN, USSR, USA, GERMANY, FRANCE
}

public class Tank implements Serializable {
	private static class TankInfo {
		private static TankInfo instance = new TankInfo();

		public static TankInfo getInstance() {
			return instance;
		}

		// maps for name, nation, tier and class
		private TreeMap<TankType, String> name_map = new TreeMap<TankType, String>();
		private TreeMap<TankType, String> short_name_map = new TreeMap<TankType, String>();
		private TreeMap<TankType, Integer> tier_map = new TreeMap<TankType, Integer>();
		private TreeMap<TankType, TankNation> nation_map = new TreeMap<TankType, TankNation>();
		private TreeMap<TankType, TankClass> class_map = new TreeMap<TankType, TankClass>();

		private TankInfo() {
			// fill name map
			name_map.put(TankType.UNKNOWN, "Unknown");
			name_map.put(TankType.IS_7, "IS-7");
			name_map.put(TankType.IS_4, "IS-4");
			name_map.put(TankType.IS_8, "IS-8");
			name_map.put(TankType.ST_I, "ST-I");
			name_map.put(TankType.T_62A, "T-62A");
			name_map.put(TankType.T_54, "T-54");
			name_map.put(TankType.T_44, "T-44");
			name_map.put(TankType.T_50_2, "T-50-2");
			name_map.put(TankType.OBJECT_268, "Object 268");
			name_map.put(TankType.OBJECT_704, "Object 704");
			name_map.put(TankType.ISU_152, "ISU-152");
			name_map.put(TankType.OBJECT_261, "Object 261");
			name_map.put(TankType.OBJECT_212, "Object 212");
			name_map.put(TankType.SU_14, "SU-14");
			name_map.put(TankType.S_51, "S-51");
			name_map.put(TankType.SU_8, "SU-8");
			name_map.put(TankType.T110E5, "T110E5");
			name_map.put(TankType.M103, "M103");
			name_map.put(TankType.M48, "Patton 3");
			name_map.put(TankType.M46, "Patton");
			name_map.put(TankType.PERSHING, "Pershing");
			name_map.put(TankType.CHAFFEE, "Chaffee");
			name_map.put(TankType.T110E3, "T110E3");
			name_map.put(TankType.T30, "T30");
			name_map.put(TankType.T28_P, "T28 Prototype");
			name_map.put(TankType.T110E4, "T110E4");
			name_map.put(TankType.T95, "T95");
			name_map.put(TankType.T28, "T28");
			name_map.put(TankType.T92, "T92");
			name_map.put(TankType.M40_M43, "M40/M43");
			name_map.put(TankType.M12, "M12");
			name_map.put(TankType.M41, "M41");
			name_map.put(TankType.MAUS, "Maus");
			name_map.put(TankType.E_100, "E-100");
			name_map.put(TankType.E_75, "E-75");
			name_map.put(TankType.VK4502B, "VK 4502 Ausf. B");
			name_map.put(TankType.E_50_M, "E-50 Ausf. M");
			name_map.put(TankType.E_50, "E-50");
			name_map.put(TankType.PANTHER_2, "Panther II");
			name_map.put(TankType.VK2801, "VK 2801");
			name_map.put(TankType.JPZ_E_100, "JagdPz E-100");
			name_map.put(TankType.JAGDTIGER, "Jagdtiger");
			name_map.put(TankType.FERDINAND, "Ferdinand");
			name_map.put(TankType.GW_TYP_E, "GW Typ E");
			name_map.put(TankType.GW_TIGER, "GW Tiger");
			name_map.put(TankType.GW_PANTHER, "GW Panther");
			name_map.put(TankType.HUMMEL, "Hummel");
			name_map.put(TankType.AMX_50B, "AMX 50B");
			name_map.put(TankType.AMX_50_120, "AMX 50 120");
			name_map.put(TankType.BATCHAT, "Bat Chatillon 25 t");
			name_map.put(TankType.LORRAINE, "Lorraine 40 t");
			name_map.put(TankType.AMX_13_90, "AMX 13 90");
			name_map.put(TankType.AMX_50_FOCH, "AMX 50 Foch");
			name_map.put(TankType.AMX_50_FOCH_155,
					"AMX 50 Foch (155)");

			// short name
			short_name_map = new TreeMap<TankType, String>(name_map);
			short_name_map.put(TankType.IS_7, "7");
			short_name_map.put(TankType.IS_4, "4");
			short_name_map.put(TankType.IS_8, "8");
			name_map.put(TankType.T_62A, "62");
			short_name_map.put(TankType.T_54, "54");
			short_name_map.put(TankType.T_44, "44");
			name_map.put(TankType.OBJECT_268, "268");
			short_name_map.put(TankType.OBJECT_704, "704");
			short_name_map.put(TankType.ISU_152, "ISU");
			short_name_map.put(TankType.OBJECT_261, "261");
			short_name_map.put(TankType.OBJECT_212, "212");
			short_name_map.put(TankType.T110E5, "E5");
			short_name_map.put(TankType.M103, "103");
			name_map.put(TankType.M48, "M48");
			short_name_map.put(TankType.M46, "M46");
			short_name_map.put(TankType.PERSHING, "M26");
			name_map.put(TankType.T110E3, "E3");
			name_map.put(TankType.T110E4, "E4");
			short_name_map.put(TankType.T28_P, "T28P");
			short_name_map.put(TankType.M40_M43, "M43");
			short_name_map.put(TankType.VK4502B, "Ausf.B");
			name_map.put(TankType.E_50_M, "E-50M");
			short_name_map.put(TankType.VK2801, "2801");
			name_map.put(TankType.JPZ_E_100, "JP E-100");
			short_name_map.put(TankType.JAGDTIGER, "JT");
			short_name_map.put(TankType.FERDINAND, "Ferdi");
			short_name_map.put(TankType.GW_TYP_E, "GW E");
			short_name_map.put(TankType.GW_TIGER, "GW T");
			short_name_map.put(TankType.GW_PANTHER, "GW P");
			short_name_map.put(TankType.AMX_50B, "50B");
			short_name_map.put(TankType.AMX_50_120, "50 120");
			short_name_map.put(TankType.BATCHAT, "Bat");
			short_name_map.put(TankType.LORRAINE, "Lorraine");
			short_name_map.put(TankType.AMX_13_90, "13 90");
			name_map.put(TankType.AMX_50_FOCH, "Foch");
			name_map.put(TankType.AMX_50_FOCH_155, "Foch 155");

			// tier
			tier_map.put(TankType.UNKNOWN, 0);
			tier_map.put(TankType.IS_7, 10);
			tier_map.put(TankType.IS_4, 10);
			tier_map.put(TankType.T110E5, 10);
			tier_map.put(TankType.MAUS, 10);
			tier_map.put(TankType.E_100, 10);
			tier_map.put(TankType.AMX_50B, 10);
			tier_map.put(TankType.T_62A, 10);
			tier_map.put(TankType.M48, 10);
			tier_map.put(TankType.E_50_M, 10);
			tier_map.put(TankType.BATCHAT, 10);
			tier_map.put(TankType.OBJECT_268, 10);
			tier_map.put(TankType.T110E3, 10);
			tier_map.put(TankType.T110E4, 10);
			tier_map.put(TankType.AMX_50_FOCH_155, 10);
			tier_map.put(TankType.IS_8, 9);
			tier_map.put(TankType.ST_I, 9);
			tier_map.put(TankType.T_54, 9);
			tier_map.put(TankType.OBJECT_704, 9);
			tier_map.put(TankType.M103, 9);
			tier_map.put(TankType.M46, 9);
			tier_map.put(TankType.T30, 9);
			tier_map.put(TankType.T95, 9);
			tier_map.put(TankType.E_75, 9);
			tier_map.put(TankType.VK4502B, 9);
			tier_map.put(TankType.E_50, 9);
			tier_map.put(TankType.JAGDTIGER, 9);
			tier_map.put(TankType.AMX_50_120, 9);
			tier_map.put(TankType.LORRAINE, 9);
			tier_map.put(TankType.AMX_50_FOCH, 9);
			tier_map.put(TankType.T_44, 8);
			tier_map.put(TankType.ISU_152, 8);
			tier_map.put(TankType.OBJECT_261, 8);
			tier_map.put(TankType.PERSHING, 8);
			tier_map.put(TankType.T28_P, 8);
			tier_map.put(TankType.T28, 8);
			tier_map.put(TankType.T92, 8);
			tier_map.put(TankType.PANTHER_2, 8);
			tier_map.put(TankType.FERDINAND, 8);
			tier_map.put(TankType.GW_TYP_E, 8);
			tier_map.put(TankType.AMX_13_90, 8);
			tier_map.put(TankType.OBJECT_212, 7);
			tier_map.put(TankType.M40_M43, 7);
			tier_map.put(TankType.GW_TIGER, 7);
			tier_map.put(TankType.SU_14, 6);
			tier_map.put(TankType.S_51, 6);
			tier_map.put(TankType.M12, 6);
			tier_map.put(TankType.GW_PANTHER, 6);
			tier_map.put(TankType.T_50_2, 5);
			tier_map.put(TankType.SU_8, 5);
			tier_map.put(TankType.CHAFFEE, 5);
			tier_map.put(TankType.M41, 5);
			tier_map.put(TankType.VK2801, 5);
			tier_map.put(TankType.HUMMEL, 5);

			// nation
			nation_map.put(TankType.UNKNOWN, TankNation.UNKNOWN);
			nation_map.put(TankType.IS_7, TankNation.USSR);
			nation_map.put(TankType.IS_4, TankNation.USSR);
			nation_map.put(TankType.IS_8, TankNation.USSR);
			nation_map.put(TankType.ST_I, TankNation.USSR);
			nation_map.put(TankType.T_62A, TankNation.USSR);
			nation_map.put(TankType.T_54, TankNation.USSR);
			nation_map.put(TankType.T_44, TankNation.USSR);
			nation_map.put(TankType.T_50_2, TankNation.USSR);
			nation_map.put(TankType.OBJECT_268, TankNation.USSR);
			nation_map.put(TankType.OBJECT_704, TankNation.USSR);
			nation_map.put(TankType.ISU_152, TankNation.USSR);
			nation_map.put(TankType.OBJECT_261, TankNation.USSR);
			nation_map.put(TankType.OBJECT_212, TankNation.USSR);
			nation_map.put(TankType.SU_14, TankNation.USSR);
			nation_map.put(TankType.S_51, TankNation.USSR);
			nation_map.put(TankType.SU_8, TankNation.USSR);
			nation_map.put(TankType.T110E5, TankNation.USA);
			nation_map.put(TankType.M103, TankNation.USA);
			nation_map.put(TankType.M48, TankNation.USA);
			nation_map.put(TankType.M46, TankNation.USA);
			nation_map.put(TankType.PERSHING, TankNation.USA);
			nation_map.put(TankType.CHAFFEE, TankNation.USA);
			nation_map.put(TankType.T110E3, TankNation.USA);
			nation_map.put(TankType.T110E4, TankNation.USA);
			nation_map.put(TankType.T30, TankNation.USA);
			nation_map.put(TankType.T28_P, TankNation.USA);
			nation_map.put(TankType.T95, TankNation.USA);
			nation_map.put(TankType.T28, TankNation.USA);
			nation_map.put(TankType.T92, TankNation.USA);
			nation_map.put(TankType.M40_M43, TankNation.USA);
			nation_map.put(TankType.M12, TankNation.USA);
			nation_map.put(TankType.M41, TankNation.USA);
			nation_map.put(TankType.MAUS, TankNation.GERMANY);
			nation_map.put(TankType.E_100, TankNation.GERMANY);
			nation_map.put(TankType.E_75, TankNation.GERMANY);
			nation_map.put(TankType.VK4502B, TankNation.GERMANY);
			nation_map.put(TankType.E_50_M, TankNation.GERMANY);
			nation_map.put(TankType.E_50, TankNation.GERMANY);
			nation_map.put(TankType.PANTHER_2, TankNation.GERMANY);
			nation_map.put(TankType.VK2801, TankNation.GERMANY);
			nation_map.put(TankType.JPZ_E_100, TankNation.GERMANY);
			nation_map.put(TankType.JAGDTIGER, TankNation.GERMANY);
			nation_map.put(TankType.FERDINAND, TankNation.GERMANY);
			nation_map.put(TankType.GW_TYP_E, TankNation.GERMANY);
			nation_map.put(TankType.GW_TIGER, TankNation.GERMANY);
			nation_map.put(TankType.GW_PANTHER, TankNation.GERMANY);
			nation_map.put(TankType.HUMMEL, TankNation.GERMANY);
			nation_map.put(TankType.AMX_50B, TankNation.FRANCE);
			nation_map.put(TankType.AMX_50_120, TankNation.FRANCE);
			nation_map.put(TankType.BATCHAT, TankNation.FRANCE);
			nation_map.put(TankType.LORRAINE, TankNation.FRANCE);
			nation_map.put(TankType.AMX_13_90, TankNation.FRANCE);
			nation_map.put(TankType.AMX_50_FOCH, TankNation.FRANCE);
			nation_map.put(TankType.AMX_50_FOCH_155,
					TankNation.FRANCE);

			// class
			class_map.put(TankType.UNKNOWN, TankClass.UNKNOWN);
			class_map.put(TankType.IS_7, TankClass.HEAVY);
			class_map.put(TankType.IS_4, TankClass.HEAVY);
			class_map.put(TankType.IS_8, TankClass.HEAVY);
			class_map.put(TankType.ST_I, TankClass.HEAVY);
			class_map.put(TankType.T110E5, TankClass.HEAVY);
			class_map.put(TankType.M103, TankClass.HEAVY);
			class_map.put(TankType.MAUS, TankClass.HEAVY);
			class_map.put(TankType.E_100, TankClass.HEAVY);
			class_map.put(TankType.E_75, TankClass.HEAVY);
			class_map.put(TankType.VK4502B, TankClass.HEAVY);
			class_map.put(TankType.AMX_50B, TankClass.HEAVY);
			class_map.put(TankType.AMX_50_120, TankClass.HEAVY);
			class_map.put(TankType.T_62A, TankClass.MEDIUM);
			class_map.put(TankType.T_54, TankClass.MEDIUM);
			class_map.put(TankType.T_44, TankClass.MEDIUM);
			class_map.put(TankType.M48, TankClass.MEDIUM);
			class_map.put(TankType.M46, TankClass.MEDIUM);
			class_map.put(TankType.PERSHING, TankClass.MEDIUM);
			class_map.put(TankType.E_50_M, TankClass.MEDIUM);
			class_map.put(TankType.E_50, TankClass.MEDIUM);
			class_map.put(TankType.PANTHER_2, TankClass.MEDIUM);
			class_map.put(TankType.BATCHAT, TankClass.MEDIUM);
			class_map.put(TankType.LORRAINE, TankClass.MEDIUM);
			class_map.put(TankType.T_50_2, TankClass.LIGHT);
			class_map.put(TankType.CHAFFEE, TankClass.LIGHT);
			class_map.put(TankType.VK2801, TankClass.LIGHT);
			class_map.put(TankType.AMX_13_90, TankClass.LIGHT);
			class_map.put(TankType.OBJECT_268, TankClass.TD);
			class_map.put(TankType.OBJECT_704, TankClass.TD);
			class_map.put(TankType.ISU_152, TankClass.TD);
			class_map.put(TankType.T110E3, TankClass.TD);
			class_map.put(TankType.T110E4, TankClass.TD);
			class_map.put(TankType.T30, TankClass.TD);
			class_map.put(TankType.T28_P, TankClass.TD);
			class_map.put(TankType.T95, TankClass.TD);
			class_map.put(TankType.T28, TankClass.TD);
			class_map.put(TankType.JPZ_E_100, TankClass.TD);
			class_map.put(TankType.JAGDTIGER, TankClass.TD);
			class_map.put(TankType.FERDINAND, TankClass.TD);
			class_map.put(TankType.AMX_50_FOCH, TankClass.TD);
			class_map.put(TankType.AMX_50_FOCH_155, TankClass.TD);
			class_map.put(TankType.OBJECT_261, TankClass.SPG);
			class_map.put(TankType.OBJECT_212, TankClass.SPG);
			class_map.put(TankType.SU_14, TankClass.SPG);
			class_map.put(TankType.S_51, TankClass.SPG);
			class_map.put(TankType.SU_8, TankClass.SPG);
			class_map.put(TankType.T92, TankClass.SPG);
			class_map.put(TankType.M40_M43, TankClass.SPG);
			class_map.put(TankType.M12, TankClass.SPG);
			class_map.put(TankType.M41, TankClass.SPG);
			class_map.put(TankType.GW_TYP_E, TankClass.SPG);
			class_map.put(TankType.GW_TIGER, TankClass.SPG);
			class_map.put(TankType.GW_PANTHER, TankClass.SPG);
			class_map.put(TankType.HUMMEL, TankClass.SPG);
		}

		public String getTankName(TankType t) {
			return name_map.get(t);
		}

		public String getTankShortName(TankType t) {
			return short_name_map.get(t);
		}

		public TankClass getTankClass(TankType t) {
			return class_map.get(t);
		}

		public int getTankTier(TankType t) {
			return tier_map.get(t);
		}

		public TankNation getTankNation(TankType t) {
			return nation_map.get(t);
		}
	}

	private TankType type;
	private TankStatus status;

	public Tank(TankType type) {
		this.type = type;
		status = new TankStatus(type);
	}

	public static String getTankName(TankType t) {
		return TankInfo.getInstance().getTankName(t);
	}

	public static String getTankShortName(TankType t) {
		return TankInfo.getInstance().getTankShortName(t);
	}

	public static TankClass getTankClass(TankType t) {
		return TankInfo.getInstance().getTankClass(t);
	}

	public static int getTankTier(TankType t) {
		return TankInfo.getInstance().getTankTier(t);
	}

	public static TankNation getTankNation(TankType t) {
		return TankInfo.getInstance().getTankNation(t);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Tank))
			return false;
		Tank t = (Tank) o;
		return this.type == t.getType();
	}

	public TankType getType() {
		return type;
	}

	public void setType(TankType type) {
		this.type = type;
	}

	public TankNation getNation() {
		return Tank.getTankNation(type);
	}

	public String getName() {
		return Tank.getTankName(type);
	}

	public int getTier() {
		return Tank.getTankTier(type);
	}

	public TankClass getTankClass() {
		return Tank.getTankClass(type);
	}

	public String getShortName() {
		return Tank.getTankShortName(type);
	}

	public TankStatus getStatus() {
		return status;
	}

	public void setStatus(TankStatus s) {
		this.status = s;
	}
}