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

import hacks.PDFTool;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.TreePathAction;
import org.ximtec.igesture.tool.util.FileType;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;


public class ExportPDFGestureSetAction extends TreePathAction {

   private static final Logger LOG = Logger
         .getLogger(ExportPDFGestureSetAction.class.getName());

   public ExportPDFGestureSetAction(Controller controller, TreePath treePath) {
      super(GestureConstants.GESTURE_SET_PDF, controller, treePath);
   }


   public void actionPerformed(ActionEvent event) {

      GestureSet gestureSet = (GestureSet)getTreePath().getLastPathComponent();

      Document.compress = false;
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(FileType.pdf.getFilter());
      fileChooser.showSaveDialog((JMenuItem)event.getSource());
      File selectedFile = fileChooser.getSelectedFile();

      if (selectedFile != null) {
         Document document = PDFTool.createDocument(selectedFile);
         document.open();

         try {
            document.add(PDFTool.createGestureSetTable(gestureSet));
         }
         catch (DocumentException e) {
            LOG.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }

         document.close();
      }

   } // actionPerformed

}
