package org.ximtec.igesture.tool.explorer;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;

public class ExplorerTree extends JTree{
	
	public ExplorerTree(TreeModel treeModel){
		super(treeModel);
		setCellRenderer(new NodeRenderer());
		
	}

}
