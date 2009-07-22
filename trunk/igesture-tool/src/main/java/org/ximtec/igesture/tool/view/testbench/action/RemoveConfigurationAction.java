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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class RemoveConfigurationAction extends TreePathAction {
   
    public RemoveConfigurationAction(Controller controller, TreePath treePath) {
      super(GestureConstants.CONFIGURATION_DEL, controller, treePath);
   }

   @Override
   public void actionPerformed(ActionEvent arg0) {
      Configuration configuration = (Configuration)getTreePath().getLastPathComponent();
      AlgorithmWrapper algorithWrapper = (AlgorithmWrapper)getTreePath().getParentPath().getLastPathComponent();
      
     algorithWrapper.removeConfiguration(configuration);

   }

}
