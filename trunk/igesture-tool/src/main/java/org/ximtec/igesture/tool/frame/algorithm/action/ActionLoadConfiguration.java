/*
 * @(#)ActionLoadConfigration.java	1.0   Dec 4, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Loads a configration
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 4.12.2006		ukurmann	Initial Release
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
 * Comment
 * 
 * @version 1.0 Dec 4, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ActionLoadConfiguration extends BasicAction {

   private AlgorithmConfiguration algorithmConfiguration;


   public ActionLoadConfiguration(AlgorithmConfiguration algorithmConfiguration) {
      super(GestureConstants.CONFIG_OPEN_ACTION, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_OPEN));
      this.algorithmConfiguration = algorithmConfiguration;
   }


   public void actionPerformed(ActionEvent arg0) {
      algorithmConfiguration.openSelectedConfiguration();
   }

}
