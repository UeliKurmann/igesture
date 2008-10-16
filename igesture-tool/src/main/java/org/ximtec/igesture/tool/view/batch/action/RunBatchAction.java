/*
 * @(#)$Id: AddGestureSampleAction.java 526 2008-05-04 18:01:18Z kurmannu $
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


package org.ximtec.igesture.tool.view.batch.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.batch.BatchController;


public class RunBatchAction extends BasicAction {
   
   private Controller controller;

   public RunBatchAction(Controller controller) {
      super(GestureConstants.BATCH_RUN, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      
      this.controller = controller;

   }


   @Override
   public void actionPerformed(ActionEvent action) {
      Command command = new Command(BatchController.CMD_RUN_BATCH);
      controller.execute(command);
      
   }

}
