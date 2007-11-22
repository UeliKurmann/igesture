/*
 * @(#)CloseDialogAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Action to close a dialog.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006		ukurmann	Initial Release
 * Feb 26, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;


/**
 * Action to close a dialogue.
 * 
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class CloseDialogAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "CloseDialogAction";


   public CloseDialogAction() {
      super(KEY, GuiTool.getGuiBundle());
   }


   public void actionPerformed(ActionEvent event) {
      Component component = (Component)event.getSource();

      while (!(component instanceof JDialog)) {
         component = component.getParent();
      }

      component.setVisible(false);
   } // actionPerformed

}
