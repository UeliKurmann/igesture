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

package org.ximtex.igesture.tool.binding;

import javax.swing.JTextArea;

import org.ximtec.igesture.core.DataObject;


public class TextAreaBinding extends DataBinding<JTextArea> {

   private JTextArea textArea;


   public TextAreaBinding(JTextArea textField, DataObject obj, String property) {
      super(obj, property);
      this.textArea = textField;
      this.textArea.addFocusListener(this);
      updateView();
   }


   @Override
   public JTextArea getComponent() {
      return textArea;
   }


   @Override
   protected void updateView() {
      textArea.setText(getValue());
   }


   @Override
   protected void updateModel() {
      setValue(textArea.getText());
   }
}
