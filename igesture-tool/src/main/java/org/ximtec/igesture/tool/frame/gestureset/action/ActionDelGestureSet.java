/*
 * @(#)ActionDelGestureSet.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Deletes a gesture set.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
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
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.frame.gestureset.GestureTreeModel;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Deletes a gesture set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionDelGestureSet extends BasicAction {

   public static final String KEY = "PopUpDelGestureSet";

   private GestureTreeModel gestureTreeModel;

   private GestureSet gestureSet;


   public ActionDelGestureSet(GestureTreeModel gestureTreeModel,
         GestureSet gestureSet) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DELETE));
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
