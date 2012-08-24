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

public class ClanAddDialog extends JDialog {
	private final ClanAddDialog _this;
	private JTextField name = new JTextField();
	private JTextField tag = new JTextField();
	private JButton add = new JButton("Add");

	public ClanAddDialog(Window parent) {
		super(parent);
		_this = this;

		AbstractAction addAction = new AbstractAction("Add") {
			public void actionPerformed(ActionEvent e) {
				dispose();
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
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * TODO Blocks until it's closed
	 */
	public void waitInput() {
//		 
	}
}
