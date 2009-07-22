/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		:   Computes the hamming distance.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 11, 2006		ukurmann	Initial Release
 * Mar 18, 2007     bsigner     Cleanup
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

import java.util.BitSet;


/**
 * Computes the hamming distance.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class HammingDistance implements DistanceFunction {

   public int computeDistance(GestureSignature sig1, GestureSignature sig2) {
      final int minLen = Math.min(sig1.getNumberOfPoints(), sig2
            .getNumberOfPoints());
      final int maxLen = Math.max(sig1.getNumberOfPoints(), sig2
            .getNumberOfPoints());
      int result = 0;

      for (int i = 0; i < minLen; i++) {
         final BitSet bitSet = (BitSet)sig1.getPointSignature(i).clone();
         bitSet.xor(sig2.getPointSignature(i));
         result += bitSet.cardinality();
      }

      if (maxLen != minLen) {
         result += (maxLen - minLen) * sig1.getBitStringLength();
      }

      return result;
   } // computeDistance

}
