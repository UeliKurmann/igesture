/*
 * @(#)TreeRenderer.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   A tree renderer.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.sigtec.graphix.IconTool;
import org.sigtec.util.Decorator;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;


/**
 * A tree renderer.
 * 
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TreeRenderer extends DefaultTreeCellRenderer {

   private static final ImageIcon PLUS_ICON = IconTool.getIcon("plus",
         Decorator.SIZE_16);

   private static final ImageIcon MINUS_ICON = IconTool.getIcon("minus",
         Decorator.SIZE_16);


   @Override
   public Component getTreeCellRendererComponent(JTree tree, Object value,
         boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
            hasFocus);
      setOpenIcon(PLUS_ICON);
      setClosedIcon(MINUS_ICON);

      if (value instanceof GestureSet) {
         setIcon(IconLoader.getIcon(IconLoader.PACKAGE));
      }
      else if (value instanceof GestureClass) {
         setIcon(IconLoader.getIcon(IconLoader.TEXT_SCRIPT));
      }
      else {
         setIcon(IconLoader.getIcon(IconLoader.HARDDISK));
      }

      return this;
   } // getTreeCellRendererComponent

}
