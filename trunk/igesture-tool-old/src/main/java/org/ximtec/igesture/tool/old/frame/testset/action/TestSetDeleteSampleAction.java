/*
 * @(#)TestSetDeleteSampleAction.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Deletes a sample of the test set.
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


package org.ximtec.igesture.tool.old.frame.testset.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.frame.testset.TestSetListFrame;


/**
 * Deletes a sample of the test set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSetDeleteSampleAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "TestSetDeleteSampleAction";

   private TestSetListFrame frame;


   public TestSetDeleteSampleAction(TestSetListFrame frame) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.frame = frame;
   }


   public void actionPerformed(ActionEvent event) {
      frame.deleteSelectedSample();
   } // actionPerformed

}
