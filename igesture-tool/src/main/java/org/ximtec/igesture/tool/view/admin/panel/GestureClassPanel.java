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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.admin.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.GestureClass;
import org.ximtex.igesture.tool.binding.BindingFactory;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class GestureClassPanel extends AbstractAdminPanel {

   public GestureClassPanel(GestureClass gestureClass) {

      setTitle(gestureClass.getName());

      FormLayout layout = new FormLayout(
            "100dlu, 4dlu, 200dlu",
            "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      builder.nextLine(4);

      builder.append(new JLabel("Name"));
      JTextField textField = new JTextField();

      BindingFactory.createInstance(textField, gestureClass,
            GestureClass.PROPERTY_NAME);

      // textField.setText(gestureClass.getName());
      builder.append(textField);
      builder.nextLine(2);

      builder.append(new JLabel("Number of Descriptors"));
      builder.append(new JLabel(Integer.toString(gestureClass.getDescriptors()
            .size())));
      builder.nextLine(2);
      for (Descriptor descriptor : gestureClass.getDescriptors()) {
         builder.append(new JLabel("Descriptor Name"));
         builder.append(new JLabel(descriptor.getType().getName()));
         builder.nextLine(2);
      }

      JPanel panel = builder.getPanel();
      panel.setOpaque(false);

      setCenter(panel);

   }

}
