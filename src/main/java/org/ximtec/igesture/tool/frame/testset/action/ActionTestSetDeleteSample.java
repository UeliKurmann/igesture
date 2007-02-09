/*
 * @(#)ActionTestSetDeleteSample.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Deletes a sample of the test set
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

import org.ximtec.igesture.tool.frame.testset.TestSetListFrame;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


/**
 * Comment
 * 
 * @version 1.0 Nov 21, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ActionTestSetDeleteSample extends BaseAction {

   public static final String KEY = "ActionTestSetDeleteSample";

   private TestSetListFrame frame;


   public ActionTestSetDeleteSample(TestSetListFrame frame) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DELETE));
      this.frame = frame;
   }


   public void actionPerformed(ActionEvent event) {
      frame.deleteSelectedSample();
   }

}
