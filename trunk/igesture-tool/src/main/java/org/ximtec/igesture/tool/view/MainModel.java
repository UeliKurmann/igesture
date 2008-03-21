package org.ximtec.igesture.tool.view;


import java.io.File;

import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.storage.IStorageManager;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.tool.util.PropertyChangeVisitor;
import org.ximtec.igesture.tool.util.StorageManagerProxy;
import org.ximtec.igesture.util.XMLTool;

public class MainModel implements Service{
	
	public static final String IDENTIFIER = "MainModel";
	
	private IStorageManager storageManager;
	
	private MainController mainController;
	
	public MainModel(StorageEngine engine, MainController mainController){
		
		this.mainController = mainController;
		
		// StorageManager is wrapped with a Dynamic Proxy to register a PropertyChangeListener
		PropertyChangeVisitor visitor = new PropertyChangeVisitor(mainController);
		this.storageManager = StorageManagerProxy.newInstance(new StorageManager(engine), visitor);
	}
	
	
	
	
	/**
	 * This method is used for test purposes only and will be removed later on...
	 * @return
	 */
	private GestureSet gestureSet;
	@Deprecated
	public RootSet getTestGestureSet(){
		if(gestureSet == null){
	  String filename = "D:/workspace/igesture/igesture/igesture-db/igesture-db-graffitiNumbers/src/main/resources/gestureSet/graffitiNumbers.xml";
		gestureSet = XMLTool.importGestureSet(new File(filename)).get(0);
		PropertyChangeVisitor visitor = new PropertyChangeVisitor(mainController);
		gestureSet.accept(visitor);
		}
		
		RootSet rootSet = new RootSet();
		rootSet.addGestureSet(gestureSet);
		rootSet.addPropertyChangeListener(mainController);
		
		return rootSet;
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
		storageManager.dispose();
	}
	
	public IStorageManager getStorageManager(){
	  return storageManager;
	}

}
