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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicTextField;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.UserAction.CommandExecutor;
import org.ximtec.igesture.geco.UserAction.KeyboardSimulation;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.graphics.SwingTool;



/**
 * Dialog for mapping a gesture to an action
 * 
 * @version 1.0 Nov 26, 2007
 * @author Michele Croci, mcroci@gmail.com  
 */
public class MappingDialog extends BasicDialog implements KeyListener{
   
   
   private GestureClass gestureClass;
   private GestureToActionMapping gestureMapping;
   private GecoMainView view;
   
   //GUI elements
   private JTabbedPane tabbedPane = new JTabbedPane();
   private JCheckBox ctrlCheckBox =  new JCheckBox("CTRL");
   private JCheckBox shiftCheckBox =  new JCheckBox("SHIFT");
   private JCheckBox altCheckBox =  new JCheckBox("ALT");
   private JComboBox comboBox;
   private JLabel gestureLabel = new JLabel();
   private int DIALOG_WIDTH = 450;
   private int DIALOG_HEIGHT = 500;
   private JTextPane textField;
   private BasicTextField btextField;
   
   //constants
   private final int HOTKEY = 0;
   private final int COMMAND = 1;
   
   private static final String CONTROL = "CONTROL";
   private static final String SHIFT = "SHIFT";
   private static final String ALT = "ALT";
   
   
   public MappingDialog(GecoMainView gmv){
      view = gmv;
      setModal(true);
      initDialog();
//      this.addFocusListener(this);
   }//MappingDialog
   
   
   /**
    * Shows the dialog.
    * 
    * @param gc the gesture to be mapped.
    */
   public void showDialog(GestureClass gc){
      gestureMapping = view.getModel().mappingTable.getMapping(gc);
      gestureClass = gc;
      initValues();
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
   private void initValues(){
      gestureLabel.setText(gestureClass.getName());
      if (gestureMapping==null){
         ctrlCheckBox.setSelected(false);
         altCheckBox.setSelected(false);
         shiftCheckBox.setSelected(false);
         comboBox.setSelectedIndex(0);
         textField.setText("");
         tabbedPane.setSelectedIndex(HOTKEY);
      }
      else{
         if (gestureMapping.getAction() instanceof KeyboardSimulation){
            KeyboardSimulation gkm = (KeyboardSimulation)gestureMapping.getAction();
            altCheckBox.setSelected(gkm.isAltSelected());
            shiftCheckBox.setSelected(gkm.isShiftSelected());
            ctrlCheckBox.setSelected(gkm.isCtrlSelected());
            comboBox.setSelectedItem(gkm.getSelectedKey());
            tabbedPane.setSelectedIndex(HOTKEY);
         }
         else if(gestureMapping.getAction() instanceof CommandExecutor){
            CommandExecutor cmd = (CommandExecutor)gestureMapping.getAction();
            textField.setText(cmd.getCommand());
            tabbedPane.setSelectedIndex(COMMAND);
         }
      }
   }//initButtonsState
   

   
   /**
    * Init the Dialog
    */
   private void initDialog() {
         this.setTitle(GecoConstants.MAIN_FRAME_TITLE);
         this.setLayout(new GridBagLayout());
         this.add(tabbedPane, new GridBagConstraints(0,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
               new Insets(20,40,20,40),0,0 ) );
         Point p = new Point(view.getLocation().x+200, view.getLocation().y+100);
         this.setLocation(p);
         this.setSize(new Dimension( DIALOG_WIDTH, DIALOG_HEIGHT));
         
         comboBox = new JComboBox(new String[]{"A","B","C","D","E","F","G",
               "H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z", " ",
               "F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12",
               "0","1","2","3","4","5","6","7","8","9","BACK","DELETE","SPACE","RETURN",
               "ESCAPE","UP","DOWN","LEFT","DOWN","TAB","PAGE UP","PAGE DOWN"});
         showGesture();
         addButtonPanel();
         addFirstTab();
         addSecondTab();
   } // initDialog
   
   
   
   private void showGesture() {
      JPanel topPanel = new JPanel();
      topPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.GESTURE));
      topPanel.add(gestureLabel);
      this.add(topPanel,
            new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                     new Insets(10,40,10,40),0,0 ) );

   }
   
   
   
   /**
    * Add the buttons
    * 
    */
   private void addButtonPanel(){
      JPanel buttonPanel = new JPanel();
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
      

      buttonPanel.add(addButton);
      buttonPanel.add(cancelButton);
      
      this.add(buttonPanel,  new GridBagConstraints(0,2,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0,20,0,20),0,0 ) );
   }

   
   /**
    * Add the first tab to the dialog
    * 
    */
   private void addFirstTab() {
      
      JComponent panel1 = new JPanel();
      view.addKeyListener(this);
      tabbedPane.addTab(GecoConstants.HOTKEY, panel1);
      tabbedPane.setMnemonicAt(HOTKEY, KeyEvent.VK_1);
      
      JPanel bottomPanel = new JPanel();
      bottomPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.HOTKEY));
      panel1.setLayout(new GridBagLayout());
      //gestureLabel.setText(gestureClass.getName());
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
      
      panel1.add(bottomPanel,
            new GridBagConstraints(1,2,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                     new Insets(40,20,40,20),0,0 ) );
      
      btextField = GuiTool.createTextField("buttonTextField");
      btextField.addKeyListener(this);
      bottomPanel.add(btextField,
            new GridBagConstraints(1,2,7,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                     new Insets(10,20,10,20),0,0 ) );

      //remove keystroke
      //KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
      //btextField.getInputMap().remove(ks);

   } // addFirstTab
   
   
   /**
    * Add the second tab to the dialog
    * 
    */
   private void addSecondTab() {
      
      JComponent panel2 = new JPanel();
      panel2.setLayout(new GridBagLayout());
      tabbedPane.addTab(GecoConstants.COMMAND, panel2);
      tabbedPane.setMnemonicAt(COMMAND, KeyEvent.VK_2);
      
      
      JPanel aPanel = new JPanel();
      aPanel.setLayout(new GridLayout());
      aPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.COMMAND));
      textField = new JTextPane();
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.getViewport().add(textField);
      textField.setMaximumSize(new Dimension(300,200));
