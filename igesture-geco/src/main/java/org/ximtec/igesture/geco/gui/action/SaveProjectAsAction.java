/*
 * @(#)SaveAsAction.java   1.0   Jan 16, 2008
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   Saves the mappings with a new name.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Jan 16, 2008     crocimi     Initial Release
 * Jan 20, 2008     bsigner     Cleanup
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

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.FileHandler;
import org.sigtec.util.MIME;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.util.Constant;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;
import org.ximtec.igesture.geco.util.GuiBundleTool;


/**
 * Saves the mappings with a new name.
 * 
 * @version 0.9, Jan 2008
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SaveProjectAsAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "SaveProjectAsAction";

   private MainView mainView;


   public SaveProjectAsAction(MainView mainView) {
      super(KEY, GuiBundleTool.getBundle());
      this.mainView = mainView;
   }


   /**
    * Saves the mappings.
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      showSaveDialog();
   } // actionPerformed


   private void showSaveDialog() {
      String xmlExtension = MIME.getExtension(MIME.XML);
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new ExtensionFileFilter(xmlExtension,
            new String[] { xmlExtension }));
      fileChooser.setCurrentDirectory(mainView.getModel().getProjectFile());
      int status = fileChooser.showSaveDialog(null);

      if (status == JFileChooser.APPROVE_OPTION) {
         File selectedFile = fileChooser.getSelectedFile();

         if (selectedFile != null) {
            String ext = Constant.EMPTY_STRING;

            if (selectedFile.getName().length() >= 4) {
               ext = selectedFile.getName().substring(
                     selectedFile.getName().length() - 4,
                     selectedFile.getName().length());
            }

            File pFile = null;

            if (!ext.equals(Constant.DOT + xmlExtension)) {
               pFile = FileHandler.createFile(selectedFile.getPath(),
                     xmlExtension);
            }
            else {
               pFile = selectedFile;
            }

            String projectName = pFile.getName();
            projectName = projectName.substring(0, projectName.length() - 4);
            mainView.getModel().setProjectFile(pFile);
            mainView.getModel().setProjectName(projectName);
            mainView.initProjectView(projectName);
            mainView.updateGestureList();
            mainView.enableMenuItem();
            mainView.getModel().setNeedSave(true);
            (new SaveProjectAction(mainView)).save();
            (new OpenProjectAction(mainView)).exportConfiguration();
         }

      }

   } // showSaveDialog

}
