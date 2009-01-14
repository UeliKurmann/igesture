package org.ximtec.igesture.io.wiimote;

import java.io.File;
import java.util.Iterator;

import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchProcessContainer;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.batch.BatchTools;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Sample3DDescriptor;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.util.RecordedGesture3D;
import org.ximtec.igesture.util.XMLTool;

public class TestBatchTool {

	private File configFile;
	private File outputDir;
	TestSet testSet;
	GestureSet gestureSet;

	/**
	 * Constructor
	 * 
	 * @param testSet
	 * @param gestureSet
	 */
	public TestBatchTool(TestSet testSet, GestureSet gestureSet, String configFileName, String outputDirName) {
		if(configFileName == null || configFileName.equals(""))
			configFile = new File("C:\\configuration.xml");
		if(outputDirName == null || outputDirName.equals(""))
			outputDir = new File("C:\\Output");
		if(testSet == null){
			//this.testSet = 
		}
		else {
			this.testSet = testSet;
		}			
		if(gestureSet == null){
			//this.gestureSet 
		}
		else {
			this.gestureSet = gestureSet;
		}
	}

	/**
	 * Runs a batch process to find the best setting for the algorithm features
	 * 
	 * @return
	 * @throws Exception
	 */
	public BatchResultSet runBatch() throws Exception {
		// Output variable
		BatchResultSet resultSet = null;
		// If conditions are met to start the batch process
		if (configFile.exists() && testSet != null && gestureSet != null) {
			// New batch process container
			BatchProcessContainer container = XMLTool
					.importBatchProcessContainer(configFile);
			// New batch process
			BatchProcess batchProcess = new BatchProcess(container);
			// Set sets for batch process
			batchProcess.setTestSet(testSet);
			batchProcess.addGestureSet(gestureSet);
			// Run the process
			resultSet = batchProcess.run();
			// Write results to file
			BatchTools.writeResultsOnDisk(outputDir, resultSet);
		}
		// Return result
		return resultSet;
	}

	/**
	 * Converts a GestureSet to a TestSet
	 * 
	 * @param gestureSet
	 * @return
	 */
	public static TestSet convertGestureSetToTestSet(GestureSet gestureSet) {
		TestSet testSet = new TestSet(gestureSet.getName());
		// Iterate through gesture classes in gesture set and add them to
		// testset
		Iterator<GestureClass> i = gestureSet.getGestureClasses().iterator();
		while (i.hasNext()) {
			GestureClass tempGestureClass = i.next();
			// Create new TestClass
			TestClass tempTestClass = new TestClass(tempGestureClass.getName());
			// Add gesture samples from the gesture class to the test class
			Iterator<Gesture<RecordedGesture3D>> sampleIter = tempGestureClass
					.getDescriptor(Sample3DDescriptor.class).getSamples()
					.iterator();
			while (sampleIter.hasNext()) {
				GestureSample3D tempSample = (GestureSample3D) sampleIter
						.next();
				// Add sample to tempTestClass
				tempTestClass.add(tempSample);
			}
			// Add tempTestClass to set
			testSet.addTestClass(tempTestClass);
		}
		// Return
		return testSet;
	}

}
