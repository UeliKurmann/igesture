/*
 * @(#)ActionDeleteDescriptor.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Deletes a descriptor 
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


package org.ximtec.igesture.tool.frame.classlist.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionDeleteDescriptor extends BaseAction {

   private GestureClass gestureClass;

   private Descriptor descriptor;

   private GestureToolView mainView;


   public ActionDeleteDescriptor(String key, GestureToolView mainView,
         GestureClass gestureClass, Descriptor descriptor) {
      super(key, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getActionIcon(IconLoader.DELETE));
      this.gestureClass = gestureClass;
      this.descriptor = descriptor;
      this.mainView = mainView;
   }


   public void actionPerformed(ActionEvent event) {
      if (descriptor != null) {
         gestureClass.removeDescriptor(descriptor.getType());
         mainView.getModel().updateDataObject(gestureClass);
      }
   }

}
