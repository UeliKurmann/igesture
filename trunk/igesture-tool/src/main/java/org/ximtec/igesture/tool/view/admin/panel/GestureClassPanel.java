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

package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

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
import org.ximtec.igesture.core.SampleDescriptor3D;
import org.ximtec.igesture.core.composite.CompositeDescriptor;
import org.ximtec.igesture.core.composite.Constraint;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.binding.BindingFactory;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.FormBuilder;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.util.GestureTool;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

public class GestureClassPanel extends AbstractPanel {
  
  private GestureClass gestureClass;

  public GestureClassPanel(Controller controller, GestureClass gestureClass) {
    super(controller);
    
    this.gestureClass = gestureClass;
    
    setTitle(TitleFactory.createDynamicTitle(gestureClass,
        GestureClass.PROPERTY_NAME));

    JPanel panel = createHeaderPanel();
    
    JPanel basePanel = ComponentFactory.createBorderLayoutPanel();
    basePanel.add(panel, BorderLayout.NORTH);

    setContent(basePanel);

  }
  


  private JPanel createHeaderPanel(){
    
    FormBuilder formBuilder = new FormBuilder();
    
    JLabel labelName = getComponentFactory().createLabel(GestureConstants.GESTURE_CLASS_PANEL_NAME);
    formBuilder.addLeft(labelName);
    
    JTextField textField = new JTextField();

    BindingFactory.createInstance(textField, gestureClass, GestureClass.PROPERTY_NAME);
    formBuilder.addRight(textField);
    
    formBuilder.nextLine();
   
    JLabel labelNod = getComponentFactory().createLabel(GestureConstants.GESTURE_CLASS_PANEL_NOD);
    JLabel labelNodValue = new JLabel(Integer.toString(gestureClass.getDescriptors().size()));
    
    formBuilder.addLeft(labelNod);
    formBuilder.addRight(labelNodValue);
    
    formBuilder.nextLine();
    
    for (Descriptor descriptor : gestureClass.getDescriptors()) {
      JLabel labelDescriptorName = getComponentFactory().createLabel(GestureConstants.GESTURE_CLASS_PANEL_DESCRIPTOR_NAME);
      JLabel labelDescriptorNameValue = new JLabel(descriptor.getType().getName());
      
      formBuilder.addLeft(labelDescriptorName);
      formBuilder.addRight(labelDescriptorNameValue);

      formBuilder.nextLine();
    }
    
    if (gestureClass.hasDescriptor(SampleDescriptor.class)) {
      Gesture<Note> sample = null;
      
      SampleDescriptor descriptor = gestureClass.getDescriptor(SampleDescriptor.class);
      List<Gesture<Note>> samples = descriptor.getSamples();
      
      if (samples.size() > 0) {
        sample = descriptor.getSample(0);
      }
      
      if (sample != null) {
        JLabel label = new JLabel(new ImageIcon(GestureTool.createNoteImage(
            sample.getGesture(), 150, 150)));
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        formBuilder.addRight(label);
      } else {
        formBuilder.addRight(getComponentFactory().createLabel(
            GestureConstants.GESTURE_CLASS_PANEL_NSA));
      }
    }
    else if(gestureClass.hasDescriptor(SampleDescriptor3D.class))
    {
    	Gesture<RecordedGesture3D> sample = null;
    	
    	SampleDescriptor3D descriptor = gestureClass.getDescriptor(SampleDescriptor3D.class);
    	List<Gesture<RecordedGesture3D>> samples = descriptor.getSamples();
    	
    	if(samples.size() > 0)
    		sample = descriptor.getSample(0);
    	
    	if(sample != null)
    	{
    		JLabel label = new JLabel(new ImageIcon(GestureTool.createRecordedGesture3DImage(
    	            sample.getGesture(), 150, 150)));
    	        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    	        formBuilder.addRight(label);
    	}
    	else
    	{
    		formBuilder.addRight(getComponentFactory().createLabel(
    	            GestureConstants.GESTURE_CLASS_PANEL_NSA));
    	}
    }
    else if(gestureClass.hasDescriptor(CompositeDescriptor.class))
    {
    	Constraint constraint = null;
    	
    	CompositeDescriptor descriptor = gestureClass.getDescriptor(CompositeDescriptor.class);
    	constraint = descriptor.getConstraint();
    	
    	if(constraint != null)
    	{
    		JLabel label = new JLabel(new ImageIcon(GestureTool.createCompositeImage(constraint,150,150)));
    		label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    		formBuilder.addRight(label);
    	}
    	else
    	{
    		formBuilder.addRight(getComponentFactory().createLabel(
    	            GestureConstants.GESTURE_CLASS_PANEL_NCA));
    	}
    		
    	
    }

    return formBuilder.getPanel();
  }

  @Override
  protected void refreshUILogic() {
    // TODO Auto-generated method stub
    
  }
  
  

}
