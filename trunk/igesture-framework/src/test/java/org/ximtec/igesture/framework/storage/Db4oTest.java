package org.ximtec.igesture.framework.storage;

import java.io.File;

import org.junit.Before;
import org.ximtec.igesture.storage.Db4oStorageEngine;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageManager;

public class Db4oTest extends StorageEngineTest {
	
	private static final String DB_FILE = "/db4oTestDatabase.db";
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
		StorageEngine engine = new Db4oStorageEngine(System
				.getProperty(USER_DIR)
				+ DB_FILE);
		return new StorageManager(engine);
	}

}
