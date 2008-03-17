/*
 * @(#)TestSetAddAction.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Adds a gesture to the test set.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 21, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.testset.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureToolMain;
import org.ximtec.igesture.tool.TestTab;
import org.ximtec.igesture.tool.frame.testset.TestSetFrame;


/**
 * Adds a gesture to the test set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSetAddAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "TestSetAddAction";

   private TestSetFrame testSetFrame;

   private TestTab testTab;


   public TestSetAddAction(TestTab testTab, TestSetFrame testSetFrame) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.testTab = testTab;
      this.testSetFrame = testSetFrame;
   }


   public void actionPerformed(ActionEvent event) {
      final TestSet testSet = testTab.getTestSetListFrame().getTestSet();
      testSet.add(testSetFrame.getGestureSample());
      testTab.getMainView().getModel().updateDataObject(testSet);
   } // actionPerformed

}
