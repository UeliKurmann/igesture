/*
 * @(#)$Id: VectorTools.java 689 2009-07-22 00:10:27Z bsigner $
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Static methods operating on the DoubleVector class.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.util;

import java.math.BigDecimal;
import java.util.Collection;



/**
 * Static methods operating on the DoubleVector class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, bsigner@vub.ac.be
 */
public class BDVectorTools {

   /**
    * Performs an addition of two double vectors.
    * 
    * @param v1 the first vector.
    * @param v2 the second vector.
    * @return the sum of the two vectors.
    */
   public static BigDecimalVector add(BigDecimalVector v1, BigDecimalVector v2) {
      assert (v1.size() == v2.size());
      BigDecimalVector result = new BigDecimalVector(v1.size());

      for (int i = 0; i < v1.size(); i++) {
         result.set(i, v1.get(i).add(v2.get(i)));
      }

      return result;
   } // add


   /**
    * Initialises the vector with a given value.
    * 
    * @param vector the vector to be initialised.
    * @param initValue the initialisation value.
    */
   public static void init(BigDecimalVector vector, BigDecimal initValue) {
      for (int i = 0; i < vector.size(); i++) {
         vector.add(i, initValue);
      }

   } // init


   /**
    * Computes the scalar division of a double vector.
    * 
    * @param vector the vector.
    * @param divisor the divisor.
    * @return the vector divided by divisor.
    */
   public static BigDecimalVector scalarDiv(BigDecimalVector vector, int divisor) {
     BigDecimalVector result = new BigDecimalVector(vector.size());

     BigDecimal divisor2 = new BigDecimal(divisor);
      for (int i = 0; i < vector.size(); i++) {
        result.set(i, vector.get(i).divide(divisor2, BigDecimal.ROUND_UP));
      }

      return result;
   } // scalarDiv


   /**
    * Computes the sum of a collection of double vectors. By contract the size of
    * all vectors must be the same.
    * 
    * @param vectors a collection of double vectors.
    * @return a double vector containing the sum of all vectors.
    */
   public static BigDecimalVector sum(Collection<BigDecimalVector> vectors) {
      // FIXME Remvoe assert statements, use exceptions
      assert (vectors.size() > 0);
      int vectorLenght = vectors.iterator().next().size();
      BigDecimalVector result = new BigDecimalVector(vectorLenght);
      
      for (BigDecimalVector vector : vectors) {
         assert (vector.size() == vectorLenght);
         result = add(result, vector);
      }
      
      return result;
   } // sum


   /**
    * Computes the mean vector of a set of vectors.
    * 
    * @param vectors the collection of double vectors whose mean vector has to be computed.
    * @return the mean vector of the specified set of vectors.
    */
   public static BigDecimalVector mean(Collection<BigDecimalVector> vectors) {
      // FIXME replace assert, use exception handling
      assert (vectors.size() > 0);
      int numOfVectors = vectors.size();
      return scalarDiv(sum(vectors), numOfVectors);
   } // mean


   /**
    * Computes the LP norm of the vector.
    * 
    * @param vector the vector to compute the LP norm.
    * @return the LP norm of the vector.
    */
   public static BigDecimal normLP(BigDecimalVector vector) {
      int dim = vector.size();
      BigDecimal result = new BigDecimal(0);
      for (int i = 0; i < dim; i++) {
        result = result.add(vector.get(i).abs().pow(dim));
      }
     
      //FIXME double computation
      return new BigDecimal(Math.pow(result.doubleValue(), 1d / dim));
   } // normLP


   /**
    * Normalises a vector (LP norm is used).
    * 
    * @param vector the vector to be normalised.
    * @return the normalised vector.
    */
   public static BigDecimalVector normalize(BigDecimalVector vector) {
      BigDecimal norm = normLP(vector);
      BigDecimalVector result = new BigDecimalVector(vector.size());
      
      for (int i = 0; i < vector.size(); i++) {
         result.set(i, vector.get(i).divide(norm));
      }
      
      return result;
   } // normalize
   
   /**
    * Tests if all values in the vector are valid. (not NaN, Infinite)
    * @param vector the vector to test.
    * @return true if all values are valid.
    */
   public static boolean hasValidValues(BigDecimalVector vector){
      //FIXME
      return true;
   }

}
