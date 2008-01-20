/*
 * @(#)SaveAsAction.java   1.0   Jan 16, 2008
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  Save the gesture map project in another file.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Jan 16, 2008     crocimi    Initial Release
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
import org.sigtec.util.MIME;
import org.ximtec.igesture.geco.Geco;
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;


/**
 * Save the gesture map project in another file.
 * 
 * @version Jan 16, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class SaveAsAction extends BasicAction {

   private MainView mainView;


   public SaveAsAction(MainView mainView) {
      super(Constant.SAVE_AS_ACTION, Geco.getGuiBundle());
      this.mainView = mainView;
   }


   /**
    * Creates a new mapping
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      showSaveDialog();

   } // actionPerformed


   private void showSaveDialog() {

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new ExtensionFileFilter(MIME
            .getExtension(MIME.XML),
            new String[] { MIME.getExtension(MIME.XML) }));
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
            if (!ext.equals(Constant.DOT + MIME.getExtension(MIME.XML))) {
               pFile = new File(selectedFile.getPath()
                     + org.sigtec.util.Constant.DOT
                     + MIME.getExtension(MIME.XML));
            }
            else {
               pFile = selectedFile;
            }
            String projectName = pFile.getName();
            projectName = projectName.substring(0, projectName.length() - 4);

            mainView.getModel().setProjectFile(pFile);
            mainView.getModel().setProjectName(projectName);

            // update view
            mainView.initProjectView(projectName);
            mainView.updateGestureList();
            mainView.enableMenuItem();

            mainView.getModel().setNeedSave(true);
            (new SaveProjectAction(mainView)).save();
            (new OpenProjectAction(mainView)).exportConfiguration();
         }

      }

   }
}
