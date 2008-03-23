/*
 * @(#)$Id$
 *
 * Author   : Ueli Kurmann, igesture@uelikurmann.ch
 *                                   
 *                                   
 * Purpose  : 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date       Who     Reason
 *
 * 23.03.2008 ukurmann  Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import org.ximtec.igesture.tool.core.TabbedView;


@SuppressWarnings("serial")
public class MainView extends JFrame {

   JTabbedPane tabbedPane;
   JMenuBar menuBar;


   public MainView() {
      initMenu();
      initView();
   }


   private void initView() {
      setBounds(100, 100, 900, 650);
      setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
      setVisible(true);

      tabbedPane = new JTabbedPane();
      this.add(tabbedPane);
   }


   private void initMenu() {
      menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu menu = new JMenu("File");
      menuBar.add(menu);
      menu.add(new JMenuItem("Exit"));
   }


   public void addTab(TabbedView view) {
      tabbedPane.add(view.getName(), view.getPane());
   }
}
