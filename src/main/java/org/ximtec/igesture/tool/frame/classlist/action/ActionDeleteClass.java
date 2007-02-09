/*
 * @(#)ActionDeleteClass.java	1.0   Nov 15, 2006
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
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.classlist.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionDeleteClass extends BaseAction {

   private GestureToolView mainView;

   private GestureClass gestureClass;


   public ActionDeleteClass(String key, GestureToolView mainModel,
         GestureClass gestureClass) {
      super(key, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getActionIcon(IconLoader.DELETE));
      this.mainView = mainModel;
      this.gestureClass = gestureClass;
   }


   public void actionPerformed(ActionEvent event) {
      mainView.getModel().removeGestureClass(gestureClass);
   }

}
