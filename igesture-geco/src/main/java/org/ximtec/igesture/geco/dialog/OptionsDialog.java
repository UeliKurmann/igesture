/*
 * @(#)NewProjectDialog.java    1.0   Jan 10, 2008
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  Options dialog
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Jan 10, 2008     crocimi     Initial Release
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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.geco.Configuration;
import org.ximtec.igesture.geco.Geco;
import org.ximtec.igesture.geco.gui.Constant;
import org.ximtec.igesture.geco.gui.MainView;
import org.ximtec.igesture.geco.xml.XMLGeco;



/**
 *  Options dialog
 *  
 * @version 0.9, Dec 3, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class OptionsDialog extends BasicDialog{
   
   
   private MainView view;
   private Configuration configuration;
 
   
   private int DIALOG_WIDTH = 350;
   private int DIALOG_HEIGHT = 450;
   
   private int INPUTDEVICE = 0;
   private int STARTUP = 0;
   
   private Hashtable hashTable = new Hashtable();
   private String selectedDeviceName;
   private ButtonGroup group = new ButtonGroup();
   private JCheckBox startupBox = new JCheckBox(Constant.MINIMIZE_STARTUP);
   // GUI elements
   private JTabbedPane tabbedPane = new JTabbedPane();


   
   public OptionsDialog(MainView view){
      this.view=view;
      setModal(true);
      init();
   }//NewProjectDialog
   
   /**
    * Inits the dialog.
    */
   private void init(){
      this.setResizable(false);
      this.setTitle(Constant.OPTIONS_DIALOG_TITLE);
      this.setLayout(new GridBagLayout());
      this.setLayout(new GridBagLayout());
      Point p = new Point(view.getLocation().x+100, view.getLocation().y+100);
      this.setLocation(p);
      this.setSize(new Dimension( DIALOG_WIDTH, DIALOG_HEIGHT ));

      addFirstTab();
      addSecondTab();

      
      this.add(tabbedPane,
            new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(30,30,30,15),0,0 ) );
      
      JButton saveButton = GuiTool.createButton(Constant.SAVE);
      saveButton.addActionListener(new OkListener());
      JButton cancelButton = GuiTool.createButton(Constant.CANCEL);
      cancelButton.addActionListener(new CancelListener());
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(1,1, 20, 0));
      buttonPanel.add(saveButton);
      buttonPanel.add(cancelButton);
      this.add(buttonPanel,  
            new GridBagConstraints(0,2,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(10,10,10,10),0,0 ) );
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
      configuration = view.getModel().getGestureConfiguration();
      selectedDeviceName = configuration.getInputDeviceName();
      JRadioButton button = (JRadioButton)hashTable.get(configuration.getInputDeviceName());
      button.setSelected(true);
      startupBox.setSelected(configuration.getMinimize());
   }//reset
   
   
   
   /**
    * Add the first tab to the dialog
    * 
    */
   private void addFirstTab() {
      JPanel aPanel = new JPanel();
      aPanel.setLayout(new GridLayout(1, 1));
      
      //Creates the radio buttons.
      JPanel radioButtonPanel = new JPanel();
      radioButtonPanel.setLayout(new GridBagLayout());
      List<String> devices = view.getModel().getGestureConfiguration().getInputDevices();
      for(int i=0; i<devices.size();i++){
         JRadioButton button = new JRadioButton(devices.get(i));
         button.setActionCommand(devices.get(i));
         group.add(button);
         radioButtonPanel.add(button,i);
               
         radioButtonPanel.add( button,
               new GridBagConstraints(0,i,1,1,1,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
               new Insets(50,0,0,50),0,0 ) );
         hashTable.put(devices.get(i),button);
        
      }
      
      aPanel.add(radioButtonPanel);
      
      tabbedPane.addTab(Constant.INPUT_DEVICE, aPanel);
      tabbedPane.setMnemonicAt(INPUTDEVICE, KeyEvent.VK_1);

   } // addFirstTab
   
   /**
    * Add the first tab to the dialog
    * 
    */
   private void addSecondTab() {
      JPanel aPanel = new JPanel();
      aPanel.setLayout(new GridBagLayout());
      
      //Creates the radio buttons.
      aPanel.add(startupBox,
            new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(50,50,50,50),0,0 ) );
      
      tabbedPane.addTab(Constant.STARTUP, aPanel);
      tabbedPane.setMnemonicAt(STARTUP, KeyEvent.VK_2);

   } // addFirstTab
   
   
   
   /**
    * Listener for save options event.
    */
   private class OkListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         String newSelDevice = group.getSelection().getActionCommand();
         boolean needUpdate = (!newSelDevice.equals(configuration.getInputDeviceName())||
               configuration.getMinimize()!=startupBox.isSelected());
         
         
         if(needUpdate){
            List<String> devices = configuration.getInputDevices();
            boolean arr[]=new boolean[devices.size()]; 
            for(int i=0;i<devices.size();i++){
               if(newSelDevice.equals(devices.get(i)))
                  arr[i]=true;
               else
                  arr[i]=false;
            }
            File confFile;
           
            
            try {
               confFile = new File(ClassLoader.getSystemResource(Geco.GECO_CONFIGURATION).toURI());
            } catch(URISyntaxException ex) {
               confFile = new File(ClassLoader.getSystemResource(Geco.GECO_CONFIGURATION).getPath());
            }
            XMLGeco.exportGestureConfiguration(confFile, devices, arr, startupBox.isSelected(), 
                  view.getModel().getProjectFile().getPath());
            
            view.getModel().resetInputDevice();
            view.getModel().setGestureConfiguration(new Configuration(Geco.GECO_CONFIGURATION));
            view.getModel().configureInputDevice();
         }
      
         
         
         
        // view.getModel().setGestureConfiguration();
         OptionsDialog.this.dispose();
      }
   }
   
   
   /**
    * Listener for cancel event.
    */
   private class CancelListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         OptionsDialog.this.dispose();
      }//actionPerformed
      
   }
   
   
   
     
}
