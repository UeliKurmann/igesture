/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 17.06.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.view.action.SelectDatabaseAction;
import org.ximtec.igesture.tool.view.databaseselector.BrowseAction;



/**
 * Comment
 * @version 1.0 17.06.2008
 * @author Ueli Kurmann
 */
public class DatabaseSelector extends JDialog{
   
   private JPanel panel;
   
   public DatabaseSelector(){
      init();
   }
   
   public File getDatabase(){
    return null;  
   }
   
   private void init(){
      
      panel = new JPanel();
      BorderLayout layout = new BorderLayout();
      panel.setLayout(layout);
      panel.setBackground(Color.white);
      panel.setForeground(Color.white);
      panel.setOpaque(true);
      setSize(new Dimension(400,200));
      
      JLabel label = new JLabel();
      Image img = null;
      
      try {
         img = ImageIO.read(DatabaseSelector.class.getClassLoader().getResourceAsStream("image/igesture.gif"));
      }
      catch (IOException e) {
         e.printStackTrace();
      }
      
      ImageIcon icon = new ImageIcon(img);
      label.setIcon(icon);
      panel.add(label,BorderLayout.NORTH);
      
      panel.add(createSelectPanel(), BorderLayout.CENTER);
      panel.add(createNavigationPanel(), BorderLayout.SOUTH);
      
      add(panel);
      
      this.setVisible(true);
   }
   
   private JPanel createSelectPanel(){
      JPanel panel = new JPanel();
      panel.setBackground(Color.white);
      panel.setForeground(Color.white);
      panel.setOpaque(true);
      
      panel.setLayout(new FlowLayout());
      JTextField field = new JTextField();
      field.setSize(new Dimension(300, 25));
      field.setPreferredSize(new Dimension(300,25));
      panel.add(field);
      JButton button = new JButton();
      button.setAction(new BrowseAction(field));
      panel.add(button);
      return panel;
   }
   
   private JPanel createNavigationPanel(){
      JPanel panel = new JPanel();
      panel.setBackground(Color.white);
      panel.setForeground(Color.white);
      panel.setOpaque(true);
      panel.setLayout(new FlowLayout());
      panel.add(new JButton(new SelectDatabaseAction(null)));
      return panel;
   }
   
   public static void main(String[] args) {
      Locator locator = Locator.getDefault();
      locator.addService(new GuiBundleService("igestureMenu"));
      
      new DatabaseSelector();
   }
}
