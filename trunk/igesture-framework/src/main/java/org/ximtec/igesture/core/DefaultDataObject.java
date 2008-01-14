/*
 * @(#)DefaultDataObject.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Default data object implementation.
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
 * Default data object implementation.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class DefaultDataObject implements DataObject {

   private String objectID;


   /**
    * Constructs a new default data object.
    */
   public DefaultDataObject() {
      this.objectID = StorageManager.generateUUID();
   }


   public void setID(String objectID) {
      this.objectID = objectID;
   } // setID


   public String getID() {
      if (objectID == null) {
         objectID = StorageManager.generateUUID();
      }

      return objectID;
   } // getID

}
