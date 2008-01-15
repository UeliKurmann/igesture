/*
 * @(#)CommandView.java    1.0   Dec 11, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   View for the defined command
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 11, 2007     crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.GUI1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;



/**
 *  View for the defined command
 * @version 0.9, Dec 11, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class CommandView extends JPanel{

   
   //GUI elements
   private JTextPane textField;


   public CommandView(){
      populateView();

      initView();
   }
   
   public String getCommand(){
      return textField.getText();
   }
   
   
   /**
    * Update the view
    * 
    * @command the current command
    */
   public void updateView(String command){
      textField.setText(command);
   }
   
   
   /**
    * Reset the view.
    */
   public void initView(){
      textField.setText("");
   }

   
   private void populateView(){
      this.setLayout(new GridBagLayout());
      JPanel aPanel = new JPanel();
      aPanel.setLayout(new GridLayout());
      aPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.COMMAND));
      textField = new JTextPane();
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.getViewport().add(textField);
      textField.setMaximumSize(new Dimension(300,200));
      textField.setBorder(new BevelBorder(0,Color.gray,Color.gray));
      aPanel.add(scrollPane);
      
      this.add(aPanel, new GridBagConstraints(0,0,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(40,20,40,20),0,0 ) );

   }
   

   


}
