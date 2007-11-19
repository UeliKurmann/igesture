/*
 * @(#)ActionMapGesture.java	1.0   Nov 19, 2007
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:   Map gesture to custom action
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 19, 2007		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI.action;

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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.geco.GestureMappingTable;
import org.ximtec.igesture.geco.GUI.GestureMappingConstants;
import org.ximtec.igesture.geco.GUI.GestureMappingView;
import org.ximtec.igesture.tool.util.SwingTool;



/**
 * Comment
 * @version 1.0 Nov 19, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
/**
*  Map gesture to custom action
* 
* @version 1.0, Nov 2006
* @author Michele Croci, mcroci@gmail.com
*/
public class ActionMapGesture extends BasicAction {

  private GestureMappingView mainView;
  private GestureMappingTable mappingTable;
  
  //private BasicDialog dialog;
  private JDialog dialog;
  private JTabbedPane tabbedPane = new JTabbedPane();
  private JCheckBox ctrlCheckBox =  new JCheckBox("CTRL");
  private JCheckBox shiftCheckBox =  new JCheckBox("SHIFT");
  private JCheckBox altCheckBox =  new JCheckBox("ALT");
  private JComboBox comboBox =  new JComboBox(new String[]{"0","1","2","3","4","5","6","7","8","9"," ","A","B",
        "C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"});
  
  private int DIALOG_WIDTH = 400;
  private int DIALOG_HEIGHT = 400;


  public ActionMapGesture(GestureMappingView mainView, GestureMappingTable mappingTable) {
     super(GestureMappingConstants.MAP_GESTURE, SwingTool.getGuiBundle());
     this.mainView = mainView;
     this.mappingTable =  mappingTable;
  }


  /**
   * Show a dialog
   * 
   * @param event the action event.
   */
  public void actionPerformed(ActionEvent event) {
     //dialog = SwingTool.createDialog(GestureMappingConstants.MAPPING_GESTURE);
     
     
     initDialog();
     

     dialog.setVisible(true);
    

    
  } // actionPerformed¨
  
  
  /**
   * Init the Dialog
   * 
   * @param event the action event.
   */
  public void initDialog() {
     if (dialog==null){
        dialog = new JDialog(mainView);
        dialog.add(tabbedPane);
        Point p = new Point(mainView.getLocation().x+200, mainView.getLocation().y+200);
        dialog.setLocation(p);
        dialog.setSize(new Dimension(DIALOG_HEIGHT, DIALOG_WIDTH));
        addFirstTab();
     }
     //TODO: set gesture!
    
  } // initDialog
  
  
  /**
   * Creates a new mapping
   * 
   * @param event the action event.
   */
  public void addFirstTab() {
     
     JComponent panel1 = new JPanel();
     tabbedPane.addTab(GestureMappingConstants.HOTKEY, panel1);
     tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
     JPanel topPanel = new JPanel();
     JPanel bottomPanel = new JPanel();
     topPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GestureMappingConstants.GESTURE));
     bottomPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GestureMappingConstants.HOTKEY));
     panel1.setLayout(new GridBagLayout());
     topPanel.add(new JLabel("Shows gesture here..."));
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
     
     BasicButton addButton = SwingTool.createButton("save");
     BasicButton cancelButton = SwingTool.createButton("cancel");
     addButton.setText(GestureMappingConstants.ADD);
     addButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent event) {
            //TODO: add map
            //mappingTable.addMapping(gestureName, action)
            
         }
     });
     cancelButton.setText(GestureMappingConstants.CANCEL);
     cancelButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event) {
           dialog.dispose();
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
                    new Insets(0,20,0,20),0,0 ) );
     panel1.add(buttonPanel,
           new GridBagConstraints(1,3,1,1,0.0,0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(20,0,0,0),0,0 ) );
 
    // panel1.add(topPanel);
    // panel1.add(bottomPanel);

    // panel1.add(buttonPanel);
     
     

    
  } // actionPerformed
  
  
  
}