/*
 * @(#)GestureSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Container to manage a set of gesture classes
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.List;


public class GestureSet extends DefaultDataObject {

   private List<GestureClass> gestureClasses;

   private String name;


   /**
    * Constructor
    * 
    */
   public GestureSet() {
      this("");
   }


   /**
    * Constructor
    * 
    * @param name
    */
   public GestureSet(String name) {
      super();
      gestureClasses = new ArrayList<GestureClass>();
      this.name = name;
   }


   /**
    * Returns the name of the gesture set
    * 
    * @return the name of the gesture set
    */
   public String getName() {
      return name;
   }


   /**
    * Constructor
    * 
    * @param gestureClasses a list of gesture classes
    */
   public GestureSet(List<GestureClass> gestureClasses) {
      this.gestureClasses = gestureClasses;
   }


   /**
    * Adds a gesture class to the set
    * 
    * @param gestureClass the gesture class to add
    */
   public void addGestureClass(GestureClass gestureClass) {
      this.gestureClasses.add(gestureClass);
   }


   /**
    * Deletes a gesture class from the gesture set
    * 
    * @param gestureClass the gesture class to delete
    */
   public void delGestureClass(GestureClass gestureClass) {
      this.gestureClasses.remove(gestureClass);
   }


   /**
    * Returns the ith gesture class
    * 
    * @param i the index of the gesture class
    * @return the ith gesture class
    */
   public GestureClass getGestureClass(int i) {
      assert (i >= 0 && i < gestureClasses.size());
      return this.gestureClasses
            .toArray(new GestureClass[gestureClasses.size()])[i];
   }


   /**
    * Returns the number of gesture classes of this set
    * 
    * @return the number of gesture classes
    */
   public int size() {
      return gestureClasses.size();
   }


   /**
    * Returns the list of all gesture classes of this set
    * 
    * @return list of gesture classes
    */
   public List<GestureClass> getGestureClasses() {
      return gestureClasses;
   }


   /**
    * Returns the gesture class with the given name
    * 
    * @param name name of the gesture class
    * @return the gestureclass with the given name
    */
   public GestureClass getGestureClass(String name) {
      for (final GestureClass gestureClass : gestureClasses) {
         if (gestureClass.getName().equals(name)) {
            return gestureClass;
         }
      }
      return null;
   }


   @Override
   public String toString() {
      return name;
   }
}
