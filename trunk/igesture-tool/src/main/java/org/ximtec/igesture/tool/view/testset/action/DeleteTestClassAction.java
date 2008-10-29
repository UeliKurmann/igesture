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

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;


/**
 * Comment
 * @version 1.0 08.10.2008
 * @author Ueli Kurmann
 */
public class DeleteTestClassAction extends BasicAction {

   private TreePath treePath;

   public DeleteTestClassAction(TreePath treePath) {
      super(GestureConstants.TESTSET_REMOVE, Locator.getDefault().getService(
            GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }

   @Override
   public void actionPerformed(ActionEvent evt) {
      TestClass testClass = (TestClass)treePath.getLastPathComponent();
      TestSet testSet = (TestSet)treePath.getParentPath().getLastPathComponent();
      testSet.remove(testClass);
   }
}
