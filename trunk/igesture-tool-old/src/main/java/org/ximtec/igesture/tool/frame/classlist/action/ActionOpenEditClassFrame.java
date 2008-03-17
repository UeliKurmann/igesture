/*
 * @(#)ActionOpenEditClassFrame.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Opens the gesture class detail view.
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


package org.ximtec.igesture.tool.frame.classlist.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.tool.AdminTab;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolMain;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Opens the gesture class detail view.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionOpenEditClassFrame extends BasicAction {

   private GestureClass gestureClass;

   private AdminTab adminTab;


   public ActionOpenEditClassFrame(String key, AdminTab adminTab,
         GestureClass gestureClass) {
      super(key, GestureToolMain.getGuiBundle());

      if (GestureConstants.GESTURE_CLASS_LIST_FRAME_BTN_ADD.equals(key)) {
         putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_NEW));
      }
      else {
         putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_OPEN));
      }

      this.gestureClass = gestureClass;
      this.adminTab = adminTab;
   }


   public void actionPerformed(ActionEvent arg0) {
      if (gestureClass == null) {
         gestureClass = new GestureClass(Constant.EMPTY_STRING);
      }

      adminTab.addClassView(gestureClass);
   } // actionPerformed

}