//      textField.setSize(new Dimension(200,200));
      textField.setBorder(new BevelBorder(0,Color.gray,Color.gray));
//      aPanel.add(textField, new GridBagConstraints(0,0,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
//            new Insets(40,20,40,20),0,0 ) );
      aPanel.add(scrollPane);
      
      panel2.add(aPanel, new GridBagConstraints(0,0,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(40,20,40,20),0,0 ) );
      
   }
   
   
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
         
         GestureToActionMapping mapping = new GestureToActionMapping(gestureClass,new KeyboardSimulation(keys));
         view.getModel().mappingTable.addMapping(gestureClass, mapping);
      }   
      else if(tabbedPane.getSelectedIndex()==COMMAND){
         GestureToActionMapping mapping = new GestureToActionMapping(gestureClass,new CommandExecutor(textField.getText()));
         view.getModel().mappingTable.addMapping(gestureClass, mapping);
      }
   }//addGestureMappingToTable
   
   
   public void keyPressed(KeyEvent e){
   
   }

   public void keyReleased(KeyEvent e){ 
      
      switch(e.getKeyCode()){
         case KeyEvent.VK_CONTROL:
            break;
         case KeyEvent.VK_SHIFT:
            break;
         case KeyEvent.VK_ALT:
            break;
         default:
            
            String text="";
         if (e.isControlDown()){
            ctrlCheckBox.setSelected(true);
            text+="CONTROL+";
         }else{
            ctrlCheckBox.setSelected(false);
         }
         if (e.isShiftDown()){
            shiftCheckBox.setSelected(true);
            text+="SHIFT+";
         }else{
            shiftCheckBox.setSelected(false);
         }
         
         if (e.isAltDown()){
            altCheckBox.setSelected(true);
            text+="ALT+";
         }else{
            altCheckBox.setSelected(false);
         }
            btextField.setText(text+e.getKeyText(e.getKeyCode()));
            comboBox.setSelectedItem(e.getKeyText(e.getKeyCode()));
            //System.out.println(e.getKeyText(e.getKeyCode()));                
      }
      System.out.println(e.getKeyText(e.getKeyCode()));  


      
   }

   public void keyTyped(KeyEvent e){
      btextField.setText("");

   
   }
  
   
   /*
   public void focusGained(FocusEvent e){
      view.setFocusableWindowState(true);
   }

   public void focusLost(FocusEvent e){
      
   }

   
   
*/
}
