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

import org.ximtec.igesture.io.GestureDevice;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.LocateableAction;


public class ClearGestureSampleAction extends LocateableAction {

   GestureDevice<?,?> mouseReader;


   public ClearGestureSampleAction(Controller controller, GestureDevice<?,?> note) {
      super(GestureConstants.GESTURE_SAMPLE_CLEAR, controller.getLocator());
      this.mouseReader = note;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      mouseReader.clear();
   }

}
