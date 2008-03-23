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

package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;
import org.ximtec.igesture.tool.util.FontFactory;


public abstract class AbstractAdminPanel extends DefaultExplorerTreeView {

   JScrollPane centerPane;


   public AbstractAdminPanel() {
      setLayout(new BorderLayout());

      centerPane = new JScrollPane();
      centerPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      centerPane.setOpaque(false);
      centerPane.setForeground(Color.white);
      centerPane.setBackground(Color.white);
      this.add(centerPane, BorderLayout.CENTER);
   }


   public void setTitle(String s) {
      JLabel title = new JLabel(s);
      title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      title.setFont(FontFactory.getArialBold(24));
      this.add(title, BorderLayout.NORTH);
   }


   public void setCenter(JComponent component) {
      centerPane.setViewportView(component);
   }


   public void setBottom(JComponent component) {
      this.add(component, BorderLayout.SOUTH);
   }

}
