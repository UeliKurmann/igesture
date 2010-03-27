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


package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.DefaultSampleDescriptor;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;


public class AddGestureSampleAction extends LocateableAction {

   DefaultSampleDescriptor descriptor;
   GestureDevice<?,?> gestureDevice;


   public AddGestureSampleAction(Controller controller, DefaultSampleDescriptor descriptor, GestureDevice<?, ?> device) {
      super(GestureConstants.GESTURE_SAMPLE_ADD, controller.getLocator());
      this.descriptor = descriptor;
      this.gestureDevice = device;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      
      if(gestureDevice.getGesture() != null){
         Gesture<?> note = gestureDevice.getGesture();
         gestureDevice.clear();
         note.setName(Constant.EMPTY_STRING);
         if(note instanceof GestureSample){
            descriptor.addSample((GestureSample)note);
         }else if(note instanceof GestureSample3D){
        	 System.out.println("STILL TO IMPLEMENT");
        	 descriptor.addSample((GestureSample3D)note);
         }else{
            throw new IllegalStateException();
         }
      }
   }

}
