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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.util.GestureTool;
import org.ximtex.igesture.tool.binding.BindingFactory;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class GestureSetPanel extends AbstractPanel {

   public GestureSetPanel(GestureSet gestureSet) {

      setTitle(TitleFactory.createDynamicTitle(gestureSet,
            GestureSet.PROPERTY_NAME));

      FormLayout layout = new FormLayout(
            "100dlu, 4dlu, 200dlu",
            "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      builder.append(new JLabel("Name"));
      JTextField textField = new JTextField();

      BindingFactory.createInstance(textField, gestureSet,
            GestureSet.PROPERTY_NAME);

      builder.append(textField);
      builder.nextLine(2);

      builder.append(new JLabel("Number of Gesture Classes"));
      builder.append(new JLabel(Integer.toString(gestureSet.size())));

      for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
         
         if (gestureClass.hasDescriptor(SampleDescriptor.class)) {
            builder.nextLine(2);
            builder.append(new JLabel(gestureClass.getName()));

            Gesture<Note> sample = null;
            if(gestureClass.getDescriptor(SampleDescriptor.class).getSamples().size() > 0){
               sample = gestureClass.getDescriptor(SampleDescriptor.class).getSample(0);
            }
            if(sample != null){
               JLabel label = new JLabel(new ImageIcon(GestureTool
                     .createNoteImage(sample.getGesture(), 100, 100)));
               label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
               builder.append(label);
            }else{
               // FIXME resource file
               builder.append("No sample available");
            }    
         }
      }
      
      JPanel panel = builder.getPanel();
      panel.setOpaque(false);
      // panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

      setCenter(panel);

   }

}
