/*
 * @(#)$Id: BatchTest3DTool.java
 *
 * Author		:	Arthur Vogels, arthur.vogels@gmail.com
 *                  
 *
 * Purpose		:   Provides tools for running batch tests.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 15.01.2009		vogelsar	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.rubine3d.tools;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.sigtec.ink.Note;
import org.ximtec.igesture.batch.BatchProcess;
import org.ximtec.igesture.batch.BatchProcessContainer;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.batch.BatchTools;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.util.XMLTool;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

public class BatchTest3DTool {

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
	public BatchTest3DTool(TestSet testSet, GestureSet gestureSet,
			String configFileName, String outputDirName) {
		if (configFileName == null || configFileName.equals(""))
			configFile = new File("C:\\configuration3d.xml");
		else
			configFile = new File(configFileName);
		System.out.println("CONFIG FILE: " + configFile);
		if (outputDirName == null || outputDirName.equals(""))
			outputDir = new File("C:\\Output");
		if (testSet == null) {
			// this.testSet =
		} else {
			this.testSet = testSet;
		}
		if (gestureSet == null) {
			// this.gestureSet
		} else {
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
	public static TestSet convert3DGestureSetToTestSet(GestureSet gestureSet) {
		TestSet testSet = new TestSet(gestureSet.getName());
		//System.err.println("NUMBER OF TEST CLASSES IN TEST SET: " + testSet.getTestClasses().size());
		// Iterate through gesture classes in gesture set and add them to
		// testset
		Iterator<GestureClass> i = gestureSet.getGestureClasses().iterator();
		while (i.hasNext()) {
			GestureClass tempGestureClass = i.next();
			//System.err.println("TEMP CLASS NAME: " + tempGestureClass.getName());
			// Create new TestClass
			TestClass tempTestClass = new TestClass(tempGestureClass.getName());
			// Add gesture samples from the gesture class to the test class
			Iterator<Gesture<?>> sampleIter = tempGestureClass
					.getDescriptor(SampleDescriptor.class).getSamples()
					.iterator();
			while (sampleIter.hasNext()) {
				GestureSample3D tempSample = (GestureSample3D) sampleIter
						.next();
				//System.err.println("TEMP SAMPLE NAME: " + tempSample.getName());
				// Add sample to tempTestClass
				tempTestClass.add(tempSample);
			}
			// Add tempTestClass to set
			testSet.addTestClass(tempTestClass);
		}
		//System.err.println("TESTSET NAME: " + testSet.getName());
		// Return
		return testSet;
	}

	/**
	 * Converts a GestureSet to a TestSet
	 * 
	 * @param gestureSet
	 * @return
	 */
	public static TestSet convert2DGestureSetToTestSet(GestureSet gestureSet) {
		TestSet testSet = new TestSet(gestureSet.getName());
		// Iterate through gesture classes in gesture set and add them to
		// testset
		Iterator<GestureClass> i = gestureSet.getGestureClasses().iterator();
		while (i.hasNext()) {
			GestureClass tempGestureClass = i.next();
			// Create new TestClass
			TestClass tempTestClass = new TestClass(tempGestureClass.getName());
			// Add gesture samples from the gesture class to the test class
			List<Gesture<?>> samples = tempGestureClass.getDescriptor(SampleDescriptor.class).getSamples();
			Iterator<Gesture<?>> sampleIter = samples.iterator();
			while (sampleIter.hasNext()) {
				GestureSample tempSample = (GestureSample) sampleIter.next();
				// Add sample to tempTestClass
				tempTestClass.add(tempSample);
				// System.err.println("Sample: " + tempSample.getGesture());
			}
			// Add tempTestClass to set
			testSet.addTestClass(tempTestClass);
			// System.err.println("Number of samples in TestSet Class " +
			// tempTestClass.getName() + ": " +
			// tempTestClass.getGestures().size());

		}
		// Return
		return testSet;
	}

	public static List<GestureSet> splitSetToPlanes(GestureSet gestureSet3D) {
		GestureSet gestureSetXY = new GestureSet(gestureSet3D.getName());
		GestureSet gestureSetYZ = new GestureSet(gestureSet3D.getName());
		GestureSet gestureSetZX = new GestureSet(gestureSet3D.getName());
		// Iterate through gesture classes
		Iterator<GestureClass> i = gestureSet3D.getGestureClasses().iterator();
		while (i.hasNext()) {
			GestureClass tempClass = (GestureClass) i.next();
			GestureClass xyClass = new GestureClass(tempClass.getName());
			GestureClass yzClass = new GestureClass(tempClass.getName());
			GestureClass zxClass = new GestureClass(tempClass.getName());
			SampleDescriptor xyDescriptor = new SampleDescriptor();
			SampleDescriptor yzDescriptor = new SampleDescriptor();
			SampleDescriptor zxDescriptor = new SampleDescriptor();
			// Iterate through samples
			Iterator<Gesture<?>> sampleIter = tempClass
					.getDescriptor(SampleDescriptor.class).getSamples()
					.iterator();
			while (sampleIter.hasNext()) {
				GestureSample3D tempSample = (GestureSample3D) sampleIter
						.next();
				// Split sample into three plane projections
				List<Gesture<Note>> planes = tempSample.splitToPlanes();
				// Add to 2D gesture descriptors
				xyDescriptor.addSample(planes.get(0));
				yzDescriptor.addSample(planes.get(1));
				zxDescriptor.addSample(planes.get(2));
			}

			// System.err.println("Number of samples in XY set class " +
			// xyClass.getName() + ": " + xyDescriptor.getSamples().size());
			// System.err.println("Number of samples in YZ set class " +
			// xyClass.getName() + ": " + yzDescriptor.getSamples().size());
			// System.err.println("Number of samples in ZX set class " +
			// xyClass.getName() + ": " + zxDescriptor.getSamples().size());
			// System.err.println();

			// Add 2D gesture descriptors to 2D gesture classes
			xyClass.addDescriptor(xyDescriptor);
			yzClass.addDescriptor(yzDescriptor);
			zxClass.addDescriptor(zxDescriptor);
			// Add 2D gesture classes to 2D gesture sets
			gestureSetXY.addGestureClass(xyClass);
			gestureSetYZ.addGestureClass(yzClass);
			gestureSetZX.addGestureClass(zxClass);
		}
		// Add 2D sets to return list
		Vector<GestureSet> returnList = new Vector<GestureSet>();
		returnList.add(gestureSetXY);
		returnList.add(gestureSetYZ);
		returnList.add(gestureSetZX);
		// Return
		return returnList;
	}

}
