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

import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;


public class ExplorerTreeController implements TreeSelectionListener, Controller {

   private static final Logger LOG = Logger.getLogger(ExplorerTreeController.class.getName());

   private ExplorerTreeContainer container;
   private ExplorerTreeModel model;
   private Map<Class< ? >, NodeInfo> nodeInfos;
   private ExplorerTree tree;


   public ExplorerTreeController(ExplorerTreeContainer container,
         ExplorerTreeModel model, Map<Class< ? >, NodeInfo> nodeInfos) {
      this.container = container;
      this.model = model;
      this.nodeInfos = nodeInfos;
      tree = new ExplorerTree(this.model);
      tree.addTreeSelectionListener(this);
      tree.addMouseListener(new ExplorerPopupDispatcher(nodeInfos));

      container.setTree(tree);
      container.setView((JComponent)nodeInfos.get(model.getRoot().getClass())
            .getView(model.getRoot()));

   }


   @Override
   public void valueChanged(TreeSelectionEvent e) {
      Object node = e.getPath().getLastPathComponent();
      if (nodeInfos.get(node.getClass()) != null) {
         container.setView((JComponent)nodeInfos.get(node.getClass()).getView(
               node));
      }
   }


   @Override
   public JComponent getView() {
      return tree;
   }


   @Override
   public void propertyChange(PropertyChangeEvent arg0) {
      LOG.info("PropertyChange, Update Tree");

      // FIXME find a solution to update the tree more efficiently
      tree.updateUI();
   }

}
