/*
 * @(#)$Id: RunBatchAction.java 588 2008-10-25 13:09:33Z kurmannu $
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.batch.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.view.batch.BatchController;


public class CancelBatchAction extends LocateableAction {
   
   private Controller controller;

   public CancelBatchAction(Controller controller) {
      super(GestureConstants.BATCH_CANCEL, controller.getLocator());
      this.controller = controller;

   }


   @Override
   public void actionPerformed(ActionEvent action) {
      Command command = new Command(BatchController.CMD_CANCEL_BATCH);
      controller.execute(command);
      
   }

}
