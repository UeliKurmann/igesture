/*
 * @(#)GestureTreeModel.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Tree Model used by GestureTree.java
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
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.frame.gestureset;

import java.util.List;

import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureMainModel;


public class GestureTreeModel implements TreeModel {

   private JTree tree;

   protected EventListenerList listeners;

   public class rootElement {

      public List<GestureSet> childs;


      public rootElement(List<GestureSet> gestures) {
         childs = gestures;
      }


      @Override
      public String toString() {
         return "Gestures";
      }
   }


   public void setTree(JTree tree) {
      this.tree = tree;
   }


   public JTree getTree() {
      return this.tree;
   }

   rootElement root;

   private GestureMainModel model;


   public GestureTreeModel(GestureMainModel model) {
      this.model = model;
      root = new rootElement(model.getGestureSets());
      this.listeners = new EventListenerList();
   }


   public void valueChanged(TreeSelectionEvent e) {
      // TODO Auto-generated method stub

   }


   public Object getChild(Object col, int i) {
      if (col instanceof GestureSet) {
         return ((GestureSet) col).getGestureClasses().toArray()[i];
      }
      else if (col instanceof rootElement) {
         return ((rootElement) col).childs.toArray()[i];
      }
      return null;

   }


   public int getChildCount(Object col) {
      if (col instanceof GestureSet) {
         return ((GestureSet) col).getGestureClasses().size();
      }
      else if (col instanceof GestureClass) {
         return 0;
      }
      else if (col instanceof rootElement) {
         return ((rootElement) col).childs.size();
      }
      return 0;
   }


   public int getIndexOfChild(Object arg0, Object arg1) {
      return 0;
   }


   public Object getRoot() {
      return root;
   }


   public boolean isLeaf(Object obj) {
      return obj instanceof GestureClass;
   }


   public void addGestureSet(GestureSet set) {
      model.addGestureSet(set);
   }


   public GestureMainModel getModel() {
      return model;
   }


   public void addTreeModelListener(TreeModelListener arg0) {
      // TODO Auto-generated method stub

   }


   public void removeTreeModelListener(TreeModelListener arg0) {
      // TODO Auto-generated method stub

   }


   public void valueForPathChanged(TreePath arg0, Object arg1) {
      // TODO Auto-generated method stub

   }

}
