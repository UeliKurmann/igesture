package org.ximtec.igesture.tool.view.admin;

import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTree;
import org.ximtec.igesture.tool.explorer.ExplorerTreeController;
import org.ximtec.igesture.tool.explorer.ExplorerTreeModel;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;

public class AdminView extends JSplitPane implements TabbedView, ExplorerTreeContainer {
	
	private JScrollPane scrollPaneRight;
	private JScrollPane scrollPaneLeft;
	
	public AdminView(ExplorerTreeModel explorerModel, List<NodeInfo> nodeInfos){
		super(JSplitPane.HORIZONTAL_SPLIT);
		
		scrollPaneRight = new JScrollPane();
		scrollPaneLeft = new JScrollPane();
		
		setRightComponent(scrollPaneRight);
		setLeftComponent(scrollPaneLeft);
		
		ExplorerTreeController controller = new ExplorerTreeController(this, explorerModel, nodeInfos);
	}

	@Override
	public Icon getIcon() {
		
		return null;
		
	}

	@Override
	public String getName() {
		return "Admin";
	}

	@Override
	public JComponent getPane() {
		return this;
	}

	@Override
	public void setTree(ExplorerTree tree) {
		scrollPaneLeft.setViewportView(tree);
	}

	@Override
	public void setView(JComponent view) {

		scrollPaneRight.setViewportView(view);
	}

}
