/*
 * @(#)ActionSaveConfigration.java	1.0   Dec 4, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Saves a configuration.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 04, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.frame.algorithm.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.algorithm.AlgorithmConfiguration;
import org.ximtec.igesture.tool.util.IconLoader;
import org.ximtec.igesture.tool.util.SwingTool;


/**
 * Saves a configuration.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ActionSaveConfiguration extends BasicAction {

   private AlgorithmConfiguration algorithmConfiguration;


   public ActionSaveConfiguration(AlgorithmConfiguration algorithmConfiguration) {
      super(GestureConstants.CONFIG_OPEN_ACTION, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.SAVE));
      this.algorithmConfiguration = algorithmConfiguration;
   }


   public void actionPerformed(ActionEvent event) {
      algorithmConfiguration.updateCurrentConfiguration();
   } // actionPerformed

}