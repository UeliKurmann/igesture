/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 07.10.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view.testset.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
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
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.testset.action.AddSampleAction;
import org.ximtec.igesture.tool.view.testset.action.RemoveSampleAction;
import org.ximtex.igesture.tool.binding.BindingFactory;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Comment
 * 
 * @version 1.0 07.10.2008
 * @author Ueli Kurmann
 */
public class TestClassPanel extends AbstractPanel {

  private TestClass testClass;
  private GestureDevice<?, ?> gestureDevice;

  public TestClassPanel(Controller controller, TestClass testClass) {
    super(controller);
    this.testClass = testClass;
    init(this.testClass);
    setTitle(TitleFactory.createStaticTitle("Test Class Panel"));
  }

  private void init(TestClass descriptor) {
    initTitle(descriptor);
    initSampleSection(descriptor);
    initInputSection(descriptor);
  }

  /**
   * Sets the title of the form
   * 
   * @param descriptor
   */
  private void initTitle(TestClass descriptor) {
    setTitle(TitleFactory.createStaticTitle(descriptor.toString()));
  }

  /**
   * Shows the already existing samples
   * 
   * @param testSet
   */
  private void initSampleSection(TestClass testSet) {

    FormLayout layout = new FormLayout("100px, 4dlu, 100px, 4dlu, 100px, 4dlu, 100px,4dlu, 100px",
        "pref, 4dlu, pref, pref, pref, pref,  pref,  pref,  pref,  pref,  pref,  pref");

    DefaultFormBuilder builder = new DefaultFormBuilder(layout);
    builder.setDefaultDialogBorder();

    builder.append(getComponentFactory().createLabel(GestureConstants.TESTSET_NAME));
    JTextField textField = new JTextField();
    BindingFactory.createInstance(textField, testSet, TestSet.PROPERTY_NAME);
    builder.append(textField, 3);

    builder.nextLine(2);

    builder.append(new JLabel("Test Class has " + testSet.size() + " samples."), 3);
    builder.nextLine(2);

    for (final Gesture<?> sample : testSet.getGestures()) {
      builder.append(createGesture(sample));
    }

    JPanel panel = builder.getPanel();
    panel.setOpaque(true);
    panel.setAutoscrolls(true);
    setContent(panel);
  }

  private JPanel createGesture(final Gesture<?> gesture) {

    GesturePanel gesturePanel = InputPanelFactory.createGesturePanel(gesture);
    final JPanel panel = gesturePanel.getPanel(new Dimension(100, 100));
    panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

    panel.addMouseListener(new MouseAdapter() {

      RemoveSampleAction action = new RemoveSampleAction(getController(), TestClassPanel.this.testClass, gesture);

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
          getComponentFactory().createPopupMenu(action).show(panel, e.getX(), e.getY());
        }
      }
    });

    return panel;

  }

  /**
   * Input Area to capture a new sample
   */
  private void initInputSection(TestClass testClass) {
    JPanel basePanel = new JPanel();

    // input area
    basePanel.setLayout(new FlowLayout());

    gestureDevice = getController().getLocator().getService(SwingMouseReaderService.IDENTIFIER, GestureDevice.class);

    InputPanel inputPanel = InputPanelFactory.createInputPanel(gestureDevice);
    basePanel.add(inputPanel.getPanel(new Dimension(200, 200)));

    // buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

    JButton addSampleButton = getComponentFactory().createButton(GestureConstants.GESTURE_SAMPLE_ADD,
        new AddSampleAction(getController(), testClass));
    Formatter.formatButton(addSampleButton);
    buttonPanel.add(addSampleButton);

    JButton clearSampleButton = getComponentFactory().createButton(null,
        new ClearGestureSampleAction(getController(), gestureDevice));
    Formatter.formatButton(clearSampleButton);
    buttonPanel.add(clearSampleButton);

    basePanel.add(buttonPanel);

    setBottom(basePanel);
  }

  @Override
  public void refreshUILogic() {
    super.refreshUILogic();
    gestureDevice.clear();
    initSampleSection(testClass);
  }
}
