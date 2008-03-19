package org.ximtec.igesture.tool.explorer.core;

import javax.swing.JComponent;

import org.ximtec.igesture.tool.explorer.ExplorerTree;

public interface ExplorerTreeContainer {
	
	void setTree(ExplorerTree tree);
	
	void setView(JComponent view);

}
