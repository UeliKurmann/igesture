/*
 * @(#)AddgestureClassDialog.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Dialog for selecting gesture classes
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.gestureset;

import java.awt.Component;
import java.awt.GridBagLayout;

import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionAddGestureClass;
import org.ximtec.igesture.tool.utils.CloseDialogAction;
import org.ximtec.igesture.tool.utils.ScrollableList;
import org.ximtec.igesture.tool.utils.SimpleListModel;
import org.ximtec.igesture.tool.utils.SwingTool;


public class AddGestureClassDialog extends BasicDialog {

   private ScrollableList list;

   private final GestureTreeModel model;

   private GestureSet set;


   public AddGestureClassDialog(GestureTreeModel model, GestureSet set) {
      super(GestureConstants.ADD_CLASS_DIALOG_KEY, SwingTool.getGuiBundle());
      this.model = model;
      this.set = set;
      initialize();
   }


   private void initialize() {
      this.setLayout(new GridBagLayout());
      this.setSize(SwingTool.DIALOG_SIZE);

      addComponent(createList(), SwingTool.createGridBagConstraint(0, 0, 2, 1));

      addComponent(
            SwingTool.createButton(new ActionAddGestureClass(model, set)),
            SwingTool.createGridBagConstraint(0, 1));

      addComponent(SwingTool.createButton(new CloseDialogAction(
            GestureConstants.COMMON_CANCEL)), SwingTool.createGridBagConstraint(
            1, 1));

      this.setVisible(true);
   }


   private Component createList() {
      final SimpleListModel<GestureClass> listModel = new SimpleListModel<GestureClass>(
            model.getModel().getGestureClasses());

      list = new ScrollableList(listModel, 180, 200);

      return list;
   }


   public Object[] getSelectedGestureClass() {
      return list.getList().getSelectedValues();
   }

}