/*
 * @(#)GestureClass.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      :   This class represents the concept of a specific
 *                  gesture. (e.g. circles, rectangles, triangles, ...).
 *                  The class provides a storage container for managing
 *                  descriptors in the form of samples, character
 *                  strings, etc.
 * 
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
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


/**
 * This class represents the concept of a specific gesture. (e.g. circles,
 * rectangles, triangles, ...). The class provides a storage container for
 * managing descriptors in the form of samples, character strings, etc.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureClass extends DefaultDataObject {

   private HashMap<Class< ? extends Descriptor>, Descriptor> descriptors;

   /**
    * The name of the gesture class (e.g. circle).
    */
   private String name;


   /**
    * Constructs a new gesture class instance.
    * 
    * @param name the name of the gesture class to be created.
    */
   public GestureClass(String name) {
      super();
      this.name = name;
      this.descriptors = new HashMap<Class< ? extends Descriptor>, Descriptor>();
   }


   /**
    * Sets the name of the gesture class.
    * 
    * @param name the name of the gesture class.
    */
   public void setName(String name) {
      this.name = name;
   } // setName


   /**
    * Returns the name of the gesture class.
    * 
    * @return the name of the gesture class.
    */
   public String getName() {
      return name;
   } // getName


   /**
    * Returns the gesture class descriptor for a given key.
    * 
    * @param classname the classname for which the gesture class descriptor has
    *            to be returned.
    * @param <T>
    * @return the gesture class descriptor.
    */
   @SuppressWarnings("unchecked")
   public <T extends Descriptor> T getDescriptor(Class<T> classname) {
      return (T)descriptors.get(classname);
   } // getDescriptor


   /**
    * Returns all gesture class descriptors.
    * 
    * @return all gesture class descriptors.
    */
   public List<Descriptor> getDescriptors() {
      return new ArrayList<Descriptor>(descriptors.values());
   } // getDescriptors


   /**
    * Adds a descriptor.
    * 
    * @param descriptor the descriptor to be added.
    */
   public void addDescriptor(Descriptor descriptor) {
      descriptors.put(descriptor.getType(), descriptor);
   } // addDescriptor


   /**
    * Adds a descriptor.
    * 
    * @param classname the type of the descriptor.
    * @param descriptor the descriptor to be added.
    */
   public void addDescriptor(Class< ? extends Descriptor> classname,
         Descriptor descriptor) {
      descriptors.put(classname, descriptor);
   } // addDescriptor


   /**
    * Removes the given descriptor.
    * 
    * @param descriptor the descriptor to be removed.
    */
   public void removeDescriptor(Class< ? extends Descriptor> descriptor) {
      descriptors.remove(descriptor);
   } // removeDescriptor


   @Override
   public String toString() {
      return this.name;
   } // toString

}
