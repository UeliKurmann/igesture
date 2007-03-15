/*
 * @(#)GestureClass.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   this class represents the concept of a certain gesture. 
 * 					(e.g. circles, rectangles, triangles, ...)
 * 
 * it provides a storage container for managing descriptors as samples, 
 * character strings, etc.
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
import java.util.HashMap;
import java.util.List;


public class GestureClass extends DefaultDataObject {

   private HashMap<Class< ? extends Descriptor>, Descriptor> descriptors;

   /**
    * The name of the Gesture Class. E.g. Circle, etc.
    */
   private String name;


   /**
    * Constructs a new Gesture Class instance
    * 
    * @param name
    */
   public GestureClass(String name) {
      super();
      this.name = name;
      this.descriptors = new HashMap<Class< ? extends Descriptor>, Descriptor>();
   }


   /**
    * Returns the name of the Gesture Class
    * 
    * @return
    */
   public String getName() {
      return name;
   }


   /**
    * Returns the GestureClassDescriptor for a given key.
    * 
    * @param classname 
    * @param <T> 
    * @return
    */
   @SuppressWarnings("unchecked")
   public <T extends Descriptor> T getDescriptor(Class<T> classname) {
      return (T) descriptors.get(classname);
   } // getDescriptor


   /**
    * Returns all GestureClassDescriptors
    * 
    * @return
    */
   public List<Descriptor> getDescriptors() {
      return new ArrayList<Descriptor>(descriptors.values());
   }


   /**
    * Adds a descriptor
    * 
    * @param descriptor the descriptor to be added
    */
   public void addDescriptor(Descriptor descriptor) {
      descriptors.put(descriptor.getType(), descriptor);
   }


   /**
    * Add a descriptor
    * 
    * @param classname type of the descriptor
    * @param descriptor the descriptor to be added
    */
   public void addDescriptor(Class< ? extends Descriptor> classname,
         Descriptor descriptor) {
      descriptors.put(classname, descriptor);
   }


   /**
    * Removes the given descriptor
    * 
    * @param descriptor the descriptor to be removed
    */
   public void removeDescriptor(Class< ? extends Descriptor> descriptor) {
      descriptors.remove(descriptor);
   }


   /**
    * Sets the name of the gesture class
    * 
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }


   @Override
   public String toString() {
      return this.name;
   }

}
