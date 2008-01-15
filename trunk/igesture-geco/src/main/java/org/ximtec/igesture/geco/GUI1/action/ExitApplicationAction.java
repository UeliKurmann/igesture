/*
 * @(#)ActionExitApplication.java   1.0   Nov 15, 2006
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Terminates the application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2006     crocimi    Initial Release
 * 
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI1.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.GUI1.GecoConstants;
import org.ximtec.igesture.geco.GUI1.GecoMainView;
import org.ximtec.igesture.geco.util.GecoSystemTray;


/**
 * Terminates the application.
 * 
 * @version 0.9, Nov 15, 2006
 * @author  Michele Croci, mcroci@gmail.com
 */

public class ExitApplicationAction extends BasicAction {

   
   private GecoMainView mainView;

   public ExitApplicationAction(GecoMainView mainView) {
      super(GecoConstants.EXIT_APPLICATION_ACTION, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      //display save dialog, if needed
      

      
      if (mainView.getModel().needSave()){
         int n = JOptionPane.showConfirmDialog(
               mainView,
               GecoConstants.SAVE_DIALOG_TITLE,
               "",
               JOptionPane.YES_NO_CANCEL_OPTION);
         if (n==0){
            (new SaveProjectAction(mainView)).save();
            System.exit(0);
         }else if(n==1){
            System.exit(0);
         }
      }else{
         GecoSystemTray.removeTrayIcon();
         System.exit(0);
      }
      
   } // actionPerformed

}
