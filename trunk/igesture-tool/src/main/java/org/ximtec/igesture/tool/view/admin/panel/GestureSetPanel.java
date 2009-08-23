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
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sigtec.graphix.GridBagLayouter;
import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.binding.BindingFactory;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.util.FormBuilder;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.util.GestureTool;

public class GestureSetPanel extends AbstractPanel {
  
  private GestureSet gestureSet;
  
  private static final int SPACE_SIZE = 5;
  private static final int SAMPLE_SIZE = 100;

  public GestureSetPanel(Controller controller, GestureSet gestureSet) {
    super(controller);
    this.gestureSet = gestureSet;
    
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        updateView();
      }
    });
    
    setTitle(TitleFactory.createDynamicTitle(gestureSet, GestureSet.PROPERTY_NAME));

    
  }

  private JPanel createGestureIconList() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    // if the component has size 0 (before it is placed into another container
    // component, don't visualize the samples
    if (getWidth() > 0) {

      int x = 0;
      int y = 1;

      // compute the number of samples shown in a row
      int elementsPerRow = (getWidth() - 20) / (SAMPLE_SIZE + 20);
      elementsPerRow = elementsPerRow * 2 - 1;

      // add a line of space elements before the first row
      GridBagLayouter.addComponent(panel, createSpacerPanel(SPACE_SIZE), 0, 0);

      // iterate over all the samples
      for (GestureClass gestureClass : gestureSet.getGestureClasses()) {
        GridBagLayouter.addComponent(panel, createGesturePanel(gestureClass, SAMPLE_SIZE), x, y);
        GridBagLayouter.addComponent(panel, createSpacerPanel(SPACE_SIZE), x + 1, y);
        if (x + 1 >= elementsPerRow) {
          x = 0;
          y++;
          // add a line (spacer) between two sample rows
          GridBagLayouter.addComponent(panel, createSpacerPanel(SPACE_SIZE), 0, y);
          y++;
        } else {
          // element + spacer = 2
          x = x + 2;
        }
      }
    }

    panel.setOpaque(true);
    panel.setOpaque(false);
    panel.setBackground(Color.WHITE);
    panel.setAutoscrolls(true);
  
    return panel;
  }

  private JPanel createGesturePanel(GestureClass gestureClass, int size) {
    JPanel gesturePanel = new JPanel();
    gesturePanel.setLayout(new BorderLayout());
    JLabel gestureName = new JLabel(gestureClass.getName());
    gestureName.setHorizontalAlignment(JLabel.CENTER);
    gesturePanel.add(gestureName, BorderLayout.SOUTH);
    
    if (gestureClass.hasDescriptor(SampleDescriptor.class)) {
   
      SampleDescriptor descriptor = gestureClass.getDescriptor(SampleDescriptor.class);
      
      if (descriptor.getSamples().size() > 0 && descriptor.getSample(0) != null) {
        Gesture<Note> sample = descriptor.getSample(0);
        JLabel label = new JLabel(new ImageIcon(GestureTool.createNoteImage(sample.getGesture(), size, size)));
        gesturePanel.add(label, BorderLayout.CENTER);
      } else {
        JLabel labelNsa = getComponentFactory().createLabel(GestureConstants.GESTURE_SET_PANEL_NSA);
        gesturePanel.add(labelNsa, BorderLayout.CENTER);
      }
    }else{
      JLabel labelNsa = getComponentFactory().createLabel(GestureConstants.GESTURE_SET_PANEL_NSA);
      gesturePanel.add(labelNsa, BorderLayout.CENTER);
    }
    
    gesturePanel.setOpaque(false);
    gesturePanel.setBackground(Color.WHITE);
    gesturePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    
    return gesturePanel;
  }

  private JPanel createHeader(GestureSet gestureSet) {
    FormBuilder formBuilder = new FormBuilder();
    
    JLabel labelName = getComponentFactory().createLabel(GestureConstants.GESTURE_SET_PANEL_NAME);
    JTextField textField = new JTextField();
    BindingFactory.createInstance(textField, gestureSet, GestureSet.PROPERTY_NAME);

    formBuilder.addLeft(labelName);
    formBuilder.addRight(textField);
    formBuilder.nextLine();
    
    JLabel labelNogc = getComponentFactory().createLabel(GestureConstants.GESTURE_SET_PANEL_NOGC);
    JLabel labelNogcValue = new JLabel(Integer.toString(gestureSet.size()));
    formBuilder.addLeft(labelNogc);
    formBuilder.addRight(labelNogcValue);
    
    JPanel basePanel = formBuilder.getPanel();
    basePanel.setOpaque(false);
    basePanel.setBackground(Color.WHITE);
    
    return basePanel;
  }

  protected void updateView() {
    JPanel basePanel = new JPanel();
    basePanel.setLayout(new BorderLayout());
    basePanel.setOpaque(false);
    basePanel.setBackground(Color.WHITE);
    
    basePanel.add(createHeader(gestureSet), BorderLayout.NORTH);
    
    JPanel panelIcons = createGestureIconList();
    
    basePanel.add(panelIcons, BorderLayout.CENTER);
    
    setContent(basePanel);
    
  }
  
  /**
   * Returns a white, square spacer element (JPanel) of the given size.
   * 
   * @param size
   *          the size of the space element
   * @return the space element
   */
  private JPanel createSpacerPanel(int size) {
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setOpaque(true);
    panel.setBackground(Color.WHITE);
    panel.setPreferredSize(new Dimension(size, size));
    return panel;
  }

}
