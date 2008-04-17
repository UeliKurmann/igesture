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

package org.ximtec.igesture.tool.view.testbench;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTree;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;


public class TestbenchView extends JSplitPane implements TabbedView,
      ExplorerTreeContainer {

   private JScrollPane scrollPaneLeft;


   public TestbenchView() {
      super(JSplitPane.HORIZONTAL_SPLIT);

      setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      scrollPaneLeft = new JScrollPane();
      scrollPaneLeft.setOpaque(true);
      scrollPaneLeft.setBackground(Color.blue);
      scrollPaneLeft.setForeground(Color.blue);
      setLeftComponent(scrollPaneLeft);
   }


   @Override
   public Icon getIcon() {

      return null;

   }


   @Override
   public String getName() {
      // FIXME use resource bundle
      return "Test Bench";
   }


   @Override
   public JComponent getPane() {
      return this;
   }


   @Override
   public void setTree(ExplorerTree tree) {
      tree.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      scrollPaneLeft.setViewportView(tree);
   }


   @Override
   public void setView(JComponent view) {
      setRightComponent(view);
   }

}