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

import javax.swing.tree.DefaultMutableTreeNode;

import org.ximtec.igesture.tool.explorer.core.NodeInfo;


/**
 * Not used at the moment.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class ExplorerNode<T> extends DefaultMutableTreeNode {



   private T userObject;


   public ExplorerNode(T userObject, NodeInfo nodeInfo) {
      this.userObject = userObject;
     
   }


   @SuppressWarnings("unchecked")
   @Override
   public void setUserObject(Object userObject) {
      this.userObject = (T)(userObject);

   }


   public T getUserObject() {
      return userObject;
   }
   
   

}
