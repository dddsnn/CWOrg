package cworg.ui;

import java.awt.Desktop.Action;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cworg.CWOrg;
import cworg.Player;

public class PlayerEditDialog extends JDialog {
	private final PlayerEditDialog _this;
	private CWOrg fm;
	private Player player;
	private JTextField name;
	private JCheckBox active;
	private JCheckBox banned;
	private JButton close;

	public PlayerEditDialog(Window parent, final CWOrg fm, Player p) {
		super(parent);
		_this = this;
		this.fm = fm;
		player = p;
		name = new JTextField(player.getName());
		active = new JCheckBox("Active", player.isActive());
		banned = new JCheckBox("Banned", player.isBanned());
		close = new JButton("Close");

		AbstractAction toggle_active_action = new AbstractAction(
				"Active") {
			public void actionPerformed(ActionEvent e) {
				player.setActive(active.isSelected());

			}
		};
		AbstractAction toggle_banned_action = new AbstractAction(
				"Banned") {
			public void actionPerformed(ActionEvent e) {
				player.setBanned(banned.isSelected());
			}
		};
		AbstractAction close_action = new AbstractAction("Close") {
			public void actionPerformed(ActionEvent e) {
				applyChanges();
				dispose();
			}
		};

		active.setAction(toggle_active_action);
		banned.setAction(toggle_banned_action);
		close.setAction(close_action);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(name);
		box.add(active);
		box.add(banned);
		box.add(close);
		setContentPane(box);
		pack();
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private void applyChanges(){
		try {
			fm.changePlayerName(player, name.getText());
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(
					_this,
					ex.getMessage(),
					"Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}
