/*
 * @(#)ActionAddSample.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Adds a sample.
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

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.frame.classlist.SampleDescriptorFrame;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Adds a sample.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionAddSample extends BasicAction {

   private SampleDescriptorFrame frame;


   public ActionAddSample(String key, SampleDescriptorFrame model) {
      super(key, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.LIST_ADD));
      this.frame = model;
   }


   public void actionPerformed(ActionEvent event) {
      frame.addSample();
   } // actionPerformed

}
