/*
 * @(#)CreateConfigurationAction.java	1.0   Dec 4, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Creates a new configuration.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
 * Creates a new configuration.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class CreateConfigurationAction extends AbstractAction {
   // FIXME: can't we use the BasicAction?
   
   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "ConfigCreateAction";
   
   private String name;

   private AlgorithmConfiguration algorithmConfiguration;


   public CreateConfigurationAction(
         AlgorithmConfiguration algorithmConfiguration, String name) {
      super(name);
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.DOCUMENT_NEW));
      this.name = name;
      this.algorithmConfiguration = algorithmConfiguration;
   }


   public void actionPerformed(ActionEvent event) {
      final Configuration config = algorithmConfiguration
            .createConfiguration(name);
      algorithmConfiguration.getMainView().getModel().addConfiguration(config);
   } // actionPerformed

}
