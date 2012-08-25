package cworg.ui;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.DefaultRowSorter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import cworg.CWOrg;
import cworg.Clan;
import cworg.Player;
import cworg.Tank;
import cworg.TankType;
import cworg.TankClass;

public class PlayerTable extends JTable {
	private static class PlayerTableModel extends DefaultTableModel {
		// /**
		// * Maps the index of a table column to the TankType it
		// * represents
		// */
		// private TreeMap<Integer, TankType> tank_cols;
		// private TreeMap<Integer, Player> player_rows;

		public PlayerTableModel() {
			addColumn("Name");
			addColumn("Availability");
			addColumn("Last CW");
			addColumn("T10 Heavies");
			addColumn("T10 TDs");
			addColumn("T10 Mediums");
			addColumn("T8 Arties");
			// tank_cols = new TreeMap<Integer, TankType>();
			// player_rows = new TreeMap<Integer, Player>();
		}

		// private void addTankColumn(TankType tank) {
		// tank_cols.put(getColumnCount(), tank);
		// super.addColumn(Tank.getTankShortName(tank));
		// }

		// public void setTankColumns(Vector<TankType> tanks) {
		// setColumnCount(1);
		// // tank_cols.clear();
		// if (tanks == null)
		// return;
		// for (TankType t : tanks)
		// addTankColumn(t);
		// }

		private void addPlayer(Player p) {
			Vector<Object> row = new Vector<Object>();
			// name
			row.add(p.getName());
			// availability
			String availability = "";
			if (p.isAvailable()) {
				if (p.getUnavailableStart() == null)
					availability = "available";
				else {
					Calendar start =
							p.getUnavailableStart();
					availability =
							"available until "
									+ start.get(Calendar.DAY_OF_MONTH)
									+ "."
									+ start.get(Calendar.MONTH);
				}
			} else {
				if (p.getUnavailableEnd() != null) {
					Calendar end = p.getUnavailableEnd();
					availability =
							"unavailable until "
									+ end.get(Calendar.DAY_OF_MONTH)
									+ "."
									+ end.get(Calendar.MONTH);
				} else
					availability = "unavailable";
			}
			row.add(availability);
			// last cw
			String lastCW;
			if (p.getLastCW() == null)
				lastCW = "never";
			else {
				Calendar c = p.getLastCW();
				lastCW =
						c.get(Calendar.DAY_OF_MONTH)
								+ "."
								+ c.get(Calendar.MONTH)
								+ ", "
								+ c.get(Calendar.HOUR_OF_DAY)
								+ ":"
								+ c.get(Calendar.MINUTE);
			}
			row.add(lastCW);
			// tanks
			int heaviesAvail = 0, heaviesFrozen = 0, tdsAvail = 0, tdsFrozen =
					0, medsAvail = 0, medsFrozen = 0, artyAvail =
					0, artyFrozen = 0;
			for (Tank t : p.getTanks()) {
				switch (t.getTier()) {
				case 10:
					switch (t.getTankClass()) {
					case HEAVY:
						if (t.getStatus().isFrozen())
							heaviesFrozen++;
						else
							heaviesAvail++;
						break;
					case TD:
						if (t.getStatus().isFrozen())
							tdsFrozen++;
						else
							tdsAvail++;
						break;
					case MEDIUM:
						if (t.getStatus().isFrozen())
							medsFrozen++;
						else
							medsAvail++;
						break;
					}
					break;
				case 8:
					if (!(t.getTankClass() == TankClass.SPG))
						break;
					if (t.getStatus().isFrozen())
						artyFrozen++;
					else
						artyAvail++;
					break;
				}
			}
			// heavies
			row.add(heaviesAvail + "/"
					+ (heaviesAvail + heaviesFrozen));
			// tds
			row.add(tdsAvail + "/"
					+ (tdsAvail + tdsFrozen));
			// meds
			row.add(medsAvail + "/"
					+ (medsAvail + medsFrozen));
			// arties
			row.add(artyAvail + "/"
					+ (artyAvail + artyFrozen));
			// // two rows for active/banned status, signalized with
			// // colors, so no text
			// row.add("");
			// row.add("");
			// for (int i = 3; i < getColumnCount(); i++) {
			// int index = p.getTanks().indexOf(
			// new Tank(tank_cols.get(i)));
			// if (index == -1) {
			// row.add("");
			// continue;
			// }
			// row.add(p.getTanks().get(index).getStatus());
			// }
			// player_rows.put(getRowCount(), p);
			addRow(row);
		}

		// public void setPlayers(Vector<Player> players) {
		// setRowCount(0);
		// player_rows.clear();
		// if (players == null)
		// return;
		// for (Player p : players)
		// addPlayer(p);
		// }
		//
		// public Player getPlayerAt(int row, int col) {
		// return player_rows.get(row);
		// }
		//
		// public Tank getTankAt(int row, int col) {
		// for (Tank t : getPlayerAt(row, col).getTanks()) {
		// if (t.getType() == tank_cols.get(col))
		// return t;
		// }
		// return null;
		// }
		//
		// public TankType getTankTypeAt(int row, int col) {
		// return tank_cols.get(col);
		// }

		public void clear() {
			setRowCount(0);
			// player_rows.clear();
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}

	private PlayerTableModel model = new PlayerTableModel();

	public PlayerTable() {
		setModel(model);
	}

	public void displayClan(Clan clan) {
		model.clear();
		if (clan == null)
			return;
		for (Player newPlayer : clan.getPlayers()) {
			model.addPlayer(newPlayer);
		}
	}

	// public void clear() {
	// model.clear();
	// }

	// public Player getSelectedPlayer() {
	// int row = getSelectedRow();
	// int col = getSelectedColumn();
	// if (row == -1 || col == -1)
	// return null;
	// return getModel().getPlayerAt(row, col);
	// }
	//
	// public TankType getSelectedTankType() {
	// int row = getSelectedRow();
	// int col = getSelectedColumn();
	// if (row == -1 || col == -1)
	// return null;
	// return getModel().getTankTypeAt(row, col);
	// }
	//
	// public Tank getSelectedTank() {
	// int row = getSelectedRow();
	// int col = getSelectedColumn();
	// if (row == -1 || col == -1)
	// return null;
	// return getModel().getTankAt(row, col);
	// }

	// @Override
	// public Component prepareRenderer(TableCellRenderer r, int row,
	// int column) {
	// Component c = super.prepareRenderer(r, row, column);
	// Player p = getModel().getPlayerAt(row, column);
	// // player unavailable
	// if ((!p.isActive() || p.isBanned()) && column != 0) {
	// c.setBackground(CWOrg.PLAYER_UNAVAILABLE);
	// return c;
	// }
	// Tank t = getModel().getTankAt(row, column);
	// if (t == null) {
	// if (column == 0) {
	// c.setBackground(Color.WHITE);
	// return c;
	// } else if (column < 3) {
	// // player available
	// c.setBackground(CWOrg.PLAYER_AVAILABLE);
	// return c;
	// } else {
	//
	// c.setBackground(CWOrg.TANK_NOT_RESEARCHED);
	// return c;
	// }
	// }
	//
	// c.setBackground(t.getStatus().getColor());
	// return c;
	// }

	@Override
	public PlayerTableModel getModel() {
		return model;
	}
}
