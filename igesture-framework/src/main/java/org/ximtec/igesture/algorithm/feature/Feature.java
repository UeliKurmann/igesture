/*
 * @(#)Feature.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, ueli@smartness.ch
 *
 * Purpose      : 	Interface to be implemented by any feature that
 *                  describes a gesture.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 15, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.feature;

import org.sigtec.ink.Note;


/**
 * Interface to be implemented by any feature that describes a gesture.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, ueli@smartness.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface Feature {

   /**
    * Computes the feature.
    * 
    * @param note the note to be used.
    * @return the value of the feature.
    * @throws FeatureException if the feature cannot be computed.
    */
   public double compute(Note note) throws FeatureException;


   public int getMinimalNumberOfPoints();

}
