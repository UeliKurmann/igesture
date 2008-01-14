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
import java.util.Hashtable;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.input.InputDevice;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.WacomReader;
import org.ximtec.igesture.tool.GestureConfiguration;



/**
 *  Options dialog
 *  
 * @version 1.0 Dec 3, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class OptionsDialog extends BasicDialog{
   
   
   private GecoMainView view;
   private GestureConfiguration configuration;
 
   
   private int DIALOG_WIDTH = 200;
   private int DIALOG_HEIGHT = 300;
   
   private JRadioButton mouseButton = new JRadioButton(GecoConstants.MOUSE);
   private JRadioButton wacomButton = new JRadioButton(GecoConstants.WACOM);
   private Hashtable hashTable = new Hashtable();
   private String selectedDeviceName;
   private ButtonGroup group = new ButtonGroup();


   
   public OptionsDialog(GecoMainView view){
      this.view=view;
      setModal(true);
      init();
   }//NewProjectDialog
   
   /**
    * Inits the dialog.
    */
   private void init(){
      JPanel mainPanel = new JPanel();
      mainPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.SELECT_INPUT_DEVICE));
      this.setTitle(GecoConstants.OPTIONS_DIALOG_TITLE);
      mainPanel.setLayout(new GridBagLayout());
      this.setLayout(new GridBagLayout());
      Point p = new Point(view.getLocation().x+100, view.getLocation().y+100);
      this.setLocation(p);
      this.setSize(new Dimension( DIALOG_WIDTH, DIALOG_HEIGHT ));

    
      this.add(mainPanel,
            new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                  new Insets(20,20,20,20),0,0 ) );
      
      

      //Creates the radio buttons.
      JPanel radioButtonPanel = new JPanel();
      radioButtonPanel.setLayout(new GridBagLayout());
      List<String> devices = view.getModel().getGestureConfiguration().getInputDevices();
      for(int i=0; i<devices.size();i++){
         JRadioButton button = new JRadioButton(devices.get(i));
         group.add(button);
         radioButtonPanel.add( button,
               new GridBagConstraints(0,i,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
               new Insets(10,0,0,0),0,0 ) );
         hashTable.put(devices.get(i),button);
        
      }
      
      mainPanel.add(radioButtonPanel,
            new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(30,30,30,15),0,0 ) );
      
      JButton okButton = SwingTool.createButton(GecoConstants.OK);
      okButton.addActionListener(new OkListener());
      JButton cancelButton = SwingTool.createButton(GecoConstants.CANCEL);
      cancelButton.addActionListener(new CancelListener());
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(1,1, 20, 0));
      buttonPanel.add(okButton);
      buttonPanel.add(cancelButton);
      this.add(buttonPanel,  
            new GridBagConstraints(0,2,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
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
      InputDevice device = configuration.getInputDevice();
      JRadioButton button = (JRadioButton)hashTable.get(configuration.getInputDeviceName());
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
