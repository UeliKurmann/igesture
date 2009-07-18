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


package org.ximtec.igesture.tool.view.testset;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.util.NodeInfoFactory;
import org.ximtec.igesture.tool.view.MainModel;


public class TestSetController extends DefaultController {

   private static final Logger LOGGER = Logger.getLogger(TestSetController.class
         .getName());

   private TestSetView testSetView;

   private MainModel mainModel; 

   private ExplorerTreeController explorerTreeController;


   public TestSetController(Controller parentController) {
	   super(parentController);
	  mainModel = getLocator().getService(
		         MainModel.IDENTIFIER, MainModel.class);
      initController();
   }


   private void initController() {
      testSetView = new TestSetView(this);

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
