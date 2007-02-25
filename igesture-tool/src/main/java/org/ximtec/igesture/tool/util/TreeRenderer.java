/*
 * @(#)TreeRenderer.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   TreeRenderer
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


package org.ximtec.igesture.tool.util;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.sigtec.util.Decorator;
import org.sigtec.util.IconTool;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;


public class TreeRenderer extends DefaultTreeCellRenderer {
   

   private static final ImageIcon PLUS_ICON = IconTool.getIcon("icons/plus",
         Decorator.SIZE_16);

   private static final ImageIcon MINUS_ICON = IconTool.getIcon("icons/minus",
         Decorator.SIZE_16);


   @Override
   public Component getTreeCellRendererComponent(JTree tree, Object value,
         boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
            hasFocus);

      this.setOpenIcon(PLUS_ICON);
      this.setClosedIcon(MINUS_ICON);
      
      
      if(value instanceof GestureSet){
    	  this.setIcon(IconLoader.getIcon(IconLoader.PACKAGE));
      }else if(value instanceof GestureClass){
    	  this.setIcon(IconLoader.getIcon(IconLoader.TEXT_SCRIPT));
      }else{
    	 this.setIcon(IconLoader.getIcon(IconLoader.HARDDISK));
      }
      

      return this;
   }

}
