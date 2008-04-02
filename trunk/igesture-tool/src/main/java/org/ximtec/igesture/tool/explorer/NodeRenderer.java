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

import java.awt.Component;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.ximtec.igesture.tool.explorer.core.NodeInfo;


/**
 * TODO: use node info to use defined icons.
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class NodeRenderer extends DefaultTreeCellRenderer {

   /**
    * A Map of NodeInfo. Node Info contains information about a specific node.
    */
   private Map<Class< ? >, NodeInfo> nodeInfos;


   public NodeRenderer(Map<Class< ? >, NodeInfo> nodeInfos) {
      this.nodeInfos = nodeInfos;
   }


   @Override
   public Component getTreeCellRendererComponent(JTree tree, Object value,
         boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
            hasFocus);

      if (nodeInfos.get(value.getClass()) != null
            && nodeInfos.get(value.getClass()).getIcon() != null) {
         setIcon(nodeInfos.get(value.getClass()).getIcon());
      }

      return this;

   }
}
