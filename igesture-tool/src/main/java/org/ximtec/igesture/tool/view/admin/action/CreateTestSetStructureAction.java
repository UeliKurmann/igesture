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

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.view.MainModel;


public class CreateTestSetStructureAction extends TreePathAction {

   private static final Logger LOG = Logger
         .getLogger(CreateTestSetStructureAction.class.getName());


   public CreateTestSetStructureAction(Controller controller, TreePath treePath) {
      super(GestureConstants.GESTURE_SET_TEST_SET, controller, treePath);
   }


   public void actionPerformed(ActionEvent event) {
      LOG.info("Create Test Set structure.");
      GestureSet gestureSet = (GestureSet)getTreePath().getLastPathComponent();

      
      TestSet testSet = new TestSet(gestureSet.getName());
      for(GestureClass gestureClass:gestureSet.getGestureClasses()){
         testSet.addTestClass(new TestClass(gestureClass.getName()));
      }
      
      MainModel mainModel = getLocator().getService(MainModel.IDENTIFIER, MainModel.class);
      mainModel.getTestSetList().addTestSet(testSet);
      

   } // actionPerformed

}
