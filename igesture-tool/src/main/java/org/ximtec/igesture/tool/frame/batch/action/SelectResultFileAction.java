/*
 * @(#)SelectResultFileAction.java  1.0   Dec 4, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 04, 2006     ukurmann    Initial Release
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


package org.ximtec.igesture.tool.frame.batch.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.tool.GestureConstants;


/**
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SelectResultFileAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "SelectResultFileAction";

   private JTextField textField;


   public SelectResultFileAction(JTextField textField) {
      super(KEY, GuiTool.getGuiBundle());
      this.textField = textField;
   }


   public void actionPerformed(ActionEvent event) {
      if (event.getSource() instanceof JButton) {
         final JFileChooser fileChooser = new JFileChooser();
         fileChooser.showOpenDialog((JButton)event.getSource());
         final File selectedFile = fileChooser.getSelectedFile();

         if (selectedFile != null) {
            textField.setText(selectedFile.getPath());
         }

      }

   } // actionPerformed

}
