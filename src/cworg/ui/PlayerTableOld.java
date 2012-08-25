//package cworg.ui;
//
//import java.awt.Color;
//import java.awt.Component;
//
//import javax.swing.DefaultRowSorter;
//import javax.swing.JTable;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//
//import cworg.CWOrg;
//import cworg.Player;
//import cworg.Tank;
//import cworg.TankType;
//@Deprecated
//public class PlayerTableOld extends JTable {
//	public PlayerTableOld(PlayerTableModel m) {
//		super(m);
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
//				//player available
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
//}
