/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.binding;

import javax.swing.JTextField;

import org.ximtec.igesture.core.DataObject;


public class TextFieldBinding extends DataBinding<JTextField> {

   private JTextField textField;


   public TextFieldBinding(JTextField textField, DataObject obj, String property) {
      super(obj, property);
      this.textField = textField;
      this.textField.addFocusListener(this);
      updateView();
   }


   @Override
   public JTextField getComponent() {
      return textField;
   }


   @Override
   protected void updateView() {
      textField.setText(getValue());
   }


   @Override
   protected void updateModel() {
      setValue(textField.getText());
   }
}
