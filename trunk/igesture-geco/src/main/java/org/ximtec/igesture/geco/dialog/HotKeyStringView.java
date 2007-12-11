/*
 * @(#)HotKeyStringView.java	1.0   Dec 11, 2007
 *
 * Author		:	Beat Signer, signer@inf.ethz.ch
 *
 * Purpose		: 
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.UserAction.CommandExecutor;
import org.ximtec.igesture.geco.UserAction.KeyboardSimulation;



/**
 * Comment
 * @version 1.0 Dec 11, 2007
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class HotKeyStringView extends JPanel implements Observer {

   private HotKeyModel model;
   
   //GUI elements
   private JCheckBox ctrlCheckBox =  new JCheckBox("CTRL");
   private JCheckBox shiftCheckBox =  new JCheckBox("SHIFT");
   private JCheckBox altCheckBox =  new JCheckBox("ALT");
   private JComboBox comboBox;
   
   public HotKeyStringView(HotKeyModel model){
      this.model = model;
      this.model.addObserver(this);
      populateView();
   }
   
   

   
   private void updateGuiElements(){
      if(model.isAltSelected()){
         altCheckBox.setSelected(true);
      }
      else{
         altCheckBox.setSelected(false);
      }
      
      if(model.isShiftSelected()){
         shiftCheckBox.setSelected(true);
      }
      else{
         shiftCheckBox.setSelected(false);
      }
      
      if(model.isCtrlSelected()){
         ctrlCheckBox.setSelected(true);
      }
      else{
         ctrlCheckBox.setSelected(false);
      }
      if(!((String)comboBox.getSelectedItem()).equals(model.getKey())){
         if(!model.getKey().equals("")){
            comboBox.removeItemAt(comboBox.getItemCount()-1);
            comboBox.addItem(model.getKey());
            comboBox.setSelectedIndex(comboBox.getItemCount()-1);
         }else
         {
            comboBox.setSelectedIndex(0);
         }
      }
   }
   
   
   public void update(Observable t, Object o) // Called from the Model
   {   
      updateGuiElements();
      
   }
   
   
   
   private void populateView(){
      comboBox = new JComboBox(new String[]{"","A","B","C","D","E","F","G",
            "H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z", " ",
            "F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12",
            "0","1","2","3","4","5","6","7","8","9","BACK","DELETE","SPACE","RETURN",
            "ESCAPE","UP","DOWN","LEFT","DOWN","TAB","PAGE UP","PAGE DOWN", ""});
      
      JPanel bottomPanel = new JPanel();
      bottomPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.HOTKEY));
      this.setLayout(new GridBagLayout());
      //gestureLabel.setText(gestureClass.getName());
      bottomPanel.setLayout(new GridBagLayout());
      JLabel plus = new JLabel("+");
      
      
      ctrlCheckBox.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if(ctrlCheckBox.isSelected())
               model.setCtrlSelected(true);
               else{
                  model.setCtrlSelected(false);
            }
         }
      });
      altCheckBox.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if(altCheckBox.isSelected())
               model.setAltSelected(true);
               else{
                  model.setAltSelected(false);
            }
         }
      });
      shiftCheckBox.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if(shiftCheckBox.isSelected())
               model.setShiftSelected(true);
               else{
                  model.setShiftSelected(false);
            }
         }
      });
      comboBox.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            model.setKey((String)comboBox.getSelectedItem());
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
      
      this.add(bottomPanel,
            new GridBagConstraints(1,2,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(40,20,40,20),0,0 ) );
   }
}
