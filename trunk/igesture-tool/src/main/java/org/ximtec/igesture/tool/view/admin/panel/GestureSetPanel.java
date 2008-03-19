package org.ximtec.igesture.tool.view.admin.panel;

import java.awt.Color;

import javax.swing.JLabel;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.explorer.DefaultExplorerTreeView;


public class GestureSetPanel extends DefaultExplorerTreeView {
	
	public GestureSetPanel(GestureSet gestureSet){
		this.add(new JLabel("GestureSet "+gestureSet.getName()));
		this.setOpaque(true);
		this.setForeground(Color.red);
		this.setBackground(Color.red);
	}
	
	
	
	

}
