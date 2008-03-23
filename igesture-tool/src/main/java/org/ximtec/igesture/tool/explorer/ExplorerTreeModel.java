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


public class ExplorerTreeModel implements TreeModel {

   private Object rootElement;

   private Map<Class< ? >, NodeInfo> nodeMapping;


   public ExplorerTreeModel(Object rootElement,
         Map<Class< ? >, NodeInfo> nodeDefinitions) {
      this.rootElement = rootElement;
      this.nodeMapping = nodeDefinitions;
   }


   @Override
   public Object getChild(Object node, int index) {
      NodeInfo nodeInfo = nodeMapping.get(node.getClass());

      return nodeInfo.getChildren(node).get(index);
   }


   @Override
   public int getChildCount(Object node) {
      NodeInfo nodeInfo = nodeMapping.get(node.getClass());
      return nodeInfo.getChildren(node).size();
   }


   @Override
   public int getIndexOfChild(Object node, Object child) {
      NodeInfo nodeInfo = nodeMapping.get(node.getClass());
      return nodeInfo.getChildren(node).indexOf(child);
   }


   @Override
   public Object getRoot() {
      return rootElement;
   }


   @Override
   public boolean isLeaf(Object node) {
      NodeInfo nodeInfo = nodeMapping.get(node.getClass());
      return nodeInfo.isLeaf(node);
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
