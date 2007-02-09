/*
 * @(#)ActionExportPDFGestureSet.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Creates a PDF document containing the gesture classes
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.gestureset.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.SwingTool;
import org.ximtec.igesture.util.PDFTool;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;


public class ActionExportPDFGestureSet extends BaseAction {

   private GestureSet set;


   public ActionExportPDFGestureSet(GestureSet set) {
      super(GestureConstants.GESTURE_CLASS_VIEW_EXPORT_SET_PDF, SwingTool
            .getGuiBundle());
      putValue(SMALL_ICON, IconLoader.getIcon(IconLoader.EXPORT));
      this.set = set;
   }


   public void actionPerformed(ActionEvent event) {

      Document.compress = false;
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog((JMenuItem) event.getSource());
      final File selectedFile = fileChooser.getSelectedFile();
      if (selectedFile != null) {
         final Document document = PDFTool.createDocument(selectedFile);
         document.open();
         try {
            document.add(PDFTool.createGestureSetTable(set));
         }
         catch (final DocumentException e) {
            e.printStackTrace();
         }
         document.close();
      }
   }
}
