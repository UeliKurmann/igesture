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
import org.sigtec.ink.Note;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.InputDeviceClientService;


public class AddSampleAction extends BasicAction {

   TestClass descriptor;


   public AddSampleAction(TestClass descriptor) {
      super(GestureConstants.GESTURE_SAMPLE_ADD, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.descriptor = descriptor;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      InputDeviceClient client = Locator.getDefault().getService(
            InputDeviceClientService.IDENTIFIER, InputDeviceClient.class);
      Note note = client.createNote();
      client.clearBuffer();
      GestureSample sample = new GestureSample(Constant.EMPTY_STRING, note);
      descriptor.add(sample);
   }

}
