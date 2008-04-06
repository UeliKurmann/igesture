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

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.MainModel;

public class StoreWorkspaceAction extends BasicAction {

   
   public StoreWorkspaceAction() {
      super(GestureConstants.COMMON_SAVE, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));

   }
   
   public StoreWorkspaceAction(TreePath treePath) {
      super(GestureConstants.GESTURE_CLASS_ADD, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
      
   }


   @Override
   public void actionPerformed(ActionEvent event) {
      MainModel model = Locator.getDefault().getService(MainModel.IDENTIFIER, MainModel.class);
      model.getStorageManager().commit();
      
   }
}
