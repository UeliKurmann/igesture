/*
 * @(#)$Id:$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 27.10.2008			ukurmann	Initial Release
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
 * Comment
 * @version 1.0 27.10.2008
 * @author Ueli Kurmann
 */
public class TestClass extends DefaultDataObject {

   public static final String PROPERTY_GESTURES = "gestures";
   private ArrayList<Gesture<Note>> gestures;
   private String name;


   public TestClass(String name) {
      this.name = name;
      this.gestures = new ArrayList<Gesture<Note>>();
   }


   /**
    * Returns the name of the gesture
    * @return
    */
   public String getName() {
      return this.name;
   }
   
   public void setName(String name){
      this.name = name;
      
      for(Gesture<Note> gesture:gestures){
         gesture.setName(name);
      }
   }


   /**
    * Returns a shallow copy of the gesture instances.
    * @return a shallow copy of the gesture instances.
    */
   @SuppressWarnings("unchecked")
   public List<Gesture<Note>> getGestures() {
      return (List<Gesture<Note>>)gestures.clone();
   }


   /**
    * Add a gesture to the test class
    * @param gesture
    */
   public void add(Gesture<Note> gesture) {
      gestures.add(gesture);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, 0,
            null, gesture);
   }


   /**
    * Remove a gesture from the test class
    * @param gesture
    */
   public void remove(Gesture<Note> gesture) {
      gestures.remove(gesture);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_GESTURES, 0,
            gesture, null);
   }


   /**
    * Returns the number of gestures in the test set.
    * @return the number of gestures in the test set.
    */
   public int size() {
      return gestures.size();
   }


   @Override
   public int hashCode() {
      return name != null ? name.hashCode():0;
   }
   
   @Override
   public boolean equals(Object object) {
      if(object == this){
         return true;
      }
      
      if(!(object instanceof TestClass)){
         return false;
      }
      TestClass o = (TestClass)object;
      if(this.getName() != null && this.getName().equals(o.getName())){
         return true;
      }
      
      return false;
   }
   
   @Override
   public void accept(Visitor visitor) {
      visitor.visit(this);

      for (Gesture<Note> gesture : gestures) {
         gesture.accept(visitor);
      }

   } // accept
}
