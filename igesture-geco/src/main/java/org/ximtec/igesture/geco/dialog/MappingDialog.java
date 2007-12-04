/*
 * @(#)MappingDialog.java	1.0   Nov 26, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com  
 *
 * Purpose		:   Dialog for mapping a gesture to an action
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 26, 2007		crocimi		Initial Release
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
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.UserAction.KeyboardSimulationAction;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.graphics.SwingTool;



/**
 * Dialog for mapping a gesture to an action
 * 
 * @version 1.0 Nov 26, 2007
 * @author Michele Croci, mcroci@gmail.com  
 */
public class MappingDialog extends BasicDialog{
   
   
   private GestureClass gestureClass;
   private GestureToActionMapping gestureMapping;
   private GecoMainView view;
   
   private JTabbedPane tabbedPane = new JTabbedPane();
   private JCheckBox ctrlCheckBox =  new JCheckBox("CTRL");
   private JCheckBox shiftCheckBox =  new JCheckBox("SHIFT");
   private JCheckBox altCheckBox =  new JCheckBox("ALT");
   private JComboBox comboBox;
   private JLabel gestureLabel = new JLabel();
   private int DIALOG_WIDTH = 400;
   private int DIALOG_HEIGHT = 400;
   
   private int HOTKEY = 0;
   
   private static final String CONTROL = "CONTROL";
   private static final String SHIFT = "SHIFT";
   private static final String ALT = "ALT";
   
   
   public MappingDialog(GecoMainView gmv){
      view = gmv;
      setModal(true);
      initDialog();
   }//MappingDialog
   
   
   /**
    * Shows the dialog.
    * 
    * @param gc the gesture to be mapped.
    */
   public void showDialog(GestureClass gc){
      gestureMapping = view.getModel().mappingTable.getMapping(gc);
      gestureClass = gc;
      initButtonsState();
      this.setVisible(true);
   }//showDialog
   
   /**
    * Hides the dialog.
    */
   public void hideDialog(){
      this.setVisible(false);
   }//hideDialog
   

   /**
    * Inits the state of the buttons.
    */
   private void initButtonsState(){
      gestureLabel.setText(gestureClass.getName());
      if (gestureMapping==null){
         ctrlCheckBox.setSelected(false);
         altCheckBox.setSelected(false);
         shiftCheckBox.setSelected(false);
         comboBox.setSelectedIndex(0);
      }
      else{
         if (gestureMapping.getAction() instanceof KeyboardSimulationAction){
            KeyboardSimulationAction gkm = (KeyboardSimulationAction)gestureMapping.getAction();
            altCheckBox.setSelected(gkm.isAltSelected());
            shiftCheckBox.setSelected(gkm.isShiftSelected());
            ctrlCheckBox.setSelected(gkm.isCtrlSelected());
            comboBox.setSelectedItem(gkm.getSelectedChar());
         }
      }
   }//initButtonsState
   

   
   /**
    * Init the Dialog
    */
   private void initDialog() {
         this.setTitle(GecoConstants.MAIN_FRAME_TITLE);
         this.setLayout(new GridBagLayout());
         this.add(tabbedPane, new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
               new Insets(20,20,20,20),0,0 ) );
         Point p = new Point(view.getLocation().x+200, view.getLocation().y+100);
         this.setLocation(p);
         this.setSize(new Dimension(DIALOG_HEIGHT, DIALOG_WIDTH));
         
         comboBox = new JComboBox(new String[]{"A","B","C","D","E","F","G",
               "H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z", " "});
         addFirstTab();
   } // initDialog
   
   

   
   /**
    * Add the first tab to the dialog
    * 
    */
   private void addFirstTab() {
      
      JComponent panel1 = new JPanel();
      tabbedPane.addTab(GecoConstants.HOTKEY, panel1);
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
      JPanel topPanel = new JPanel();
      JPanel bottomPanel = new JPanel();
      topPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.GESTURE));
      bottomPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.HOTKEY));
      panel1.setLayout(new GridBagLayout());
      //gestureLabel.setText(gestureClass.getName());
      topPanel.add(gestureLabel);
      bottomPanel.setLayout(new GridBagLayout());
      JLabel plus = new JLabel("+");
      
      
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
     
      BasicButton addButton = SwingTool.createButton(GecoConstants.ADD);
      BasicButton cancelButton = SwingTool.createButton(GecoConstants.CANCEL);
      addButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent event) {
             //update model
             addGestureMappingToTable();
             view.getModel().addMapping(MappingDialog.this.view.getModel().mappingTable.getMapping(gestureClass));
             //update view
             view.updateLists();
             view.enableSaveButton();
             
             MappingDialog.this.dispose();
          }
      });
      
      cancelButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent event) {
            MappingDialog.this.dispose();
         }
     });
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(addButton);
      buttonPanel.add(cancelButton);
      
      panel1.add(topPanel,
            new GridBagConstraints(1,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                     new Insets(0,20,0,20),0,0 ) );
      panel1.add(bottomPanel,
            new GridBagConstraints(1,2,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(0,20,20,20),0,0 ) );
      this.add(buttonPanel,  new GridBagConstraints(0,1,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0,20,0,20),0,0 ) );
  
   } // addFirstTab
   
   
   /**
    * Add a Gesture-action map to the table of mappings
    * 
    */
   private void addGestureMappingToTable(){
      if (tabbedPane.getSelectedIndex()==HOTKEY){
         String keys = "";
         if (ctrlCheckBox.isSelected())
            keys+=CONTROL+"+";
         if (shiftCheckBox.isSelected())
            keys+=SHIFT+"+";
         if (altCheckBox.isSelected())
            keys+=ALT+"+";

         keys+=(String)comboBox.getSelectedItem();
         
         GestureToActionMapping action = new GestureToActionMapping(gestureClass,new KeyboardSimulationAction(keys));
         view.getModel().mappingTable.addMapping(gestureClass, action);
      }   
   }//addGestureMappingToTable
   
   

}
