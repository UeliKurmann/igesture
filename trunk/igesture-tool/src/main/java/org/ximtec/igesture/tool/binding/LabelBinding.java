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

import javax.swing.JLabel;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.DataObject;


public class LabelBinding extends DataBinding<JLabel> {

   private JLabel label;
   
   private String prefix;


   public LabelBinding(JLabel textField, DataObject obj, String property, String prefix) {
      super(obj, property);
      this.label = textField;
      this.prefix = prefix;
      updateView();
   }
   
   public LabelBinding(JLabel textField, DataObject obj, String property) {
      this(textField, obj, property, Constant.EMPTY_STRING);
   }


   @Override
   public JLabel getComponent() {
      return label;
   }


   @Override
   protected void updateView() {
      label.setText(prefix + getValue());
   }


   @Override
   protected void updateModel() {
      // do nothing, read only component
   }
}
