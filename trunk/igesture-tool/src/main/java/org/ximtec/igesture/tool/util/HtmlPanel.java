/*
 * @(#)$Id$
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
 * 18.10.2008		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicHTML;

import org.sigtec.util.Constant;
import org.sigtec.util.MIME;


/**
 * Comment
 * @version 1.0 18.10.2008
 * @author Ueli Kurmann
 */
public class HtmlPanel extends JPanel {

   private static Logger LOGGER = Logger.getLogger(HtmlPanel.class.getName());

   private JEditorPane htmlArea;


   /**
    * 
    * @param url URL of the html document
    * @param dimension dimension of the html area
    */
   public HtmlPanel(URL url, Dimension dimension) {
      init(url, dimension);
   }


   private void init(final URL url, Dimension dimension) {

      final Dimension finalDimension = dimension;

      SwingUtilities.invokeLater(new Runnable() {

         @Override
         public void run() {
            setLayout(new BorderLayout());
            setPreferredSize(finalDimension);
            try {

               if (url == null) {
                  htmlArea = new JEditorPane();
               }
               else {
                  htmlArea = new JEditorPane(url);
               }
               htmlArea.setEditable(false);
               htmlArea.setContentType(MIME.toString(MIME.HTML));
               htmlArea.putClientProperty(BasicHTML.documentBaseKey,
                     HtmlPanel.class.getResource(Constant.SLASH));

               // htmlArea.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES,
               // Boolean.TRUE);

               htmlArea.setPreferredSize(finalDimension);
               JScrollPane scrollPane = new JScrollPane(htmlArea);
               scrollPane.setPreferredSize(finalDimension);
               scrollPane.setAutoscrolls(true);
               add(scrollPane, BorderLayout.CENTER);
            }
            catch (Exception e) {
               LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
            }

         }

      });

   }


   public void setHtmlContent(String htmlCode) {
      htmlArea.setText(htmlCode);
   }

}
