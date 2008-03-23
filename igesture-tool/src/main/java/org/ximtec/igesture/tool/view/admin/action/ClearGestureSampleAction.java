/*
 * @(#)$Id: Main.java 454 2008-03-23 17:06:49Z kurmannu $
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

import hacks.JNote;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;


public class ClearGestureSampleAction extends BasicAction {

   JNote note;


   public ClearGestureSampleAction(JNote note) {
      super(GestureConstants.GESTURE_SAMPLE_CLEAR, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.note = note;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      note.clear();
   }

}
