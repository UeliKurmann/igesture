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

import javax.swing.JComponent;

import org.ximtec.igesture.tool.explorer.ExplorerTree;

/**
 * Interface is implemented by components displaying an Explorer Tree
 *
 * @author  UeliKurmann
 * @version 1.0
 * @since   iGesture
 */
public interface ExplorerTreeContainer {

   /**
    * Set the Explorer Tree (this method is invoked by the ExplorerTreeController)
    * @param tree
    */
   void setTree(ExplorerTree tree);


   /**
    * Set the View of the Explorer Tree (this method is invoked by the ExplorerTreeController)
    * @param view
    */
   void setView(JComponent view);

}
