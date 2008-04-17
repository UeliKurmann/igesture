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
import javax.swing.JTextArea;

import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtex.igesture.tool.binding.BindingFactory;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class TextDescriptorPanel extends AbstractPanel {

   public TextDescriptorPanel(TextDescriptor textDescriptor) {

      setTitle(TitleFactory.createStaticTitle(textDescriptor.getClass().getName()));

      FormLayout layout = new FormLayout(
            "100dlu, 4dlu, 200dlu",
            "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      builder.append(new JLabel("Text"));
      JTextArea textArea = new JTextArea();

      BindingFactory.createInstance(textArea, textDescriptor,
            TextDescriptor.PROPERTY_TEXT);

      builder.append(textArea);
      builder.nextLine(2);

      JPanel panel = builder.getPanel();
      panel.setOpaque(false);
      // panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

      setCenter(panel);

   }

}
