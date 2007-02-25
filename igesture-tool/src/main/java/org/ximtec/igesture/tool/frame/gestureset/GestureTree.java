/*
 * @(#)GestureTree.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Tree showing gesture sets and classes
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.gestureset;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.ximtec.igesture.tool.util.TreeRenderer;


public class GestureTree extends JTree implements TreeSelectionListener {

   public GestureTree(GestureTreeModel model) {
      super(model);
      model.setTree(this);

      this.setCellRenderer(new TreeRenderer());
      this.setEditable(false);
   }


   public void valueChanged(TreeSelectionEvent arg0) {
      /**
       * empty
       */
   }
}
