/*
 * @(#)ActionSaveText.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :	Action Component to save the text description
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 * 
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.classlist.action;

import java.awt.event.ActionEvent;

import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.classlist.TextDescriptorFrame;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


public class ActionSaveText extends BaseAction {

   private TextDescriptorFrame frame;


   public ActionSaveText(TextDescriptorFrame model) {
      super(GestureConstants.COMMON_SAVE, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getActionIcon(IconLoader.SAVE));
      this.frame = model;
   }


   public void actionPerformed(ActionEvent event) {
      frame.saveTextDescription();
   }

}
