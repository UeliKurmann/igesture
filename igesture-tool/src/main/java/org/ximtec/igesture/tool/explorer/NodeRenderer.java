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

import java.awt.Component;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.ximtec.igesture.tool.explorer.core.NodeInfo;

/**
 * 
 * @author UeliKurmann
 * @version 1.0
 * @since igesture
 */
public class NodeRenderer extends DefaultTreeCellRenderer {

  /**
   * A Map of NodeInfo. Node Info contains information about a specific node.
   */
  private Map<Class<?>, NodeInfo> nodeInfos;

  public NodeRenderer(Map<Class<?>, NodeInfo> nodeInfos) {
    this.nodeInfos = nodeInfos;
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
      int row, boolean hasFocus) {

    if (nodeInfos.get(value.getClass()) != null) {

      NodeInfo nodeInfo = nodeInfos.get(value.getClass());

      setToolTipText(nodeInfo.getTooltip());
      
      Icon icon;
      
      if(expanded){
        icon = nodeInfo.getExpandedIcon();
      }else{
        icon = nodeInfo.getIcon();
      }
      
      if(expanded && icon == null){
        icon = nodeInfo.getIcon();
      }
      
      if (icon != null) {
        setIcon(icon);
      }

      String name;
      if ((name = nodeInfo.getName(value)) != null) {
        setText(name);
      }
    }

    return this;
  }
}
