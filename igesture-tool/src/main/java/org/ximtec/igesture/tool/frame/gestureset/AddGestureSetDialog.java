/*
 * @(#)AddGestureSetDialog.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Dialog to create a new gesture set
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.gestureset;

import java.awt.GridBagLayout;

import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicTextField;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionAddGestureSet;
import org.ximtec.igesture.tool.utils.CloseDialogAction;
import org.ximtec.igesture.tool.utils.SwingTool;


public class AddGestureSetDialog extends BasicDialog {

   private BasicTextField txtName;

   private final GestureTreeModel model;


   public AddGestureSetDialog(GestureTreeModel model) {
      super(GestureConstants.ADD_SET_DIALOG_KEY, SwingTool.getGuiBundle());
      this.model = model;
      initialize();
   }


   private void initialize() {
      this.setLayout(new GridBagLayout());
      this.setSize(SwingTool.DIALOG_SIZE);

      addComponent(SwingTool.createLabel(GestureConstants.COMMON_NAME),
            SwingTool.createGridBagConstraint(0, 0));

      txtName = SwingTool.createTextField("");

      addComponent(txtName, SwingTool.createGridBagConstraint(1, 0));

      addComponent(SwingTool.createButton(new ActionAddGestureSet(model)),
            SwingTool.createGridBagConstraint(0, 1));

      addComponent(SwingTool.createButton(new CloseDialogAction(
            GestureConstants.COMMON_CANCEL)), SwingTool.createGridBagConstraint(
            1, 1));

      this.setVisible(true);
   }


   @Override
   public String getName() {
      return txtName.getText();
   }

}
