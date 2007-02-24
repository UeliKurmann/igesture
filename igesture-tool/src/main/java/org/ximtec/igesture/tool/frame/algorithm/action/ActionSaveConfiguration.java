/*
 * @(#)ActionSaveConfigration.java	1.0   Dec 4, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Saves a configuration
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 4.12.2006 		ukurmann	Initial Release
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
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;


/**
 * Comment
 * 
 * @version 1.0 Dec 4, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ActionSaveConfiguration extends BasicAction {

   private AlgorithmConfiguration algorithmConfiguration;


   public ActionSaveConfiguration(AlgorithmConfiguration algorithmConfiguration) {
      super(GestureConstants.CONFIG_OPEN_ACTION, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.SAVE));
      this.algorithmConfiguration = algorithmConfiguration;
   }


   public void actionPerformed(ActionEvent arg0) {
      algorithmConfiguration.updateCurrentConfiguration();
   }

}
