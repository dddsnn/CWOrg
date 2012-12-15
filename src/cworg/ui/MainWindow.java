package cworg.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import cworg.CWOrg;
import cworg.Clan;
import cworg.IllegalOperationException;
import cworg.Player;
import cworg.Project;
import cworg.Tank;
import cworg.TankType;
import cworg.UICallback;
import cworg.replay.ReplayImport;
import cworg.replay.ReplayException;
import cworg.web.UnknownClanException;
import cworg.web.UnknownWebFormatException;
import cworg.web.WebAccess;

public class MainWindow extends JFrame implements ActionProvider {
	private final UICallback uic;
	// private final JPopupMenu popmen;
	private MainWindow _this = this;
	private JMenu fileMenu, viewMenu, clanMenu, playedBattlesMenu;
	private Action fileNewProjectAction, fileSaveProjectAction,
			fileLoadProjectAction, fileQuitAction, viewTopTierAction,
			viewTopTanksAction, viewTopHeaviesAction, viewArtyAction,
			viewCustomAction, clanAddAction, clanAddFromWebAction,
			playedBattlesImportAction;
	private JSplitPane splitPane;
	private ClanListComponent clanList;
	private DetailsComponent detailsComp;

	public MainWindow(final UICallback uic) {
		this.uic = uic;

		setTitle("FreezeMon");
		setVisible(true);

		setupMenu();

		clanList = new ClanListComponent(uic, this);
		detailsComp = new DetailsComponent();
		splitPane =
				new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, clanList,
						detailsComp);
		add(splitPane);
		validate();
		splitPane.setDividerLocation(0.2);
		// // Right click menu
		// popmen = new JPopupMenu();
		// setupPopupMenu();
		//
		// // Player table
		// tm = new PlayerTableModel();
		// table = new PlayerTable(tm);
		// // first col must be player name
		// table.getTableHeader().setReorderingAllowed(false);
		// // Split pane
		// JScrollPane tablescroll = new JScrollPane(table);
		// JSplitPane splitpane = new JSplitPane(
		// JSplitPane.VERTICAL_SPLIT, tablescroll,
		// new JButton());
		// tablescroll.addMouseListener(new MouseAdapter() {
		// public void mouseReleased(MouseEvent me) {
		// if (me.isPopupTrigger())
		// popmen.show(me.getComponent(),
		// me.getX(), me.getY());
		// }
		//
		// public void mousePressed(MouseEvent me) {
		// if (me.isPopupTrigger())
		// popmen.show(me.getComponent(),
		// me.getX(), me.getY());
		// }
		// });
		// table.addMouseListener(new MouseAdapter() {
		// public void mouseReleased(MouseEvent me) {
		// if (me.isPopupTrigger())
		// popmen.show(me.getComponent(),
		// me.getX(), me.getY());
		// }
		//
		// public void mousePressed(MouseEvent me) {
		// if (me.isPopupTrigger())
		// popmen.show(me.getComponent(),
		// me.getX(), me.getY());
		// }
		// });
		setSize(800, 600);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		displayProject(null);
	}

	private void setupMenu() {
		// File
		fileMenu = new JMenu("File");
		fileNewProjectAction = new AbstractAction("New monitor project") {
			public void actionPerformed(ActionEvent e) {
				String name =
						JOptionPane.showInputDialog(_this,
								"Enter a name for the project");
				uic.createProject(name);
			}
		};
		fileSaveProjectAction = new AbstractAction("Save monitor project") {
			public void actionPerformed(ActionEvent e) {
				if (!uic.hasProject()) {
					JOptionPane.showMessageDialog(_this, "No project to save",
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter =
						new FileNameExtensionFilter("WoT CW information",
								"tanks");
				fc.setFileFilter(filter);
				int res = fc.showSaveDialog(_this);
				if (res == JFileChooser.APPROVE_OPTION) {
					String path = fc.getSelectedFile().getPath();
					File f;
					if (!path.endsWith(".tanks"))
						f = new File(path + ".tanks");
					else
						f = new File(path);
					uic.saveProject(f);
				}
			}
		};
		fileLoadProjectAction = new AbstractAction("Load monitor project") {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter =
						new FileNameExtensionFilter("WoT CW information",
								"tanks");
				fc.setFileFilter(filter);
				int res = fc.showOpenDialog(_this);
				if (res == JFileChooser.APPROVE_OPTION)
					uic.loadProject(fc.getSelectedFile());
			}
		};
		fileQuitAction = new AbstractAction("Quit") {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		fileMenu.add(fileNewProjectAction);
		fileMenu.add(fileSaveProjectAction);
		fileMenu.add(fileLoadProjectAction);
		fileMenu.add(fileQuitAction);

		// View
		viewMenu = new JMenu("View");
		viewTopTierAction = new AbstractAction("Only top tiers") {
			public void actionPerformed(ActionEvent e) {
				uic.setDisplayedTanks(Project.TOP_TIERS());
			}
		};
		viewTopTanksAction = new AbstractAction("Only top tier tanks") {
			public void actionPerformed(ActionEvent e) {
				uic.setDisplayedTanks(Project.TOP_TANKS());
			}
		};
		viewTopHeaviesAction = new AbstractAction("Only top tier heavies") {
			public void actionPerformed(ActionEvent e) {
				uic.setDisplayedTanks(Project.TOP_HEAVIES());
			}
		};
		viewArtyAction = new AbstractAction("Only high tier arties") {
			public void actionPerformed(ActionEvent e) {
				uic.setDisplayedTanks(Project.ARTIES());
			}
		};
		viewCustomAction = new AbstractAction("Custom") {
			public void actionPerformed(ActionEvent e) {
			}
		};
		viewMenu.add(viewTopTierAction);
		viewMenu.add(viewTopTanksAction);
		viewMenu.add(viewTopHeaviesAction);
		viewMenu.add(viewArtyAction);
		viewMenu.add(viewCustomAction);

		// Clans
		clanMenu = new JMenu("Clans");
		clanAddAction = new AbstractAction("Add clan manually") {
			public void actionPerformed(ActionEvent arg0) {
				ClanAddDialog d = new ClanAddDialog(_this);
				Clan c = new Clan(d.getClanTag(), d.getClanName());
				try {
					uic.addClan(c);
				} catch (IllegalOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		clanAddFromWebAction =
				new AbstractAction("Add Clan with info from web") {
					public void actionPerformed(ActionEvent e) {
						String name =
								JOptionPane.showInputDialog(_this,
										"Enter the clan name (must be exact)");
						// cancel
						if (name == null)
							return;
						Clan clan = null;
						try {
							clan = WebAccess.getInstance().getClanByName(name);
						} catch (UnknownClanException e1) {
							// TODO Auto-generated
							// catch block
							e1.printStackTrace();
						} catch (UnknownWebFormatException e1) {
							// TODO Auto-generated
							// catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated
							// catch block
							e1.printStackTrace();
						}
						if (clan != null) {
							try {
								uic.addClan(clan);
							} catch (IllegalArgumentException ex) {
								JOptionPane.showMessageDialog(_this,
										ex.getMessage(), "Warning",
										JOptionPane.WARNING_MESSAGE);
							} catch (IllegalOperationException ex) {
								JOptionPane.showMessageDialog(_this,
										ex.getMessage(), "Warning",
										JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				};
		clanMenu.add(clanAddAction);
		clanMenu.add(clanAddFromWebAction);

		// battles
		playedBattlesMenu = new JMenu("Played Battles");
		playedBattlesImportAction = new AbstractAction("Import Replay") {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter =
						new FileNameExtensionFilter("WoT Replays", "wotreplay");
				fc.setFileFilter(filter);
				int res = fc.showOpenDialog(_this);
				if (res == JFileChooser.APPROVE_OPTION)
					try {
						ReplayImport.getInstance().getBattleFromReplay(
								fc.getSelectedFile());
					} catch (IllegalArgumentException e) {
						// TODO
						// Auto-generated
						// catch
						// block
						e.printStackTrace();
					} catch (ReplayException e) {
						// TODO
						// Auto-generated
						// catch
						// block
						e.printStackTrace();
					}
			}
		};
		playedBattlesMenu.add(playedBattlesImportAction);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		menuBar.add(clanMenu);
		menuBar.add(playedBattlesMenu);
		setJMenuBar(menuBar);
	}

	public void displayProject(Project project) {
		clanList.displayProject(project);
		if (project == null) {
			setTitle("CWOrg");
			// turn off options
			splitPane.setVisible(false);
			clanMenu.setEnabled(false);
			viewMenu.setEnabled(false);
			playedBattlesMenu.setEnabled(false);
			fileSaveProjectAction.setEnabled(false);
			detailsComp.displayClan(null);
			return;
		}
		detailsComp.displayClan(uic.getSelectedClan());
		setTitle("CWOrg - " + project.getName());
		// enable options
		splitPane.setVisible(true);
		clanMenu.setEnabled(true);
		viewMenu.setEnabled(true);
		playedBattlesMenu.setEnabled(true);
		fileSaveProjectAction.setEnabled(true);
	}

	@Override
	public Action getClanAddAction() {
		return clanAddAction;
	}

	@Override
	public Action getClanAddFromWebAction() {
		return clanAddFromWebAction;
	}
}
