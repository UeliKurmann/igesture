/*
 * @(#)$Id: AddGestureSampleAction.java 494 2008-04-05 11:57:45Z kurmannu $
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
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;


public class RemoveGestureSampleAction extends BasicAction {

   SampleDescriptor descriptor;
   Gesture<Note> sample;


   public RemoveGestureSampleAction(SampleDescriptor descriptor, Gesture<Note> sample) {
      super(GestureConstants.GESTURE_SAMPLE_DEL, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.descriptor = descriptor;
      this.sample = sample;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      descriptor.removeSample(sample);
   }
}
