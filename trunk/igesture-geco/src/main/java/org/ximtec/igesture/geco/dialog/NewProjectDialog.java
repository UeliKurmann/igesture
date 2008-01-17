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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicTextField;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainModel;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.gui.action.SaveProjectAction;
import org.ximtec.igesture.geco.xml.XMLGeco;
import org.ximtec.igesture.graphics.SwingTool;


/**
 * New project dialog
 * 
 * @version 0.9, Dec 3, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class NewProjectDialog extends BasicDialog {

   private MainView view;

   private int DIALOG_WIDTH = 700;
   private int DIALOG_HEIGHT = 300;

   private static String XML_EXTENSION = "xml";
   private BasicTextField fileTextField;
   private BasicTextField projectTextField;
   private JButton createButton;
   private String filePath = Constant.EMPTY_STRING;
   
  

   public NewProjectDialog(MainView view) {
      this.view = view;
      setModal(true);
      init();
   }// NewProjectDialog


   /**
    * Initialises the dialog.
    */
   private void init() {
      
      try {
         filePath = new File(ClassLoader.getSystemResource(org.ximtec.igesture.geco.gui.Constant.MAPPINGS)
               .toURI()).getPath()+ Constant.BACKSLASH;;
      }
      catch (URISyntaxException e) {
         filePath = new File(ClassLoader.getSystemResource(org.ximtec.igesture.geco.gui.Constant.MAPPINGS)
               .getPath()).getPath()+ Constant.BACKSLASH;;
      }

      
      JPanel mainPanel = new JPanel();
      mainPanel.setBorder(new TitledBorder(new BevelBorder(0, Color.gray,
            Color.gray), Constant.NEW_PROJECT_TITLE));

      setTitle(Constant.NEW_PROJECT_TITLE);
      mainPanel.setLayout(new GridBagLayout());
      setLayout(new GridBagLayout());
      Point p = new Point(view.getLocation().x + 50, view.getLocation().y + 100);
      setLocation(p);
      setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));

      mainPanel.add(GuiTool.createLabel(Constant.PROJECT_NAME),
            new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                  GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

      projectTextField = GuiTool
            .createTextField(Constant.PROJECT_NAME_TEXT_FIELD);
      projectTextField.getDocument().addDocumentListener(
            new MyDocumentListener());
      projectTextField.addKeyListener(new MyKeyListener());
      projectTextField.setText(Constant.EMPTY_STRING);
      mainPanel.add(projectTextField, new GridBagConstraints(1, 0, 1, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(10, 10, 10, 10), 0, 0));

      mainPanel.add(GuiTool.createLabel(Constant.PROJECT_LOCATION),
            new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
                  GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

      fileTextField = GuiTool.createTextField(Constant.PROJECT_FILE_TEXT_FIELD);
      fileTextField.setEditable(false);
      fileTextField.setText(filePath);
      mainPanel.add(fileTextField, new GridBagConstraints(1, 1, 1, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(10, 10, 10, 10), 0, 0));

      JButton browseButton = SwingTool.createButton(Constant.BROWSE);
      browseButton.addActionListener(new BrowseListener());
      mainPanel.add(browseButton, new GridBagConstraints(2, 1, 1, 1, 0, 0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10,
                  10, 10, 10), 0, 0));

      createButton = SwingTool.createButton(Constant.CREATE);
      createButton.setEnabled(false);
      createButton.addActionListener(new CreateListener());
      JButton cancelButton = SwingTool.createButton(Constant.CANCEL);
      cancelButton.addActionListener(new CancelListener());

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(1, 1, 20, 0));
      buttonPanel.add(createButton);
      buttonPanel.add(cancelButton);
      mainPanel.add(buttonPanel, new GridBagConstraints(0, 3, 3, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10,
                  10, 10, 10), 0, 0));

      this.add(mainPanel, new GridBagConstraints(0, 3, 3, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20,
                  20, 20, 20), 0, 0));
   }// init


   /**
    * Shows the dialog.
    */
   public void showDialog() {
      reset();
      setVisible(true);
   }// showDialog


   /**
    * Resets the dialog.
    */
   public void reset() {
      fileTextField.setText(filePath);
      projectTextField.setText(Constant.EMPTY_STRING);
   }// reset

   /**
    * Listener for browse event.
    */
   private class BrowseListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {
         // display file chooser dialog

         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         //fileChooser.setCurrentDirectory(new File(filePath));
         int status = fileChooser.showDialog(null, "Open");
         if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {

               String fileName = Constant.EMPTY_STRING;
               if (selectedFile.getAbsolutePath().charAt(
                     selectedFile.getAbsolutePath().length() - 1) != '\\') {
                  fileName = selectedFile.getAbsolutePath() + Constant.BACKSLASH
                        + NewProjectDialog.this.projectTextField.getText();
                  if (!NewProjectDialog.this.projectTextField.getText().equals(
                        Constant.EMPTY_STRING)) {
                     fileName += Constant.DOT + XML_EXTENSION;
                  }
                  filePath = selectedFile.getAbsolutePath() + Constant.BACKSLASH;
               }
               else {
                  fileName = selectedFile.getAbsolutePath()
                        + NewProjectDialog.this.projectTextField.getText();
                  if (!NewProjectDialog.this.projectTextField.getText().equals(
                        Constant.EMPTY_STRING)) {
                     fileName += org.sigtec.util.Constant.DOT + XML_EXTENSION;
                  }
                  filePath = selectedFile.getAbsolutePath();
               }
               fileTextField.setText(fileName);
            }
         }
         else if (status == JFileChooser.CANCEL_OPTION) {

         }

      }
   }

   /**
    * Listener for create project event.
    */
   private class CreateListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {

            createProject();
      }// actionPerformed

   }

   /**
    * Listener for cancel event.
    */
   private class CancelListener implements ActionListener {

      public void actionPerformed(ActionEvent e) {
         NewProjectDialog.this.dispose();
      }// actionPerformed

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
         // Plain text components do not fire these events
      }


      public void update() {
         if ((!projectTextField.getText().equals(Constant.EMPTY_STRING))) {
            NewProjectDialog.this.createButton.setEnabled(true);
            NewProjectDialog.this.fileTextField
                  .setText(NewProjectDialog.this.filePath
                        + NewProjectDialog.this.projectTextField.getText()
                        + org.sigtec.util.Constant.DOT + XML_EXTENSION);
         }
         else {
            NewProjectDialog.this.createButton.setEnabled(false);
            NewProjectDialog.this.fileTextField
                  .setText(NewProjectDialog.this.filePath
                        + NewProjectDialog.this.projectTextField.getText());
         }
      }// update
   }
   
   /**
    * Listener for key event.
    */
   
   private class MyKeyListener implements KeyListener{
      public void keyPressed(KeyEvent e){ 
         if (e.getKeyCode()==KeyEvent.VK_ENTER){
            createProject();
         }
      }

      public void keyReleased(KeyEvent e){
      }

      public void keyTyped(KeyEvent e){
      }
   }
   
   public void createProject(){
      if ((!fileTextField.getText().equals(Constant.EMPTY_STRING))
            && (!projectTextField.getText().equals(Constant.EMPTY_STRING))) {
         String fileName = fileTextField.getText();

         boolean ok = true;
         File temp = new File(fileName);
         if (temp.exists()) {
            int n = JOptionPane.showConfirmDialog(NewProjectDialog.this,
                  Constant.OVERWRITE_FILE, Constant.EMPTY_STRING,
                  JOptionPane.YES_NO_OPTION);
            if (n != 0) {
               ok = false;
            }
         }

         if (ok) {
            //close dialog
            NewProjectDialog.this.dispose();
            
            // update model
            File gsFile;
            try {
               gsFile = new File(ClassLoader.getSystemResource(
                     MainModel.GESTURE_SET).toURI());
            }
            catch (URISyntaxException ex) {
               gsFile = new File(ClassLoader.getSystemResource(
                     MainModel.GESTURE_SET).getPath());
            }

            GestureSet gestureSet = XMLGeco.importGestureSet(gsFile).get(0);

            view.getModel().clearData();
            view.getModel().initRecogniser(gestureSet);
            view.getModel().loadGestureSet(gestureSet);

            view.getModel().setProjectFile(temp);
            view.getModel().setGestureSetFileName(MainModel.GESTURE_SET);
            view.getModel().setProjectName(projectTextField.getText());

            // update view
            view.initProjectView(projectTextField.getText());
            view.enableMenuItem();
            view.updateGestureList();
            

            // save
            (new SaveProjectAction(view)).save();
         }
      }

   }
}