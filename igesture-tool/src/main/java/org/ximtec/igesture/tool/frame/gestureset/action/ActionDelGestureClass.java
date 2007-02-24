/*
 * @(#)ActionDelgestureClass.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Deletes a gesture class
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

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.frame.gestureset.GestureTreeModel;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionDelGestureClass extends BasicAction {

   public static final String KEY = "PopUpDelClassFromGestureSet";

   private GestureTreeModel gestureTreeModel;

   private GestureClass gestureClass;

   private GestureSet gestureSet;


   public ActionDelGestureClass(GestureTreeModel gestureTreeModel,
         GestureClass gestureClass, GestureSet gestureSet) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.LIST_REMOVE));
      this.gestureClass = gestureClass;
      this.gestureTreeModel = gestureTreeModel;
      this.gestureSet = gestureSet;
   }


   public void actionPerformed(ActionEvent event) {

      if (event.getSource() instanceof JButton) {

      }
      else if (event.getSource() instanceof JMenuItem) {
         gestureTreeModel.getModel()
               .removeClassFromSet(gestureSet, gestureClass);
      }
   }

}
