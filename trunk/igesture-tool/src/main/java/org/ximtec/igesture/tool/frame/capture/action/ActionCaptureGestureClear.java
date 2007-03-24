/*
 * @(#)ActionCaptureGesture.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   
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


package org.ximtec.igesture.tool.frame.capture.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.capture.GestureCaptureArea;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionCaptureGestureClear extends BasicAction {

   private GestureCaptureArea area;


   public ActionCaptureGestureClear(GestureCaptureArea area) {
      super(GestureConstants.COMMON_DEL, SwingTool.getGuiBundle());
      this.area = area;
   }


   public void actionPerformed(ActionEvent event) {
      area.clearCurrentGesture();
   } // actionPerformed

}
