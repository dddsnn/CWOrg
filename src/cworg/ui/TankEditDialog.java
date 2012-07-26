package cworg.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import cworg.CWOrg;
import cworg.Player;
import cworg.Tank;

public class TankEditDialog extends JDialog {
	private CWOrg fm;
	private final Tank tank;
	private Player player;
	private JLabel info;
	private JLabel frozen_desc;
	private JCheckBox in_garage;
	private JCheckBox frozen;
	private JSpinner time;
	private SpinnerDateModel model;
	private JButton close;

	public TankEditDialog(Window parent, final CWOrg fm, Tank t,
			Player owner) {
		super(parent);
		tank = t;
		player = owner;
		this.fm = fm;

		info = new JLabel(player.getName() + ": " + t.getName());
		frozen_desc = new JLabel("Freezing start time");
		in_garage = new JCheckBox("In garage", t.getStatus()
				.isInGarage());
		frozen = new JCheckBox("Frozen", t.getStatus().isFrozen());
		model = new SpinnerDateModel();
		time = new JSpinner(model);
		time.setEnabled(frozen.isSelected());
		if (frozen.isSelected())
			time.setValue(t.getStatus().getFrozenFrom().getTime());
		close = new JButton("Close");

		AbstractAction toggle_in_garage_action = new AbstractAction(
				"In garage") {
			public void actionPerformed(ActionEvent e) {
				fm.setInGarage(tank, in_garage.isSelected());
			}
		};
		AbstractAction toggle_frozen_action = new AbstractAction(
				"Frozen") {
			public void actionPerformed(ActionEvent e) {
				if (!frozen.isSelected()) {
					time.setEnabled(false);
					fm.setUnfrozen(tank);
					return;
				}
				time.setEnabled(true);
			}
		};
		AbstractAction close_action = new AbstractAction("Close") {
			public void actionPerformed(ActionEvent e) {
				applyChange();
				dispose();
			}
		};

		in_garage.setAction(toggle_in_garage_action);
		close.setAction(close_action);
		frozen.setAction(toggle_frozen_action);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(info);
		box.add(in_garage);
		box.add(frozen);
		box.add(frozen_desc);
		box.add(time);
		box.add(close);
		setContentPane(box);
		pack();
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private void applyChange() {
		if (frozen.isSelected()) {

			Calendar freezetime = Calendar.getInstance();
			freezetime.setTime(model.getDate());
			fm.setFrozen(tank, freezetime);
		} else {
			fm.setUnfrozen(tank);
		}
	}
}
