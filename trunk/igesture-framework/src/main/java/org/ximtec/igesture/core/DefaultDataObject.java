/*
 * @(#)DefaultDataObject.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Default implementation of DataObject
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

import org.ximtec.igesture.storage.StorageManager;


public abstract class DefaultDataObject implements DataObject {

   private String objectID;


   /**
    * Constructor
    * 
    */
   public DefaultDataObject() {
      this.objectID = StorageManager.generateUUID();
   }


   public String getID() {
      if (objectID == null) {
         objectID = StorageManager.generateUUID();
      }
      return objectID;
   }


   public void setID(String objectID) {
      this.objectID = objectID;
   }
}
