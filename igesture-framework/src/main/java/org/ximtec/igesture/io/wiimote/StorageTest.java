package org.ximtec.igesture.io.wiimote;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import org.ximtec.igesture.core.DataObject;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.util.RecordedGesture3D;

public class StorageTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StorageManager storage = new StorageManager(StorageManager.createStorageEngine(new File("C:\\ali.db")));
		
		
		JFrame frame = new JFrame("Testframe");
		
		
		
		//DataObject obj = new GestureSample3D("TEST2", new RecordedGesture3D());
		
		//storage.store(obj);
		
		List<GestureSample3D> samples = storage.load(GestureSample3D.class, "name", "TEST3");
		
		
		System.out.println("Retrieved object 0 name: " + samples.get(0).getName());
		
	}

}
