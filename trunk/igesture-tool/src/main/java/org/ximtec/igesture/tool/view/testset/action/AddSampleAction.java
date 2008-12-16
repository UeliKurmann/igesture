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


package org.ximtec.igesture.tool.view.testset.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.InputDeviceClientService;


public class AddSampleAction extends BasicAction {

   TestClass testClass;


   public AddSampleAction(TestClass descriptor) {
      super(GestureConstants.GESTURE_SAMPLE_ADD, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.testClass = descriptor;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      
      GestureDevice<?, ?> gestureDevice = Locator.getDefault().getService(
            InputDeviceClientService.IDENTIFIER, GestureDevice.class);
      
      if(gestureDevice.getGesture() != null){
         Gesture<?> gesture = gestureDevice.getGesture();
         gestureDevice.clear();
         gesture.setName(testClass.getName());
         testClass.add(gesture);
      }
     
   }

}
