/*
 * @(#)$Id: ExportPDFGestureSetAction.java 589 2008-10-28 18:55:50Z kurmannu $
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

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.MainModel;


public class CreateTestSetStructureAction extends BasicAction {

   private static final Logger LOG = Logger
         .getLogger(CreateTestSetStructureAction.class.getName());

   private TreePath treePath;


   public CreateTestSetStructureAction(TreePath treePath) {
      super(GestureConstants.GESTURE_SET_TEST_SET, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }


   public void actionPerformed(ActionEvent event) {
      LOG.info("Create Test Set structure.");
      GestureSet gestureSet = (GestureSet)treePath.getLastPathComponent();

      
      TestSet testSet = new TestSet(gestureSet.getName());
      for(GestureClass gestureClass:gestureSet.getGestureClasses()){
         testSet.addTestClass(new TestClass(gestureClass.getName()));
      }
      
      MainModel mainModel = Locator.getDefault().getService(MainModel.IDENTIFIER, MainModel.class);
      mainModel.getTestSetList().addTestSet(testSet);
      

   } // actionPerformed

}
