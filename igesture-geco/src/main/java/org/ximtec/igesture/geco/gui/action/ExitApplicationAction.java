/*
 * @(#)$Id$
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
 * Nov 15, 2006     crocimi     Initial Release
 * Jan 20, 2007     bsigner     Cleanup
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


package org.ximtec.igesture.geco.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.util.Constant;
import org.ximtec.igesture.geco.util.GuiBundleTool;
import org.ximtec.igesture.geco.util.SystemTray;


/**
 * Terminates the application.
 * 
 * @version 0.9, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */

public class ExitApplicationAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "ExitApplicationAction";

   private MainView mainView;


   public ExitApplicationAction(MainView mainView) {
      super(KEY, GuiBundleTool.getBundle());
      this.mainView = mainView;
   }


   /**
    * Exits the application.
    * 
    * @param event the event to be handled.
    */
   public void actionPerformed(ActionEvent event) {
      if (mainView.getModel().needSave()) {
         int n = JOptionPane.showConfirmDialog(mainView,
               Constant.SAVE_DIALOG_TITLE, Constant.EMPTY_STRING,
               JOptionPane.YES_NO_CANCEL_OPTION);

         if (n == 0) {
            (new SaveProjectAction(mainView)).save();
            System.exit(0);
         }
         else if (n == 1) {
            System.exit(0);
         }

      }
      else {
         SystemTray.removeTrayIcon();
         System.exit(0);
      }

   } // actionPerformed

}
