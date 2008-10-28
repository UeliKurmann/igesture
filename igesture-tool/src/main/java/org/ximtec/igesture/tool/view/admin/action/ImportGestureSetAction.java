/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.util.FileFilterFactory;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;
import org.ximtec.igesture.util.XMLTool;


public class ImportGestureSetAction extends BasicAction {

   private static final Logger LOG = Logger
         .getLogger(ImportGestureSetAction.class.getName());

   private TreePath treePath;


   public ImportGestureSetAction(TreePath treePath) {
      super(GestureConstants.GESTURE_SET_IMPORT, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.treePath = treePath;
   }


   public void actionPerformed(ActionEvent event) {
      LOG.info("Import Gesture Set...");
      GestureSetList rootSet = (GestureSetList)treePath.getLastPathComponent();

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(FileFilterFactory.getGestureSet());
      fileChooser.showOpenDialog((JMenuItem)event.getSource());
      File selectedFile = fileChooser.getSelectedFile();

      if (selectedFile != null) {
         GestureSet gestureSet = XMLTool.importGestureSet(selectedFile);

         if (gestureSet != null) {
            rootSet.addGestureSet(gestureSet);
         }
      }
      LOG.info("Gesture Set imported...");
   } // actionPerformed
}
