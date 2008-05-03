package org.ximtec.igesture.framework.storage;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.storage.ZipStorageEngine;

public class ZipTest extends StorageEngineTest {
	
	private static final String DB_FILE = "/ZipDatabase.zip";
	private static final String USER_DIR = "user.dir";
	
	
	@Override
	@Before
	public void setUp() {
		File file = new File(System.getProperty(USER_DIR) + DB_FILE);
		if(file.exists()){
			file.delete();
		}
		super.setUp();
	}
	
	public StorageManager getStorageManager() {
		StorageEngine engine = new ZipStorageEngine(System
				.getProperty(USER_DIR)
				+ DB_FILE);
		return new StorageManager(engine);
	}
	
	@Test
    public void testParamLoad() {
        
    }
	
	@After
	public void cleanUp(){
	   
	}

}
