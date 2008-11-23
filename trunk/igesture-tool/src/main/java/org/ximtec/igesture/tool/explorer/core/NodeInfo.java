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

package org.ximtec.igesture.tool.explorer.core;

import java.util.List;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.tool.core.Controller;

/**
 * NodeInfo. Defines the structure of an Exlorer Tree.
 *
 * @author  UeliKurmann
 * @version 1.0
 * @since   igesture
 */
public interface NodeInfo {

   /**
    * Rerturns the type of the node
    * @return
    */
   Class< ? > getType();


   /**
    * Returns the name of the node
    * @param object
    * @return
    */
   String getName(Object object);


   /**
    * Returns the tool tip of the node
    * @return
    */
   String getTooltip();


   /**
    * Returns the icon of the node
    * @return
    */
   Icon getIcon();


   /**
    * Returns a list of children
    * @param node
    * @return
    */
   List<Object> getChildren(Object node);


   /**
    * Returns true if the current node is a leaf
    * @param node the node
    * @return true if the current node is a leaf. 
    */
   boolean isLeaf(Object node);


   /**
    * Returns the view of the node
    * @param controller
    * @param node
    * @return
    */
   ExplorerTreeView getView(Controller controller, Object node);


   /**
    * Returns the context menu of a node. 
    * @param node
    * @return
    */
   JPopupMenu getPopupMenu(TreePath node);

}
