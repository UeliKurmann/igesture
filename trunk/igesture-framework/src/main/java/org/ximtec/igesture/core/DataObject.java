/*
 * @(#)DataObject.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, ueli@smartness.ch
 *
 * Purpose      : 	Interface to be implemented by any persistent-capable
 *                  object.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Feb 27, 2007     bsigner     Cleanup
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

import java.io.Serializable;


/**
 * Interface to be implemented by any persistent-capable object.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, ueli@smartness.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface DataObject extends Serializable, PropertyChangeNotifier {

   /**
    * Returns the object's universally unique identifier (UUID).
    * 
    * @return the object's UUID.
    */
   public String getId();


   /**
    * Sets the object's universally unique identifier (UUID).
    * 
    * @param id the object's UUID.
    */
   public void setId(String id);


   /**
    * Accepts a visitor.
    * @param visitor the visitor to ne used.
    */
   public void accept(Visitor visitor);

}
