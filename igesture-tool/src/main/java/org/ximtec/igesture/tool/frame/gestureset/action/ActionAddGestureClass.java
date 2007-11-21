/*
 * @(#)ActionAddGestureClass.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Adds a gesture class.
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

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.frame.gestureset.AddGestureClassDialog;
import org.ximtec.igesture.tool.frame.gestureset.GestureTreeModel;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Adds a gesture class.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionAddGestureClass extends BasicAction {

   public static final String KEY = "PopUpAddClassToGestureSet";

   private GestureTreeModel gestureTreeModel;

   private GestureSet set;


   public ActionAddGestureClass(GestureTreeModel gestureTreeModel, GestureSet set) {
      super(KEY, GuiTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.LIST_ADD));
      this.set = set;
      this.gestureTreeModel = gestureTreeModel;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JButton) {

         for (final Object gestureClass : getDialog((JButton)event.getSource())
               .getSelectedGestureClass()) {
            gestureTreeModel.getModel().addClassToSet(set,
                  (GestureClass)gestureClass);
         }

      }
      else if (event.getSource() instanceof JMenuItem) {
         new AddGestureClassDialog(gestureTreeModel, set);
      }

   } // actionPerformed


   /**
    * Climbs up the tree and returns an AddGestureSetDialog instance.
    * 
    * @return the AddGestureSetDialog instance
    */
   private AddGestureClassDialog getDialog(Component comp) {
      while (!(comp instanceof AddGestureClassDialog)) {
         comp = comp.getParent();
      }

      return (AddGestureClassDialog)comp;
   } // getDialog

}
