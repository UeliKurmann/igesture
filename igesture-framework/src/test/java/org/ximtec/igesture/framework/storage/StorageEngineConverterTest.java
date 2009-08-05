package org.ximtec.igesture.framework.storage;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.sigtec.ink.Note;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.StorageEngine;
import org.ximtec.igesture.storage.StorageEngineConverter;
import org.ximtec.igesture.storage.StorageManager;

public class StorageEngineConverterTest {

  private static final String TEST_CLASS_2 = "Test Class 2";
  private static final String TEST_CLASS_1 = "Test Class 1";
  private static final String TEST_SET_NAME = "Test Set Name";
  private static final String SAMPLE2_2 = "sample2.2";
  private static final String SAMPLE2_1 = "sample2.1";
  private static final String SAMPLE1_2 = "sample1.2";
  private static final String SAMPLE1_1 = "sample1.1";
  private static final String GESTURE_SET_NAME = "testGestureSet";
  private static final String GESTURE1_CLASS_NAME = "testGestureClass1";
  private static final String GESTURE2_CLASS_NAME = "testGestureClass2";

  private GestureSet gestureSet;

  private TestSet testSet;
  private Configuration configuration;

  @Before
  public void setUp() {
    Note note = new Note();

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

    configuration = new Configuration("Config1");

  }

  @Test
  public void testWorkingCopyZip() {
    testWorkingCopy("igz", "igz", "igz");
  }
  
  @Test
  public void testWorkingCopyXml() {
    testWorkingCopy("igx", "igx", "igx");
  }
  
  @Test
  public void testWorkingCopyDb4o() {
    testWorkingCopy("igd", "igd", "igd");
  }
  
  @Test
  public void testConversionCommited_zx() {
    testConversion("igz", "igx");
  }
  
  @Test
  public void testConversionCommited_zd() {
    testConversion("igz", "igd");
  }
  
  @Test
  public void testConversionCommited_xz() {
    testConversion("igx", "igz");
  }
  
  @Test
  public void testConversionCommited_xd() {
    testConversion("igx", "igd");
  }
  
  @Test
  public void testConversionCommited_dz() {
    testConversion("igd", "igz");
  }
  
  @Test
  public void testConversionCommited_dx() {
    testConversion("igd", "igx");
  }
  
  @Test
  public void testConversionUnCommited_zx() {
    testConversionU("igz", "igx");
  }
  
  @Test
  public void testConversionUnCommited_zd() {
    testConversionU("igz", "igd");
  }
  
  @Test
  public void testConversionUnCommited_xz() {
    testConversionU("igx", "igz");
  }
  
  @Test
  public void testConversionUnCommited_xd() {
    testConversionU("igx", "igd");
  }
  
  @Test
  public void testConversionUnCommited_dz() {
    testConversionU("igd", "igz");
  }
  
  @Test
  public void testConversionUnCommited_dx() {
    testConversionU("igd", "igx");
  }

  private void testWorkingCopy(String ext1, String ext2, String extWorkingCopy) {
    File file1 = new File("test1." + ext1);
    File file2 = new File("test2." + ext2);
    File workingCopy = new File("workingcopy." + extWorkingCopy);
    
    file1.delete();
    file2.delete();
    workingCopy.delete();

    StorageEngine storageEngine = StorageManager.createStorageEngine(file1);
    StorageEngine storageEngineTarget = StorageManager.createStorageEngine(file2);

    storageEngine.store(gestureSet);
    storageEngine.store(configuration);
    storageEngine.store(testSet);
    storageEngine.copyTo(workingCopy);
    storageEngine.dispose();
    
    StorageEngine storageEngineWC = StorageManager.createStorageEngine(workingCopy);

    GestureSet loadedGS = storageEngineWC.load(GestureSet.class).get(0);
    Assert.assertEquals(gestureSet.getName(), loadedGS.getName());
    Assert.assertEquals(gestureSet.getGestureClasses().size(), loadedGS.getGestureClasses().size());
   
    TestSet loadedTS = storageEngineWC.load(TestSet.class).get(0);
    Assert.assertEquals(testSet.getName(), loadedTS.getName());
    Assert.assertEquals(testSet.getTestClasses().size(), loadedTS.getTestClasses().size());
    
    Assert.assertEquals(configuration.getName(), storageEngineWC.load(Configuration.class).get(0).getName());

    
    storageEngineTarget.dispose();
    storageEngineWC.dispose();
    
    file1.delete();
    file2.delete();
    workingCopy.delete();
  }
  
