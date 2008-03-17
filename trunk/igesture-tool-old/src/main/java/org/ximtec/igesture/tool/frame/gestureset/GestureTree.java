/*
 * @(#)GestureTree.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Tree showing gesture sets and classes.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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


/**
 * Tree showing gesture sets and classes.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureTree extends JTree implements TreeSelectionListener {

   public GestureTree(GestureTreeModel model) {
      super(model);
      model.setTree(this);
      setCellRenderer(new TreeRenderer());
      setEditable(false);
   }


   public void valueChanged(TreeSelectionEvent event) {
   } // valueChanged
   
}
