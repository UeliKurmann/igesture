/*
 * @(#)ExportIPaperFormAction.java	1.0   Jan 15, 2007
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Creates a PDF Form used by appGesture.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Jan 15, 2007     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.old.frame.gestureset.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.util.PDFTool;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;


/**
 * Creates a PDF Form used by appGesture.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ExportIPaperFormAction extends BasicAction {

   private static final Logger LOGGER = Logger
         .getLogger(ExportIPaperFormAction.class.getName());

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "ExportIPaperFormAction";

   private GestureSet set;

   private final int numberOfColumns = 6;


   public ExportIPaperFormAction(GestureSet set) {
      super(KEY, GestureToolMain.getGuiBundle());
      this.set = set;
   }


   public void actionPerformed(ActionEvent event) {
      Document.compress = false;
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog((JMenuItem)event.getSource());
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
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }

         document.close();
      }

   } // actionPerformed

}