/*
 * @(#)$Id$
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui;

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

import org.ximtec.igesture.geco.util.Constant;


/**
 * View for the defined command
 * @version 0.9, Dec 11, 2007
 * @author Michele Croci, mcroci@gmail.com
 */
public class CommandView extends JPanel {

   private JTextPane textField;


   public CommandView() {
      populate();
      init();
   }


   public String getCommand() {
      return textField.getText();
   }


   /**
    * Update the view
    * 
    * @command the current command
    */
   public void updateView(String command) {
      textField.setText(command);
   }


   /**
    * Reset the view.
    */
   public void init() {
      textField.setText(Constant.EMPTY_STRING);
   }


   private void populate() {
      this.setLayout(new GridBagLayout());
      JPanel aPanel = new JPanel();
      aPanel.setLayout(new GridLayout());
      aPanel.setBorder(new TitledBorder(new BevelBorder(0, Color.gray,
            Color.gray), Constant.COMMAND));
      textField = new JTextPane();
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.getViewport().add(textField);
      textField.setMaximumSize(new Dimension(300, 200));
      textField.setBorder(new BevelBorder(0, Color.gray, Color.gray));
      aPanel.add(scrollPane);

      this.add(aPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(40,
                  20, 40, 20), 0, 0));
   }

}
