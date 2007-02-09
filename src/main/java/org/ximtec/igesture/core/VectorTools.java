/*
 * @(#)VectorTools.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	static methods operating on DoubleVector
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

import java.util.Collection;


public class VectorTools {

   /**
    * Performs an addition of two DoubleVectors
    * 
    * @param v1 the first vector
    * @param v2 the second vector
    * @return the sum of the two vectors
    */
   public static DoubleVector add(DoubleVector v1, DoubleVector v2) {
      assert (v1.size() == v2.size());
      final DoubleVector result = new DoubleVector(v1.size());
      for (int i = 0; i < v1.size(); i++) {
         result.set(i, v1.get(i) + v2.get(i));
      }
      return result;
   }


   /**
    * Initialises the vector with a given value
    * 
    * @param value the vector to initialise
    * @param initValue the initialisation value
    */
   public static void initialize(DoubleVector value, double initValue) {
      for (int i = 0; i < value.size(); i++) {
         value.add(i, initValue);
      }
   }


   /**
    * Computes the scalar division of a DoubleVector
    * 
    * @param vector the vector
    * @param divisor the divisor
    * @return the Vector divided by divisor
    */
   public static DoubleVector scalarDiv(DoubleVector vector, double divisor) {
      final DoubleVector result = new DoubleVector(vector.size());
      for (int i = 0; i < vector.size(); i++) {
         result.set(i, vector.get(i) / divisor);
      }
      return result;
   }


   /**
    * Computes the sum of a collection of double vectors. by contract the size of
    * all vectors must be equal
    * 
    * @param vectors a collection of DoubleVectors
    * @return a DoubleVector representing the sum
    */
   public static DoubleVector sum(Collection<DoubleVector> vectors) {
      assert (vectors.size() > 0);
      final int vectorLenght = vectors.iterator().next().size();
      DoubleVector result = new DoubleVector(vectorLenght);
      for (final DoubleVector vector : vectors) {
         assert (vector.size() == vectorLenght);
         result = add(result, vector);
      }
      return result;
   }


   /**
    * Computes the mean vector
    * 
    * @param vectors a collection of DoubleVectors
    * @return the mean vector
    */
   public static DoubleVector mean(Collection<DoubleVector> vectors) {
      assert (vectors.size() > 0);
      final int numOfVectors = vectors.size();
      return scalarDiv(sum(vectors), numOfVectors);
   }


   /**
    * computes the LP norm of the vector
    * 
    * @param v
    * @return
    */
   public static double normLP(DoubleVector v) {
      final int dim = v.size();
      double result = 0;
      for (int i = 0; i < dim; i++) {
         result += Math.pow(Math.abs(v.get(i)), dim);
      }
      return Math.pow(result, 1d / dim);
   }


   /**
    * normalizes a vector. the LP norm is used.
    * 
    * @param v
    * @return
    */
   public static DoubleVector normalize(DoubleVector v) {
      final double norm = normLP(v);
      final DoubleVector result = new DoubleVector(v.size());
      for (int i = 0; i < v.size(); i++) {
         result.set(i, v.get(i) / norm);
      }
      return result;
   }

}
