/*
 * @(#)ActionTestSetNew.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Creates a new Testset
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


package org.ximtec.igesture.tool.frame.testset.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


/**
 * Comment
 * 
 * @version 1.0 Nov 21, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ActionTestSetNew extends BaseAction {

   public static final String KEY = "ActionTestSetNew";

   private GestureToolView mainView;


   public ActionTestSetNew(GestureToolView mainView) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_NEW));
      this.mainView = mainView;
   }


   public void actionPerformed(ActionEvent arg0) {
      final TestSet testSet = new TestSet(String.valueOf(System
            .currentTimeMillis()));
      mainView.getModel().addTestSet(testSet);
   }

}
