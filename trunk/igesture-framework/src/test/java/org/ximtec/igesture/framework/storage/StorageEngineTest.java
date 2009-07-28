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
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.StorageManager;

public abstract class StorageEngineTest {

  private static final String TEST_CLASS_2 = "Test Class 2";
  private static final String TEST_CLASS_1 = "Test Class 1";
  private static final String TEST_SET_NAME = "Test Set Name";
  private static final String SAMPLE2_2 = "sample2.2";
  private static final String SAMPLE2_1 = "sample2.1";
  private static final String SAMPLE1_2 = "sample1.2";
  private static final String SAMPLE1_1 = "sample1.1";
  private static final String NOTE_XML = "note.xml";
  private static final String GESTURE_SET_NAME = "testGestureSet";
  private static final String GESTURE1_CLASS_NAME = "testGestureClass1";
  private static final String GESTURE2_CLASS_NAME = "testGestureClass2";

  private GestureSet gestureSet;

  private TestSet testSet;

  public abstract StorageManager getStorageManager();

  @Before
  public void setUp() {
    Note note = NoteTool.importXML(ClassLoader.getSystemResourceAsStream(NOTE_XML));

    gestureSet = new GestureSet(GESTURE_SET_NAME);
    GestureClass originalGestureClass1 = new GestureClass(GESTURE1_CLASS_NAME);
    GestureClass originalGestureClass2 = new GestureClass(GESTURE2_CLASS_NAME);

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

    testSet = new TestSet(TEST_SET_NAME);

    TestClass testClass1 = new TestClass(TEST_CLASS_1);
    testClass1.add(new GestureSample("tc1", note));
    testClass1.add(new GestureSample("tc1", note));

    TestClass testClass2 = new TestClass(TEST_CLASS_2);
    testClass2.add(new GestureSample("tc2", note));
    testClass2.add(new GestureSample("tc2", note));

    testSet.addTestClass(testClass1);
    testSet.addTestClass(testClass2);

    StorageManager manager = getStorageManager();
    manager.store(gestureSet);
    manager.store(testSet);
    manager.commit();
    manager.dispose();

  }

  @Test
  public void testGestureSet() {
    StorageManager manager = getStorageManager();
    List<GestureSet> gestureSets = manager.load(GestureSet.class);
    Assert.assertEquals(1, gestureSets.size());

    gestureSets.get(0).setName("asdf");

    manager.dispose();
  }

  @Test
  public void testTestSet() {
    StorageManager manager = getStorageManager();
    List<TestSet> testSets = manager.load(TestSet.class);

    Assert.assertEquals(1, testSets.size());

    TestSet testSet = testSets.get(0);

    Assert.assertEquals(TEST_SET_NAME, testSet.getName());
    Assert.assertEquals(2, testSet.size());

    manager.dispose();
  }

  @Test
  public void testGestureSetName() {
    StorageManager manager = getStorageManager();
    List<GestureSet> gestureSets = manager.load(GestureSet.class);
    Assert.assertEquals(GESTURE_SET_NAME, gestureSets.get(0).getName());
    manager.dispose();
  }

  @Test
  public void testGestureClasses() {
    StorageManager manager = getStorageManager();
    List<GestureSet> gestureSets = manager.load(GestureSet.class);
    Assert.assertEquals(2, gestureSets.get(0).getGestureClasses().size());
    GestureClass gc1 = gestureSets.get(0).getGestureClass(GESTURE1_CLASS_NAME);
    Assert.assertEquals(GESTURE1_CLASS_NAME, gc1.getName());
    GestureClass gc2 = gestureSets.get(0).getGestureClass(GESTURE2_CLASS_NAME);
    Assert.assertEquals(GESTURE2_CLASS_NAME, gc2.getName());

    manager.dispose();
  }

  @Test
  public void testCommit() {
    StorageManager manager = getStorageManager();
    List<GestureSet> gestureSets = manager.load(GestureSet.class);
    Assert.assertEquals(2, gestureSets.get(0).getGestureClasses().size());
    GestureClass gc1 = gestureSets.get(0).getGestureClass(GESTURE1_CLASS_NAME);
    Assert.assertEquals(GESTURE1_CLASS_NAME, gc1.getName());
    GestureClass gc2 = gestureSets.get(0).getGestureClass(GESTURE2_CLASS_NAME);
    Assert.assertEquals(GESTURE2_CLASS_NAME, gc2.getName());
    gestureSets.get(0).removeGestureClass(gc1);
    Assert.assertEquals(1, gestureSets.get(0).getGestureClasses().size());
    manager.dispose();

    manager = getStorageManager();
    gestureSets = manager.load(GestureSet.class);
    Assert.assertEquals(2, gestureSets.get(0).getGestureClasses().size());
    gc1 = gestureSets.get(0).getGestureClass(GESTURE1_CLASS_NAME);
    gestureSets.get(0).removeGestureClass(gc1);
    Assert.assertEquals(1, gestureSets.get(0).getGestureClasses().size());

    manager.commit();

    gestureSets = manager.load(GestureSet.class);
    gc1 = gestureSets.get(0).getGestureClass(GESTURE1_CLASS_NAME);
    Assert.assertEquals(1, gestureSets.get(0).getGestureClasses().size());
    Assert.assertNull(gc1);
    manager.commit();
    manager.dispose();

    manager = getStorageManager();
    gestureSets = manager.load(GestureSet.class);
    Assert.assertEquals(1, gestureSets.get(0).getGestureClasses().size());

    gc2 = gestureSets.get(0).getGestureClass(GESTURE2_CLASS_NAME);
    Assert.assertEquals(GESTURE2_CLASS_NAME, gc2.getName());

    manager.dispose();
  }

  @Test
  public void testDeleteUpdateStore() {
    StorageManager manager = getStorageManager();

    for (int i = 1; i <= 100; i++) {
      manager.store(new GestureSet(Integer.toString(i)));
    }

    List<GestureSet> loaded = manager.load(GestureSet.class);

    Assert.assertEquals(101, loaded.size());

    manager.commit();
    manager.dispose();

    manager = getStorageManager();
    loaded = manager.load(GestureSet.class);
    Assert.assertEquals(101, loaded.size());

    for (GestureSet obj : loaded) {
      try {
        if (Integer.parseInt(obj.getName()) % 2 == 0) {
          manager.remove(obj);
        }
      } catch (Exception e) {
        //
      }
    }

    manager.commit();
    manager.dispose();

    manager = getStorageManager();
    loaded = manager.load(GestureSet.class);
    Assert.assertEquals(51, loaded.size());
    manager.dispose();

  }

}
