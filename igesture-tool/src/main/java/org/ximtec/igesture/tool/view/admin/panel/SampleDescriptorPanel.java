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

import hacks.JNote;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sigtec.graphix.widget.BasicButton;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.InputDeviceClientService;
import org.ximtec.igesture.tool.util.TitleFactory;
import org.ximtec.igesture.tool.view.AbstractPanel;
import org.ximtec.igesture.tool.view.admin.action.AddGestureSampleAction;
import org.ximtec.igesture.tool.view.admin.action.ClearGestureSampleAction;
import org.ximtec.igesture.util.GestureTool;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


public class SampleDescriptorPanel extends AbstractPanel {
   //FIXME CLEANUP!!!
   private SampleDescriptor descriptor;
   private JNote note;
   
   public SampleDescriptorPanel(SampleDescriptor descriptor) {
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

      builder.append(new JLabel("Descriptor has "
            + descriptor.getSamples().size() + " samples."));
      builder.nextLine(4);

      for (GestureSample sample : descriptor.getSamples()) {
         JLabel label = new JLabel(new ImageIcon(GestureTool.createNoteImage(
               sample.getGesture(), 100, 100)));
         label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
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

      note = new JNote(200, 200);

      Locator.getDefault().getService(InputDeviceClientService.IDENTIFIER,
            InputDeviceClient.class).addInputHandler(note);

      basePanel.add(note);

      // buttons
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); 
      buttonPanel.add(new BasicButton(new AddGestureSampleAction(descriptor, this)));

      buttonPanel.add(new BasicButton(new ClearGestureSampleAction(note)));

      basePanel.add(buttonPanel);

      setBottom(basePanel);
   }
   
   public void reload(){
      note.clear();
      initSampleSection(descriptor);
   }

}
