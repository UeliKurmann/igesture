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
 * 					bsigner		Initial Release
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.sigtec.graphix.GuiBundle;
import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicTextField;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.util.ExtensionFileFilter;
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
      init();
   }
   
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
      
      //
      //this.add(cancelButton,  
        //    new GridBagConstraints(0,3,3,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          //  new Insets(10,10,10,10),0,0 ) );
      
      //dialog.add(GuiTool.createLabel(GecoConstants.PROJECT_FILE));    
      this.add(mainPanel,
            new GridBagConstraints(0,3,3,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                  new Insets(20,20,20,20),0,0 ) );
   }
   
   private class BrowseListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         //display file chooser dialog

         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       //  fileChooser.setFileFilter(new ExtensionFileFilter(XML_EXTENSION,
         //      new String[] {XML_EXTENSION}));
         int status = fileChooser.showDialog(null,"Open");
         if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if(selectedFile != null){
               filePath=selectedFile.getAbsolutePath()+"\\";
                String fileName= selectedFile.getAbsolutePath()+"\\"+
                   NewProjectDialog.this.projectTextField.getText();
                if(NewProjectDialog.this.projectTextField.getText().equals("")){
                   fileName+="."+XML_EXTENSION;
                }
                fileTextField.setText(fileName);
            }
         } else if (status == JFileChooser.CANCEL_OPTION) {
           
         }

      }
   }
   
   private class CreateListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
      
         if((fileTextField.getText()!=null)&&(!fileTextField.getText().equals(""))&&
               (projectTextField.getText()!=null)&&(!projectTextField.getText().equals(""))){
            String fileName= fileTextField.getText();

            try{
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            File temp = new File(fileName);
            //update model
            view.getModel().setProjectFile(temp);
            view.getModel().setProjectName(projectTextField.getText());
            view.getModel().clearData();
            
            //update view
            view.initProjectView(projectTextField.getText());
            view.enableMenuItem();
            }
            catch(IOException ioe){
               ioe.printStackTrace();
            }
         }
         
         
      NewProjectDialog.this.dispose();
      }
      
   }
   
   private class CancelListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         NewProjectDialog.this.dispose();
      }
      
   }
   
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
      
   }
      
      
   }
}
