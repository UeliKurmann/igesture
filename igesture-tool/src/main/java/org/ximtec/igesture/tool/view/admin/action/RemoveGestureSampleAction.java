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

import org.sigtec.ink.Note;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.LocateableAction;
import org.ximtec.igesture.tool.locator.Locator;


public class RemoveGestureSampleAction extends LocateableAction {

   SampleDescriptor descriptor;
   Gesture<Note> sample;


   public RemoveGestureSampleAction(SampleDescriptor descriptor, Gesture<Note> sample) {
      super(GestureConstants.GESTURE_SAMPLE_DEL, Locator.getDefault());
      this.descriptor = descriptor;
      this.sample = sample;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      descriptor.removeSample(sample);
   }
}
