/*
 * @(#)TextDescriptorFrame.java	1.0   Dec 20, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Shows a text descriptor.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 04, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.frame.classlist;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextField;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.frame.classlist.action.SaveTextAction;
import org.ximtec.igesture.tool.util.CloseFrameAction;


/**
 * Shows a text descriptor.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TextDescriptorFrame extends BasicInternalFrame {

   private TextDescriptor textDescriptor;

   private GestureToolView mainView;

   private TextField textField;


   public TextDescriptorFrame(GestureToolView mainView,
         TextDescriptor textDescriptor) {
      super(GestureConstants.TEXT_DESCRIPTOR_KEY, GuiTool.getGuiBundle());
      this.textDescriptor = textDescriptor;
      this.mainView = mainView;
      SwingTool.initFrame(this);
      init();
   }


   private void init() {
      addComponent(createTextField(), SwingTool.createGridBagConstraint(0, 0, 2,
            1));
      addComponent(createSaveButton(), SwingTool.createGridBagConstraint(0, 1));
      addComponent(createCloseButton(), SwingTool.createGridBagConstraint(1, 1));
   } // init


   private Component createTextField() {
      textField = new TextField();
      textField.setSize(180, 360);
      textField.setPreferredSize(new Dimension(180, 260));
      textField.setText(textDescriptor.getText());
      return textField;
   } // createTextField


   private Component createSaveButton() {
      return SwingTool.createButton(new SaveTextAction(this));
   } // createSaveButton


   private Component createCloseButton() {
      return SwingTool.createButton(new CloseFrameAction(
            GestureConstants.COMMON_CLOSE));
   } // createCloseButton


   public void saveTextDescription() {
      textDescriptor.setText(textField.getText());
      mainView.getModel().updateDataObject(textDescriptor);
   } // saveTextDescription

}
