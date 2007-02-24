/*
 * @(#)ActionCaptureGesture.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   DataModel Configuration Listener
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


package org.ximtec.igesture.tool.frame.capture.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.capture.GestureCaptureArea;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionCaptureGestureClear extends BaseAction {

   private GestureCaptureArea area;


   public ActionCaptureGestureClear(GestureCaptureArea area) {
      super(GestureConstants.COMMON_DEL, SwingTool.getGuiBundle());
      this.area = area;
   }


   public void actionPerformed(ActionEvent event) {
      area.clearCurrentGesture();
   }
}
