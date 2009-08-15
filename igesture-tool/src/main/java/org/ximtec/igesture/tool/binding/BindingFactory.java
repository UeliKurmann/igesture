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

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.ximtec.igesture.core.DataObject;


public class BindingFactory {

   public static DataBinding< ? > createInstance(JComponent component,
         DataObject obj, String property) {
      if (component instanceof JTextField) {
         return new TextFieldBinding((JTextField)component, obj, property);
      }else if (component instanceof JTextArea) {
         return new TextAreaBinding((JTextArea)component, obj, property);
      }else if (component instanceof JLabel) {
         return new LabelBinding((JLabel)component, obj, property);
      }
      return null;
   }
}
