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

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;


public abstract class AbstractPanel extends DefaultExplorerTreeView {

   JScrollPane centerPane;


   public AbstractPanel() {
      setLayout(new BorderLayout());

      centerPane = new JScrollPane();
      centerPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.add(centerPane, BorderLayout.CENTER);
   }


   public void setTitle(JComponent component) {
      this.add(component, BorderLayout.NORTH);
   }


   public void setCenter(JComponent component) {
      centerPane.setViewportView(component);
   }


   public void setBottom(JComponent component) {
      this.add(component, BorderLayout.SOUTH);
   }

}
