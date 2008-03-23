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

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.ink.Note;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.InputDeviceClientService;
import org.ximtec.igesture.tool.view.admin.panel.SampleDescriptorPanel;


public class AddGestureSampleAction extends BasicAction {

   SampleDescriptor descriptor;
   SampleDescriptorPanel panel;


   public AddGestureSampleAction(SampleDescriptor descriptor, SampleDescriptorPanel panel) {
      super(GestureConstants.GESTURE_SAMPLE_ADD, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.panel = panel;
      this.descriptor = descriptor;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      System.out.println(action);
      InputDeviceClient client = Locator.getDefault().getService(
            InputDeviceClientService.IDENTIFIER, InputDeviceClient.class);
      // FIXME how to use sigtec's trace detection? 
      Note note = client.createNote(0, System.currentTimeMillis(), 70);
      client.clearBuffer();
      GestureSample sample = new GestureSample("", note);
      descriptor.addSample(sample);
      // FIXME find a proper design
      panel.reload();
   }

}
