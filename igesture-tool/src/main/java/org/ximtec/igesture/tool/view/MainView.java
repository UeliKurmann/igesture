package org.ximtec.igesture.tool.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.view.admin.AdminController;
import org.ximtec.igesture.tool.view.admin.AdminView;

@SuppressWarnings("serial")
public class MainView extends JFrame {

	JTabbedPane tabbedPane;
	JMenuBar menuBar;

	public MainView() {
		init();
	}

	private void init() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		menu.add(new JMenuItem("Exit"));
		
		MainModel model = Locator.getDefault().getService("MainModel", MainModel.class);
		AdminController adminController = new AdminController();
		ExplorerTreeModel explorerModel = new ExplorerTreeModel(model.getTestGestureSet(), adminController.getNodeInfoList());
		
		tabbedPane = new JTabbedPane();
		TabbedView adminView = new AdminView(explorerModel, adminController.getNodeInfoList());
		this.add(tabbedPane);
		tabbedPane.add(adminView.getName(), adminView.getPane());
		
		setBounds(0, 0, 900, 650);
		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
}
