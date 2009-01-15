/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 23.03.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Command;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.testbench.TestbenchController;


/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class RecogniseAction extends BasicAction {

   private Configuration configuration;
   private Controller controller;


   public RecogniseAction(Controller controller, Configuration configuration) {
      super(GestureConstants.RECONGISE, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.configuration = configuration;
      this.controller = controller;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      
      controller.execute(new Command(TestbenchController.CMD_RECOGNIZE,
            configuration));
   }

}
