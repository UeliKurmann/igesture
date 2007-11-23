/*
 * @(#)ExportPDFGestureSetAction.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Creates a PDF document containing the gesture classes.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.util.PDFTool;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;


/**
 * Creates a PDF document containing the gesture classes.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ExportPDFGestureSetAction extends BasicAction {

   private static final Logger LOGGER = Logger
         .getLogger(ExportPDFGestureSetAction.class.getName());

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "ExportPDFGestureSetAction";

   private GestureSet set;


   public ExportPDFGestureSetAction(GestureSet set) {
      super(KEY, GuiTool.getGuiBundle());
      this.set = set;
   }


   public void actionPerformed(ActionEvent event) {
      Document.compress = false;
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.showSaveDialog((JMenuItem)event.getSource());
      final File selectedFile = fileChooser.getSelectedFile();

      if (selectedFile != null) {
         final Document document = PDFTool.createDocument(selectedFile);
         document.open();

         try {
            document.add(PDFTool.createGestureSetTable(set));
         }
         catch (final DocumentException e) {
            LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         }

         document.close();
      }

   } // actionPerformed

}
