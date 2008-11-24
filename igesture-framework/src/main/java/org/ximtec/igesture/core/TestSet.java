/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		: 	Set of gesture samples used to evaluate algorithms.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 20, 2006		ukurmann	Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.ink.Note;


/**
 * Set of gesture samples used to evaluate algorithms.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class TestSet extends DefaultDataObject {

   public static final String PROPERTY_NAME = "name";
   public static final String PROPERTY_TEST_CLASSES = "testClasses";

   public static final String NOISE = "None";

   private List<TestClass> testClasses;

   private String name;


   /**
    * Constructs a new test set.
    * 
    * @param name the name of the test set.
    */
   public TestSet(String name) {
      super();
      testClasses = new ArrayList<TestClass>();
      setName(name);
      addTestClass(new TestClass(NOISE));
   }


   /**
    * Adds a sample to the list of samples.
    * 
    * @param sample the sample to be added.
    */
   public void add(Gesture<Note> sample) {
      if (sample == null) {
         return;
      }

      TestClass testClass = getTestClass(sample.getName());
      if (testClass == null) {
         testClass = new TestClass(sample.getName());
         addTestClass(testClass);

      }
      testClass.add(sample);
   } // add

   
   public TestClass getTestClass(String name){
      for(TestClass testClass:testClasses){
         if(testClass.getName().equals(name)){
            return testClass;
         }
      }
      return null;
   }

   public void addTestClass(TestClass testClass) {
      if (testClass != null) {
         if (getTestClass(testClass.getName()) == null) {
            testClasses.add(testClass);

            propertyChangeSupport.fireIndexedPropertyChange(
                  PROPERTY_TEST_CLASSES, 0, null, testClass);
         }
         else {
            addAll(testClass.getGestures());
         }
      }
   }


   public void addTestClasses(List<TestClass> testClasses) {
      if (testClasses == null) {
         return;
      }
      for (TestClass testClass : testClasses) {
         addTestClass(testClass);
      }
   }


   /**
    * Adds a list of samples.
    * 
    * @param samples the samples to be added.
    */
   public void addAll(List<Gesture<Note>> samples) {
      for (Gesture<Note> sample : samples) {
         add(sample);
      }

      for (Gesture< ? > sample : samples) {
         propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_TEST_CLASSES,
               samples.indexOf(sample), null, sample);
      }

   } // addAll


   /**
    * Removes a the sample from the list of samples.
    * 
    * @param sample the sample to be removed.
    */
   public void remove(TestClass testClass) {
      testClasses.remove(testClass);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_TEST_CLASSES, 0,
            testClass, null);
   } // remove


   /**
    * Returns all samples.
    * 
    * @return a list with all samples.
    */
   public List<TestClass> getTestClasses() {
      return new ArrayList<TestClass>(testClasses);
   } // getSamples



   /**
    * Returns the number of samples which should be rejected by the recogniser.
    * 
    * @return the number of "noise" entries in the test set.
    */
   public int getNoiseSize() {

      TestClass testClass = getTestClass(TestSet.NOISE);
      return testClass == null ? 0 : testClass.size();

   } // getNoiseSize


   /**
    * Sets the name of the test set.
    * 
    * @param name the name of the test set.
    */
   public void setName(String name) {
      String oldValue = this.name;
      this.name = name;
      propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldValue, name);
   } // setName


   /**
    * Returns the name of the test set.
    * 
    * @return the name of the test set.
    */
   public String getName() {
      if (name == null) {
         name = String.valueOf(System.currentTimeMillis());
      }

      return name;
   } // getName


   /**
    * Returns the size of the test set. (Number of test classes)
    * 
    * @return the size of the test set.
    */
   public int size() {
      return testClasses.size();
   } // size


   /**
    * Returns the number of samples in the test set. This is the sum of all
    * samples in all test classes.
    * @return the number of samples
    */
   public int getNumberOfSamples() {
      int result = 0;

      for (TestClass testClass : testClasses) {
         result += testClass.size();
      }

      return result;
   }


   /**
    * Returns true if the test set is empty.
    * 
    * @return true if the test set is empty.
    */
   public boolean isEmpty() {
      return testClasses.isEmpty();
   } // isEmpty


   /**
    * {@inheritDoc}
    */
   @Override
   public void accept(Visitor visitor) {
      visitor.visit(this);

      for (TestClass testClass : testClasses) {
         testClass.accept(visitor);
      }

   } // accept


   @Override
   public String toString() {
      return name;
   } // toString

}