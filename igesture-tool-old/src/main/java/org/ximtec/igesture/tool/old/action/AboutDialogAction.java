/*
 * @(#)AboutDialogAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   About dialog.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006		ukurmann	Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.old.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.AboutDialogAction;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.util.JAboutDialog;


/**
 * About dialog.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AboutDialogAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "AboutAction";

   JAboutDialog dialog;


   public AboutDialogAction(String path) {
      super(KEY, GestureToolMain.getGuiBundle());
      dialog = new JAboutDialog(300, 500, AboutDialogAction.class
            .getClassLoader().getResource(path));
   }


   /**
    * Makes the dialogue visible.
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      dialog.setVisible(true);
   } // actionPerformed

}
