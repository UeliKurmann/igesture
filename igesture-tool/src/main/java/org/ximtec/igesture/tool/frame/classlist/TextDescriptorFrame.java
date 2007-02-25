/*
 * @(#)TextDescriptorFrame.java	1.0   Dec 20, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Shows a TextDescriptor
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 4.12.2006		ukurmann	Initial Release
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

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.frame.classlist.action.ActionSaveText;
import org.ximtec.igesture.tool.util.CloseFrameAction;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * Comment
 * 
 * @version 1.0 Dec 20, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class TextDescriptorFrame extends BasicInternalFrame {

   private TextDescriptor textDescriptor;

   private GestureToolView mainView;

   private TextField textField;


   public TextDescriptorFrame(GestureToolView mainView,
         TextDescriptor textDescriptor) {
      super(GestureConstants.TEXT_DESCRIPTOR_KEY, SwingTool.getGuiBundle());
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
   }


   private Component createTextField() {
      textField = new TextField();
      textField.setSize(180, 360);
      textField.setPreferredSize(new Dimension(180, 260));
      textField.setText(textDescriptor.getText());

      return textField;
   }


   private Component createSaveButton() {
      return SwingTool.createButton(new ActionSaveText(this));
   }


   private Component createCloseButton() {
      return SwingTool.createButton(new CloseFrameAction(
            GestureConstants.COMMON_CLOSE));
   }


   public void saveTextDescription() {
      textDescriptor.setText(textField.getText());
      mainView.getModel().updateDataObject(textDescriptor);
   }

}
