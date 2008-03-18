/*
 * @(#)AddGestureSetAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Adds a gesture set.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.old.frame.gestureset.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.frame.gestureset.AddGestureSetDialog;
import org.ximtec.igesture.tool.old.frame.gestureset.GestureTreeModel;


/**
 * Adds a gesture set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AddGestureSetAction extends BasicAction {

   public static final String KEY = "AddGestureSetAction";

   private GestureTreeModel gestureTreeModel;


   public AddGestureSetAction(GestureTreeModel gestureTreeModel) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.gestureTreeModel = gestureTreeModel;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JButton) {
         gestureTreeModel.addGestureSet(new GestureSet(getDialog(
               (JButton)event.getSource()).getName()));
         getDialog((JButton)event.getSource()).setVisible(false);
      }
      else {
         new AddGestureSetDialog(gestureTreeModel);
      }

   } // actionPerformed


   /**
    * Goes up in the tree and returns a AddGestureSetDialog instance.
    * 
    * @return the AddGestureSetDialog instance.
    */
   private AddGestureSetDialog getDialog(Component comp) {
      while (!(comp instanceof AddGestureSetDialog)) {
         comp = comp.getParent();
      }

      return (AddGestureSetDialog)comp;
   } // getDialog

}
