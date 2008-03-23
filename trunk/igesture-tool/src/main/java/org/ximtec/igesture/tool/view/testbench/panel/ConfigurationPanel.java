/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 23.03.2008		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench.panel;

import javax.swing.JTextArea;

import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.view.admin.panel.AbstractAdminPanel;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class ConfigurationPanel extends AbstractAdminPanel {

   public ConfigurationPanel(Configuration algorithm){
      // FIXME Implement View
      setTitle("Configuration");
      
      JTextArea textArea = new JTextArea();
      
      textArea.setText("- Configuration Properties \n- Gesture Set Selection\n- Capture Area\n- Result List");
      
      setCenter(textArea);
   }
}
