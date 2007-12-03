/*
 * @(#)NewProjectAction.java	1.0   Nov 15, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   represent action for creating a new gesture mapping project
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 	Nov 15, 2007	crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI.action;



import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicAction;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;


/**
 * Creates a new data source.
 * 
 * @version 1.0, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class NewProjectAction extends BasicAction {

   private GecoMainView mainView;


   public NewProjectAction(GecoMainView mainView) {
      super(GecoConstants.NEW_PROJECT_ACTION, GuiTool.getGuiBundle());
      this.mainView = mainView;
   }
   
   private static final String XML_EXTENSION = ".xml";


   /**
    * Creates a new mapping
    * 
    * @param event the action event.
    */
   public void actionPerformed(ActionEvent event) {
      //display save dialog, if needed
      /*
      if (mainView.getModel().needSave()){
         int n = JOptionPane.showConfirmDialog(
               mainView,
               GecoConstants.SAVE_DIALOG_TITLE,
               "",
               JOptionPane.YES_NO_OPTION);
         if (n!=0){
            (new SaveProjectAction(mainView)).save();
         }
      }
      */
      //display new project dialog
      mainView.getComponentHandler().getNewProjectDialog().setVisible(true);

      
      
     

   } // actionPerformed
   
   
   private void showFileDialog(){
      //display file chooser dialog
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new ExtensionFileFilter(XML_EXTENSION,
            new String[] {XML_EXTENSION}));
      int status = fileChooser.showDialog(null,"Save");
      if (status == JFileChooser.APPROVE_OPTION) {
         File selectedFile = fileChooser.getSelectedFile();
         if(selectedFile != null){
            
            String ext = "";
            if (selectedFile.getName().length()>4){
               ext = selectedFile.getName().substring(selectedFile.getName().length()-4,
                  selectedFile.getName().length());
            }
            File temp;
            try{
               String fileName;
               String projectName ="";
               if(ext.equals(XML_EXTENSION)){
                  projectName = selectedFile.getName().substring(0,selectedFile.getName().length()-4);
                  fileName = selectedFile.getAbsolutePath();
                }else{
                   projectName = selectedFile.getName();
                   fileName = selectedFile.getAbsolutePath()+XML_EXTENSION;
             }

               BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
               temp = new File(fileName);
               //update model
               mainView.getModel().setProjectFile(temp);
               mainView.getModel().setProjectName(projectName);
               mainView.getModel().clearData();
               
               //update view
               mainView.initProjectView(projectName);
               mainView.enableMenuItem();

            }catch(IOException e){
               e.printStackTrace();
            }

         }
      } else if (status == JFileChooser.CANCEL_OPTION) {
        
      }

   }
   
   }
