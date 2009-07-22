/*
 * @(#)$Id: AddSampleAction.java 589 2008-10-28 18:55:50Z kurmannu $
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


package org.ximtec.igesture.tool.view.testset.action;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;


public class AddTestClassAction extends TreePathAction {

   public AddTestClassAction(Controller controller, TreePath treePath) {
      super(GestureConstants.TESTSET_ADD_CLASS, controller, treePath);
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      TestSet testSet = (TestSet)getTreePath().getLastPathComponent();
      TestClass testClass = new TestClass("unnamed test class");
      testSet.addTestClass(testClass);
   }

}
