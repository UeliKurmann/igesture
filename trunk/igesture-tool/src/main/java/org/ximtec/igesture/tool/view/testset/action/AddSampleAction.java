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

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.service.SwingMouseReaderService;


public class AddSampleAction extends LocateableAction {

   private TestClass testClass;
   private Controller controller;


   public AddSampleAction(Controller controller, TestClass descriptor) {
      super(GestureConstants.GESTURE_SAMPLE_ADD, controller.getLocator());
      this.controller = controller;
      this.testClass = descriptor;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      
      GestureDevice<?, ?> gestureDevice = controller.getLocator().getService(
            SwingMouseReaderService.IDENTIFIER, GestureDevice.class);
      
      if(gestureDevice.getGesture() != null){
         Gesture<?> gesture = gestureDevice.getGesture();
         gestureDevice.clear();
         gesture.setName(testClass.getName());
         testClass.add(gesture);
      }
     
   }

}
