package org.ximtec.igesture.tool.explorer.core;

import java.util.List;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

public interface NodeInfo{
	
	Class<?> getType();
	
	String getName(Object object);
	
	String getTooltip();
	
	Icon getIcon();
	
	List<Object> getChildren(Object node);
	
	boolean isLeaf(Object node);
	
	ExplorerTreeView getView(Object node);
	
	JPopupMenu getPopupMenu(TreePath node);
	
}
