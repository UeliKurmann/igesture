package org.ximtec.igesture.tool.explorer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;


public class ExplorerPopupDispatcher extends MouseAdapter {
  
  private static final Logger LOG = Logger.getLogger(ExplorerPopupDispatcher.class);

  private List<NodeInfo> nodeInfos;
  
  public ExplorerPopupDispatcher(List<NodeInfo> nodeInfos){
    this.nodeInfos = nodeInfos;
  }
  
   @Override
   public void mouseReleased(MouseEvent e) {
      if (e.isPopupTrigger()) {

         // lookup selected tree node
         JTree tree = (JTree)e.getSource();
         TreePath closestPathForLocation = tree.getClosestPathForLocation(e.getX(), e.getY());

         // select the nearest node if it is not active.
         if (!tree.isPathSelected(closestPathForLocation)) {
            tree.setSelectionPath(closestPathForLocation);
         }
         
         // get the selected node
         Object node = tree.getLastSelectedPathComponent();
        
         // FIXME use a map to avoid list traversals
         // get the corresponding NodeInfo instance
         for(NodeInfo nodeInfo:nodeInfos){
           if(nodeInfo.getType().equals(node.getClass())){
             LOG.info("NodeInfo was found. Get the Popup Menu.");
             // show Popup Menu
             JPopupMenu popupMenu = nodeInfo.getPopupMenu(closestPathForLocation);
             popupMenu.show(e.getComponent(), e.getX(), e.getY());
             break;
           }
         }
         
      }

   } // mouseReleased

}
