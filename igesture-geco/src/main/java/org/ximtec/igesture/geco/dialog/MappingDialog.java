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
import org.ximtec.igesture.geco.GUI.CommandView;
import org.ximtec.igesture.geco.GUI.GecoConstants;
import org.ximtec.igesture.geco.GUI.GecoMainView;
import org.ximtec.igesture.geco.GUI.HotKeyView;
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
   private BasicButton addButton;
   private int DIALOG_WIDTH = 450;
   private int DIALOG_HEIGHT = 500;

   //views
   private HotKeyView hotkeyView;
   private CommandView commandView;

//   private JPanel labelPanel;
   
   //constants
   private final int HOTKEY = 0;
   private final int COMMAND = 1;
   
   //models
   //private HotKeyModel hotkeyModel = new HotKeyModel();
   
   
   public MappingDialog(GecoMainView gmv){
      view = gmv;
      setFocusable(false);
      setModal(true);
      initDialog();
      tabbedPane.setFocusable(false);

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
        
         
         commandView.initView();
         hotkeyView.initView();
         tabbedPane.setSelectedIndex(COMMAND);
         tabbedPane.setSelectedIndex(HOTKEY);
      }
      else{
         if (gestureMapping.getAction() instanceof KeyboardSimulation){
            KeyboardSimulation ks = (KeyboardSimulation)gestureMapping.getAction();
            hotkeyView.updateView(ks.getAllKeys());


            
         }
         else if(gestureMapping.getAction() instanceof CommandExecutor){
            CommandExecutor cmd = (CommandExecutor)gestureMapping.getAction();
            commandView.updateView(cmd.getCommand());
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
     //    tabbedPane.addChangeListener(hotkeyView.getChangeListener());
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
      addButton = SwingTool.createButton(GecoConstants.ADD);
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
      hotkeyView = new HotKeyView(this);

      
      JPanel aPanel = new JPanel();
      aPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.HOTKEY));
      
      aPanel.setLayout(new GridLayout(1,1));
      aPanel.add(hotkeyView);
      
      tabbedPane.addTab(GecoConstants.HOTKEY, aPanel);
      tabbedPane.setMnemonicAt(HOTKEY, KeyEvent.VK_1);



   } // addFirstTab
   
   
   /**
    * Add the second tab to the dialog
    * 
    */
   private void addSecondTab() {
      
      commandView = new CommandView();
      tabbedPane.addTab(GecoConstants.COMMAND, commandView);
      tabbedPane.setMnemonicAt(COMMAND, KeyEvent.VK_2);
      

      
   }
   
 
   
   /**
    * Add a Gesture-action map to the table of mappings
    * 
    */
   private void addGestureMappingToTable(){
      if (tabbedPane.getSelectedIndex()==HOTKEY){

         GestureToActionMapping mapping = new GestureToActionMapping(
               gestureClass,new KeyboardSimulation(hotkeyView.getAllKeys()));
         view.getModel().mappingTable.addMapping(gestureClass, mapping);
      }   
      else if(tabbedPane.getSelectedIndex()==COMMAND){
         GestureToActionMapping mapping = new GestureToActionMapping(gestureClass,new CommandExecutor(commandView.getCommand()));
         view.getModel().mappingTable.addMapping(gestureClass, mapping);
      }
   }//addGestureMappingToTable
   
 
   
   public void setAddButtonEnabled(boolean b){
      addButton.setEnabled(b);
   }
}
