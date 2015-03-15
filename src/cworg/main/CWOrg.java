//package cworg.main;
//
//import java.awt.Color;
//
//import cworg.data.Clan;
//import cworg.data.Player;
//import cworg.data.Project;
//
//public class CWOrg /*implements UICallback */{
//	public static final Color TANK_NOT_RESEARCHED = Color.BLACK;
//	public static final Color TANK_NOT_IN_GARAGE = Color.LIGHT_GRAY;
//	public static final Color TANK_AVAILABLE = Color.GREEN;
//	public static final Color TANK_FROZEN_LONG = Color.RED;
//	public static final Color TANK_FROZEN_SHORT = Color.YELLOW;
//	public static final Color PLAYER_AVAILABLE = Color.GREEN;
//	public static final Color PLAYER_UNAVAILABLE = Color.RED;
//
////	private MainWindow mw;
//	private Project project = null;
//	private Clan selectedClan = null;
//
//	CWOrg() {
////		mw = new MainWindow(this);
//	}
//
//	public static void main(String[] args) {
//		new CWOrg();
//	}
//
//	public void createProject(String name) {
//		project = new Project(name);
////		mw.displayProject(project);
//	}
//
////	@Override
////	public void saveProject(File f) {
////		FileOutputStream fos;
////		ObjectOutputStream oos;
////		try {
////			fos = new FileOutputStream(f);
////			oos = new ObjectOutputStream(fos);
////		} catch (FileNotFoundException e) {
////			System.err.println("File not found");
////			return;
////		} catch (IOException e) {
////			e.printStackTrace();
////			return;
////		}
////		try {
////			oos.writeObject(project);
////		} catch (NotSerializableException e) {
////			System.err.println("Not serializable");
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////			System.exit(1);
////		}
////	}
//
////	@Override
////	public void loadProject(File f) {
////		FileInputStream fis;
////		ObjectInputStream ois;
////		try {
////			fis = new FileInputStream(f);
////			ois = new ObjectInputStream(fis);
////		} catch (FileNotFoundException e) {
////			System.err.println("File not found");
////			return;
////		} catch (SecurityException e) {
////			e.printStackTrace();
////			return;
////		} catch (IOException e) {
////			e.printStackTrace();
////			return;
////		}
////		Project p;
////		try {
////			p = (Project) ois.readObject();
////			project = p;
////			p.refresh();
//////			mw.displayProject(project);
////		} catch (ClassNotFoundException e) {
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////	}
//
////	@Override
////	public void addPlayer(String name) throws IllegalOperationException,
////			IllegalArgumentException {
////		if (project == null)
////			throw new IllegalOperationException(
////					"Cannot add players without a project.");
////		if (selectedClan == null)
////			throw new IllegalOperationException("No clan selected.");
////		// names must be unique
////		for (Clan c : project.getClans()) {
////			for (Player p : c.getPlayers()) {
////				if (name.equals(p.getName()))
////					throw new IllegalArgumentException(
////							"Player names must be unique");
////			}
////		}
////		selectedClan.getPlayers().add(new Player(name));
//////		mw.displayProject(project);
////	}
//
//	public void removePlayer(Player player) {
//		for (Clan c : project.getClans()) {
//			if (c.getPlayers().remove(player))
//				break;
//		}
////		mw.displayProject(project);
//	}
//
////	@Override
////	public void addClan(Clan clan) throws IllegalOperationException {
////		if (project == null) {
////			throw new IllegalOperationException(
////					"Cannot add a clan without a project.");
////		}
////		project.addClan(clan);
////		if (selectedClan == null)
////			selectedClan = clan;
//////		mw.displayProject(project);
////	}
//
//	public void removeClan(Clan clan) {
//		// TODO
//		// reset selectedClan
//	}
//
//	// public void changePlayerName(Player player, String name)
//	// throws IllegalArgumentException {
//	// for (Clan c : project.getClans()) {
//	// for (Player p : c.getPlayers()) {
//	// // names must be unique
//	// if (p != player && p.getName().equals(name)) {
//	// throw new IllegalArgumentException(
//	// "Player names must be unique");
//	// }
//	// }
//	// }
//	// player.setName(name);
//	// }
//
//	// public void setInGarage(Tank tank, boolean selected) {
//	// tank.getStatus().setInGarage(selected);
//	// }
//	//
//	// public void setUnfrozen(Tank tank) {
//	// tank.getStatus().setFrozen(false);
//	// }
//	//
//	// public void setFrozen(Tank tank, Calendar start) {
//	// tank.getStatus().setFrozenFrom(start);
//	// }
////	@Override
////	public void setDisplayedTanks(Vector<TankType> displayedTanks) {
////		if (project == null)
////			return;
////		project.setDisplayedTanks(displayedTanks);
//////		mw.displayProject(project);
////	}
//
////	@Override
////	public void setSelectedClan(Clan c) {
////		if (c == null || !project.getClans().contains(c) || c == selectedClan)
////			return;
////		selectedClan = c;
//////		mw.displayProject(project);
////	}
//
////	@Override
////	public Clan getSelectedClan() {
////		return selectedClan;
////	}
//
////	@Override
////	public boolean hasProject() {
////		return project != null;
////	}
//
////	@Override
////	public void addReplayBattle(ReplayBattle replayBattle)
////			throws IllegalArgumentException {
////		if (!replayBattle.isSameClanBattle())
////			throw new IllegalArgumentException("Not a clan wars battle.");
////		// load clan info if necessary
////		String tag1 = replayBattle.getTeam1().firstElement().getClanTag();
////		if (project.containsClanByTag(tag1)) {
////			try {
////				addClan(WebAccess.getInstance().getClanByTag(tag1));
////			} catch (IllegalOperationException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			} catch (UnknownClanException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			} catch (UnknownWebFormatException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		}
////		String tag2 = replayBattle.getTeam2().firstElement().getClanTag();
////		if (project.containsClanByTag(tag2)) {
////			try {
////				addClan(WebAccess.getInstance().getClanByTag(tag2));
////			} catch (IllegalOperationException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			} catch (UnknownClanException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			} catch (UnknownWebFormatException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		}
////		// TODO update existing information here?
//////		Clan clan1 = project.getClans().getClanByTag(tag1);
////		for (ReplayBattlePlayer rp : replayBattle.getTeam1()) {
//////			Player p = clan1.getPlayerByName(rp.getName());
//////			if(p == null)
//////				continue;
////			
////		}
////	}
//}
