package cworg.ui;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

public class ProgressDialog extends JDialog {
	private JProgressBar progress;

	public ProgressDialog() {
		progress = new JProgressBar(0, 100);
		progress.setString("asd");
		setContentPane(progress);
		pack();
		setVisible(true);
	}
}
