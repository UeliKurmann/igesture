package org.ximtec.igesture.tool.view;

import org.ximtec.igesture.storage.XMLStorageEngine;
import org.ximtec.igesture.tool.locator.Locator;

public class MainController {
	
	private Locator locator;
	
	public MainController(){
		locator = Locator.getDefault();
		locator.addService(new MainModel(new XMLStorageEngine("file.xml")));
		
	}

}
