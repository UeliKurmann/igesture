/*
 * @(#)ActionExportConfiguration.java	1.0   Dec 4, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Exports a configuration
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
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
import java.io.File;

import javax.swing.JFileChooser;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.frame.algorithm.AlgorithmConfiguration;
import org.ximtec.igesture.tool.util.IconLoader;
import org.ximtec.igesture.tool.util.SwingTool;
import org.ximtec.igesture.util.XMLTool;


/**
 * Comment
 * 
 * @version 1.0 Dec 4, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class ActionExportConfiguration extends BasicAction {

   private AlgorithmConfiguration algorithmConfiguration;


   public ActionExportConfiguration(AlgorithmConfiguration algorithmConfiguration) {
      super(GestureConstants.CONFIG_EXPORT_ACTION, SwingTool.getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.EXPORT));
      this.algorithmConfiguration = algorithmConfiguration;
   }


   public void actionPerformed(ActionEvent event) {
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog(algorithmConfiguration);
      final File file = fileChooser.getSelectedFile();
      if (file != null) {
         XMLTool.exportConfiguration(algorithmConfiguration
               .getCurrentConfiguration(), file);
      }
   }

}
