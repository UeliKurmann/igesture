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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.explorer;

import java.awt.Color;

import org.ximtec.igesture.tool.explorer.core.ExplorerTreeView;


/**
 * Default Implementation of a view. A view visualizes a node of the tree.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public abstract class DefaultExplorerTreeView extends ExplorerTreeView {

   public DefaultExplorerTreeView() {
      setOpaque(true);
      this.setBackground(Color.white);
      this.setForeground(Color.white);
   }

}
