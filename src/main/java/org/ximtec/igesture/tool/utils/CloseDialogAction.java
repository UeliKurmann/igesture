/*
 * @(#)CloseDialogAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Closes a dialog
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
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.utils;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;


/**
 * Action to close a JDialog
 * 
 * @version 1.0 09.06.2006
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class CloseDialogAction extends BaseAction {

   public CloseDialogAction(String key) {
      super(key, SwingTool.getGuiBundle());
   }


   public void actionPerformed(ActionEvent e) {
      Component comp = (Component) e.getSource();
      while (!(comp instanceof JDialog)) {
         comp = comp.getParent();
      }
      comp.setVisible(false);
   }

}
