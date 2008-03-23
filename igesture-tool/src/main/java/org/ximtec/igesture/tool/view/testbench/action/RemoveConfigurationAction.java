/*
 * @(#)$Id:$
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

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class RemoveConfigurationAction extends BasicAction {
   
   private TreePath treePath;


   public RemoveConfigurationAction(TreePath treePath) {
      super(GestureConstants.CONFIGURATION_DEL, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }

   @Override
   public void actionPerformed(ActionEvent arg0) {
      Configuration configuration = (Configuration)treePath.getLastPathComponent();
      AlgorithmWrapper algorithWrapper = (AlgorithmWrapper)treePath.getParentPath().getLastPathComponent();
      
     algorithWrapper.removeConfiguration(configuration);

   }

}
