package org.ximtec.igesture.tool.explorer;

import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.log4j.Logger;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;

public class ExplorerTreeController implements TreeSelectionListener, Controller{
  
  private static final Logger LOG = Logger.getLogger(ExplorerTreeController.class);

	private ExplorerTreeContainer container;
	private ExplorerTreeModel model;
	private List<NodeInfo> nodeInfos;
	private ExplorerTree tree;
	
	public ExplorerTreeController(ExplorerTreeContainer container, ExplorerTreeModel model, List<NodeInfo> nodeInfos){
		this.container = container;
		this.model = model;
		this.nodeInfos = nodeInfos;
		tree = new ExplorerTree(this.model);
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(new ExplorerPopupDispatcher(nodeInfos));
		container.setTree(tree);
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		Object node = e.getPath().getLastPathComponent();
		for(NodeInfo nodeInfo:nodeInfos){
			if(nodeInfo.getType().equals(node.getClass())){
				container.setView((JComponent)nodeInfo.getView(node));
			}
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
