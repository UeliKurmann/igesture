/*
 * @(#)LevenshteinDistance.java	1.0   Dec 11, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Computes the Levenshtein distance. The algorithm is
 *                  based on the implementation presented at
 *                  http://en.wikipedia.org/wiki/Levenshtein_distance.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 11, 2006     ukurmann	Initial Release
 * Mar 18, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.signature;

/**
 * Computes the Levenshtein distance.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class LevenshteinDistance implements DistanceFunction {

   public int computeDistance(GestureSignature sig1, GestureSignature sig2) {
      final String str1 = sig1.toString();
      final String str2 = sig2.toString();
      final int[][] distance = new int[str1.length() + 1][];

      for (int i = 0; i <= str1.length(); i++) {
         distance[i] = new int[str2.length() + 1];
         distance[i][0] = i;
      }

      for (int j = 0; j < str2.length() + 1; j++) {
         distance[0][j] = j;
      }

      for (int i = 1; i <= str1.length(); i++) {

         for (int j = 1; j <= str2.length(); j++) {
            distance[i][j] = minimum(distance[i - 1][j] + 1,
                  distance[i][j - 1] + 1, distance[i - 1][j - 1]
                        + ((str1.substring(i - 1, i).equals(
                              str2.subSequence(j - 1, j)) ? 0 : 1)));
         }

      }

      return distance[str1.length()][str2.length()];
   } // computeDistance


   private static int minimum(int a, int b, int c) {
      return Math.min(Math.min(a, b), c);
   } // minimum

}
