/*
 * @(#)ActionCreateConfiguration.java	1.0   Dec 4, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Creates a new configration
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

import javax.swing.AbstractAction;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.frame.algorithm.AlgorithmConfiguration;
import org.ximtec.igesture.tool.util.IconLoader;


/**
 * Comment
 * 
 * @version 1.0 Dec 4, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ActionCreateConfiguration extends AbstractAction {

   private String name;

   private AlgorithmConfiguration algorithmConfiguration;


   public ActionCreateConfiguration(
         AlgorithmConfiguration algorithmConfiguration, String name) {
      super(name);
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_NEW));
      this.name = name;
      this.algorithmConfiguration = algorithmConfiguration;
   }


   public void actionPerformed(ActionEvent arg0) {
      final Configuration config = algorithmConfiguration
            .createConfiguration(name);
      algorithmConfiguration.getMainView().getModel().addConfiguration(config);
   }

}
