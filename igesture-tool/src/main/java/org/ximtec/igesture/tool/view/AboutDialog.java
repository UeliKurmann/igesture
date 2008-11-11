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


package org.ximtec.igesture.tool.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.sigtec.graphix.GuiBundle;
import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.tool.util.HtmlPanel;


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
    * @param key the key to be used for the lookup of information in the GUI
    *            bundle.
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
      LOGGER.log(Level.FINER, "Init About Dialog");
      super.init(key, guiBundle);
      int width = guiBundle.getWidth(key);
      int height = guiBundle.getHeight(key);

      setLayout(new BorderLayout());

      String resource = guiBundle.getProperty(key, RESOURCE);
      URL path = AboutDialog.class.getClassLoader().getResource(resource);

      Dimension dimension = new Dimension(width - 10, height - 50);
      HtmlPanel htmlPanel = new HtmlPanel(path, dimension);

      add(htmlPanel, BorderLayout.CENTER);

      //FIXME i18!
      JButton closeButton = new JButton("Close");
      closeButton.addMouseListener(new MouseAdapter() {

         @Override
         public void mouseClicked(MouseEvent arg0) {
            super.mouseClicked(arg0);
            closeDialog();
         }
      });
      JPanel panel = new JPanel();
      panel.add(closeButton);
      add(panel, BorderLayout.SOUTH);
      pack();
   } // init


   private void closeDialog() {
      setVisible(false);
      dispose();
   } // closeDialog

}
