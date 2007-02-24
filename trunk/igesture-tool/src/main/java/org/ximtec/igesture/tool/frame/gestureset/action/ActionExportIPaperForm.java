/*
 * @(#)ActionExportIPaperForm.java	1.0   Jan 15, 2007
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Creates a PDF Form used by appGesture
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 15.01.2007		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.gestureset.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.utils.BaseAction;
import org.ximtec.igesture.tool.utils.IconLoader;
import org.ximtec.igesture.tool.utils.PDFTool;
import org.ximtec.igesture.tool.utils.SwingTool;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;


public class ActionExportIPaperForm extends BaseAction {

   private GestureSet set;

   private final int numberOfColumns = 6;


   public ActionExportIPaperForm(GestureSet set) {
      super(GestureConstants.GESTURE_CLASS_VIEW_EXPORT_IPAPER, SwingTool
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
         Collections.sort(set.getGestureClasses(),
               new Comparator<GestureClass>() {

                  public int compare(GestureClass arg0, GestureClass arg1) {
                     return arg0.getName().compareTo(arg1.getName());
                  }
               });

         final Document document = PDFTool.createDocument(selectedFile);
         document.open();
         try {
            final PdfPTable table = PDFTool.createTable(numberOfColumns);
            for (final GestureClass gestureClass : set.getGestureClasses()) {
               table.addCell(PDFTool.createImageCell(gestureClass));
               for (int i = 0; i < numberOfColumns - 1; i++) {
                  table.addCell(PDFTool.createEmptyCell());
               }
            }
            document.add(table);
         }
         catch (final DocumentException e) {
            e.printStackTrace();
         }
         document.close();
      }
   }
}
