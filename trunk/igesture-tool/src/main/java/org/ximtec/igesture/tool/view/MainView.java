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

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import org.sigtec.graphix.IconTool;
import org.sigtec.graphix.widget.BasicMenu;
import org.sigtec.graphix.widget.BasicMenuItem;
import org.sigtec.util.Decorator;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.view.action.ExitAction;
import org.ximtec.igesture.tool.view.action.LoadWorkspaceAction;
import org.ximtec.igesture.tool.view.action.ShowAboutAction;
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
      setDefaultCloseOperation(Frame.NORMAL);
      setIconImage(IconTool.getIcon("igesture", Decorator.SIZE_16).getImage());
      setVisible(true);
      tabbedPane = new JTabbedPane();
      this.add(tabbedPane);
      setTitle(ComponentFactory.getGuiBundle().getName(GestureConstants.APPLICATION_TITLE));
      
   }


   private void initMenu() {
      menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      
      JMenu fileMenu = new BasicMenu(GestureConstants.MENUBAR_FILE, Locator.getDefault().getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      menuBar.add(fileMenu);
      
      JMenu helpMenu = new BasicMenu(GestureConstants.MENUBAR_HELP, Locator.getDefault().getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      menuBar.add(helpMenu);
      
      BasicMenuItem openItem = new BasicMenuItem();
      openItem.setAction(new LoadWorkspaceAction(null));
      fileMenu.add(openItem);
      BasicMenuItem storeItem = new BasicMenuItem();
      storeItem.setAction(new StoreWorkspaceAction());
      fileMenu.add(storeItem);
      BasicMenuItem exitItem = new BasicMenuItem();
      exitItem.setAction(new ExitAction());
      fileMenu.addSeparator();
      fileMenu.add(exitItem);
      
      BasicMenuItem aboutItem = new BasicMenuItem();
      aboutItem.setAction(new ShowAboutAction());
      helpMenu.add(aboutItem);
      
      
   }


   public void addTab(TabbedView view) {
      tabbedPane.add(view.getName(), view.getPane());
   }
   
   public void removeAllTabs(){
      tabbedPane.removeAll();
   }
}
