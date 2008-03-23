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

/**
 * NodeInfo. Defines the structure of an Exlorer Tree.
 *
 * @author  UeliKurmann
 * @version 1.0
 * @since   igesture
 */
public interface NodeInfo {

   Class< ? > getType();


   String getName(Object object);


   String getTooltip();


   Icon getIcon();


   List<Object> getChildren(Object node);


   boolean isLeaf(Object node);


   ExplorerTreeView getView(Object node);


   JPopupMenu getPopupMenu(TreePath node);

}
