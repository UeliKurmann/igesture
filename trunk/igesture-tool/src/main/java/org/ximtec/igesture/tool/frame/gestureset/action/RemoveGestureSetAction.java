/*
 * @(#)RemoveGestureSetAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Removes a gesture set from the main model.
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


package org.ximtec.igesture.tool.frame.gestureset.action;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureToolMain;
import org.ximtec.igesture.tool.frame.gestureset.GestureTreeModel;


/**
 * Removes a gesture set from the main model.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class RemoveGestureSetAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "RemoveGestureSetAction";

   private GestureTreeModel gestureTreeModel;

   private GestureSet gestureSet;


   public RemoveGestureSetAction(GestureTreeModel gestureTreeModel,
         GestureSet gestureSet) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.gestureTreeModel = gestureTreeModel;
      this.gestureSet = gestureSet;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JButton) {
         // FIXME: add something or reduce to simple if statement.
      }
      else if (event.getSource() instanceof JMenuItem) {
         gestureTreeModel.getModel().removeGestureSet(gestureSet);
      }

   } // actionPerformed

}
