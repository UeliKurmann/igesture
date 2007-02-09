/*
 * @(#)DefaultDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Default implementation of an descriptor. It implements
 *                  the necessary methods and extends the default
 *                  implementation of a DataObject.
 *                  
 *                  Usually a new implementation of a gesture class 
 *                  descriptor will extend this abstract class to 
 *                  avoid code duplication.
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
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

public abstract class DefaultDescriptor extends DefaultDataObject implements
      Descriptor {

   public DefaultDescriptor() {
      super();
   }


   public Class< ? extends Descriptor> getType() {
      return this.getClass();
   }

}
