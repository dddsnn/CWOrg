package cworg.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import cworg.main.Clan;
import cworg.main.IllegalOperationException;
import cworg.main.Player;
import cworg.main.Project;
import cworg.main.Tank;
import cworg.main.TankType;

public class DetailsComponent extends JTabbedPane {
	private PlayerTable playerTable = new PlayerTable();
	private TanksTable tanksTable = new TanksTable();
	JScrollPane playerTableScroll = new JScrollPane(playerTable);
	JScrollPane tanksTableScroll = new JScrollPane(tanksTable);

	public DetailsComponent() {
		playerTableScroll.setName("Players");
		add(playerTableScroll);
		tanksTableScroll.setName("Tanks");
		add(tanksTableScroll);
	}

	public void displayClan(Clan clan) {
		playerTable.displayClan(clan);
		// tanksTable.displayClan(clan);
	}

	// private void setupPopupMenu() {
	// Action edit_tank_action = new AbstractAction("Edit Tank") {
	// @Override
	// public void actionPerformed(ActionEvent arg0) {
	// Tank t = table.getSelectedTank();
	// Player p = table.getSelectedPlayer();
	// if (p == null) {
	// JOptionPane.showMessageDialog(
	// _this,
	// "No player selected",
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// return;
	// }
	// if (t == null) {
	// JOptionPane.showMessageDialog(
	// _this,
	// "Tank not available",
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// return;
	// }
	// new TankEditDialog(_this, org, t, p);
	// updateCurrentProject();
	// }
	// };
	// Action tank_researched_action = new AbstractAction(
	// "Toggle tank researched") {
	// public void actionPerformed(ActionEvent e) {
	// Player p = table.getSelectedPlayer();
	// if (p == null) {
	// JOptionPane.showMessageDialog(
	// _this,
	// "No player selected",
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// return;
	// }
	// TankType type = table.getSelectedTankType();
	// if (type == null) {
	//
	// JOptionPane.showMessageDialog(
	// _this,
	// "That's not a tank",
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// return;
	// }
	// // if it's not available, add it
	// if (table.getSelectedTank() == null)
	// p.getTanks().add(new Tank(type));
	// else
	// p.getTanks().remove(new Tank(type));
	// updateCurrentProject();
	// }
	// };
	// Action new_player_action = new AbstractAction("Add new player") {
	// public void actionPerformed(ActionEvent e) {
	// if (org.getProject() == null) {
	// JOptionPane.showMessageDialog(
	// _this,
	// "You need to create or load a project first",
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// return;
	// }
	// String name = JOptionPane.showInputDialog(
	// _this, "Enter the player name");
	// try {
	// org.addPlayer(name);
	// } catch (IllegalArgumentException ex) {
	// JOptionPane.showMessageDialog(
	// _this,
	// ex.getMessage(),
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// } catch (IllegalOperationException ex) {
	// JOptionPane.showMessageDialog(
	// _this,
	// ex.getMessage(),
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// }
	// }
	// };
	// Action edit_player_action = new AbstractAction("Edit player") {
	// public void actionPerformed(ActionEvent e) {
	// Player p = table.getSelectedPlayer();
	// if (p == null) {
	// JOptionPane.showMessageDialog(
	// _this,
	// "No player selected",
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// return;
	// }
	// new PlayerEditDialog(_this, org, p);
	// updateCurrentProject();
	// }
	// };
	// Action remove_player_action = new AbstractAction(
	// "Remove player") {
	// public void actionPerformed(ActionEvent e) {
	// int sel = table.getSelectedRow();
	// if (sel == -1) {
	// JOptionPane.showMessageDialog(
	// _this,
	// "No player selected",
	// "Warning",
	// JOptionPane.WARNING_MESSAGE);
	// return;
	// }
	// Player p = tm.getPlayerAt(sel, 0);
	// // String name = (String) tm.getValueAt(sel, 0);
	// org.removePlayer(p);
	// updateCurrentProject();
	// }
	// };
	//
	// // popmen.add(edit_tank_action);
	// // popmen.add(tank_researched_action);
	// // popmen.add(new_player_action);
	// // popmen.add(edit_player_action);
	// // popmen.add(remove_player_action);
	//
	// }
}
