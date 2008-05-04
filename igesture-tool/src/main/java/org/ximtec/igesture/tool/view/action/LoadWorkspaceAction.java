/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.MainController;


public class LoadWorkspaceAction extends BasicAction {

   public LoadWorkspaceAction(MainController mainController) {
      super(GestureConstants.APPLICATION_BROWSE, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      MainController mainController = Locator.getDefault().getService(
            MainController.IDENTIFIER, MainController.class);
      mainController.execute(new Command(MainController.CMD_LOAD, this));
   }

}
