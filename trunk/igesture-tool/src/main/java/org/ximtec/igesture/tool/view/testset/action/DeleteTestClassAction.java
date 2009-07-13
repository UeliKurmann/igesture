/*
 * @(#)$Id: DeleteTestSetAction.java 588 2008-10-25 13:09:33Z kurmannu $
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
 * 08.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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


/**
 * Comment
 * @version 1.0 08.10.2008
 * @author Ueli Kurmann
 */
public class DeleteTestClassAction extends TreePathAction {


   public DeleteTestClassAction(Controller controller, TreePath treePath) {
      super(GestureConstants.TESTSET_REMOVE, controller, treePath);
   }

   @Override
   public void actionPerformed(ActionEvent evt) {
      TestClass testClass = (TestClass)getTreePath().getLastPathComponent();
      TestSet testSet = (TestSet)getTreePath().getParentPath().getLastPathComponent();
      testSet.remove(testClass);
   }
}
