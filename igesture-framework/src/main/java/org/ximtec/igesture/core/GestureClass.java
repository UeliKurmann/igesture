/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   This class represents the concept of a specific
 *                  gesture (e.g. circle, rectangle, triangle).
 *                  The class provides a storage container for managing
 *                  descriptors in the form of samples, character
 *                  strings etc.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class represents the concept of a specific gesture (e.g. circle,
 * rectangle, triangle). The class provides a storage container for managing
 * descriptors in the form of samples, character strings etc.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureClass extends DefaultDataObject {

   public static final String PROPERTY_NAME = "name";

   public static final String PROPERTY_DESCRIPTORS = "descriptors";

   private Map<Class< ? extends Descriptor>, Descriptor> descriptors;

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
      setName(name);
      this.descriptors = new HashMap<Class< ? extends Descriptor>, Descriptor>();
   }


   /**
    * Sets the name of the gesture class.
    * 
    * @param name the name of the gesture class.
    */
   public void setName(String name) {
      String oldValue = this.name;
      this.name = name;
      propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldValue, name);
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
    * Returns the gesture class descriptor for a given classname.
    * 
    * @param classname the classname for which the gesture class descriptor has
    *            to be returned.
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
      addDescriptor(descriptor.getType(), descriptor);
   } // addDescriptor


   /**
    * Adds a descriptor.
    * 
    * @param classname the type of the descriptor.
    * @param descriptor the descriptor to be added.
    */
   public void addDescriptor(Class< ? extends Descriptor> classname,
         Descriptor descriptor) {
      if (!descriptors.containsKey(classname)) {
         descriptors.put(classname, descriptor);
         propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_DESCRIPTORS, 0, null, descriptor);
      }

   } // addDescriptor


   /**
    * Removes the given descriptor.
    * 
    * @param descriptor the descriptor to be removed.
    */
   public void removeDescriptor(Class< ? extends Descriptor> descriptor) {
      descriptors.remove(descriptor);
      propertyChangeSupport.fireIndexedPropertyChange(PROPERTY_DESCRIPTORS, 0,
            descriptor, null);
   } // removeDescriptor


   /**
    * Returns true if the given descriptor exists.
    * @param descriptor the descriptor to be checked for.
    * @return true if the given descriptor exists.
    */
   public boolean hasDescriptor(Class< ? extends Descriptor> descriptor) {
      return descriptors.containsKey(descriptor);
   } // hasDescriptor


   /**
    * {@inheritDoc}
    */
   @Override
   public void accept(Visitor visitor) {
      visitor.visit(this);
      
      if (descriptors == null) {
         descriptors = new HashMap<Class< ? extends Descriptor>, Descriptor>();
      }
      
      for (Descriptor descriptor : descriptors.values()) {
         
         if (descriptor != null) {
            descriptor.accept(visitor);
         }
      
      }
   
   } // accept


   @Override
   public String toString() {
      return this.name;
   } // toString

}
