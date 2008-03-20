package org.ximtec.igesture.tool.explorer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.ximtec.igesture.tool.explorer.core.NodeInfo;

public class ExplorerTreeModel implements TreeModel{

	private Object rootElement;
	
	private Map<Class<Object>, NodeInfo> nodeMapping;
	
	public ExplorerTreeModel(Object rootElement, List<NodeInfo> nodeDefinitions){
		this.rootElement = rootElement;
		
		nodeMapping = new HashMap<Class<Object>, NodeInfo>();
		for(NodeInfo node:nodeDefinitions){
			nodeMapping.put(node.getType(), node);
		}
	}
	
	
	@Override
	public Object getChild(Object node, int index) {
		NodeInfo nodeInfo = nodeMapping.get(node.getClass());
		
		return nodeInfo.getChildren(node).get(index);
	}

	@Override
	public int getChildCount(Object node) {
		NodeInfo nodeInfo = nodeMapping.get(node.getClass());
		return nodeInfo.getChildren(node).size();
	}

	@Override
	public int getIndexOfChild(Object node, Object child) {
		NodeInfo nodeInfo = nodeMapping.get(node.getClass());	
		return nodeInfo.getChildren(node).indexOf(child);
	}

	@Override
	public Object getRoot() {
		return rootElement;
	}

	@Override
	public boolean isLeaf(Object node) {
		NodeInfo nodeInfo = nodeMapping.get(node.getClass());
		return nodeInfo.isLeaf(node);
	}

	@Override
	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		
	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
		
	}

}
