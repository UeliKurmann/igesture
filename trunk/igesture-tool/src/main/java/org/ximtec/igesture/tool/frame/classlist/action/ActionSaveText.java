/*
 * @(#)ActionSaveText.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :	Action component to save the text description.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 * 
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.classlist.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.classlist.TextDescriptorFrame;
import org.ximtec.igesture.tool.util.IconLoader;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * Action component to save the text description.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionSaveText extends BasicAction {

   private TextDescriptorFrame frame;


   public ActionSaveText(TextDescriptorFrame model) {
      super(GestureConstants.COMMON_SAVE, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.SAVE));
      this.frame = model;
   }


   public void actionPerformed(ActionEvent event) {
      frame.saveTextDescription();
   } // actionPerformed

}
