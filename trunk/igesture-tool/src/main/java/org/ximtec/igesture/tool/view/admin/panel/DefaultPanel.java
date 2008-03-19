package org.ximtec.igesture.tool.view.admin.panel;

import javax.swing.JLabel;

import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;


public class DefaultPanel extends DefaultExplorerTreeView {
	
	public DefaultPanel(String obj){
		this.add(new JLabel("Default "+obj.toString()));
	}
	
	
	
	

}
