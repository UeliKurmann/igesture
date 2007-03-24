/*
 * @(#)GestureToolView.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Main application view.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann	Initial Release
 * Mar 24, 2007     bsigner     Cleanup
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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.sigtec.util.Constant;
import org.sigtec.util.Decorator;
import org.sigtec.util.IconTool;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.tool.action.ActionAboutDialog;
import org.ximtec.igesture.tool.action.ActionExitApplication;
import org.ximtec.igesture.tool.action.ActionNewDataSouce;
import org.ximtec.igesture.tool.action.ActionOpenDataSouce;
import org.ximtec.igesture.tool.util.IconLoader;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * Main application view.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureToolView extends JFrame {

   private static final Logger LOGGER = Logger.getLogger(GestureToolView.class
         .getName());

   private static final String ABOUT_HTML = "about.html";

   private static final String GUI_STYLE = "org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel";

   private static final String IGESTURE_ICON = "icons/iserver";

   private GestureConfiguration configuration;

   private GestureMainModel model;

   private JTabbedPane tabbedPane = null;


   /**
    * Constructs a new main view.
    * 
    * @param model the model for this main view.
    */
   public GestureToolView(GestureMainModel model,
         GestureConfiguration configuration) {
      super();
      this.configuration = configuration;
      this.model = model;
      initialise();
   }


   /**
    * Initialises the main view.
    */
   private void initialise() {
      try {
         UIManager.setLookAndFeel(GUI_STYLE);
      }
      catch (Exception e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      setIconImage(IconTool.getIcon(IGESTURE_ICON, Decorator.SIZE_16).getImage());
      setBounds(0, 0, 900, 650);
      setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
      setJMenuBar(createMenuBar());
      setTitle(SwingTool.getGuiBundle().getName(
            GestureConstants.GESTUREVIEW_MAINFRAME_KEY));
      setVisible(true);
      tabbedPane = new JTabbedPane();
      setContentPane(tabbedPane);
      loadTabs();
      this.repaint();
   } // initialise


   /**
    * Initialises the tabs.
    */
   private void loadTabs() {
      for (final String className : configuration.getTabs()) {

         try {
            final GestureTab tab = (GestureTab)Class.forName(className)
                  .newInstance();
            tab.init(this);
            tabbedPane.add(SwingTool.getGuiBundle().getName(tab.getName()), tab
                  .getDesktopPane());
         }
         catch (InstantiationException e) {
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }
         catch (IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }
         catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }

      }

   } // loadTabs


   /**
    * Reloads the main model with a new data source.
    * 
    * @param engine the storage engine for reloading the new data source.
    */
   public void reloadModelData(StorageEngine engine) {
      this.model.loadData(engine);
   } // reloadModelData


   /**
    * Creates the menu bar.
    * 
    * @return the newly created menu bar.
    */
   private JMenuBar createMenuBar() {
      final JMenuBar menuBar = new JMenuBar();
      menuBar.add(createFileMenu());
      menuBar.add(createInfoMenu());
      return menuBar;
   } // createMenuBar


   private JMenu createFileMenu() {
      final JMenu menu = SwingTool.createMenu(SwingTool.getGuiBundle().getName(
            GestureConstants.COMMON_FILE), null);
      menu.add(SwingTool.createMenuItem(new ActionNewDataSouce(this), IconLoader
            .getIcon(IconLoader.DOCUMENT_NEW)));
      menu.add(SwingTool.createMenuItem(new ActionOpenDataSouce(this),
            IconLoader.getIcon(IconLoader.DOCUMENT_OPEN)));
      menu.addSeparator();
      menu.add(SwingTool.createMenuItem(new ActionExitApplication(this)));
      return menu;
   } // createFileMenu


   /**
    * Creates the info model.
    * 
    * @return the newly created info model.
    */
   private JMenu createInfoMenu() {
      final JMenu menu = SwingTool.createMenu(SwingTool.getGuiBundle().getName(
            GestureConstants.COMMON_HELP), null);
      menu.add(SwingTool.createMenuItem(new ActionAboutDialog(ABOUT_HTML)));
      return menu;
   } // createInfoMenu


   /**
    * Returns the main model.
    * 
    * @return the main model.
    */
   public GestureMainModel getModel() {
      return model;
   } // getModel

}
