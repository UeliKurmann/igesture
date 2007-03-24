/*
 * @(#)JAboutDialog.java	1.0   Feb 01, 2007
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   About dialog.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Feb 01, 2007     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.util;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicHTML;

import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.util.Constant;
import org.ximtec.igesture.tool.GestureConstants;


/**
 * About dialog.
 * 
 * @version 1.0 Feb 2007
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class JAboutDialog extends BasicDialog {

   private static final Logger LOGGER = Logger.getLogger(JAboutDialog.class
         .getName());


   public JAboutDialog(int width, int height, URL path) {
      super(GestureConstants.COMMON_ABOUT, SwingTool.getGuiBundle());
      init(width, height, path);
   }


   private void init(int width, int height, URL path) {
      setIconImage(IconLoader.getImage(IconLoader.INFORMATION));
      setSize(width, height);
      JEditorPane aboutField;

      try {
         aboutField = new JEditorPane(path);
         aboutField.setEditable(false);
         aboutField.setContentType("text/html");
         aboutField.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES,
               Boolean.TRUE);
         aboutField.putClientProperty(BasicHTML.documentBaseKey,
               JAboutDialog.class.getResource("/"));
         aboutField.setSize(width - 10, height - 50);
         JScrollPane scrollPane = new JScrollPane(aboutField);
         scrollPane.setPreferredSize(new Dimension(width - 10, height - 50));
         scrollPane.setAutoscrolls(true);
         addComponent(scrollPane, SwingTool.createGridBagConstraint(0, 0));
      }
      catch (IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      BasicButton okButton = new BasicButton(GestureConstants.COMMON_CLOSE,
            SwingTool.getGuiBundle());
      okButton.addMouseListener(new MouseAdapter() {

         @Override
         public void mouseClicked(MouseEvent arg0) {
            super.mouseClicked(arg0);
            closeDialog();
         }
      });

      addComponent(okButton, SwingTool.createGridBagConstraint(0, 1));
      this.pack();
   } // init


   private void closeDialog() {
      setVisible(false);
      dispose();
   } // closeDialog

}
