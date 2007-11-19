/*
 * @(#)ActionTestSetOpen.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Opens a test set.
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

import javax.swing.JList;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.frame.testset.TestSetListFrame;
import org.ximtec.igesture.tool.frame.testset.TestSetListModel;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Opens a test set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionTestSetOpen extends BasicAction {

   public static final String KEY = "ActionTestSetOpen";

   private JList list;

   private TestSetListFrame frame;


   public ActionTestSetOpen(JList list, TestSetListFrame frame) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_OPEN));
      this.list = list;
      this.frame = frame;
   }


   public void actionPerformed(ActionEvent event) {
      final String name = (String)list.getSelectedValue();
      final TestSet testSet = ((TestSetListModel)list.getModel())
            .getTestSet(name);
      frame.loadGestureListData(testSet);
   } // actionPerformed

}
