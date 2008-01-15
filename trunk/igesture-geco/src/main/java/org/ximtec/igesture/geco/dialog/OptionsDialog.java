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
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

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
 
   
   private int DIALOG_WIDTH = 200;
   private int DIALOG_HEIGHT = 300;
   
   private JRadioButton mouseButton = new JRadioButton(Constant.MOUSE);
   private JRadioButton wacomButton = new JRadioButton(Constant.WACOM);
   private Hashtable hashTable = new Hashtable();
   private String selectedDeviceName;
   private ButtonGroup group = new ButtonGroup();


   
   public OptionsDialog(MainView view){
      this.view=view;
      setModal(true);
      init();
   }//NewProjectDialog
   
   /**
    * Inits the dialog.
    */
   private void init(){
      JPanel mainPanel = new JPanel();
      this.setResizable(false);
      mainPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), Constant.SELECT_INPUT_DEVICE));
      this.setTitle(Constant.OPTIONS_DIALOG_TITLE);
      mainPanel.setLayout(new GridBagLayout());
      this.setLayout(new GridBagLayout());
      Point p = new Point(view.getLocation().x+100, view.getLocation().y+100);
      this.setLocation(p);
      this.setSize(new Dimension( DIALOG_WIDTH, DIALOG_HEIGHT ));

    
      this.add(mainPanel,
            new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                  new Insets(20,20,20,20),0,0 ) );
      
      

      //Creates the radio buttons.
      JPanel radioButtonPanel = new JPanel();
      radioButtonPanel.setLayout(new GridBagLayout());
      List<String> devices = view.getModel().getGestureConfiguration().getInputDevices();
      for(int i=0; i<devices.size();i++){
         JRadioButton button = new JRadioButton(devices.get(i));
         button.setActionCommand(devices.get(i));
         group.add(button);
         radioButtonPanel.add( button,
               new GridBagConstraints(0,i,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
               new Insets(10,0,0,0),0,0 ) );
         hashTable.put(devices.get(i),button);
        
      }
      
      mainPanel.add(radioButtonPanel,
            new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(30,30,30,15),0,0 ) );
      
      JButton okButton = GuiTool.createButton(Constant.OK);
      okButton.addActionListener(new OkListener());
      JButton cancelButton = GuiTool.createButton(Constant.CANCEL);
      cancelButton.addActionListener(new CancelListener());
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(1,1, 20, 0));
      buttonPanel.add(okButton);
      buttonPanel.add(cancelButton);
      this.add(buttonPanel,  
            new GridBagConstraints(0,2,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
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
      //InputDevice device = configuration.getInputDevice();
      JRadioButton button = (JRadioButton)hashTable.get(configuration.getInputDeviceName());
      Enumeration buttons = group.getElements();
      
      
      button.setSelected(true);
      /*
      if(device instanceof MouseReader){
         mouseButton.setSelected(true);
      }else if(device instanceof WacomReader){
         wacomButton.setSelected(true);
      }
      */
      
   }//reset
   
   
   
   /**
    * Listener for create project event.
    */
   private class OkListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         
         String newSelDevice = group.getSelection().getActionCommand();
         if(!newSelDevice.equals(configuration.getInputDevice())){
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
            
            XMLGeco.exportGestureConfiguration(devices, arr, confFile);
         }
         
      
         
         System.out.println();
         //SE la device è stata cambiata
         //-> aggiorna l'XML
         //- >aggiorna il model & la clien device
         //altrimenti non fare niente!
         
        // view.getModel().setGestureConfiguration();
         OptionsDialog.this.dispose();
      }//actionPerformed
      
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
