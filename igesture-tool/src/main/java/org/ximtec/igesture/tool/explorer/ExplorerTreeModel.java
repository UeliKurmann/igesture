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

import java.util.Map;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.tool.explorer.core.NodeInfo;


/**
 * Explorer Tree Model. NodeMappings are used to dynamically build up the tree in
 * a generic manner. The tree structure is defined with NodeInfo instances. 
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class ExplorerTreeModel implements TreeModel {

   /**
    * The root element of the tree.
    */
   private Object rootElement;

   /**
    * Node Info Map
    */
   private Map<Class< ? >, NodeInfo> nodeInfos;


   /**
    * Explorer Tree Model Model
    * @param rootElement the root node
    * @param nodeInfos node Info
    */
   public ExplorerTreeModel(Object rootElement,
         Map<Class< ? >, NodeInfo> nodeInfos) {
      this.rootElement = rootElement;
      this.nodeInfos = nodeInfos;
   }


   @Override
   public Object getChild(Object node, int index) {
      NodeInfo nodeInfo = nodeInfos.get(node.getClass());
      return nodeInfo.getChildren(node).get(index);
   }


   @Override
   public int getChildCount(Object node) {
      NodeInfo nodeInfo = nodeInfos.get(node.getClass());
      return nodeInfo.getChildren(node).size();
   }


   @Override
   public int getIndexOfChild(Object node, Object child) {
      NodeInfo nodeInfo = nodeInfos.get(node.getClass());
      return nodeInfo.getChildren(node).indexOf(child);
   }


   @Override
   public Object getRoot() {
      return rootElement;
   }


   @Override
   public boolean isLeaf(Object node) {
      NodeInfo nodeInfo = nodeInfos.get(node.getClass());
      if(nodeInfo != null){
         return nodeInfo.isLeaf(node);
      }
      return true;
   }


   @Override
   public void addTreeModelListener(TreeModelListener arg0) {
      // TODO Auto-generated method stub
   }


   @Override
   public void removeTreeModelListener(TreeModelListener arg0) {

   }


   @Override
   public void valueForPathChanged(TreePath arg0, Object arg1) {

   }

}
