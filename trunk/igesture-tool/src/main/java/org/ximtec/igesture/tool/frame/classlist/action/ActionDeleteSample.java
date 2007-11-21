/*
 * @(#)ActionDeleteSample.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Deletes a gesture sample.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
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

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.frame.classlist.SampleDescriptorFrame;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Deletes a gesture sample.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionDeleteSample extends BasicAction {

   private SampleDescriptorFrame frame;


   public ActionDeleteSample(String key, SampleDescriptorFrame listModel) {
      super(key, GuiTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DELETE));
      this.frame = listModel;
   }


   public void actionPerformed(ActionEvent event) {
      frame.deleteSelectedSample();
   } // actionPerformed
   
}
