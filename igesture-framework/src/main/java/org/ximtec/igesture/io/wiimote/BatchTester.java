package org.ximtec.igesture.io.wiimote;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.sigtec.ink.Note;
import org.ximtec.igesture.batch.BatchResult;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Sample3DDescriptor;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.util.RecordedGesture3D;

public class BatchTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// ******************GET GESTURE AND TEST SET FROM DATABASE PART******************
		
		// Create storage manager for gesture set
		StorageManager storageGesture = new StorageManager(StorageManager.createStorageEngine(new File("C:\\GestureDB\\Batch\\gestureset.db")));
		// Get first gesture set from database
		GestureSet gestureSet3D = storageGesture.load(GestureSet.class).get(0);
		
		// Create storage manager for test set
		StorageManager storageTest = new StorageManager(StorageManager.createStorageEngine(new File("C:\\GestureDB\\Batch\\testset.db")));
		// Get first gesture set from database
		GestureSet testSet3D = storageTest.load(GestureSet.class).get(0);
		
		
		
		//******************SPLIT 3D GESTURE AND TEST SET TO 3 2D SETS PART******************
		
		// Gesture set
		List<GestureSet> gestureSets2D = TestBatchTool.splitSetToPlanes(gestureSet3D);
		GestureSet gestureSetXY = gestureSets2D.get(0);
		GestureSet gestureSetYZ = gestureSets2D.get(1);
		GestureSet gestureSetZX = gestureSets2D.get(2);
		// Test set
		List<GestureSet> testSets2D = TestBatchTool.splitSetToPlanes(testSet3D);
		GestureSet testSetXY = testSets2D.get(0);
		GestureSet testSetYZ = testSets2D.get(1);
		GestureSet testSetZX = testSets2D.get(2);

		
		
		//******************CONVERT TO TEST SETS PART******************
		
		//Make XY 2D test set
		TestSet xyTestSet = TestBatchTool.convert2DGestureSetToTestSet(testSetXY);
		//Make XY 2D test set
		TestSet yzTestSet = TestBatchTool.convert2DGestureSetToTestSet(testSetYZ);
		//Make XY 2D test set
		TestSet zxTestSet = TestBatchTool.convert2DGestureSetToTestSet(testSetZX);
		// Make 3D test set
		TestSet testGestureSet3D = TestBatchTool.convert3DGestureSetToTestSet(testSet3D);
		
		
		
		StorageManager store = new StorageManager(StorageManager.createStorageEngine(new File("C:\\gestureset.igz")));
		store.store(gestureSetXY);
		store.store(xyTestSet);
		store.commit();
		
		
		
//
//		System.err.println("\nGESTURE SET NAME: " + gestureSet3D.getName());
//		for(int i = 0; i < gestureSet3D.getGestureClasses().size(); i++){
//			System.err.println("  GESTURE CLASS " + i + " NAME: " + gestureSet3D.getGestureClasses().get(i).getName());
//			for(int j = 0; j < gestureSet3D.getGestureClasses().get(i).getDescriptor(Sample3DDescriptor.class).getSamples().size(); j++){
//				System.err.println("    SAMPLE: " + gestureSet3D.getGestureClasses().get(i).getDescriptor(Sample3DDescriptor.class).getSample(j));
//			}
//		}
//		
//		System.err.println("\nTEST SET NAME: " + testGestureSet3D.getName());
//		for(int i = 0; i < testGestureSet3D.getTestClasses().size(); i++){
//			System.err.println("  TEST CLASS " + i + " NAME: " + testGestureSet3D.getTestClasses().get(i).getName());
//			for(int j = 0; j < testGestureSet3D.getTestClasses().get(i).getGestures().size(); j++){
//				System.err.println("    SAMPLE: " + testGestureSet3D.getTestClasses().get(i).getGestures().get(j));
//			}
//		}
		
//
//		System.err.println("\nGESTURE SET NAME: " + gestureSetXY.getName());
//		for(int i = 0; i < gestureSetXY.getGestureClasses().size(); i++){
//			System.err.println("  GESTURE CLASS " + i + " NAME: " + gestureSetXY.getGestureClasses().get(i).getName());
//			for(int j = 0; j < gestureSetXY.getGestureClasses().get(i).getDescriptor(SampleDescriptor.class).getSamples().size(); j++){
//				System.err.println("    SAMPLE: " + gestureSetXY.getGestureClasses().get(i).getDescriptor(SampleDescriptor.class).getSample(j).getGesture().getClass());
//			}
//		}
//		
//		System.err.println("\nTEST SET NAME: " + xyTestSet.getName());
//		for(int i = 0; i < xyTestSet.getTestClasses().size(); i++){
//			System.err.println("  TEST CLASS " + i + " NAME: " + xyTestSet.getTestClasses().get(i).getName());
//			for(int j = 0; j < xyTestSet.getTestClasses().get(i).getGestures().size(); j++){
//				System.err.println("    SAMPLE: " + xyTestSet.getTestClasses().get(i).getGestures().get(j).getGesture().getClass());
//			}
//		}
//		
		
//		
//		
//		// ******************TEST BATCH PART******************
//		
//		// Create a testbatchtool
//		TestBatchTool batchTool = new TestBatchTool(yzTestSet,gestureSetYZ,null,null);
//		BatchResultSet batchResultSet = null;
//		// Try to get a result out of it
//		try {
//			batchResultSet = batchTool.runBatch();
//			System.out.println("Number of Batch Results: " + batchResultSet.getBatchResults().size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		// ******************TEST RESULTS PART******************
//		
//		//Check for the best result
//		BatchResult bestResult = batchResultSet.getBatchResults().get(0);
//		Iterator<BatchResult> i = batchResultSet.getBatchResults().iterator();
//		while(i.hasNext()){
//			BatchResult tempResult = i.next();
//			if(tempResult.getNumberOfCorrects() > bestResult.getNumberOfCorrects()){
//				bestResult = tempResult;
//			}
//		}		
//		System.out.println("Highest correct number: " + bestResult.getNumberOfCorrects());
		
		// Indicate end of code
		System.out.println("End of test main.");

	}

}
