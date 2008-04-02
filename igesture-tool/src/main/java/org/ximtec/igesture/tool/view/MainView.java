/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import org.sigtec.graphix.widget.BasicMenu;
import org.sigtec.graphix.widget.BasicMenuItem;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.action.ExitAction;
import org.ximtec.igesture.tool.view.action.StoreWorkspaceAction;


@SuppressWarnings("serial")
public class MainView extends JFrame {

   JTabbedPane tabbedPane;
   JMenuBar menuBar;

   public MainView() {
      initMenu();
      initView();
   }


   private void initView() {
      setBounds(100, 100, 900, 650);
      setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
      setVisible(true);

      tabbedPane = new JTabbedPane();
      this.add(tabbedPane);
   }


   private void initMenu() {
      menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      //FIXME
      JMenu fileMenu = new BasicMenu(GestureConstants.MENUBAR_FILE, Locator.getDefault().getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      menuBar.add(fileMenu);
      
      JMenu aboutMenu = new BasicMenu(GestureConstants.MENUBAR_ABOUT, Locator.getDefault().getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      menuBar.add(aboutMenu);
      
      BasicMenuItem storeItem = new BasicMenuItem();
      storeItem.setAction(new StoreWorkspaceAction());
      fileMenu.add(storeItem);
      BasicMenuItem exitItem = new BasicMenuItem();
      exitItem.setAction(new ExitAction());
      fileMenu.add(exitItem);
   }


   public void addTab(TabbedView view) {
      tabbedPane.add(view.getName(), view.getPane());
   }
}
