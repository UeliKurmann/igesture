/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Default implementation of the DataObject interface.
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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import org.ximtec.igesture.storage.StorageManager;


/**
 * Default implementation of the DataObject interface.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class DefaultDataObject extends DefaultPropertyChangeNotifier
      implements DataObject {

   public static final String PROPERTY_ID = "id";

   private String objectID;


   /**
    * Constructs a new default data object.
    */
   public DefaultDataObject() {
      setId(StorageManager.generateUUID());
   }


   /**
    * {@inheritDoc}
    */
   @Override
   public void setId(String id) {
      String oldValue = this.objectID;
      this.objectID = id;
      propertyChangeSupport.firePropertyChange(PROPERTY_ID, oldValue, id);
   } // setID


   /**
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      if (objectID == null) {
         setId(StorageManager.generateUUID());
      }

      return objectID;
   } // getID


   /**
    * {@inheritDoc}
    */
   public void accept(Visitor visitor) {
      visitor.visit(this);
   } // accept

}
