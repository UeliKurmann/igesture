/*
 * @(#)OpenProjectAction.java	1.0   Nov 15, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Opens an existing gesture mapping project.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2007		crocimi     Initial Release
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


package org.ximtec.igesture.geco.GUI.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.MIME;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;
import org.ximtec.igesture.geco.xml.XMLImportGeco;


/**
 * Opens an existing gesture mapping project.
 * 
 * @version 0.9,, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class OpenProjectAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "OpenProjectAction";

   private GecoMainView mainView;


   public OpenProjectAction(GecoMainView mainView) {
      super(KEY, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Creates a new mapping
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      // display save dialog, if needed

      if (mainView.getModel().needSave()) {
         int n = JOptionPane.showConfirmDialog(mainView,
               GecoConstants.SAVE_DIALOG_TITLE, "", JOptionPane.YES_NO_OPTION);
         if (n == 0) {
            (new SaveProjectAction(mainView)).save();
         }
      }

      // display file chooser
      displayFileChooser();
   } // actionPerformed


   private void displayFileChooser() {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new ExtensionFileFilter(MIME
            .getExtension(MIME.XML),
            new String[] { MIME.getExtension(MIME.XML) }));
      int status = fileChooser.showOpenDialog(null);

      if (status == JFileChooser.APPROVE_OPTION) {
         File selectedFile = fileChooser.getSelectedFile();
         if (selectedFile != null) {
            String ext = selectedFile.getName().substring(
                  selectedFile.getName().length() - 3,
                  selectedFile.getName().length());

            if (ext.equals(MIME.getExtension(MIME.XML))) {
               XMLImportGeco XMLImport = new XMLImportGeco(mainView);
               XMLImport.importProject(selectedFile);

               if (!XMLImport.hasError()) {
                  String projectName = selectedFile.getName();
                  projectName = projectName.substring(0,
                        projectName.length() - 4);

                  // update model
                  mainView.getModel().clearData();

                  if (XMLImport.getGestureSet() != null) {
                     mainView.getModel().initRecogniser(
                           XMLImport.getGestureSet());
                     mainView.getModel().loadGestureSet(
                           XMLImport.getGestureSet());
                     List<GestureToActionMapping> list = XMLImport.getMappings();

                     for (GestureToActionMapping mapping : list) {
                        mainView.getModel().addMapping(mapping);
                     }

                     mainView.getModel().setProjectFile(selectedFile);
                     mainView.getModel().setProjectName(projectName);
                     mainView.getModel().setGestureSetFileName(
                           XMLImport.getGestureSetFileName());
                     mainView.getModel().setNeedSave(false);

                     // update view
                     mainView.initProjectView(projectName);
                     mainView.updateGestureList();
                     mainView.enableMenuItem();
                  }

               }

            }

         }

      }

   }

}
