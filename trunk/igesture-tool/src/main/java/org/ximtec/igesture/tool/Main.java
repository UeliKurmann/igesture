package org.ximtec.igesture.tool;

import javax.swing.UIManager;

import org.ximtec.igesture.tool.view.MainController;
import org.ximtec.igesture.tool.view.MainView;

public class Main {
	
	public static void main(String[] args){
	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MainController();
		new MainView();
		
	}

}
