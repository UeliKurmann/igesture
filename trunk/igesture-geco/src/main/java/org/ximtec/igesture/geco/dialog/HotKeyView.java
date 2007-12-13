/*
 * @(#)HotKeyStringView.java	1.0   Dec 11, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   View for the choosed hotkey
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 11, 2007		crocimi		Initial Release
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sigtec.graphix.GuiTool;



/**
 * View for the choosed hotkey
 * @version 1.0 Dec 11, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class HotKeyView extends JPanel{

   private String keys = "";
   private MappingDialog view;

   
   //GUI elements
   private JCheckBox ctrlCheckBox =  new JCheckBox("CTRL");
   private JCheckBox shiftCheckBox =  new JCheckBox("SHIFT");
   private JCheckBox altCheckBox =  new JCheckBox("ALT");
   private JComboBox comboBox;
   private JTextField buttonLabel;
   
   private boolean update=true;

   
   public HotKeyView(MappingDialog view){
      this.view=view;
      populateView();
      this.view.addWindowListener( new WindowAdapter() {
         public void windowOpened( WindowEvent e ){
            buttonLabel.requestFocus();
           }
         } ); 
      buttonLabel.addKeyListener(new ButtonLabelKeyListener());
      initView();
   }
   
   public String getAllKeys(){
      return keys;
   }
   
   public void updateView(String keys){
      this.keys=keys;
      updateBoxElements();
      updateTextFieldElement();
      buttonLabel.requestFocus();
   }
   
   public void initView(){
      keys="";
      ctrlCheckBox.setSelected(false);
      altCheckBox.setSelected(false);
      shiftCheckBox.setSelected(false);
      comboBox.setSelectedIndex(0);
      buttonLabel.setText("");   
      buttonLabel.requestFocus();
   }
   

   private void updateBoxElements(){
      if(keys.contains("CTRL")){
         ctrlCheckBox.setSelected(true);
      }
      else{
         ctrlCheckBox.setSelected(false);
      }
      
      if(keys.contains("SHIFT")){
         shiftCheckBox.setSelected(true);
      }
      else{
         shiftCheckBox.setSelected(false);
      }
      
      if(keys.contains("ALT")){
         altCheckBox.setSelected(true);
      }
      else{
         altCheckBox.setSelected(false);
      }
      //get key
      String s= keys.split("\\+")[keys.split("\\+").length-1];
      if(s.equals("CTRL")||s.equals("SHIFT")||s.equals("ALT")){
         comboBox.setSelectedIndex(0);
         update=false;
      }else{
         if(!s.equals("")){
            comboBox.setSelectedItem(s);
            if(!comboBox.getSelectedItem().equals(s)){
               comboBox.setSelectedItem("Invalid Key");
               view.setAddButtonEnabled(false);
            }else{
               view.setAddButtonEnabled(true);
            }
         }else
         {
            comboBox.setSelectedIndex(0);
         }
      }
      } 

   
   

   

   private void updateTextFieldElement(){
      String text="";
      if(ctrlCheckBox.isSelected()){
         text+="CTRL";
      }
      if(shiftCheckBox.isSelected()){
         if(ctrlCheckBox.isSelected()){
            text+="+";
         }
         text+="SHIFT";
      }
      if(altCheckBox.isSelected()){
         if(shiftCheckBox.isSelected()||ctrlCheckBox.isSelected()){
            text+="+";
         }
         text+="ALT";
      }
      if(ctrlCheckBox.isSelected()||shiftCheckBox.isSelected()||altCheckBox.isSelected()){
         if(!comboBox.getSelectedItem().equals("")){
            text+="+";
         }
      }
      text+=comboBox.getSelectedItem();
      keys=text;
      buttonLabel.setText(text);
   }
   

   
   public void requestFocus(){
      buttonLabel.requestFocus();
   }
   
   
   private class ButtonLabelKeyListener implements KeyListener{
      
      public void keyPressed(KeyEvent e){

      }
      String text="";
      boolean notLastKey=false;
      
      public void keyReleased(KeyEvent e){ 
         
            if(!text.equals(""))
               text ="+"+text;
            
            text=e.getKeyText(e.getKeyCode()).toUpperCase()+text;

            if(!e.isAltDown()&&!e.isShiftDown()&&!e.isControlDown())
               notLastKey=false;
            else
               notLastKey=true;
            
         if(!notLastKey){
            buttonLabel.setText(text);
            keys=text;
            updateBoxElements();
            text="";
            requestFocus();
         }

      }
         public void keyTyped(KeyEvent e){
         }
      }
   

   
   
   private void populateView(){
            
      comboBox = new JComboBox(new String[]{"","A","B","C","D","E","F","G",
            "H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z", " ",
            "F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12",
            "0","1","2","3","4","5","6","7","8","9","BACKSPACE","DELETE","SPACE","ENTER",
            "ESCAPE","UP","DOWN","LEFT","RIGHT","TAB","PAGE UP","PAGE DOWN","END","HOME","Invalid Key"});
      
      JPanel bottomPanel = new JPanel();
      this.setLayout(new GridBagLayout());
      //gestureLabel.setText(gestureClass.getName());
      bottomPanel.setLayout(new GridBagLayout());
      JLabel plus = new JLabel("+");
      
      
      ctrlCheckBox.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            updateTextFieldElement();
         }
      });
      altCheckBox.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            updateTextFieldElement();
         }
      });
      shiftCheckBox.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            updateTextFieldElement();
         }
      });
      comboBox.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            String s ="";
            if(!keys.equals(""))
               s = keys.split("\\+")[keys.split("\\+").length-1];
            if(!comboBox.getSelectedItem().equals(s)){
               if(update){
                  updateTextFieldElement();
               }else{
                  update=true;
               }
            }
              
         }
      });

      
      bottomPanel.add(ctrlCheckBox,
            new GridBagConstraints(1,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(0,20,0,0),0,0 ) );
      bottomPanel.add(plus,
            new GridBagConstraints(2,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(0,0,0,0),0,0 ) );
      bottomPanel.add(shiftCheckBox,
            new GridBagConstraints(3,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(0,0,0,0),0,0 ) );
      plus = new JLabel("+");
      bottomPanel.add(plus,
            new GridBagConstraints(4,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(0,0,0,0),0,0 ) );
      bottomPanel.add(altCheckBox,
            new GridBagConstraints(5,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(0,0,0,0),0,0 ) );
      plus = new JLabel("+");
      bottomPanel.add(plus,
            new GridBagConstraints(6,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(0,0,0,0),0,0 ) );
      bottomPanel.add(comboBox,
            new GridBagConstraints(7,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                     new Insets(0,0,0,20),0,0 ) );
      
      buttonLabel = GuiTool.createTextField("buttonTextField");
      buttonLabel.setEditable(false);
      buttonLabel.setBackground(Color.WHITE);

      ActionListener act =  new ActionListener(){
         public void actionPerformed(ActionEvent e){
            buttonLabel.requestFocus();
         }
      };
      ctrlCheckBox.addActionListener(act);
      altCheckBox.addActionListener(act);
      shiftCheckBox.addActionListener(act);
      comboBox.addActionListener(act);
      
      
      
      this.setLayout(new GridBagLayout());
      this.add(bottomPanel,
            new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(10,10,10,10),0,0 ) );
      

      this.add(buttonLabel,
            new GridBagConstraints(0,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(10,20,10,20),0,0 ) );
   }
   

   


}
