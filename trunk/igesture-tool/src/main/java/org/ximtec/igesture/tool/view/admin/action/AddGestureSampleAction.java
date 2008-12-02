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
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.mouseclient.SwingMouseReader;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.InputDeviceClientService;


public class AddGestureSampleAction extends BasicAction {

   SampleDescriptor descriptor;


   public AddGestureSampleAction(SampleDescriptor descriptor) {
      super(GestureConstants.GESTURE_SAMPLE_ADD, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.descriptor = descriptor;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      SwingMouseReader gestureDevice = Locator.getDefault().getService(
            InputDeviceClientService.IDENTIFIER, SwingMouseReader.class);
      if(gestureDevice.getGesture() != null && gestureDevice.getGesture().getGesture() instanceof Note){
         Note note = (Note)gestureDevice.getGesture().getGesture();
         gestureDevice.clear();
         GestureSample sample = new GestureSample(Constant.EMPTY_STRING, note);
         descriptor.addSample(sample);
      }
      
      
   }

}
