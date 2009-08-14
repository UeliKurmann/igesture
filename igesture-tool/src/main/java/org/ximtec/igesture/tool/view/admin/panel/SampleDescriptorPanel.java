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
 * Date       Who      Reason
 *
 * 23.03.2008 ukurmann Initial Release
 * 13.08.2009 ukurmann Complete Redesign
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.sigtec.graphix.GridBagLayouter;
import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.gesturevisualisation.GesturePanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputPanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputPanelFactory;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.admin.action.AddGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveGestureSampleAction;

public class SampleDescriptorPanel extends AbstractPanel {

  private final SampleDescriptor descriptor;
  private GestureDevice<?, ?> gestureDevice;

  public SampleDescriptorPanel(Controller controller, final SampleDescriptor descriptor) {
    super(controller);
    this.descriptor = descriptor;

    addComponentListener(new ComponentListener() {

      @Override
      public void componentHidden(ComponentEvent e) {

      }

      @Override
      public void componentMoved(ComponentEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void componentResized(ComponentEvent e) {
        initSampleSection(descriptor);

      }

      @Override
      public void componentShown(ComponentEvent e) {
        // TODO Auto-generated method stub

      }
    });
    init(descriptor);
  }

  private void init(SampleDescriptor descriptor) {
    initTitle(descriptor);
    initSampleSection(descriptor);
    initInputSection(descriptor);
  }

  /**
   * Sets the title of the form
   * 
   * @param descriptor
   */
  private void initTitle(SampleDescriptor descriptor) {
    setTitle(TitleFactory.createStaticTitle(descriptor.toString()));
  }

  /**
   * Shows the already existing samples
   * 
   * @param descriptor
   */
  private void initSampleSection(SampleDescriptor descriptor) {

    JPanel panel = new JPanel();

    panel.setLayout(new GridBagLayout());

    if (getWidth() > 10) {
      System.out.println(getWidth());
      int i = 0;
      int j = 0;
      int len = (getWidth())/(120);
     
      
      for (final Gesture<Note> sample : descriptor.getSamples()) {
        GridBagLayouter.addComponent(panel, createSampleIcon(sample), i, j);
        if(len == i+1){
          i = 0;
          j++;
        }else{
          i++;
        }
      }
    }

    panel.setOpaque(true);
    panel.setAutoscrolls(true);
    setContent(panel);
  }

  /**
   * FormLayout layout = new
   * FormLayout("pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,4dlu, pref",
   * "pref, pref, pref, pref, pref, pref,  pref,  pref,  pref,  pref,  pref,  pref"
   * );
   * 
   * DefaultFormBuilder builder = new DefaultFormBuilder(layout);
   * builder.setDefaultDialogBorder();
   * 
   * 
   * builder.nextLine(4);
   */

  private JPanel createSampleIcon(final Gesture<Note> sample) {
    GesturePanel gesturePanel = InputPanelFactory.createGesturePanel(sample);
    final JPanel label = gesturePanel.getPanel(new Dimension(100, 100));
    label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    label.addMouseListener(new SampleIconMouseListener(sample, label));
    return label;
  }

  /**
   * Input Area to capture a new sample
   */
  private void initInputSection(SampleDescriptor descriptor) {
    JPanel basePanel = new JPanel();

    // input area
    basePanel.setLayout(new FlowLayout());

    initGestureDevice();

    InputPanel inputPanel = InputPanelFactory.createInputPanel(gestureDevice);
    basePanel.add(inputPanel.getPanel(new Dimension(200, 200)));

    // buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.add(createAddSampleButton(descriptor));
    buttonPanel.add(createClearSampleButton());

    basePanel.add(buttonPanel);

    setBottom(basePanel);
  }

  private void initGestureDevice() {
    gestureDevice = getController().getLocator().getService(SwingMouseReaderService.IDENTIFIER, GestureDevice.class);
  }

  private JButton createAddSampleButton(SampleDescriptor descriptor) {
    JButton addSampleButton = getComponentFactory().createButton(GestureConstants.GESTURE_SAMPLE_ADD,
        new AddGestureSampleAction(getController(), descriptor));
    Formatter.formatButton(addSampleButton);
    return addSampleButton;
  }

  private JButton createClearSampleButton() {
    JButton clearSampleButton = getComponentFactory().createButton(GestureConstants.GESTURE_SAMPLE_CLEAR,
        new ClearGestureSampleAction(getController(), gestureDevice));
    Formatter.formatButton(clearSampleButton);
    return clearSampleButton;
  }

  @Override
  public void refreshUILogic() {
    super.refreshUILogic();
    gestureDevice.clear();
    initSampleSection(descriptor);
  }

  private class SampleIconMouseListener extends MouseAdapter {
    private final Gesture<Note> sample;
    private final JPanel label;

    private SampleIconMouseListener(Gesture<Note> sample, JPanel label) {
      this.sample = sample;
      this.label = label;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
      popUp(arg0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      popUp(e);
    }

    private void popUp(MouseEvent e) {
      if (e.isPopupTrigger()) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem();
        item.setAction(new RemoveGestureSampleAction(getController(), SampleDescriptorPanel.this.descriptor, sample));
        menu.add(item);
        menu.show(label, e.getX(), e.getY());
      }
    }
  }

}
