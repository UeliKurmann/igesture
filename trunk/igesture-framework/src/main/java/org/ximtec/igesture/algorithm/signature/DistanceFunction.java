/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		: 	Interface for distance functions.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 11, 2006		ukurmann	Initial Release
 * Mar 17, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.signature;

/**
 * Interface for distance functions.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public interface DistanceFunction {

   /**
    * Computes the distance between two signatures.
    * 
    * @param s1 the first signature.
    * @param s2 the second signature.
    * @return the distance between signature 1 and 2
    */
   public int computeDistance(GestureSignature s1, GestureSignature s2);

}
