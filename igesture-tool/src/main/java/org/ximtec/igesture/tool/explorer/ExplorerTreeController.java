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

import java.beans.PropertyChangeEvent;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeView;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;


/**
 * Controller Component of the Explorer Tree.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class ExplorerTreeController extends DefaultController implements
      TreeSelectionListener {

   private static final Logger LOG = Logger
         .getLogger(ExplorerTreeController.class.getName());

   /**
    * The Container where the Tree and Views are shown. A Container has to
    * implement the Container interface.
    */
   private ExplorerTreeContainer container;

   /**
    * The Model of the Explorer Tree
    */
   private ExplorerTreeModel model;

   /**
    * A Map of NodeInfo. Node Info contains information about a specific node.
    */
   private Map<Class< ? >, NodeInfo> nodeInfos;

   /**
    * The Explorer Tree instance.
    */
   private ExplorerTree tree;

   private ExplorerTreeView selectedExplorerTreeView;


   public ExplorerTreeController(Controller parentController, ExplorerTreeContainer container,
         ExplorerTreeModel model, TreeCellRenderer renderer) {
	   super(parentController);
      this.container = container;
      this.model = model;
      this.nodeInfos = model.getNodeInfos();

      tree = new ExplorerTree(this.model, renderer);
      tree.addTreeSelectionListener(this);
      tree.addMouseListener(new ExplorerPopupDispatcher(nodeInfos));

      container.setTree(tree);
      selectedExplorerTreeView = nodeInfos.get(model.getRoot().getClass())
            .getView(this, model.getRoot());
      container.setView(selectedExplorerTreeView);

   }


   /**
    * 
    * @param container the container where the components are visualized.
    * @param model the Explorer Tree model.
    * @param nodeInfos a map of NodeInfos
    */
   public ExplorerTreeController(Controller parentController, ExplorerTreeContainer container,
         ExplorerTreeModel model) {
      this(parentController, container, model, new NodeRenderer(model.getNodeInfos()));
   }


   /*
    * (non-Javadoc)
    * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
    */
   @Override
   public void valueChanged(TreeSelectionEvent e) {
      final Object node = e.getPath().getLastPathComponent();
      if (nodeInfos.get(node.getClass()) != null) {
         SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
               selectedExplorerTreeView = nodeInfos.get(node.getClass())
                     .getView(ExplorerTreeController.this, node);
               container.setView(selectedExplorerTreeView);
            }
         });
      }
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.core.Controller#getView()
    */
   @Override
   public TabbedView getView() {
      return new TabbedView(){

         @Override
         public Icon getIcon() {
            return null;
         }

         @Override
         public String getTabName() {
            return tree.getName();
         }

         @Override
         public JComponent getPane() {
            return tree;
         }
         
      };
   }


   public void selectNode(Object obj) {
      TreePath path = new TreePath(obj);
      tree.setSelectionPath(path);
      tree.setExpandsSelectedPaths(true);
   }


   public ExplorerTreeView getExplorerTreeView() {
      return selectedExplorerTreeView;
   }


   /*
    * (non-Javadoc)
    * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
    */
   @Override
   public void propertyChange(PropertyChangeEvent evt) {

      LOG.info("PropertyChange, Update Tree");

      // node was inserted, select the inserted node
      if (evt.getOldValue() == null && evt.getNewValue() != null) {
         TreePath[] paths = tree.getSelectionPaths();
         if (paths != null) {
            for (TreePath treePath : paths) {

               if (treePath.getLastPathComponent() == evt.getSource() && !nodeInfos.get(treePath.getLastPathComponent().getClass()).isLeaf(treePath.getLastPathComponent())) {
                  tree.setSelectionPath(treePath.pathByAddingChild(evt
                        .getNewValue()));
               }
            }
         }
      }
      // node was deleted, select the parent node
      else if (evt.getOldValue() != null && evt.getNewValue() == null) {
         TreePath[] paths = tree.getSelectionPaths();
         if (paths != null) {
            for (TreePath treePath : paths) {

               if (treePath.getParentPath() != null && treePath.getParentPath().getLastPathComponent() == evt.getSource()) {
                  tree.setSelectionPath(treePath.getParentPath());
               }
            }
         }
      }

      // FIXME find a solution to update the tree more efficiently
      tree.updateUI();
   }

}
