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

public class ClanAddDialog extends JDialog {
	private final ClanAddDialog _this;
	private JTextField name = new JTextField();
	private JTextField tag = new JTextField();
	private JButton add = new JButton("Add");
	private CountDownLatch latch = new CountDownLatch(1);

	public ClanAddDialog(Window parent) {
		super(parent);
		_this = this;

		AbstractAction addAction = new AbstractAction("Add") {
			public void actionPerformed(ActionEvent e) {
				dispose();
				latch.countDown();
			}
		};
		add.setAction(addAction);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(new JLabel("Clan Tag:"));
		box.add(tag);
		box.add(new JLabel("Name:"));
		box.add(name);
		box.add(add);
		setContentPane(box);
		pack();
		setTitle("Add new Clan");
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public String getClanName() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name.getText();
	}
	
	public String getClanTag() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tag.getText();
	}
}
