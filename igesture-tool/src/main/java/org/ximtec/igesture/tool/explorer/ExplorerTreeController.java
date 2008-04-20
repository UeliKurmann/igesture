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

import java.beans.PropertyChangeEvent;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.tool.core.DefaultController;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;


/**
 * Controller Component of the Explorer Tree.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class ExplorerTreeController extends DefaultController implements TreeSelectionListener {

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


   /**
    * 
    * @param container the container where the components are visualized.
    * @param model the Explorer Tree model.
    * @param nodeInfos a map of NodeInfos
    */
   public ExplorerTreeController(ExplorerTreeContainer container,
         ExplorerTreeModel model, Map<Class< ? >, NodeInfo> nodeInfos) {
      this.container = container;
      this.model = model;
      this.nodeInfos = nodeInfos;
      
      //FIXME renderer should not be hard coded. 
      DefaultTreeCellRenderer renderer = new NodeRenderer(nodeInfos);
      
      tree = new ExplorerTree(this.model, renderer);
      tree.addTreeSelectionListener(this);
      tree.addMouseListener(new ExplorerPopupDispatcher(nodeInfos));

      container.setTree(tree);
      container.setView((JComponent)nodeInfos.get(model.getRoot().getClass())
            .getView(model.getRoot()));
   }


   /*
    * (non-Javadoc)
    * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
    */
   @Override
   public void valueChanged(TreeSelectionEvent e) {
      Object node = e.getPath().getLastPathComponent();
      if (nodeInfos.get(node.getClass()) != null) {
         container.setView((JComponent)nodeInfos.get(node.getClass()).getView(node));
      }
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.tool.core.Controller#getView()
    */
   @Override
   public JComponent getView() {
      return tree;
   }
   
   public void selectNode(Object obj){
      // FIXME how to implement this?
      
      TreePath path = new TreePath(obj);
      tree.setSelectionPath(path);
      tree.setExpandsSelectedPaths(true);
   }


   /*
    * (non-Javadoc)
    * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
    */
   @Override
   public void propertyChange(PropertyChangeEvent arg0) {
      LOG.info("PropertyChange, Update Tree");

      // FIXME find a solution to update the tree more efficiently
      tree.updateUI();
   }

}
