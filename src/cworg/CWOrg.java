package cworg;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Vector;

import cworg.ui.MainWindow;
import cworg.web.UnknownClanException;
import cworg.web.UnknownFormatException;
import cworg.web.WebAccess;

public class CWOrg implements UICallback {
	public static final Color TANK_NOT_RESEARCHED = Color.BLACK;
	public static final Color TANK_NOT_IN_GARAGE = Color.LIGHT_GRAY;
	public static final Color TANK_AVAILABLE = Color.GREEN;
	public static final Color TANK_FROZEN_LONG = Color.RED;
	public static final Color TANK_FROZEN_SHORT = Color.YELLOW;
	public static final Color PLAYER_AVAILABLE = Color.GREEN;
	public static final Color PLAYER_UNAVAILABLE = Color.RED;

	private MainWindow mw;
	private Project project = null;

	CWOrg() {
		mw = new MainWindow(this);
	}

	public static void main(String[] args) {
		new CWOrg();
	}

	public void createProject(String name) {
		project = new Project(name);
		mw.displayProject(project);
	}

	// void setFrozen(String name, TankType tank, Calendar cal) {
	//
	// }

	public void saveProject(File f) {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(f);
			oos = new ObjectOutputStream(fos);
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			oos.writeObject(project);
		} catch (NotSerializableException e) {
			System.err.println("Not serializable");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// void setFrozen(String name, TankType tank, Calendar cal) {
	//
	// }

	public void loadProject(File f) {
		FileInputStream fis;
		ObjectInputStream ois;
		try {
			fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			return;
		} catch (SecurityException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Project p;
		try {
			p = (Project) ois.readObject();
			project = p;
			p.refresh();
			mw.displayProject(project);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addPlayer(String name) throws IllegalOperationException,
			IllegalArgumentException {
		if (project == null)
			throw new IllegalOperationException(
					"Cannot add players without a project.");
		// names must be unique
		for (Clan c : project.getClans()) {
			for (Player p : c.getPlayers()) {
				if (name.equals(p.getName()))
					throw new IllegalArgumentException(
							"Player names must be unique");
			}
		}
		project.getSelectedClan().getPlayers().add(new Player(name));
		mw.displayProject(project);
	}

	// void setFrozen(String name, TankType tank, Calendar cal) {
	//
	// }

	public void removePlayer(Player player) {
		for (Clan c : project.getClans()) {
			if (c.getPlayers().remove(player))
				break;
		}
		mw.displayProject(project);
	}

	public void addClan(Clan clan) throws IllegalOperationException {
		if (project == null) {
			throw new IllegalOperationException(
					"Cannot add a clan without a project.");
		}
		project.addClan(clan);
		mw.displayProject(project);
	}

	public void removeClan(Clan clan) {
		// TODO
	}

	public Project getProject() {
		return this.project;
	}

	// public void changePlayerName(Player player, String name)
	// throws IllegalArgumentException {
	// for (Clan c : project.getClans()) {
	// for (Player p : c.getPlayers()) {
	// // names must be unique
	// if (p != player && p.getName().equals(name)) {
	// throw new IllegalArgumentException(
	// "Player names must be unique");
	// }
	// }
	// }
	// player.setName(name);
	// }

	// public void setInGarage(Tank tank, boolean selected) {
	// tank.getStatus().setInGarage(selected);
	// }
	//
	// public void setUnfrozen(Tank tank) {
	// tank.getStatus().setFrozen(false);
	// }
	//
	// public void setFrozen(Tank tank, Calendar start) {
	// tank.getStatus().setFrozenFrom(start);
	// }

	public void setDisplayedTanks(Vector<TankType> displayedTanks) {
		if (getProject() == null)
			return;
		getProject().setDisplayedTanks(displayedTanks);
		mw.displayProject(getProject());
	}

	@Override
	public void setSelectedClan(Clan c) {
		if (project == null || !project.getClans().contains(c)
				|| c == project.getSelectedClan())
			return;
		project.setSelectedClan(c);
		mw.displayProject(project);
	}
}
