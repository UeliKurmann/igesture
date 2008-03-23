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
 * 23.03.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.testbench.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.util.XMLTool;



/**
 * Comment
 * @version 1.0 23.03.2008
 * @author Ueli Kurmann
 */
public class ExportConfigurationAction extends BasicAction {

   private TreePath treePath;


   public ExportConfigurationAction(TreePath treePath) {
      super(GestureConstants.CONFIGURATION_EXPORT, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }
   
   @Override
   public void actionPerformed(ActionEvent arg0) {
      
      Configuration configuration = (Configuration)treePath.getLastPathComponent();
      
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog(null);
      final File file = fileChooser.getSelectedFile();

      if (file != null) {
         XMLTool.exportConfiguration(configuration, file);
      }

   }

}
