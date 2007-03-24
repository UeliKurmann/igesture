/*
 * @(#)PDFTool.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   PDF Tools
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


public class PDFTool {

   private static final Logger LOGGER = Logger
         .getLogger(PDFTool.class.getName());

   public static int NUMBER_OF_COLUMNS = 3;


   /**
    * Creates a new PDF document
    * 
    * @param file the file of the new PDF document
    * @return the pdf document
    */
   public static Document createDocument(File file) {
      final Document document = new Document();
      try {
         PdfWriter.getInstance(document, new FileOutputStream(file));
      }
      catch (final FileNotFoundException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final DocumentException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      return document;

   }


   /**
    * Creates a Table filled with a sample gesture and the corresponding name
    * 
    * @param set the gesture set
    * @param columns the number of columns of the table
    * @return the pdf table
    */
   public static PdfPTable createGestureSetTable(GestureSet set, int columns) {
      final PdfPTable table = createTable(columns);
      for (final GestureClass gestureClass : set.getGestureClasses()) {
         table.addCell(createImageCell(gestureClass));
      }

      for (int i = 0; i <= set.size() % 3; i++) {
         table.addCell(Constant.EMPTY_STRING);
      }

      return table;

   }


   public static PdfPTable createTable(int columns) {
      return new PdfPTable(columns);
   }


   /**
    * @see createGestureSetTable
    * @param set the gesture set
    * @return the pdf table
    */
   public static PdfPTable createGestureSetTable(GestureSet set) {
      return createGestureSetTable(set, NUMBER_OF_COLUMNS);
   }


   /**
    * Creates a table cell with a gesture sample and the name
    * 
    * @param gestureClass the gesture class
    * @return the table cell
    */
   public static PdfPCell createImageCell(GestureClass gestureClass) {
      final PdfPCell cell = new PdfPCell();
      try {
         final Image img = Image
               .getInstance(getImage(gestureClass), Color.black);
         cell.addElement(img);
         cell.setBackgroundColor(new Color(0xFF, 0xFF, 0xFF));
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         cell.addElement(createTitle(gestureClass.getName()));
      }
      catch (final BadElementException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      return cell;
   }


   /**
    * Creates an empty table cell
    * 
    * @return the empty table cell
    */
   public static PdfPCell createEmptyCell() {
      final PdfPCell cell = new PdfPCell();
      return cell;
   }


   /**
    * Creates a buffered image of the gesture class
    * 
    * @param gestureClass
    * @return
    */
   private static BufferedImage getImage(GestureClass gestureClass) {
      final JNote jnote = new JNote(200, 200);
      jnote.setNote(gestureClass.getDescriptor(SampleDescriptor.class)
            .getSamples().get(0).getNote());
      return jnote.getImage();
   }


   /**
    * Creates the Title of the Gesture Class
    * 
    * @param text
    * @return
    */
   private static Element createTitle(String text) {
      final Font font = FontFactory.getFont("Helvetica", 10, Font.BOLDITALIC,
            Color.BLACK);
      final Paragraph p = new Paragraph(text, font);
      p.setAlignment(Element.ALIGN_CENTER);
      p.setLeading(font.size() * 1.2f);
      return p;
   }

}
