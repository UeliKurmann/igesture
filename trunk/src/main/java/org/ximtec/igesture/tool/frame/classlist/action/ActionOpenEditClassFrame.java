/*
 * @(#)ActionOpenEditClassFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Opens the gesture class detail view
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
import org.ximtec.igesture.tool.AdminTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionOpenEditClassFrame extends BaseAction {

   private GestureClass gestureClass;

   private AdminTab adminTab;


   public ActionOpenEditClassFrame(String key, AdminTab adminTab,
         GestureClass gestureClass) {
      super(key, SwingTool.getGuiBundle());
      if(GestureConstants.GESTURE_CLASS_LIST_FRAME_BTN_ADD.equals(key)){
    	  putValue(SMALL_ICON, IconLoader.getActionIcon(IconLoader.DOCUMENT_NEW));
      }else{
    	  putValue(SMALL_ICON, IconLoader.getActionIcon(IconLoader.DOCUMENT_OPEN));
      }
      this.gestureClass = gestureClass;
      this.adminTab = adminTab;
   }


   public void actionPerformed(ActionEvent arg0) {
      if (gestureClass == null) {
         gestureClass = new GestureClass("");
      }
      adminTab.addClassView(gestureClass);
   }

}
