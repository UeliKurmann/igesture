/*
 * @(#)TestTab.java	1.0   Nov 28, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Test Set Tab
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool;

import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.frame.testset.TestSetListFrame;
import org.ximtec.igesture.tool.utils.GuiFactory;


/**
 * Comment
 * 
 * @version 1.0 Nov 28, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class TestTab extends GestureTab {

   private TestSetListFrame testSetListFrame;


   @Override
   public String getName() {
      return GestureConstants.GESTUREVIEW_TAB_TESTCASE;
   }


   @Override
   public void init(GestureToolView mainView) {
      super.init(mainView);

      getDesktopPane().add(GuiFactory.createGestureDrawArea(mainView, 0, 0));
      getDesktopPane().add(GuiFactory.createTestView(this, 0, 280));

      TestSet testSet = null;

      final GestureMainModel mainModel = getMainView().getModel();

      if (mainModel.getTestSets().size() == 0) {
         mainModel.addTestSet(new TestSet("Testset"));
         testSet = mainModel.getTestSets().get(0);
      }
      else {
         testSet = mainModel.getTestSets().get(0);
      }

      testSetListFrame = (TestSetListFrame) GuiFactory.createTestSetList(
            mainView, testSet, 200, 0);
      getDesktopPane().add(testSetListFrame);
   }


   /**
    * Returns the TestSetListFrame
    * 
    * @return
    */
   public TestSetListFrame getTestSetListFrame() {
      return testSetListFrame;
   }

}
