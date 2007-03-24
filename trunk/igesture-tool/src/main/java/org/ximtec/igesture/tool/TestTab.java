/*
 * @(#)TestTab.java	1.0   Nov 28, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Test set tab.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 28, 2006		ukurmann	Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool;

import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.frame.testset.TestSetListFrame;
import org.ximtec.igesture.tool.util.GuiFactory;


/**
 * Test set tab.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
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

      testSetListFrame = (TestSetListFrame)GuiFactory.createTestSetList(
            mainView, testSet, 200, 0);
      getDesktopPane().add(testSetListFrame);
   } // init


   /**
    * Returns the TestSetListFrame.
    * 
    * @return the TestSetListFrame.
    */
   public TestSetListFrame getTestSetListFrame() {
      return testSetListFrame;
   } // getTestSetListFrame

}
