package cworg.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cworg.main.Clan;
import cworg.main.Project;
import cworg.main.UICallback;

public class ClanListComponent extends JComponent {
	private JList list = new JList();
	private DefaultListModel model = new DefaultListModel();
	private JLabel label = new JLabel("Clans");
	private GridBagLayout gbl = new GridBagLayout();
	private JPopupMenu popmen;
	private Action clanAddAction, clanAddFromWebAction;
	private Vector<Clan> displayedClans = null;
	private UICallback uic;

	public ClanListComponent(final UICallback uic, ActionProvider ap) {
		this.clanAddAction = ap.getClanAddAction();
		this.clanAddFromWebAction = ap.getClanAddFromWebAction();
		this.uic = uic;
		list.setModel(model);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				int i = list.getSelectedIndex();
				if (i == -1)
					return;
				Clan c = displayedClans.get(i);
				uic.setSelectedClan(c);
			}
		});
		setLayout(gbl);
		add(label);
		GridBagConstraints labelConstraints =
				new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
						GridBagConstraints.NORTH,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
						0, 0);
		gbl.setConstraints(label, labelConstraints);
		JScrollPane listScroll = new JScrollPane(list);
		add(listScroll);
		GridBagConstraints listScrollConstraints =
				new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
						GridBagConstraints.NORTH, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0);
		gbl.setConstraints(listScroll, listScrollConstraints);
		setupPopupMenu();
	}

	private void setupPopupMenu() {
		popmen = new JPopupMenu();
		popmen.add(clanAddAction);
		popmen.add(clanAddFromWebAction);
		MouseAdapter ma = new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					popmen.show(me.getComponent(), me.getX(), me.getY());
			}

			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger())
					popmen.show(me.getComponent(), me.getX(), me.getY());
			}
		};
		list.addMouseListener(ma);
	}

	public void displayProject(Project project) {
		model.clear();
		if (project == null) {
			displayedClans = null;
			return;
		}
		displayedClans = project.getClans();
		for (Clan c : project.getClans()) {
			model.addElement("[" + c.getClantag() + "]: " + c.getName());
		}
		list.setSelectedIndex(project.getClans().indexOf(
				uic.getSelectedClan()));
	}
}
