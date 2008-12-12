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


package org.ximtec.igesture.tool.view.admin;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.util.NodeInfoFactory;
import org.ximtec.igesture.tool.view.MainModel;


public class AdminController extends DefaultController {

   private static final Logger LOGGER = Logger.getLogger(AdminController.class
         .getName());

   /**
    * the view of this controller
    */
   private AdminView adminView;

   /**
    * Reference to the main model.
    */
   private MainModel mainModel = Locator.getDefault().getService(
         MainModel.IDENTIFIER, MainModel.class);

   /**
    * Explorer Tree Controller
    */
   private ExplorerTreeController explorerTreeController;


   public AdminController() {
      initController();
   }


   /**
    * Initializes the controller.
    */
   private void initController() {
      adminView = new AdminView();

      ExplorerTreeModel explorerModel = new ExplorerTreeModel(mainModel
            .getGestureSetList(), NodeInfoFactory.createAdminNodeInfo());
      explorerTreeController = new ExplorerTreeController(adminView,
            explorerModel);

      addController(explorerTreeController);
   }


   @Override
   public TabbedView getView() {
      return adminView;
   }


   @Override
   public void propertyChange(PropertyChangeEvent evt) {
      LOGGER.info("PropertyChange");
      super.propertyChange(evt);
 
      explorerTreeController.getExplorerTreeView().refresh();
   }
}
