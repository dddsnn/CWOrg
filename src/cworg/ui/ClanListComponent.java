package cworg.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;

import cworg.Clan;
import cworg.Project;

public class ClanListComponent extends JComponent {
	private JList list = new JList();
	private DefaultListModel model = new DefaultListModel();
	private JLabel label = new JLabel("Clans");
	private GridBagLayout gbl = new GridBagLayout();

	public ClanListComponent() {
		list.setModel(model);
		setLayout(gbl);
		add(label);
		GridBagConstraints labelConstraints = new GridBagConstraints(0,
				0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0,
						0, 0), 0, 0);
		gbl.setConstraints(label, labelConstraints);
		add(list);
		GridBagConstraints listConstraints = new GridBagConstraints(0,
				1, 1, 1, 1.0, 1.0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0);
		gbl.setConstraints(list, listConstraints);
	}

	public void displayProject(Project project) {
		model.clear();
		if (project == null) {
			return;
		}
		for (Clan c : project.getClans()) {
			model.addElement("[" + c.getClantag() + "]: "
					+ c.getName());
		}
		list.setSelectedIndex(project.getClans().indexOf(
				project.getSelectedClan()));
	}
}
