/*
 * @(#)GestureTreeModel.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Tree model used by the GestureTree class.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.old.frame.gestureset;

import java.util.List;

import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.old.GestureMainModel;


/**
 * Tree model used by the GestureTree class.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
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
      } // toString

   }


   public void setTree(JTree tree) {
      this.tree = tree;
   } // setTree


   public JTree getTree() {
      return this.tree;
   } // getTree

   rootElement root;

   private GestureMainModel model;


   public GestureTreeModel(GestureMainModel model) {
      this.model = model;
      root = new rootElement(model.getGestureSets());
      listeners = new EventListenerList();
   }


   public void valueChanged(TreeSelectionEvent e) {
   } // valueChanged


   public Object getChild(Object col, int i) {
      if (col instanceof GestureSet) {
         return ((GestureSet)col).getGestureClasses().toArray()[i];
      }
      else if (col instanceof rootElement) {
         return ((rootElement)col).childs.toArray()[i];
      }

      return null;
   } // getChild


   public int getChildCount(Object col) {
      if (col instanceof GestureSet) {
         return ((GestureSet)col).getGestureClasses().size();
      }
      else if (col instanceof GestureClass) {
         return 0;
      }
      else if (col instanceof rootElement) {
         return ((rootElement)col).childs.size();
      }

      return 0;
   } // getChildCount


   public int getIndexOfChild(Object arg0, Object arg1) {
      return 0;
   } // getIndexOfChild


   public Object getRoot() {
      return root;
   } // getRoot


   public boolean isLeaf(Object obj) {
      return obj instanceof GestureClass;
   } // isLeaf


   public void addGestureSet(GestureSet set) {
      model.addGestureSet(set);
   } // addGestureSet


   public GestureMainModel getModel() {
      return model;
   } // getModel


   public void addTreeModelListener(TreeModelListener arg0) {
   } // addTreeModelListener


   public void removeTreeModelListener(TreeModelListener arg0) {
   } // removeTreeModelListener


   public void valueForPathChanged(TreePath arg0, Object arg1) {
   } // valueForPathChanged

}
