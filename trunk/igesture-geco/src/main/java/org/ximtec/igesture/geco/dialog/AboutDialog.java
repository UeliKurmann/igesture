/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   About dialog.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Feb 01, 2007     ukurmann    Initial Release
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


package org.ximtec.igesture.geco.dialog;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicHTML;

import org.sigtec.graphix.GuiBundle;
import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.geco.util.Constant;
import org.ximtec.igesture.geco.util.GuiBundleTool;
import org.ximtec.igesture.graphics.SwingTool;


/**
 * About dialog.
 * 
 * @version 1.0 Feb 2007
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class AboutDialog extends BasicDialog {

   private static final Logger LOGGER = Logger.getLogger(AboutDialog.class
         .getName());

   /**
    * The key used to retrieve dialog details from the resource bundle.
    */
   public static final String KEY = "AboutDialog";
   public static final String RESOURCE = "Resource";


   /**
    * Constructs a new AboutDialog without getting any information from a GUI
    * bundle.
    */
   public AboutDialog() {
      super();
   }


   /**
    * Constructs a new AboutDialog.
    * @param key the key to be used for the lookup of information in
    *            the GUI bundle.
    * @param guiBundle the GUI bundle to be used to create the about dialog.
    */
   public AboutDialog(String key, GuiBundle guiBundle) {
      super(key, guiBundle);
   }


   /**
    * Initialises the dialogue.
    * @param key the key to be used for the lookup of dialogue information in the
    *            GUI bundle.
    * @param guiBundle the GUI bundle to be used to create the dialogue.
    */
   protected void init(String key, GuiBundle guiBundle) {
      super.init(key, guiBundle);
      int width = guiBundle.getWidth(key);
      int height = guiBundle.getHeight(key);
      JEditorPane aboutField;

      try {
         String resource = guiBundle.getProperty(key, RESOURCE);
         URL path = AboutDialog.class.getClassLoader().getResource(resource);
         aboutField = new JEditorPane(path);
         aboutField.setEditable(false);
         aboutField.setContentType("text/html");
         aboutField.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES,
               Boolean.TRUE);
         aboutField.putClientProperty(BasicHTML.documentBaseKey,
               AboutDialog.class.getResource(Constant.SLASH));
         aboutField.setPreferredSize(new Dimension(width - 10, height - 50));
         JScrollPane scrollPane = new JScrollPane(aboutField);
         scrollPane.setPreferredSize(new Dimension(width - 10, height - 50));
         scrollPane.setAutoscrolls(true);
         addComponent(scrollPane, SwingTool.createGridBagConstraint(0, 0));
      }
      catch (IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

      JButton okButton = GuiTool.createButton(Constant.CLOSE_ABOUT_BUTTON, GuiBundleTool.getBundle());
      okButton.addMouseListener(new MouseAdapter() {

         @Override
         public void mouseClicked(MouseEvent arg0) {
            super.mouseClicked(arg0);
            closeDialog();
         }
      });

      addComponent(okButton, SwingTool.createGridBagConstraint(0, 1));
      pack();
   } // init


   private void closeDialog() {
      setVisible(false);
      dispose();
   } // closeDialog

}
