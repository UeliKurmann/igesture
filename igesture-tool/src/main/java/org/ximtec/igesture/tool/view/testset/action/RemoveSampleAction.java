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

package org.ximtec.igesture.tool.view.testset.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;


public class RemoveSampleAction extends LocateableAction {

   TestClass testSet;
   Gesture<?> sample;


   public RemoveSampleAction(Controller controller, TestClass descriptor, Gesture<?> sample) {
      super(GestureConstants.GESTURE_SAMPLE_DEL, controller.getLocator());
      this.testSet = descriptor;
      this.sample = sample;
   }


   @Override
   public void actionPerformed(ActionEvent action) {
      testSet.remove(sample);
   }
}
