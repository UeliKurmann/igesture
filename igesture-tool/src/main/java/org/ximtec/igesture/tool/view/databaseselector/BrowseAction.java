/*
 * @(#)$Id: StoreWorkspaceAction.java 465 2008-03-23 23:14:59Z kurmannu $
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.databaseselector;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JTextField;


public class BrowseAction extends AbstractAction {

   JTextField field;
   
   public BrowseAction(JTextField field) {
      this.field = field;
   }


   @Override
   public void actionPerformed(ActionEvent event) {
      JFileChooser fc = new JFileChooser();
      fc.showOpenDialog(null);
      String name = fc.getSelectedFile().getAbsolutePath();
      field.setText(name);
   }
}
