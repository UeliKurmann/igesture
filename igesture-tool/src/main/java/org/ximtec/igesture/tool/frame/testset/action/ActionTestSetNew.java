/*
 * @(#)ActionTestSetNew.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Creates a new test set.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.testset.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Creates a new test set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionTestSetNew extends BasicAction {

   public static final String KEY = "ActionTestSetNew";

   private GestureToolView mainView;


   public ActionTestSetNew(GestureToolView mainView) {
      super(KEY, GuiTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_NEW));
      this.mainView = mainView;
   }


   public void actionPerformed(ActionEvent arg0) {
      final TestSet testSet = new TestSet(String.valueOf(System
            .currentTimeMillis()));
      mainView.getModel().addTestSet(testSet);
   } // actionPerformed

}
