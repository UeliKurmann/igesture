/*
 * @(#)GestureToolView.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Main View of the Application
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.sigtec.util.Decorator;
import org.sigtec.util.IconTool;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.tool.action.ActionAboutDialog;
import org.ximtec.igesture.tool.action.ActionExitApplication;
import org.ximtec.igesture.tool.action.ActionNewDataSouce;
import org.ximtec.igesture.tool.action.ActionOpenDataSouce;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;

/**
 * Main application window.
 */
public class GestureToolView extends JFrame {

	private static final String ABOUT_HTML = "about.html";

	/**
	 * 
	 */
	private static final String GUI_STYLE = "org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel";

	private static final String ISERVER_ICON = "icons/iserver";

	private GestureConfiguration configuration;

	private GestureMainModel model;

	private JTabbedPane tabbedPane = null;

	/**
	 * Constructs a new main view.
	 * 
	 * @param model
	 *            the model for this main view.
	 */
	public GestureToolView(GestureMainModel model,
			GestureConfiguration configuration) {
		super();
		this.configuration = configuration;
		this.model = model;
		initialise();
	}

	/**
	 * This method initializes this.
	 * 
	 */
	private void initialise() {

		try {
			UIManager.setLookAndFeel(GUI_STYLE);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		setIconImage(IconTool.getIcon(ISERVER_ICON, Decorator.SIZE_16)
				.getImage());
		this.setBounds(0, 0, 900, 650);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());

		this.setTitle(SwingTool.getGuiBundle().getName(
				GestureConstants.GESTUREVIEW_MAINFRAME_KEY));
		this.setVisible(true);

		this.tabbedPane = new JTabbedPane();
		this.setContentPane(tabbedPane);

		loadTabs();

		this.repaint();
	} // initialise

	/**
	 * Initialises the tabs
	 * 
	 */
	private void loadTabs() {
		for (final String className : configuration.getTabs()) {
			try {
				final GestureTab tab = (GestureTab) Class.forName(className)
						.newInstance();
				tab.init(this);
				tabbedPane.add(SwingTool.getGuiBundle().getName(tab.getName()),
						tab.getDesktopPane());
			} catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reloads the Main Model with a new DataSource
	 * 
	 * @param engine
	 *            the new datasource
	 */
	public void reloadModelData(StorageEngine engine) {
		this.model.loadData(engine);
	}

	/**
	 * Creates the Menubar
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createInfoMenu());
		return menuBar;
	}

	private JMenu createFileMenu() {
		final JMenu menu = SwingTool.createMenu(SwingTool.getGuiBundle()
				.getName(GestureConstants.COMMON_FILE), null);

		menu.add(SwingTool.createMenuItem(new ActionNewDataSouce(this),
				IconLoader.getIcon(IconLoader.DOCUMENT_NEW)));
		menu.add(SwingTool.createMenuItem(new ActionOpenDataSouce(this),
				IconLoader.getIcon(IconLoader.DOCUMENT_OPEN)));
		menu.addSeparator();
		menu.add(SwingTool.createMenuItem(new ActionExitApplication(this)));

		return menu;
	}

	/**
	 * Creats the info Model
	 * 
	 * @return
	 */
	private JMenu createInfoMenu() {
		final JMenu menu = SwingTool.createMenu(SwingTool.getGuiBundle()
				.getName(GestureConstants.COMMON_HELP), null);

		menu.add(SwingTool.createMenuItem(new ActionAboutDialog(ABOUT_HTML)));

		return menu;
	}

	/**
	 * Returns the MainModel.
	 * 
	 * @return the MainModel
	 */
	public GestureMainModel getModel() {
		return model;
	} // getModel
}
