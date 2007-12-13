/*
 * @(#)NewProjectDialog.java	1.0   Dec 3, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  New project dialog
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 3, 2007		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicTextField;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainModel;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.GUI.action.SaveProjectAction;
import org.ximtec.igesture.geco.xml.XMLGeco;
import org.ximtec.igesture.graphics.SwingTool;



/**
 *  New project dialog
 *  
 * @version 1.0 Dec 3, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class NewProjectDialog extends BasicDialog{
   
   
   private GecoMainView view;
   
   private int DIALOG_WIDTH = 400;
   private int DIALOG_HEIGHT = 300;
   
   private static String XML_EXTENSION = "xml";
   private  BasicTextField fileTextField;
   private  BasicTextField projectTextField;
   private BasicButton createButton;
   private String filePath="C:\\";

   
   public NewProjectDialog(GecoMainView view){
      this.view=view;
      setModal(true);
      init();
   }//NewProjectDialog
   
   /**
    * Inits the dialog.
    */
   private void init(){
      JPanel mainPanel = new JPanel();
      mainPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.NEW_PROJECT_DIALOG_TITLE));
     
      
      this.setTitle(GecoConstants.NEW_PROJECT_DIALOG_TITLE);
      mainPanel.setLayout(new GridBagLayout());
      this.setLayout(new GridBagLayout());
      Point p = new Point(view.getLocation().x+200, view.getLocation().y+100);
      this.setLocation(p);
      this.setSize(new Dimension( DIALOG_WIDTH, DIALOG_HEIGHT ));

      mainPanel.add(GuiTool.createLabel(GecoConstants.PROJECT_NAME),  new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(10,10,10,10),0,0 ) );
      
      projectTextField = GuiTool.createTextField(GecoConstants.PROJECT_NAME_TEXT_FIELD);
      projectTextField.getDocument().addDocumentListener(new MyDocumentListener());
      projectTextField.setText("");
      mainPanel.add(projectTextField,  
            new GridBagConstraints(1,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(10,10,10,10),0,0 ) );
      
      
      mainPanel.add(GuiTool.createLabel(GecoConstants.PROJECT_FILE),  new GridBagConstraints(0,1,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(10,10,10,10),0,0 ) );
      
      fileTextField = GuiTool.createTextField(GecoConstants.PROJECT_FILE_TEXT_FIELD);
      fileTextField.setEditable(false);
      fileTextField.setText(filePath);
      mainPanel.add(fileTextField,  
            new GridBagConstraints(1,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(10,10,10,10),0,0 ) );
      
      BasicButton browseButton = SwingTool.createButton(GecoConstants.BROWSE);
      browseButton.addActionListener(new BrowseListener());
      mainPanel.add(browseButton,  
            new GridBagConstraints(2,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(10,10,10,10),0,0 ) );
      
      createButton = SwingTool.createButton(GecoConstants.CREATE);
      createButton.setEnabled(false);
      createButton.addActionListener(new CreateListener());
      BasicButton cancelButton = SwingTool.createButton(GecoConstants.CANCEL);
      cancelButton.addActionListener(new CancelListener());
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(1,1, 20, 0));
      buttonPanel.add(createButton);
      buttonPanel.add(cancelButton);
      mainPanel.add(buttonPanel,  
            new GridBagConstraints(0,3,3,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(10,10,10,10),0,0 ) );
  
      this.add(mainPanel,
            new GridBagConstraints(0,3,3,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                  new Insets(20,20,20,20),0,0 ) );
   }//init
   
   
   /**
    * Shows the dialog.
    */
   public void showDialog(){
      reset();
      setVisible(true);
   }//showDialog
   
   /**
    * Resets the dialog.
    */
   public void reset(){
      fileTextField.setText(filePath);
      projectTextField.setText("");
   }//reset
   
   
   /**
    * Listener for browse event.
    */
   private class BrowseListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         //display file chooser dialog

         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         int status = fileChooser.showDialog(null,"Open");
         if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if(selectedFile != null){
               
                String fileName = "";
                 if (selectedFile.getAbsolutePath().charAt(selectedFile.getAbsolutePath().length()-1)!='\\'){
                      fileName = selectedFile.getAbsolutePath()+"\\"+
                      NewProjectDialog.this.projectTextField.getText();
                      if(!NewProjectDialog.this.projectTextField.getText().equals("")){
                         fileName+="."+XML_EXTENSION;
                      }
                      filePath=selectedFile.getAbsolutePath()+"\\";
                   }
                   else{
                      fileName = selectedFile.getAbsolutePath()+
                      NewProjectDialog.this.projectTextField.getText();
                      if(!NewProjectDialog.this.projectTextField.getText().equals("")){
                         fileName+="."+XML_EXTENSION;
                      }
                      filePath=selectedFile.getAbsolutePath();
                   }
                fileTextField.setText(fileName);
            }
         } else if (status == JFileChooser.CANCEL_OPTION) {
           
         }

      }
   }
   
   
   /**
    * Listener for create project event.
    */
   private class CreateListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
      
         if((!fileTextField.getText().equals(""))&& (!projectTextField.getText().equals(""))){
            String fileName= fileTextField.getText();

            boolean ok=true;
            File temp = new File(fileName);
            if (temp.exists()){
               int n = JOptionPane.showConfirmDialog(
                     NewProjectDialog.this,
                     GecoConstants.OVERWRITE_FILE,
                     "",
                     JOptionPane.YES_NO_OPTION);
               if (n!=0){
                  ok=false;
               }
            }
              
            if(ok){
               //update model
               GestureSet gestureSet = XMLGeco.importGestureSet(
                     new File(ClassLoader.getSystemResource(GecoMainModel.GESTURE_SET).getFile())).get(0);
               view.getModel().clearData();
               view.getModel().initRecogniser(gestureSet);
               view.getModel().loadGestureSet(gestureSet);
               
               view.getModel().setProjectFile(temp);
               view.getModel().setGestureSetFileName(view.getModel().GESTURE_SET);
               view.getModel().setProjectName(projectTextField.getText());
               
               
               //update view
               view.initProjectView(projectTextField.getText());
               view.enableMenuItem();
               view.updateGestureList();
               NewProjectDialog.this.dispose();
               
               //save
               (new SaveProjectAction(view)).save();
            }
         }
         
         
      }//actionPerformed
      
   }
   
   
   /**
    * Listener for cancel event.
    */
   private class CancelListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         NewProjectDialog.this.dispose();
      }//actionPerformed
      
   }
   
   
   /**
    * Listener for project name edit event.
    */
   private class MyDocumentListener implements DocumentListener {
   
      public void insertUpdate(DocumentEvent e) {
         update();
     }
     public void removeUpdate(DocumentEvent e) {
         update();
     }
     public void changedUpdate(DocumentEvent e) {
         //Plain text components do not fire these events
     }

      public void update(){
         if((!projectTextField.getText().equals(""))){
            NewProjectDialog.this.createButton.setEnabled(true);
            NewProjectDialog.this.fileTextField.setText(NewProjectDialog.this.filePath+
                  NewProjectDialog.this.projectTextField.getText()+"."+XML_EXTENSION);
         }
         else{
            NewProjectDialog.this.createButton.setEnabled(false);
         NewProjectDialog.this.fileTextField.setText(NewProjectDialog.this.filePath+
               NewProjectDialog.this.projectTextField.getText());
         }
      
      }//update
   }
}
