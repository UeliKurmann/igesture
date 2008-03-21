package org.ximtec.igesture.tool.explorer;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;

public class ExplorerTreeController implements TreeSelectionListener{

	private ExplorerTreeContainer container;
	private ExplorerTreeModel model;
	private List<NodeInfo> nodeInfos;
	
	public ExplorerTreeController(ExplorerTreeContainer container, ExplorerTreeModel model, List<NodeInfo> nodeInfos){
		this.container = container;
		this.model = model;
		this.nodeInfos = nodeInfos;
		
		ExplorerTree tree = new ExplorerTree(model);
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(new ExplorerPopup());
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

}
