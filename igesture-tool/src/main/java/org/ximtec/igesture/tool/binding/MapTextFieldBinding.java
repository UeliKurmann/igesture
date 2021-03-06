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

import org.ximtec.igesture.configuration.Configuration;


public class MapTextFieldBinding extends DataBinding<JTextField> {

   private JTextField textField;
   private String algorithm;


   public MapTextFieldBinding(JTextField textField, Configuration configuration, String property, String algorithm) {
      super(configuration, property);
      this.textField = textField;
      this.algorithm = algorithm;
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
   
   /**
    * Read a value from the data object
    * 
    * @return
    */
   @Override
   protected String getValue() {
     return ((Configuration)getObject()).getParameters(algorithm).get(getProperty());
   }
   
   @Override
   protected void setValue(Object value) {
      if(!getValue().equals(value)){
         ((Configuration)getObject()).addParameter(algorithm, getProperty(), (String)value);
      }
      
   }
   
   
}
