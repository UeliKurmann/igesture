package org.ximtec.igesture.tool.view.admin;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.ximtec.igesture.tool.core.TabbedView;
import org.ximtec.igesture.tool.explorer.ExplorerTree;
import org.ximtec.igesture.tool.explorer.core.ExplorerTreeContainer;

public class AdminView extends JSplitPane implements TabbedView, ExplorerTreeContainer {
	
	private JScrollPane scrollPaneRight;
	private JScrollPane scrollPaneLeft;
	
	
	public AdminView(){
		super(JSplitPane.HORIZONTAL_SPLIT);
		
		scrollPaneRight = new JScrollPane();
		scrollPaneLeft = new JScrollPane();
		
		setRightComponent(scrollPaneRight);
		setLeftComponent(scrollPaneLeft);
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
