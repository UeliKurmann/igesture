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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.gesturevisualisation.GesturePanel;
import org.ximtec.igesture.tool.gesturevisualisation.InputPanel;
import org.ximtec.igesture.tool.gesturevisualisation.PanelFactory;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.admin.action.AddGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveGestureSampleAction;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class SampleDescriptorPanel extends AbstractPanel {

  private final SampleDescriptor descriptor;
  private GestureDevice<?, ?> gestureDevice;

  public SampleDescriptorPanel(Controller controller,
      SampleDescriptor descriptor) {
    super(controller);
    this.descriptor = descriptor;
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

    FormLayout layout = new FormLayout(
        "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,4dlu, pref",
        "pref, pref, pref, pref, pref, pref,  pref,  pref,  pref,  pref,  pref,  pref");

    DefaultFormBuilder builder = new DefaultFormBuilder(layout);
    builder.setDefaultDialogBorder();

    builder.append("Descriptor has " + descriptor.getSamples().size()
        + " samples.");
    builder.nextLine(4);

    for (final Gesture<?> sample : descriptor.getSamples()) {
      GesturePanel gesturePanel = PanelFactory.createGesturePanel(sample);
      final JPanel label = gesturePanel.getPanel(new Dimension(100, 100));
      label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
      label.addMouseListener(new MouseAdapter() {

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
            item.setAction(new RemoveGestureSampleAction(getController(),
                SampleDescriptorPanel.this.descriptor, (Gesture<Note>) sample));
            menu.add(item);
            menu.show(label, e.getX(), e.getY());
          }
        }
      });
      builder.append(label);
    }

    JPanel panel = builder.getPanel();
    panel.setOpaque(true);
    panel.setAutoscrolls(true);
    setCenter(panel);
  }

  /**
   * Input Area to capture a new sample
   */
  private void initInputSection(SampleDescriptor descriptor) {
    JPanel basePanel = new JPanel();

    // input area
    basePanel.setLayout(new FlowLayout());

    gestureDevice = getController().getLocator().getService(
        SwingMouseReaderService.IDENTIFIER, GestureDevice.class);

    InputPanel inputPanel = PanelFactory.createInputPanel(gestureDevice);
    basePanel.add(inputPanel.getPanel(new Dimension(200, 200)));

    // buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

    JButton addSampleButton = getComponentFactory().createButton(
        GestureConstants.GESTURE_SAMPLE_ADD, new AddGestureSampleAction(
            getController(), descriptor));
    Formatter.formatButton(addSampleButton);
    buttonPanel.add(addSampleButton);

    JButton clearSampleButton = getComponentFactory().createButton(
        GestureConstants.GESTURE_SAMPLE_CLEAR, new ClearGestureSampleAction(
            getController(), gestureDevice));
    Formatter.formatButton(clearSampleButton);
    buttonPanel.add(clearSampleButton);

    basePanel.add(buttonPanel);

    setBottom(basePanel);
  }

  @Override
  public void refresh() {
    gestureDevice.clear();
    initSampleSection(descriptor);
  }

}
