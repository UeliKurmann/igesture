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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.old.frame.gestureset;

import java.awt.GridBagLayout;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicTextField;
import org.sigtec.util.Constant;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.GestureTreeModel;
import org.ximtec.igesture.tool.old.GestureConstants;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.frame.gestureset.action.AddGestureSetAction;
import org.ximtec.igesture.tool.old.util.CloseDialogAction;


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
      super(GestureConstants.ADD_SET_DIALOG_KEY, GestureToolMain.getGuiBundle());
      this.model = model;
      init();
   }


   private void init() {
      setLayout(new GridBagLayout());
      setSize(SwingTool.DIALOG_SIZE);
      addComponent(GuiTool.createLabel(GestureConstants.COMMON_NAME, GestureToolMain
            .getGuiBundle()), SwingTool.createGridBagConstraint(0, 0));
      txtName = GuiTool.createTextField(Constant.EMPTY_STRING, GestureToolMain
            .getGuiBundle());
      addComponent(txtName, SwingTool.createGridBagConstraint(1, 0));
      addComponent(GuiTool.createButton(new AddGestureSetAction(model)),
            SwingTool.createGridBagConstraint(0, 1));
      addComponent(GuiTool.createButton(new CloseDialogAction()), SwingTool
            .createGridBagConstraint(1, 1));
      setVisible(true);
   } // init


   @Override
   public String getName() {
      return txtName.getText();
   } // getName

}
