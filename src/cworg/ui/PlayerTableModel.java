package cworg.ui;

import java.awt.Color;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import cworg.Player;
import cworg.Tank;
import cworg.TankType;

public class PlayerTableModel extends DefaultTableModel {
	/**
	 * Maps the index of a table column to the TankType it represents
	 */
	private TreeMap<Integer, TankType> tank_cols;
	private TreeMap<Integer, Player> player_rows;

	public PlayerTableModel() {
		super();
		addColumn("Name");
		addColumn("Active");
		addColumn("Banned");
		tank_cols = new TreeMap<Integer, TankType>();
		player_rows = new TreeMap<Integer, Player>();
	}

	private void addTankColumn(TankType tank) {
		tank_cols.put(getColumnCount(), tank);
		super.addColumn(Tank.getTankShortName(tank));
	}

	public void setTankColumns(Vector<TankType> tanks) {
		setColumnCount(3);
		tank_cols.clear();
		if(tanks == null)
			return;
		for (TankType t : tanks)
			addTankColumn(t);
	}

	private void addPlayer(Player p) {
		Vector<Object> row = new Vector<Object>();
		row.add(p.getName());
		// two rows for active/banned status, signalized with colors, so
		// no text
		row.add("");
		row.add("");
		for (int i = 3; i < getColumnCount(); i++) {
			int index = p.getTanks().indexOf(
					new Tank(tank_cols.get(i)));
			if (index == -1) {
				row.add("");
				continue;
			}
			row.add(p.getTanks().get(index).getStatus());
		}
		player_rows.put(getRowCount(), p);
		addRow(row);
	}

	public void setPlayers(Vector<Player> players) {
		setRowCount(0);
		player_rows.clear();
		if(players == null)
			return;
		for (Player p : players)
			addPlayer(p);
	}

	public Player getPlayerAt(int row, int col) {
		return player_rows.get(row);
	}

	public Tank getTankAt(int row, int col) {
		for (Tank t : getPlayerAt(row, col).getTanks()) {
			if (t.getType() == tank_cols.get(col))
				return t;
		}
		return null;
	}

	public TankType getTankTypeAt(int row, int col) {
		return tank_cols.get(col);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
