/*
 * @(#)ActionAboutDialog.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   About Dialog
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
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

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.JAboutDialog;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionAboutDialog extends BaseAction {

   JAboutDialog dialog;

   public ActionAboutDialog(String path) {
      super(GestureConstants.COMMON_ABOUT, SwingTool.getGuiBundle());
      dialog = new JAboutDialog(300,500,ActionAboutDialog.class.getClassLoader().getResource("about.html")); 
   }


   /**
    * Opens a JFileChooser and reloads the data model
    */
   public void actionPerformed(ActionEvent arg0) {
      dialog.setVisible(true);
   }
}
