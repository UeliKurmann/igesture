package org.ximtec.igesture.io.wiimote;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.StorageManager;



public class Tester {

	;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		

//		// ******************GESTURE RECOGNITION TOOL PART******************
//		
//		//Create controller
//		TestController controller = new TestController();
//		
//		// Create UI Frame
//		TestUI ui = new TestUI(controller);
//		ui.setVisible(true);
//		
//		controller.setUI(ui);
		

		
		// ******************CONVERT GESTURE SET TO TEST SET PART******************
		
		// Create storage manager
		StorageManager storage = new StorageManager(StorageManager.createStorageEngine(new File("C:\\gesturedata.db")));
		
		// Get first gesture set from database
		GestureSet gestureSet = storage.load(GestureSet.class).get(0);
		
		// Make test set
		TestSet testSet = TestBatchTool.convertGestureSetToTestSet(gestureSet);
		
		
		
		// ******************TEST BATCH PART******************
		
		// Create a testbatchtool
		TestBatchTool batchTool = new TestBatchTool(testSet,gestureSet,null,null);
		// Try to get a result out of it
		try {
			BatchResultSet batchResultSet = batchTool.runBatch();
			System.out.println("Batch Result: " + batchResultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Indicate end of code
		System.out.println("End of test main.");

	}

}
