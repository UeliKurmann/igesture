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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view;

import java.awt.Frame;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.sigtec.graphix.widget.BasicMenu;
import org.sigtec.graphix.widget.BasicMenuItem;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultView;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.service.GuiBundleService;

/**
 * The main view of the iGesture tool.
 * 
 * @version 1.0 Mar 23, 2008
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
@SuppressWarnings("serial")
public class MainView extends DefaultView implements IMainView {

  private static final String TITLE_DELIMITER = " - ";
  private JTabbedPane tabbedPane;
  private JMenuBar menuBar;
  private String titlePrefix;

  public MainView(Controller controller) {
    super(controller);
    initMenu();
    initView();
  }

  private void initView() {
    setBounds(100, 100, 900, 650);
    setDefaultCloseOperation(Frame.NORMAL);
    titlePrefix = getComponentFactory().getGuiBundle().getName(GestureConstants.APPLICATION_ROOT);
    setTitle(titlePrefix);
    setIconImage(getComponentFactory().getGuiBundle().getSmallIcon(GestureConstants.APPLICATION_ROOT).getImage());
    setVisible(true);
    tabbedPane = new JTabbedPane();
    this.add(tabbedPane);

    tabbedPane.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent arg0) {
        getController().execute(new Command(MainController.CMD_CHANGE_TAB, this));

      }

    });
  }

  private void initMenu() {
    menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu fileMenu = new BasicMenu(GestureConstants.FILE_MENU, getController().getLocator().getService(
        GuiBundleService.IDENTIFIER, GuiBundleService.class));
    menuBar.add(fileMenu);

    JMenu helpMenu = new BasicMenu(GestureConstants.HELP_MENU, getController().getLocator().getService(
        GuiBundleService.IDENTIFIER, GuiBundleService.class));
    menuBar.add(helpMenu);

    fileMenu.add(createMenuItem(MainController.CMD_LOAD));
    fileMenu.add(createMenuItem(MainController.CMD_CLOSE_WS));
    fileMenu.addSeparator();
    fileMenu.add(createMenuItem(MainController.CMD_SAVE));
    fileMenu.add(createMenuItem(MainController.CMD_SAVE_AS));
    fileMenu.addSeparator();
    fileMenu.add(createMenuItem(MainController.CMD_EXIT));

    BasicMenuItem aboutItem = new BasicMenuItem();
    aboutItem.setAction(getController().getAction(MainController.CMD_SHOW_ABOUT_DIALOG));
    helpMenu.add(aboutItem);
  }

  /* (non-Javadoc)
   * @see org.ximtec.igesture.tool.view.IMainView#addTab(org.ximtec.igesture.tool.core.TabbedView)
   */
  public void addTab(TabbedView view) {
    tabbedPane.add(view.getTabName(), view.getPane());
  } // addTab

  /* (non-Javadoc)
   * @see org.ximtec.igesture.tool.view.IMainView#removeAllTabs()
   */
  public void removeAllTabs() {
    tabbedPane.removeAll();
  } // removeAllTabs

  /**
   * Creates a menu item. The action is identified with action string and part
   * of the controller.
   * 
   * @param action
   * @return the menu item
   */
  private BasicMenuItem createMenuItem(String action) {
    BasicMenuItem item = new BasicMenuItem();
    item.setAction(getController().getAction(action));
    return item;
  }

  /* (non-Javadoc)
   * @see org.ximtec.igesture.tool.view.IMainView#setTitlePostfix(java.io.File)
   */
  public void setTitlePostfix(File file) {
    if (file == null) {
      setTitle(titlePrefix);
    } else {
      setTitle(titlePrefix + TITLE_DELIMITER + file.getAbsolutePath());
    }

  }

}
