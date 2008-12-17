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
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;


public class AddGestureSampleAction extends BasicAction {

   SampleDescriptor descriptor;


   public AddGestureSampleAction(SampleDescriptor descriptor) {
      super(GestureConstants.GESTURE_SAMPLE_ADD, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.descriptor = descriptor;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      GestureDevice<?,?> gestureDevice = Locator.getDefault().getService(
            SwingMouseReaderService.IDENTIFIER, GestureDevice.class);
      
      // FIXME generic implementation
      if(gestureDevice.getGesture() != null){
         Gesture<?> note = gestureDevice.getGesture();
         gestureDevice.clear();
         note.setName(Constant.EMPTY_STRING);
         if(note instanceof GestureSample){
            descriptor.addSample((GestureSample)note);
         }else{
            throw new IllegalStateException();
         }
      }
   }

}
