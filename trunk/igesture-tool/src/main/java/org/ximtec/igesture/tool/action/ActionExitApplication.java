/*
 * @(#)ActionExitApplication.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Terminates the application
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


package org.ximtec.igesture.tool.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionExitApplication extends BasicAction {
	
	private GestureToolView mainView;

   public ActionExitApplication(GestureToolView mainView) {
      super(GestureConstants.COMMON_EXIT, SwingTool.getGuiBundle());
      this.mainView = mainView;
   }


   public void actionPerformed(ActionEvent arg0) {
	  mainView.getModel().getStorageManager().dispose();
      System.exit(0);
   }

}