  private void testConversion(String ext1, String ext2) {
    File file1 = new File("test1." + ext1);
    File file2 = new File("test2." + ext2);
    File workingCopy = new File("workingcopy." + ext1);
    
    file1.delete();
    file2.delete();
    workingCopy.delete();

    StorageEngine storageEngine = StorageManager.createStorageEngine(file1);


    storageEngine.store(gestureSet);
    storageEngine.store(configuration);
    storageEngine.store(testSet);
    storageEngine.copyTo(workingCopy);
    storageEngine.dispose();
    
    StorageEngineConverter converter = new StorageEngineConverter();
    converter.convert(workingCopy, file2);
    
    StorageEngine storageEngineWC = StorageManager.createStorageEngine(file2);

    GestureSet loadedGS = storageEngineWC.load(GestureSet.class).get(0);
    Assert.assertEquals(gestureSet.getName(), loadedGS.getName());
    Assert.assertEquals(gestureSet.getGestureClasses().size(), loadedGS.getGestureClasses().size());
   
    TestSet loadedTS = storageEngineWC.load(TestSet.class).get(0);
    Assert.assertEquals(testSet.getName(), loadedTS.getName());
    Assert.assertEquals(testSet.getTestClasses().size(), loadedTS.getTestClasses().size());
    
    Assert.assertEquals(configuration.getName(), storageEngineWC.load(Configuration.class).get(0).getName());


    storageEngineWC.dispose();
    
    file1.delete();
    file2.delete();
    workingCopy.delete();
  }

  private void testConversionU(String ext1, String ext2) {
    File file1 = new File("test1." + ext1);
    File file2 = new File("test2." + ext2);
    File workingCopy = new File("workingcopy." + ext1);
    
    file1.delete();
    file2.delete();
    workingCopy.delete();

    StorageEngine storageEngine = StorageManager.createStorageEngine(file1);


    storageEngine.store(gestureSet);
    storageEngine.store(configuration);
    storageEngine.store(testSet);
    storageEngine.commit();
    
    // do some uncommitted changes. have to copied with the copyTo command
    gestureSet.removeGestureClass(gestureSet.getGestureClass(GESTURE1_CLASS_NAME));
    Assert.assertEquals(1, gestureSet.getGestureClasses().size());
    
    storageEngine.copyTo(workingCopy);
    storageEngine.dispose();
    
    StorageEngineConverter converter = new StorageEngineConverter();
    converter.convert(workingCopy, file2);
    
    StorageEngine storageEngineWC = StorageManager.createStorageEngine(file2);
    Assert.assertEquals(1, storageEngineWC.load(GestureSet.class).size());
    GestureSet loadedGS = storageEngineWC.load(GestureSet.class).get(0);
    
    Assert.assertEquals(gestureSet.getName(), loadedGS.getName());
    Assert.assertEquals(gestureSet.getGestureClasses().size(), loadedGS.getGestureClasses().size());
   
    TestSet loadedTS = storageEngineWC.load(TestSet.class).get(0);
    Assert.assertEquals(testSet.getName(), loadedTS.getName());
    Assert.assertEquals(testSet.getTestClasses().size(), loadedTS.getTestClasses().size());
    
    Assert.assertEquals(configuration.getName(), storageEngineWC.load(Configuration.class).get(0).getName());


    storageEngineWC.dispose();
    
    file1.delete();
    file2.delete();
    workingCopy.delete();
  }

}
