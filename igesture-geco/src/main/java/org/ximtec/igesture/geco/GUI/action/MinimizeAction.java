/*
 * @(#)ActionExitApplication.java   1.0   Nov 15, 2006
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Minimizes the application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2006     crocimi    Initial Release
 * 
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;


/**
 * Minimizes the application.
 * 
 * @version 1.0, Nov 15, 2006
 * @author  Michele Croci, mcroci@gmail.com
 */

public class MinimizeAction extends BasicAction {

   
   private GecoMainView mainView;

   public MinimizeAction(GecoMainView mainView) {
      super(GecoConstants.MINIMIZE_ACTION, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      mainView.setVisible(false);
   } // actionPerformed

}
