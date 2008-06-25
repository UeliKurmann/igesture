/*
 * @(#)SystemTray.java	1.0   Dec 17, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Adds an icon to the system tray.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 17, 2007		crocimi		Initial Release
 * Jan 14, 2008     bsigner     New icon loading mechanism
 * Jan 20, 2008     bsigner     Update to new GUI bundle functionality
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.util;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.sigtec.graphix.GuiTool;
import org.ximtec.igesture.geco.Geco;
import org.ximtec.igesture.geco.gui.MainView;


/**
 * Adds an icon to the system tray.
 * @version 0.9, Dec 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SystemTray {

   private MainView mainView;
   private static TrayIcon trayIcon;
   private PopupMenu popup;
   private Image image;
   private static java.awt.SystemTray tray;


   public SystemTray(MainView view) {
      this.mainView = view;
      addIconToTray();
   }


   public void addIconToTray() {
      if (java.awt.SystemTray.isSupported()) {
         tray = java.awt.SystemTray.getSystemTray();
         image = GuiBundleTool.getBundle().getSmallIcon(GuiBundleTool.KEY)
               .getImage();
         popup = new PopupMenu();
         trayIcon = new TrayIcon(image, GuiBundleTool.getBundle()
               .getShortDescription(GuiBundleTool.KEY), popup);
         MenuItem exitItem = new MenuItem(Constant.EXIT);
         exitItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               tray.remove(trayIcon);
            }
         });

         exitItem.addActionListener(mainView.getActionHandler()
               .getExitApplicationAction());
         popup.add(exitItem);
         MenuItem showItem = new MenuItem(GuiBundleTool.getBundle().getName(
               Constant.SHOW_APPLICATION_MENU_ITEM));
         showItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               mainView.setVisible(true);
            }
         });

         popup.add(showItem);
         popup.add(exitItem);
         trayIcon.setImageAutoSize(true);
         trayIcon.addMouseListener(new IconMouseListener());

         try {
            tray.add(trayIcon);
         }
         catch (AWTException e) {
            e.printStackTrace();
         }

      }
      else {
         trayIcon = null;
      }

   } // addIconToTray

   private class IconMouseListener implements MouseListener {

      /**
       * Handles an entering of the cursor in the button area.
       * @param event associated mouse event.
       */
      public void mouseEntered(MouseEvent event) {
      } // mouseEntered


      /**
       * Handles a leaving of the cursor from the button area.
       * @param event associated mouse event.
       */
      public void mouseExited(MouseEvent event) {
      } // mouseExited


      /**
       * Handles a clicked button.
       * @param event associated mouse event.
       */
      public void mouseClicked(MouseEvent event) {
         if (event.getClickCount() == 2) {

            if (mainView.isVisible()) {
               mainView.setVisible(false);
            }
            else {
               mainView.setVisible(true);
               mainView.setState(Frame.NORMAL);
               mainView.requestFocus();
               mainView.toFront();
            }

         }

      } // mouseClicked


      /**
       * Handles a pressed button.
       * @param event the associated mouse event.
       */
      public void mousePressed(MouseEvent event) {
      } // mousePressed


      /**
       * Handles a released button.
       * @param event the associated mouse event.
       */
      public void mouseReleased(MouseEvent event) {
      } // mouseReleased

   }


   public static void removeTrayIcon() {
      tray.remove(trayIcon);
   } // removeTrayIcon

}
