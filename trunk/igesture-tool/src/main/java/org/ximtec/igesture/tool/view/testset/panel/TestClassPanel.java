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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.SwingMouseReader;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.InputDeviceClientService;
import org.ximtec.igesture.tool.util.ComponentFactory;
import org.ximtec.igesture.tool.util.Formatter;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.tool.view.testset.action.AddSampleAction;
import org.ximtec.igesture.tool.view.testset.action.RemoveSampleAction;
import org.ximtec.igesture.util.GestureTool;
import org.ximtex.igesture.tool.binding.BindingFactory;

import sun.awt.VerticalBagLayout;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


/**
 * Comment
 * @version 1.0 07.10.2008
 * @author Ueli Kurmann
 */
public class TestClassPanel extends AbstractPanel {

   private TestClass testClass;
   private SwingMouseReader note;


   public TestClassPanel(Controller controller, TestClass testClass) {
      this.testClass = testClass;
      init(this.testClass);

      setTitle(TitleFactory.createStaticTitle("Test Set Panel"));

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

      FormLayout layout = new FormLayout(
            "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,4dlu, pref",
            "pref, pref, pref, pref, pref, pref,  pref,  pref,  pref,  pref,  pref,  pref");

      DefaultFormBuilder builder = new DefaultFormBuilder(layout);
      builder.setDefaultDialogBorder();

      builder
            .append(ComponentFactory.createLabel(GestureConstants.TESTSET_NAME));
      JTextField textField = new JTextField();

      BindingFactory.createInstance(textField, testSet, TestSet.PROPERTY_NAME);
      builder.append(textField, 3);

      builder.nextLine(4);

      builder.append(
            new JLabel("Test Class has " + testSet.size() + " samples."), 3);
      builder.nextLine(4);

      for (final Gesture<Note> sample : testSet.getGestures()) {
         // TODO: Remove unsafe cast!
         final JLabel label = new JLabel(new ImageIcon(GestureTool
               .createNoteImage((Note)sample.getGesture(), 100, 100)));
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
                  item.setAction(new RemoveSampleAction(
                        TestClassPanel.this.testClass, sample));
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
   private void initInputSection(TestClass testClass) {
      JPanel basePanel = new JPanel();

      // input area
      basePanel.setLayout(new FlowLayout());

      // FIXME save implementation! (Avoid Casts and dependences of subtypes!)
      InputDeviceClient client = Locator.getDefault().getService(
            InputDeviceClientService.IDENTIFIER, InputDeviceClient.class);

      note = (SwingMouseReader)client.getInputDevice();

      basePanel.add(note.getPanel(new Dimension(200, 200)));

      // buttons
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new VerticalBagLayout());

      JButton addSampleButton = ComponentFactory
            .createButton(GestureConstants.GESTURE_SAMPLE_ADD,
                  new AddSampleAction(testClass));
      Formatter.formatButton(addSampleButton);
      buttonPanel.add(addSampleButton);

      JButton clearSampleButton = ComponentFactory.createButton(null,
            new ClearGestureSampleAction(note));
      Formatter.formatButton(clearSampleButton);
      buttonPanel.add(clearSampleButton);

      basePanel.add(buttonPanel);

      setBottom(basePanel);
   }


   @Override
   public void refresh() {
      note.clear();
      initSampleSection(testClass);
   }
}
