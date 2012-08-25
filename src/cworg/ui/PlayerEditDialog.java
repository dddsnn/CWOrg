package cworg.ui;

import java.awt.Desktop.Action;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.concurrent.CountDownLatch;

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
	private Player player;
	private JTextField name;
	private JCheckBox active;
	private JCheckBox banned;
	private JButton close;
	private CountDownLatch latch = new CountDownLatch(1);

	public PlayerEditDialog(Window parent, Player p) {
		super(parent);
		_this = this;
		player = p;
		name = new JTextField(player.getName());
//TODO		active = new JCheckBox("Active", player.isActive());
//TODO		banned = new JCheckBox("Banned", player.isBanned());
		close = new JButton("Close");

		AbstractAction closeAction = new AbstractAction("Close") {
			public void actionPerformed(ActionEvent e) {
				dispose();
				latch.countDown();
			}
		};

		close.setAction(closeAction);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(name);
		box.add(active);
		box.add(banned);
		box.add(close);
		setContentPane(box);
		pack();
		setTitle("Edit Player " + player.getName());
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public String getPlayerName() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name.getText();
	}
	
	public boolean getIsActive(){
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return active.isSelected();
	}
	
	public boolean getIsBanned(){
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return banned.isSelected();
	}
}
