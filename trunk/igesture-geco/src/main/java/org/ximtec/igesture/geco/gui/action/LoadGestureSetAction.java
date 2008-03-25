/*
 * @(#)ActionLoadGestureSet.java	1.0   Nov 20, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Loads a gesture set
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2007     crocimi     Initial Release
 * Jan 15, 2008     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.MIME;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.Geco;
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;
import org.ximtec.igesture.util.XMLTool;


/**
 * Loads a gesture set
 * 
 * @version 0.9, Nov 20, 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class LoadGestureSetAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "LoadGestureSetAction";

   private MainView mainView;


   public LoadGestureSetAction(MainView mainView) {
      super(KEY, Geco.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Opens a dialog to allow the user to choose a gesture set.
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      // ask confirm
      int n = 0;
      if (!mainView.getModel().getMappings().isEmpty()) {
         n = JOptionPane.showConfirmDialog(mainView,
               Constant.LOAD_GESTURE_SET_CONFIRMATION, Constant.EMPTY_STRING,
               JOptionPane.YES_NO_OPTION);
      }
      if (n == 0) {
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileFilter(new ExtensionFileFilter(MIME
               .getExtension(MIME.XML), new String[] { MIME
               .getExtension(MIME.XML) }));
         int status = fileChooser.showOpenDialog(null);
         if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
               String ext = selectedFile.getName().substring(
                     selectedFile.getName().length() - 3,
                     selectedFile.getName().length());
               if (ext.equals(MIME.getExtension(MIME.XML))) {
                  // update model
                  mainView.getModel().clearData();
                  mainView.getModel().loadGestureSet(
                        loadGestureSet(selectedFile));
                  mainView.getModel().setGestureSetFileName(
                        selectedFile.getAbsolutePath());
                  // update view
                  mainView.updateGestureList();
                  mainView.enableSaveButton();
               }

            }

         }

      }

   } // actionPerformed


   /**
    * Loads the gesture set.
    * 
    * @param file the file containing the gesture set to be loaded.
    */
   public GestureSet loadGestureSet(File file) {
      return XMLTool.importGestureSet(file).get(0);
   } // loadGestureSet

}
