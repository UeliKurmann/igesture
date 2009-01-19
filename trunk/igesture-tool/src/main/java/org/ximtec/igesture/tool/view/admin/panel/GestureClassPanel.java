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
import org.ximtec.igesture.core.Descriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.util.GestureTool;
import org.ximtex.igesture.tool.binding.BindingFactory;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class GestureClassPanel extends AbstractPanel {

   public GestureClassPanel(Controller controller, GestureClass gestureClass) {

      setTitle(TitleFactory.createDynamicTitle(gestureClass,
            GestureClass.PROPERTY_NAME));

      FormLayout layout = new FormLayout(
            "100dlu, 4dlu, 200dlu",
            "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      builder.nextLine(4);

      builder.append(ComponentFactory.createLabel(GestureConstants.GESTURE_CLASS_PANEL_NAME));
      JTextField textField = new JTextField();

      BindingFactory.createInstance(textField, gestureClass,
            GestureClass.PROPERTY_NAME);

      // textField.setText(gestureClass.getName());
      builder.append(textField);
      builder.nextLine(2);
      builder.append(ComponentFactory.createLabel(GestureConstants.GESTURE_CLASS_PANEL_GESTURE));
      if (gestureClass.hasDescriptor(SampleDescriptor.class)) {
         Gesture<Note> sample = null;
         if(gestureClass.getDescriptor(SampleDescriptor.class).getSamples().size() > 0){
            sample = (Gesture<Note>)gestureClass.getDescriptor(SampleDescriptor.class).getSample(0);
         }
         if(sample != null){
            JLabel label = new JLabel(new ImageIcon(GestureTool
                  .createNoteImage(sample.getGesture(), 150, 150)));
            label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            builder.append(label);
         }else{
            builder.append(ComponentFactory.createLabel(GestureConstants.GESTURE_CLASS_PANEL_NSA));
         }    
      }
      builder.nextLine(2);

      
      builder.append(ComponentFactory.createLabel(GestureConstants.GESTURE_CLASS_PANEL_NOD));
      builder.append(new JLabel(Integer.toString(gestureClass.getDescriptors()
            .size())));
      builder.nextLine(2);
      for (Descriptor descriptor : gestureClass.getDescriptors()) {
         builder.append(ComponentFactory.createLabel(GestureConstants.GESTURE_CLASS_PANEL_DESCRIPTOR_NAME));
         builder.append(new JLabel(descriptor.getType().getName()));
         builder.nextLine(2);
      }

     

      JPanel panel = builder.getPanel();
      panel.setOpaque(false);

      setCenter(panel);

   }
   
   

}
