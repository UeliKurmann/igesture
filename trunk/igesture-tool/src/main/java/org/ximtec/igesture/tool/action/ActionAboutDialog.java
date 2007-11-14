/*
 * @(#)ActionAboutDialog.java	1.0   Nov 15, 2006
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.util.JAboutDialog;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * About dialog.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionAboutDialog extends BasicAction {

   JAboutDialog dialog;


   public ActionAboutDialog(String path) {
      super(GestureConstants.COMMON_ABOUT, SwingTool.getGuiBundle());
      dialog = new JAboutDialog(300, 500, ActionAboutDialog.class
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
