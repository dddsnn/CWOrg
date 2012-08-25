package cworg.ui;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.util.TreeMap;
//import java.util.Vector;
//
//import javax.swing.DefaultRowSorter;
import javax.swing.JTable;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//
//import cworg.CWOrg;
//import cworg.Clan;
//import cworg.Player;
//import cworg.Tank;
//import cworg.TankType;
//
public class TanksTable extends JTable {
//	private static class PlayerTableModel extends DefaultTableModel {
//		/**
//		 * Maps the index of a table column to the TankType it
//		 * represents
//		 */
//		private TreeMap<Integer, TankType> tank_cols;
//		private TreeMap<Integer, Player> player_rows;
//
//		public PlayerTableModel() {
//			super();
//			addColumn("Name");
//			addColumn("Active");
//			addColumn("Banned");
//			tank_cols = new TreeMap<Integer, TankType>();
//			player_rows = new TreeMap<Integer, Player>();
//		}
//
//		private void addTankColumn(TankType tank) {
//			tank_cols.put(getColumnCount(), tank);
//			super.addColumn(Tank.getTankShortName(tank));
//		}
//
//		public void setTankColumns(Vector<TankType> tanks) {
//			setColumnCount(3);
//			tank_cols.clear();
//			if (tanks == null)
//				return;
//			for (TankType t : tanks)
//				addTankColumn(t);
//		}
//
//		private void addPlayer(Player p) {
//			Vector<Object> row = new Vector<Object>();
//			row.add(p.getName());
//			// two rows for active/banned status, signalized with
//			// colors, so
//			// no text
//			row.add("");
//			row.add("");
//			for (int i = 3; i < getColumnCount(); i++) {
//				int index = p.getTanks().indexOf(
//						new Tank(tank_cols.get(i)));
//				if (index == -1) {
//					row.add("");
//					continue;
//				}
//				row.add(p.getTanks().get(index).getStatus());
//			}
//			player_rows.put(getRowCount(), p);
//			addRow(row);
//		}
//
//		public void setPlayers(Vector<Player> players) {
//			setRowCount(0);
//			player_rows.clear();
//			if (players == null)
//				return;
//			for (Player p : players)
//				addPlayer(p);
//		}
//
//		public Player getPlayerAt(int row, int col) {
//			return player_rows.get(row);
//		}
//
//		public Tank getTankAt(int row, int col) {
//			for (Tank t : getPlayerAt(row, col).getTanks()) {
//				if (t.getType() == tank_cols.get(col))
//					return t;
//			}
//			return null;
//		}
//
//		public TankType getTankTypeAt(int row, int col) {
//			return tank_cols.get(col);
//		}
//
//		public void clear() {
//			setRowCount(0);
//			player_rows.clear();
//		}
//
//		@Override
//		public boolean isCellEditable(int row, int column) {
//			return false;
//		}
//	}
//
//	public TanksTable() {
//		super(new PlayerTableModel());
//	}
//
//	public Player getSelectedPlayer() {
//		int row = getSelectedRow();
//		int col = getSelectedColumn();
//		if (row == -1 || col == -1)
//			return null;
//		return getModel().getPlayerAt(row, col);
//	}
//
//	public TankType getSelectedTankType() {
//		int row = getSelectedRow();
//		int col = getSelectedColumn();
//		if (row == -1 || col == -1)
//			return null;
//		return getModel().getTankTypeAt(row, col);
//	}
//
//	public Tank getSelectedTank() {
//		int row = getSelectedRow();
//		int col = getSelectedColumn();
//		if (row == -1 || col == -1)
//			return null;
//		return getModel().getTankAt(row, col);
//	}
//
//	@Override
//	public Component prepareRenderer(TableCellRenderer r, int row,
//			int column) {
//		Component c = super.prepareRenderer(r, row, column);
//		Player p = getModel().getPlayerAt(row, column);
//		// player unavailable
//		if ((!p.isActive() || p.isBanned()) && column != 0) {
//			c.setBackground(CWOrg.PLAYER_UNAVAILABLE);
//			return c;
//		}
//		Tank t = getModel().getTankAt(row, column);
//		if (t == null) {
//			if (column == 0) {
//				c.setBackground(Color.WHITE);
//				return c;
//			} else if (column < 3) {
//				// player available
//				c.setBackground(CWOrg.PLAYER_AVAILABLE);
//				return c;
//			} else {
//
//				c.setBackground(CWOrg.TANK_NOT_RESEARCHED);
//				return c;
//			}
//		}
//
//		c.setBackground(t.getStatus().getColor());
//		return c;
//	}
//
//	@Override
//	public PlayerTableModel getModel() {
//		return (PlayerTableModel) super.getModel();
//	}
//
//	public void displayClan(Clan clan) {
//		// TODO Auto-generated method stub
//		
//	}
}
