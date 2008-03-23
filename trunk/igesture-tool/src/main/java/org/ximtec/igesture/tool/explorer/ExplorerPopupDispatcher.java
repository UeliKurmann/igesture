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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.tool.explorer.core.NodeInfo;


public class ExplorerPopupDispatcher extends MouseAdapter {

   private static final Logger LOG = Logger.getLogger(ExplorerPopupDispatcher.class.getName());

   private Map<Class< ? >, NodeInfo> nodeInfos;


   public ExplorerPopupDispatcher(Map<Class< ? >, NodeInfo> nodeInfos) {
      this.nodeInfos = nodeInfos;
   }


   @Override
   public void mouseReleased(MouseEvent e) {
      if (e.isPopupTrigger()) {

         // lookup selected tree node
         JTree tree = (JTree)e.getSource();
         TreePath closestPathForLocation = tree.getClosestPathForLocation(e
               .getX(), e.getY());

         // select the nearest node if it is not active.
         if (!tree.isPathSelected(closestPathForLocation)) {
            tree.setSelectionPath(closestPathForLocation);
         }

         // get the selected node
         Object node = tree.getLastSelectedPathComponent();

         NodeInfo nodeInfo = nodeInfos.get(node.getClass());
         if (nodeInfo != null) {
            LOG.info("NodeInfo was found. Get the Popup Menu.");
            // show Popup Menu
            JPopupMenu popupMenu = nodeInfo.getPopupMenu(closestPathForLocation);
            popupMenu.show(e.getComponent(), e.getX(), e.getY());

         }

      }

   } // mouseReleased

}
