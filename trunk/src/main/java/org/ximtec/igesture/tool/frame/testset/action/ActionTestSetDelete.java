/*
 * @(#)ActionTestSetDelete.java	1.0   Nov 21, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Deletes a test set
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

import javax.swing.JList;

import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.frame.testset.TestSetListModel;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


/**
 * Comment
 * 
 * @version 1.0 Nov 21, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ActionTestSetDelete extends BaseAction {

   public static final String KEY = "ActionTestSetDelete";

   private JList list;

   private GestureToolView mainView;


   public ActionTestSetDelete(JList list, GestureToolView mainView) {
      super(KEY, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DELETE));
      this.list = list;
      this.mainView = mainView;
   }


   public void actionPerformed(ActionEvent arg0) {
      final String name = (String) list.getSelectedValue();
      final TestSet testSet = ((TestSetListModel) list.getModel())
            .getTestSet(name);
      mainView.getModel().removeTestSet(testSet);
   }

}
