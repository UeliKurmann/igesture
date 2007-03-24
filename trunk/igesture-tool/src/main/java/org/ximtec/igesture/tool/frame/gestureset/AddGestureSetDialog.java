/*
 * @(#)AddGestureSetDialog.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Dialog to create a new gesture set.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.frame.gestureset;

import java.awt.GridBagLayout;

import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicTextField;
import org.sigtec.util.Constant;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.gestureset.action.ActionAddGestureSet;
import org.ximtec.igesture.tool.util.CloseDialogAction;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * Dialog to create a new gesture set.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AddGestureSetDialog extends BasicDialog {

   private BasicTextField txtName;

   private final GestureTreeModel model;


   public AddGestureSetDialog(GestureTreeModel model) {
      super(GestureConstants.ADD_SET_DIALOG_KEY, SwingTool.getGuiBundle());
      this.model = model;
      init();
   }


   private void init() {
      setLayout(new GridBagLayout());
      setSize(SwingTool.DIALOG_SIZE);
      addComponent(SwingTool.createLabel(GestureConstants.COMMON_NAME),
            SwingTool.createGridBagConstraint(0, 0));
      txtName = SwingTool.createTextField(Constant.EMPTY_STRING);
      addComponent(txtName, SwingTool.createGridBagConstraint(1, 0));
      addComponent(SwingTool.createButton(new ActionAddGestureSet(model)),
            SwingTool.createGridBagConstraint(0, 1));
      addComponent(SwingTool.createButton(new CloseDialogAction(
            GestureConstants.COMMON_CANCEL)), SwingTool.createGridBagConstraint(
            1, 1));
      setVisible(true);
   } // init


   @Override
   public String getName() {
      return txtName.getText();
   } // getName

}
