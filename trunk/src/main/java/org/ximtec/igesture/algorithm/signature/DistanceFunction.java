/*
 * @(#)DistanceFucntion.java	1.0   Dec 11, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Interface for Distance functions
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 					ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.signature;

/**
 * Comment
 * 
 * @version 1.0 Dec 11, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public interface DistanceFunction {

   /**
    * Computes the distance between two signatures
    * 
    * @param s1 Signature 1
    * @param s2 Signature 2
    * @return the distance between signature 1 and 2
    */
   public int computeDistance(GestureSignature s1, GestureSignature s2);

}
