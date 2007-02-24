/*
 * @(#)ActionDelGestureSet.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Deletes a gesture set
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


package org.ximtec.igesture.tool.frame.gestureset.action;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.frame.gestureset.GestureTreeModel;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionDelGestureSet extends BaseAction {

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

      }
      else if (event.getSource() instanceof JMenuItem) {
         gestureTreeModel.getModel().removeGestureSet(gestureSet);
      }
   }

}
