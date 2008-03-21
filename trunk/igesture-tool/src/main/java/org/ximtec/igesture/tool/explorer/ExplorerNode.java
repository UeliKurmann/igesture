package org.ximtec.igesture.tool.explorer;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.ximtec.igesture.tool.explorer.core.NodeInfo;

public class ExplorerNode<T> implements MutableTreeNode {

	private NodeInfo nodeInfo;
	
	private T userObject;
	

	public ExplorerNode(T userObject, NodeInfo nodeInfo) {
		this.userObject = userObject;
		this.nodeInfo = nodeInfo;
	}

	
	@Override
	public void insert(MutableTreeNode arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(MutableTreeNode arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromParent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(MutableTreeNode arg0) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setUserObject(Object userObject) {
		this.userObject = (T)userObject;
		
	}

	@Override
	public Enumeration<?> children() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildCount() {
		return nodeInfo.getChildren(getUserObject()).size();
	}

	@Override
	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLeaf() {
		return nodeInfo.isLeaf(getUserObject());
	}
	
	public T getUserObject(){
		return userObject;
	}
	
	@Override
	public String toString() {
		return nodeInfo.getName(getUserObject());
	}

}