package org.ximtec.igesture.tool.explorer.core;

import java.util.List;

import javax.swing.Icon;

public interface NodeInfo{
	
	Class<Object> getType();
	
	String getName(Object object);
	
	String getTooltip();
	
	Icon getIcon();
	
	List<Object> getChildren(Object node);
	
	boolean isLeaf(Object node);
	
	ExplorerTreeView getView(Object node);
	
}
