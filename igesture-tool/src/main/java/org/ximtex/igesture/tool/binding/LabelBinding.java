/*
 * @(#)$Id: TextFieldBinding.java 456 2008-03-23 17:16:31Z kurmannu $
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtex.igesture.tool.binding;

import javax.swing.JLabel;

import org.ximtec.igesture.core.DataObject;


public class LabelBinding extends DataBinding<JLabel> {

   private JLabel label;


   public LabelBinding(JLabel textField, DataObject obj, String property) {
      super(obj, property);
      this.label = textField;
      updateView();
   }


   @Override
   public JLabel getComponent() {
      return label;
   }


   @Override
   public void updateView() {
      label.setText(getValue());
   }


   @Override
   public void updateModel() {
      // do nothing, read only component
   }
}
