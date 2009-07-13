/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : The main view of the iGesture tool.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 23.03.2008       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import org.sigtec.graphix.widget.BasicMenu;
import org.sigtec.graphix.widget.BasicMenuItem;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.view.action.ExitAction;
import org.ximtec.igesture.tool.view.action.LoadWorkspaceAction;
import org.ximtec.igesture.tool.view.action.ShowAboutAction;
import org.ximtec.igesture.tool.view.action.StoreWorkspaceAction;

/**
 * The main view of the iGesture tool.
 * 
 * @version 1.0 Mar 23, 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
@SuppressWarnings("serial")
public class MainView extends JFrame {

	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private Controller controller;

	public MainView(Controller controller) {
		this.controller = controller;
		initMenu();
		initView();
	}

	private void initView() {
		setBounds(100, 100, 900, 650);
		setDefaultCloseOperation(Frame.NORMAL);
		setTitle(ComponentFactory.getGuiBundle().getName(
				GestureConstants.APPLICATION_ROOT));
		setIconImage(ComponentFactory.getGuiBundle().getSmallIcon(
				GestureConstants.APPLICATION_ROOT).getImage());
		setVisible(true);
		tabbedPane = new JTabbedPane();
		this.add(tabbedPane);
	}

	private void initMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new BasicMenu(GestureConstants.FILE_MENU, controller
				.getLocator().getService(GuiBundleService.IDENTIFIER,
						GuiBundleService.class));
		menuBar.add(fileMenu);

		JMenu helpMenu = new BasicMenu(GestureConstants.MENUBAR_HELP, controller
				.getLocator().getService(GuiBundleService.IDENTIFIER,
						GuiBundleService.class));
		menuBar.add(helpMenu);

		BasicMenuItem openItem = new BasicMenuItem();
		openItem.setAction(controller.getAction(LoadWorkspaceAction.class));
		fileMenu.add(openItem);
		BasicMenuItem storeItem = new BasicMenuItem();
		storeItem.setAction(controller.getAction(StoreWorkspaceAction.class));
		fileMenu.add(storeItem);
		BasicMenuItem exitItem = new BasicMenuItem();
		exitItem.setAction(controller.getAction(ExitAction.class));
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		BasicMenuItem aboutItem = new BasicMenuItem();
		aboutItem.setAction(controller.getAction(ShowAboutAction.class));
		helpMenu.add(aboutItem);
	}

	public void addTab(TabbedView view) {
		tabbedPane.add(view.getName(), view.getPane());
	} // addTab

	public void removeAllTabs() {
		tabbedPane.removeAll();
	} // removeAllTabs

}
