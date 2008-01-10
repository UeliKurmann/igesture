/*
 * @(#)GecoSystemTray.java	1.0   Dec 17, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Add an icon to the system tray
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 17, 2007		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URISyntaxException;

import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainModel;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.GUI.action.GecoActionHandler;



/**
 * Add an icon to the system tray
 * @version 1.0 Dec 17, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class GecoSystemTray {
   
   private GecoMainView mainView;
   private static TrayIcon trayIcon;
   private PopupMenu popup;
   private Image image;
   private static SystemTray tray;


   
   
   public GecoSystemTray(GecoMainView view){
      this.mainView = view;
      addIconToTray();
   }
      
   public void addIconToTray(){

      if(SystemTray.isSupported()){
         tray = SystemTray.getSystemTray();
         
         
         String icon;
         try {
            icon = ClassLoader.getSystemResource(GecoConstants.ICON_NAME).toURI().getPath();
         } catch(URISyntaxException ex) {
            icon = ClassLoader.getSystemResource(GecoConstants.ICON_NAME).getPath();
         }
         
         //image = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(GecoConstants.ICON_NAME).getFile());
         image = Toolkit.getDefaultToolkit().getImage(icon);

         popup = new PopupMenu();
         trayIcon = new TrayIcon(image, GecoConstants.GECO_NAME, popup);
         
         MenuItem exitItem = new MenuItem(GecoConstants.EXIT);
         exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               tray.remove(trayIcon);
            }
            });
         exitItem.addActionListener(mainView.getActionHandler().getExitApplicationAction());
         popup.add(exitItem);
         
         MenuItem showItem = new MenuItem(GecoConstants.SHOW);
         showItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               mainView.setVisible(true);
            }
            });
        
         popup.add(showItem);
         popup.add(exitItem);
         
        
         trayIcon.setImageAutoSize(true);
         
         trayIcon.addMouseListener(new IconMouseListener());
         
         try{
            tray.add(trayIcon);
         }catch(AWTException e){
            e.printStackTrace();
         }

      }
      else{
         trayIcon=null;
      }
      
   }
   
   private class IconMouseListener implements MouseListener{
      
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
         if(event.getClickCount() == 2){
            if( mainView.isVisible()){
               mainView.setVisible(false);
            }else{
               mainView.setVisible(true);
            }
         
         }else{
            //try{
             //  Robot robot = new Robot();
             //  robot.mousePress(InputEvent.BUTTON3_MASK);
             //  robot.mouseRelease(InputEvent.BUTTON3_MASK);
           // mainView.getRootPane().setVisible(true);

             //  }catch(AWTException e){
             //     e.printStackTrace();
             //  }
         }
           // mainView.setVisible(true);
      }


      /**
       * Handles a pressed button.
       * @param event associated mouse event.
       */
      public void mousePressed(MouseEvent event) {
      }


      /**
       * Handles a released button.
       * @param event associated mouse event.
       */
      public void mouseReleased(MouseEvent event) {
      }
      
   }
   
   public static void removeTrayIcon(){
      tray.remove(trayIcon);
   }
   


   
}

