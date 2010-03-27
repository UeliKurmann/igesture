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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testset;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.EdtProxy;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.util.NodeInfoFactory;
import org.ximtec.igesture.tool.view.MainModel;
import org.ximtec.igesture.tool.view.devicemanager.IDeviceManager;


public class TestSetController extends DefaultController {

   private static final Logger LOGGER = Logger.getLogger(TestSetController.class
         .getName());

   private ITestSetView testSetView;

   private MainModel mainModel; 

   private ExplorerTreeController explorerTreeController;

   // Device Manager
   private IDeviceManager deviceManagerController;

   public TestSetController(Controller parentController, IDeviceManager deviceManager) {
	   super(parentController);
	  mainModel = getLocator().getService(
		         MainModel.IDENTIFIER, MainModel.class);
	  
	  deviceManagerController = deviceManager;
	  
      initController();
   }


   private void initController() {
      testSetView = EdtProxy.newInstance(new TestSetView(this), ITestSetView.class);

      ExplorerTreeModel explorerModel = new ExplorerTreeModel(mainModel
            .getTestSetList(), NodeInfoFactory.createTestSetNodeInfo(this));
      explorerTreeController = new ExplorerTreeController(this, testSetView,
            explorerModel);
      
      addController(explorerTreeController);
   }


   @Override
   public TabbedView getView() {
      return testSetView;
   }


   @Override
   public void propertyChange(PropertyChangeEvent evt) {
      LOGGER.info("PropertyChange");
      super.propertyChange(evt);
      
      explorerTreeController.getExplorerTreeView().refresh();
   }
}
