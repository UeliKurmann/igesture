/*
 * @(#)ActionExitApplication.java   1.0   Nov 15, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   Terminates the application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
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


package org.ximtec.igesture.geco.GUI.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.GUI.GestureMappingView;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * Terminates the application.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionExitApplication extends BasicAction {

   private GestureMappingView mainView;


   public ActionExitApplication(GestureMappingView mainView) {
      super(GestureConstants.COMMON_EXIT, SwingTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      System.exit(0);
   } // actionPerformed

}
