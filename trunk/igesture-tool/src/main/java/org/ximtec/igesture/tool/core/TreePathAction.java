package org.ximtec.igesture.tool.core;

import javax.swing.tree.TreePath;

public abstract class TreePathAction extends LocateableAction {

	private TreePath treePath;
	private Controller controller;

	public TreePathAction(String key, Controller controller, TreePath treePath){
		super(key, controller.getLocator());
		this.treePath = treePath;
		this.controller = controller;
	}
	
	protected void setTreePath(TreePath treePath){
		this.treePath = treePath;
	}
	
	protected TreePath getTreePath(){
		return treePath;
	}
	
	protected Controller getController(){
		return controller;
	}
	

}
