/*
 * @(#)MappingDialog.java   1.0   Nov 26, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com  
 *
 * Purpose      :   Dialog for mapping a gesture to an action
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 26, 2007     crocimi     Initial Release
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
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
import org.ximtec.igesture.geco.GUI.action.AddMappingAction;
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
public class MappingDialog extends BasicDialog{
   
   private GestureClass gestureClass;
   private GestureToActionMapping gestureMapping;
   private GecoMainView view;

   
   //GUI elements
   private JTabbedPane tabbedPane = new JTabbedPane();

   private JLabel gestureLabel = new JLabel();
   private int DIALOG_WIDTH = 450;
   private int DIALOG_HEIGHT = 500;
   private JTextPane textField;
   private JTextField buttonLabel;
//   private JPanel labelPanel;
   
   //constants
   private final int HOTKEY = 0;
   private final int COMMAND = 1;
   
   //models
   private HotKeyModel hotkeyModel = new HotKeyModel();
   
   
   public MappingDialog(GecoMainView gmv){
      view = gmv;
      setModal(true);
      initDialog();
      
      addWindowListener( new WindowAdapter() {
         public void windowOpened( WindowEvent e ){
            buttonLabel.requestFocus();
           }
         } ); 
      
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
         hotkeyModel.updateModel(null);
         textField.setText("");
         tabbedPane.setSelectedIndex(HOTKEY);
      }
      else{
         if (gestureMapping.getAction() instanceof KeyboardSimulation){
            hotkeyModel.updateModel((KeyboardSimulation)gestureMapping.getAction());
          //  buttonLabel.setText(keys);
            
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
      JPanel keyStringView = new HotKeyStringView(hotkeyModel);
      
 

      tabbedPane.addTab(GecoConstants.HOTKEY, keyStringView);
      tabbedPane.setMnemonicAt(HOTKEY, KeyEvent.VK_1);
      
 
      
      buttonLabel = GuiTool.createTextField("buttonTextField");
     
 //     labelPanel = new JPanel();
     
//      labelPanel.setBorder(new BevelBorder(1, Color.gray,Color.gray));
//      labelPanel.add(buttonLabel);
//      labelPanel.setBackground(Color.WHITE);
      buttonLabel.setEditable(false);
      buttonLabel.setBackground(Color.WHITE);

      //buttonLabel.addKeyListener(new ButtonLabelKeyListener());
//      buttonLabel.addMouseListener(new ButtonLabelMouseListener());

    //  bottomPanel.add(buttonLabel,
      //      new GridBagConstraints(1,2,7,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        //             new Insets(10,20,10,20),0,0 ) );

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

         GestureToActionMapping mapping = new GestureToActionMapping(
               gestureClass,new KeyboardSimulation(hotkeyModel.getAllKeys()));
         view.getModel().mappingTable.addMapping(gestureClass, mapping);
      }   
      else if(tabbedPane.getSelectedIndex()==COMMAND){
         GestureToActionMapping mapping = new GestureToActionMapping(gestureClass,new CommandExecutor(textField.getText()));
         view.getModel().mappingTable.addMapping(gestureClass, mapping);
      }
   }//addGestureMappingToTable
   
   /*
   private class ButtonLabelKeyListener implements KeyListener{
   
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
            buttonLabel.setText(text+e.getKeyText(e.getKeyCode()));
            comboBox.setSelectedItem(e.getKeyText(e.getKeyCode()));
            buttonLabel.setBackground(Color.WHITE);
            comboBox.removeItemAt(comboBox.getItemCount()-1);
            comboBox.addItem(e.getKeyText(e.getKeyCode()));
            comboBox.setSelectedIndex(comboBox.getItemCount()-1);           
      }
      System.out.println(e.getKeyText(e.getKeyCode()));  



      
   }

      public void keyTyped(KeyEvent e){
      }
   }
   
   private class keyActionListener implements ActionListener{
      
      public void actionPerformed(ActionEvent e){
         MappingDialog.this.updateSelectedKeys();
         MappingDialog.this.buttonLabel.setText(MappingDialog.this.keys);
      }
      
   }
*/   
   /*
   private class ButtonLabelMouseListener implements MouseListener{
      public void mouseClicked(MouseEvent e){
      }
      
      public void mouseEntered(MouseEvent e){
      }

      public void mouseExited(MouseEvent e){  
      }

      public void mousePressed(MouseEvent e){
      }

      public void mouseReleased(MouseEvent e){
      }

   }
  */
   
   /*
   public void focusGained(FocusEvent e){
      view.setFocusableWindowState(true);
   }

   public void focusLost(FocusEvent e){
      
   }

   
   
*/
}
