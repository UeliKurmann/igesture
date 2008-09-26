/*
 * @(#)DefaultDescriptor.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Default implementation of a descriptor. It implements
 *                  the necessary methods and extends the default
 *                  implementation of a data object. Normally a new
 *                  implementation of a gesture class descriptor will
 *                  extend this abstract class to avoid code duplication.
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

/**
 * Default implementation of a descriptor. It implements the necessary methods
 * and extends the default implementation of a data object. Normally a new
 * implementation of a gesture class descriptor will extend this abstract class
 * to avoid code duplication.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public abstract class DefaultDescriptor extends DefaultDataObject implements
      Descriptor {

   public DefaultDescriptor() {
      super();
   }


   public Class< ? extends Descriptor> getType() {
      return this.getClass();
   } // getType

}
