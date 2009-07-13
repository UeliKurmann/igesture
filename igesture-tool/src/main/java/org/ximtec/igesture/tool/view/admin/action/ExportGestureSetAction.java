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

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.util.FileFilterFactory;
import org.ximtec.igesture.util.XMLTool;


public class ExportGestureSetAction extends TreePathAction {


   public ExportGestureSetAction(Controller controller, TreePath treePath) {
      super(GestureConstants.GESTURE_SET_EXPORT, controller, treePath);
   }


   public void actionPerformed(ActionEvent event) {
      GestureSet gestureSet = (GestureSet)getTreePath().getLastPathComponent();

      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(FileFilterFactory.getGestureSet());
      fileChooser.showSaveDialog((JMenuItem)event.getSource());
      final File selectedFile = fileChooser.getSelectedFile();

      if (selectedFile != null) {
         XMLTool.exportGestureSet(gestureSet, selectedFile);
      }

   }
}
