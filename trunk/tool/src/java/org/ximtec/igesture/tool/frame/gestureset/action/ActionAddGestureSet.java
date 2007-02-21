/*
 * @(#)ActionAddGestureSet.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Adds a gesture set
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


package org.ximtec.igesture.tool.frame.gestureset.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.frame.gestureset.AddGestureSetDialog;
import org.ximtec.igesture.tool.frame.gestureset.GestureTreeModel;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionAddGestureSet extends BaseAction {

   public static final String KEY = "PopUpAddGestureSet";

   private GestureTreeModel gestureTreeModel;


   public ActionAddGestureSet(GestureTreeModel gestureTreeModel) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_NEW));
      this.gestureTreeModel = gestureTreeModel;
   }
   
  


   public void actionPerformed(ActionEvent event) {

      if (event.getSource() instanceof JButton) {
         /**
          * instantiate new gesture set and add it to the mdoel
          */
         gestureTreeModel.addGestureSet(new GestureSet(getDialog(
               (JButton) event.getSource()).getName()));

         /**
          * close the dialog
          */
         getDialog((JButton) event.getSource()).setVisible(false);
      }
      else {
         new AddGestureSetDialog(gestureTreeModel);
      }
   }


   /**
    * climp up the tree and returns AddGestureSetDialog instance
    * 
    * @param comp
    * @return
    */
   private AddGestureSetDialog getDialog(Component comp) {
      while (!(comp instanceof AddGestureSetDialog)) {
         comp = comp.getParent();
      }
      return (AddGestureSetDialog) comp;
   }
}
