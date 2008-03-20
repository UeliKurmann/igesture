package org.ximtec.igesture.tool.view;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.util.XMLTool;

public class MainModel implements Service, PropertyChangeListener{
	
	public static final String IDENTIFIER = "MainModel";
	
	private StorageManager storageManager;
	
	public MainModel(StorageEngine engine){
		this.storageManager = new StorageManager(engine);
	}
	
	public GestureSet getTestGestureSet(){
		String filename = "D:/workspace/igesture/igesture/igesture-db/igesture-db-graffitiNumbers/src/main/resources/gestureSet/graffitiNumbers.xml";
		return XMLTool.importGestureSet(new File(filename)).get(0);
	}

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		System.out.println(arg0);
		
	}
}
