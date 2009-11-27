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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.util.FileType;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;
import org.ximtec.igesture.util.XMLTool;


public class ImportGestureSetAction extends TreePathAction {

   private static final Logger LOG = Logger
         .getLogger(ImportGestureSetAction.class.getName());



   public ImportGestureSetAction(Controller controller, TreePath treePath) {
      super(GestureConstants.GESTURE_SET_IMPORT, controller, treePath);
   }


   public void actionPerformed(ActionEvent event) {
      LOG.info("Import Gesture Set...");
      GestureSetList rootSet = (GestureSetList)getTreePath().getLastPathComponent();

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(FileType.gestureSet.getFilter());
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
