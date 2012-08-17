package cworg.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import cworg.CWOrg;
import cworg.Player;
import cworg.Project;
import cworg.Tank;
import cworg.TankType;
import cworg.web.UnknownClanException;
import cworg.web.UnknownFormatException;
import cworg.web.WebAccess;

//import com.toedter.calendar.JCalendar;
//import com.toedter.calendar.JDateChooser;

public class MainWindow extends JFrame {
	private final CWOrg org;
	private PlayerTableModel tm;
	private PlayerTable table;
	private final JPopupMenu popmen;
	private MainWindow _this = this;

	public MainWindow(final CWOrg org) {
		this.org = org;

		setTitle("FreezeMon");
		setVisible(true);

		setupMenu();

		// Right click menu
		popmen = new JPopupMenu();
		setupPopupMenu();

		// Player table
		tm = new PlayerTableModel();
		table = new PlayerTable(tm);
		// first col must be player name
		table.getTableHeader().setReorderingAllowed(false);
		// Split pane
		JScrollPane tablescroll = new JScrollPane(table);
		JSplitPane splitpane = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT, tablescroll,
				new JButton());
		tablescroll.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					popmen.show(me.getComponent(),
							me.getX(), me.getY());
			}

			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger())
					popmen.show(me.getComponent(),
							me.getX(), me.getY());
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					popmen.show(me.getComponent(),
							me.getX(), me.getY());
			}

			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger())
					popmen.show(me.getComponent(),
							me.getX(), me.getY());
			}
		});
		add(splitpane);
		validate();
		splitpane.setDividerLocation(0.7);
		setSize(800, 600);
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	private void setupMenu() {
		// File
		JMenu filemenu = new JMenu("File");
		Action file_new_action = new AbstractAction(
				"New monitor project") {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(
						_this,
						"Enter a name for the project");
				org.createProject(name);
			}
		};
		Action file_save_action = new AbstractAction(
				"Save monitor project") {
			public void actionPerformed(ActionEvent e) {
				if (org.getProject() == null) {
					JOptionPane.showMessageDialog(
							_this,
							"No project to save",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"WoT freezing information",
						"tanks");
				fc.setFileFilter(filter);
				int res = fc.showSaveDialog(_this);
				if (res == JFileChooser.APPROVE_OPTION) {
					String path = fc.getSelectedFile()
							.getPath();
					File f;
					if (!path.endsWith(".tanks"))
						f = new File(path + ".tanks");
					else
						f = new File(path);
					org.saveProject(f);
				}
			}
		};
		Action file_load_action = new AbstractAction(
				"Load monitor project") {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"WoT freezing information",
						"tanks");
				fc.setFileFilter(filter);
				int res = fc.showOpenDialog(_this);
				if (res == JFileChooser.APPROVE_OPTION)
					org.loadProject(fc.getSelectedFile());
			}
		};
		Action file_quit_action = new AbstractAction("Quit") {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		filemenu.add(file_new_action);
		filemenu.add(file_save_action);
		filemenu.add(file_load_action);
		filemenu.add(file_quit_action);

		// View
		JMenu viewmenu = new JMenu("View");
		Action view_toptier_action = new AbstractAction(
				"Only top tiers") {
			public void actionPerformed(ActionEvent e) {
				org.viewTanks(Project.TOP_TIERS());
			}
		};
		Action view_top_tanks_action = new AbstractAction(
				"Only top tier tanks") {
			public void actionPerformed(ActionEvent e) {
				org.viewTanks(Project.TOP_TANKS());
			}
		};
		Action view_top_heavies_action = new AbstractAction(
				"Only top tier heavies") {
			public void actionPerformed(ActionEvent e) {
				org.viewTanks(Project.TOP_HEAVIES());
			}
		};
		Action view_arty_action = new AbstractAction(
				"Only high tier arties") {
			public void actionPerformed(ActionEvent e) {
				org.viewTanks(Project.ARTIES());
			}
		};
		Action view_custom_action = new AbstractAction("Custom") {
			public void actionPerformed(ActionEvent e) {
			}
		};
		viewmenu.add(view_toptier_action);
		viewmenu.add(view_top_tanks_action);
		viewmenu.add(view_top_heavies_action);
		viewmenu.add(view_arty_action);
		viewmenu.add(view_custom_action);

		JMenuBar menubar = new JMenuBar();
		menubar.add(filemenu);
		menubar.add(viewmenu);
		setJMenuBar(menubar);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setupPopupMenu() {
		Action edit_tank_action = new AbstractAction("Edit Tank") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Tank t = table.getSelectedTank();
				Player p = table.getSelectedPlayer();
				if (p == null) {
					JOptionPane.showMessageDialog(
							_this,
							"No player selected",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (t == null) {
					JOptionPane.showMessageDialog(
							_this,
							"Tank not available",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				new TankEditDialog(_this, org, t, p);
				updateCurrentProject();
			}
		};
		Action tank_researched_action = new AbstractAction(
				"Toggle tank researched") {
			public void actionPerformed(ActionEvent e) {
				Player p = table.getSelectedPlayer();
				if (p == null) {
					JOptionPane.showMessageDialog(
							_this,
							"No player selected",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				TankType type = table.getSelectedTankType();
				if (type == null) {

					JOptionPane.showMessageDialog(
							_this,
							"That's not a tank",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				// if it's not available, add it
				if (table.getSelectedTank() == null)
					p.getTanks().add(new Tank(type));
				else
					p.getTanks().remove(new Tank(type));
				updateCurrentProject();
			}
		};
		Action new_player_action = new AbstractAction("Add new player") {
			public void actionPerformed(ActionEvent e) {
				if (org.getProject() == null) {
					JOptionPane.showMessageDialog(
							_this,
							"You need to create or load a project first",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String name = JOptionPane.showInputDialog(
						_this, "Enter the player name");
				try {
					org.addPlayer(name);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(
							_this,
							ex.getMessage(),
							"Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		};
		Action edit_player_action = new AbstractAction("Edit player") {
			public void actionPerformed(ActionEvent e) {
				Player p = table.getSelectedPlayer();
				if (p == null) {
					JOptionPane.showMessageDialog(
							_this,
							"No player selected",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				new PlayerEditDialog(_this, org, p);
				updateCurrentProject();
			}
		};
		Action remove_player_action = new AbstractAction(
				"Remove player") {
			public void actionPerformed(ActionEvent e) {
				int sel = table.getSelectedRow();
				if (sel == -1) {
					JOptionPane.showMessageDialog(
							_this,
							"No player selected",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Player p = tm.getPlayerAt(sel, 0);
				// String name = (String) tm.getValueAt(sel, 0);
				org.removePlayer(p);
				updateCurrentProject();
			}
		};

		popmen.add(edit_tank_action);
		popmen.add(tank_researched_action);
		popmen.add(new_player_action);
		popmen.add(edit_player_action);
		popmen.add(remove_player_action);

	}

	public void displayProject(Project project) {
		if (project == null) {
			setTitle("FreezeMon");
			tm.setColumnCount(0);
			tm.setRowCount(0);
		}
		setTitle("CWOrg - " + project.getName());
		tm.setTankColumns(project.getDisplayedTanks());
		if (project.getSelectedClan() != null)
			tm.setPlayers(project.getSelectedClan().getPlayers());
	}

	void updateCurrentProject() {
		displayProject(org.getProject());
	}
}
