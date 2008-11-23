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


package org.ximtec.igesture.tool.explorer;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;


/**
 * Extends JTree and supports additional functionality and default properties.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class ExplorerTree extends JTree {

   public ExplorerTree(TreeModel treeModel, TreeCellRenderer renderer) {
      super(treeModel);
      setCellRenderer(renderer);
   }
   
   
   
}
