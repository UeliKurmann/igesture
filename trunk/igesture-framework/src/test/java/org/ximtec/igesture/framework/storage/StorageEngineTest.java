package org.ximtec.igesture.framework.storage;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.sigtec.ink.Note;
import org.sigtec.ink.NoteTool;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.storage.StorageManager;

public abstract class StorageEngineTest {

	private static final String SAMPLE2_2 = "sample2.2";
	private static final String SAMPLE2_1 = "sample2.1";
	private static final String SAMPLE1_2 = "sample1.2";
	private static final String SAMPLE1_1 = "sample1.1";
	private static final String NOTE_XML = "note.xml";
	private static final String GESTURE_SET_NAME = "testGestureSet";
	private static final String GESTURE1_CLASS_NAME = "testGestureClass1";
	private static final String GESTURE2_CLASS_NAME = "testGestureClass2";

	private GestureSet gestureSet;

	public abstract StorageManager getStorageManager();
	
	@Before
	public void setUp() {
		Note note = NoteTool.importXML(ClassLoader
				.getSystemResourceAsStream(NOTE_XML));

		gestureSet = new GestureSet(GESTURE_SET_NAME);
		GestureClass originalGestureClass1 = new GestureClass(
				GESTURE1_CLASS_NAME);
		GestureClass originalGestureClass2 = new GestureClass(
				GESTURE2_CLASS_NAME);

		GestureSample sample1 = new GestureSample(SAMPLE1_1, note);
		GestureSample sample2 = new GestureSample(SAMPLE1_2, note);
		GestureSample sample3 = new GestureSample(SAMPLE2_1, note);
		GestureSample sample4 = new GestureSample(SAMPLE2_2, note);

		SampleDescriptor descriptor1 = new SampleDescriptor();
		descriptor1.addSample(sample1);
		descriptor1.addSample(sample2);
		originalGestureClass1.addDescriptor(descriptor1);

		SampleDescriptor descriptor2 = new SampleDescriptor();
		descriptor2.addSample(sample3);
		descriptor2.addSample(sample4);
		originalGestureClass2.addDescriptor(descriptor2);

		gestureSet.addGestureClass(originalGestureClass1);
		gestureSet.addGestureClass(originalGestureClass2);

		StorageManager manager = getStorageManager();
		manager.store(gestureSet);
		manager.dispose();

	}
	
	@Test
	public void testGestureSet() {
		StorageManager manager = getStorageManager();
		List<GestureSet> gestureSets = manager.load(GestureSet.class);
		Assert.assertEquals(gestureSets.size(), 1);
		manager.dispose();
	}

	@Test
	public void testGestureSetName() {
		StorageManager manager = getStorageManager();
		List<GestureSet> gestureSets = manager.load(GestureSet.class);
		Assert.assertEquals(gestureSets.get(0).getName(), GESTURE_SET_NAME);
		manager.dispose();
	}
	
	@Test
	public void testGestureClasses() {
		StorageManager manager = getStorageManager();
		List<GestureSet> gestureSets = manager.load(GestureSet.class);
		Assert.assertEquals(gestureSets.get(0).getGestureClasses().size(), 2);
		GestureClass gc1 = gestureSets.get(0).getGestureClass(GESTURE1_CLASS_NAME);
		Assert.assertEquals(gc1.getName(), GESTURE1_CLASS_NAME);
		GestureClass gc2 = gestureSets.get(0).getGestureClass(GESTURE2_CLASS_NAME);
		Assert.assertEquals(gc2.getName(), GESTURE2_CLASS_NAME);
		
		manager.dispose();
	}
	
	//@Test
	public void testParamLoad() {
		StorageManager manager = getStorageManager();
		List<GestureClass> gestureClasses = manager.load(GestureClass.class,"name",GESTURE1_CLASS_NAME);
		Assert.assertEquals(gestureClasses.get(0).getName(), GESTURE1_CLASS_NAME);
		manager.dispose();
	}
	

}
