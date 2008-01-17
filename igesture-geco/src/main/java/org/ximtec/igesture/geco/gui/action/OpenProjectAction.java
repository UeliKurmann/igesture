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


package org.ximtec.igesture.geco.gui.action;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.MIME;
import org.ximtec.igesture.geco.Geco;
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;
import org.ximtec.igesture.geco.xml.XMLGeco;
import org.ximtec.igesture.geco.xml.XMLImportGeco;
import org.ximtec.igesture.geco.Configuration;;


/**
 * Opens an existing gesture mapping project.
 * 
 * @version 0.9, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class OpenProjectAction extends BasicAction {

   /**
    * The key used to retrieve action details from the resource bundle.
    */
   public static final String KEY = "OpenProjectAction";

   private MainView mainView;


   public OpenProjectAction(MainView mainView) {
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
               Constant.SAVE_DIALOG_TITLE, Constant.EMPTY_STRING,
               JOptionPane.YES_NO_OPTION);
         if (n == 0) {
            (new SaveProjectAction(mainView)).save();
         }
      }

      // display file chooser
      displayFileChooser();
   } // actionPerformed


   private void displayFileChooser() {
      String filePath;
      try {
         filePath = new File(ClassLoader.getSystemResource(org.ximtec.igesture.geco.gui.Constant.MAPPINGS)
               .toURI()).getPath()+ Constant.BACKSLASH;;
      }
      catch (URISyntaxException e) {
         filePath = new File(ClassLoader.getSystemResource(org.ximtec.igesture.geco.gui.Constant.MAPPINGS)
               .getPath()).getPath()+ Constant.BACKSLASH;;
      }

      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new ExtensionFileFilter(MIME
            .getExtension(MIME.XML),
            new String[] { MIME.getExtension(MIME.XML) }));
      fileChooser.setCurrentDirectory(new File(filePath));
      int status = fileChooser.showOpenDialog(null);

      if (status == JFileChooser.APPROVE_OPTION) {
         File selectedFile = fileChooser.getSelectedFile();
         if (selectedFile != null) {
            String ext = "";
               if(selectedFile.getName().length()>=4){
                  ext = selectedFile.getName().substring(
                  selectedFile.getName().length() - 4,
                  selectedFile.getName().length());
               }
            if (ext.equals(Constant.DOT+MIME.getExtension(MIME.XML))) {
                  openProject(selectedFile);
                  exportConfiguration();
                  //writeFilePath(selectedFile); 
            }

         }
         
      }
}

   
   public void openProject(File selFile){
      File selectedFile = selFile;
    
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
            mainView.disableSaveButton();
         }
      }
   }
   

   
   
   
   public void exportConfiguration(){
         File file;
         try {
            file = new File(ClassLoader.getSystemResource(Geco.GECO_CONFIGURATION).toURI());
         }
         catch (URISyntaxException e) {
            file = new File(ClassLoader.getSystemResource(Geco.GECO_CONFIGURATION).getPath());
         }

         Configuration configuration = mainView.getModel().getGestureConfiguration();
         List<String> devices = configuration.getInputDevices();
         String s = configuration.getInputDeviceName();
         boolean[] arr  = new boolean[devices.size()];
         for(int i=0; i<devices.size(); i++){
            if(s.equals(devices.get(i))){
               arr[i]=true;
            }
         }
         boolean min = configuration.getMinimize();
         XMLGeco.exportGestureConfiguration(file, devices, arr, min, mainView.getModel().getProjectFile().getPath());
      }
}
