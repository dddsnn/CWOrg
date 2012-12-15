package cworg.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

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

import cworg.main.CWOrg;
import cworg.main.Player;
import cworg.main.Tank;

public class TankEditDialog extends JDialog {
	private final Tank tank;
	private Player player;
	private JLabel frozenDesc;
	private JCheckBox inGarage;
	private JCheckBox frozen;
	private JSpinner time;
	private SpinnerDateModel model;
	private JButton close;
	private CountDownLatch latch = new CountDownLatch(1);

	public TankEditDialog(Window parent, Tank t, Player owner) {
		super(parent);
		tank = t;
		player = owner;

		frozenDesc = new JLabel("Freezing start time");
		inGarage = new JCheckBox("In garage", t.getStatus().isInGarage());
		frozen = new JCheckBox("Frozen", t.getStatus().isFrozen());
		model = new SpinnerDateModel();
		time = new JSpinner(model);
		time.setEnabled(frozen.isSelected());
		if (frozen.isSelected())
			time.setValue(t.getStatus().getFrozenFrom().getTime());
		close = new JButton("Close");

		AbstractAction toggleFrozenAction = new AbstractAction("Frozen") {
			public void actionPerformed(ActionEvent e) {
				time.setEnabled(frozen.isSelected());
			}
		};
		AbstractAction closeAction = new AbstractAction("Close") {
			public void actionPerformed(ActionEvent e) {
				dispose();
				latch.countDown();
			}
		};

		close.setAction(closeAction);
		frozen.setAction(toggleFrozenAction);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(inGarage);
		box.add(frozen);
		box.add(frozenDesc);
		box.add(time);
		box.add(close);
		setContentPane(box);
		pack();
		setTitle("Edit " + player.getName() + "'s " + t.getName());
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public boolean getIsInGarage() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inGarage.isSelected();
	}

	public boolean getIsFrozen() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return frozen.isSelected();
	}

	public Calendar getFreezeTime() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar freezeTime = Calendar.getInstance();
		freezeTime.setTime(model.getDate());
		return freezeTime;
	}
}
