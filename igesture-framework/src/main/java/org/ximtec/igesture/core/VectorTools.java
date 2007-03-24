/*
 * @(#)VectorTools.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.Collection;


/**
 * Static methods operating on the DoubleVector class.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class VectorTools {

   /**
    * Performs an addition of two double vectors.
    * 
    * @param v1 the first vector.
    * @param v2 the second vector.
    * @return the sum of the two vectors.
    */
   public static DoubleVector add(DoubleVector v1, DoubleVector v2) {
      assert (v1.size() == v2.size());
      final DoubleVector result = new DoubleVector(v1.size());

      for (int i = 0; i < v1.size(); i++) {
         result.set(i, v1.get(i) + v2.get(i));
      }

      return result;
   } // add


   /**
    * Initialises the vector with a given value.
    * 
    * @param vector the vector to be initialised.
    * @param initValue the initialisation value.
    */
   public static void init(DoubleVector vector, double initValue) {
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
   public static DoubleVector scalarDiv(DoubleVector vector, double divisor) {
      final DoubleVector result = new DoubleVector(vector.size());

      for (int i = 0; i < vector.size(); i++) {
         result.set(i, vector.get(i) / divisor);
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
   public static DoubleVector sum(Collection<DoubleVector> vectors) {
      assert (vectors.size() > 0);
      final int vectorLenght = vectors.iterator().next().size();
      DoubleVector result = new DoubleVector(vectorLenght);
      
      for (final DoubleVector vector : vectors) {
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
   public static DoubleVector mean(Collection<DoubleVector> vectors) {
      assert (vectors.size() > 0);
      final int numOfVectors = vectors.size();
      return scalarDiv(sum(vectors), numOfVectors);
   } // mean


   /**
    * Computes the LP norm of the vector.
    * 
    * @param vector the vector whose LP norm has to be computed.
    * @return the LP norm of the vector.
    */
   public static double normLP(DoubleVector vector) {
      final int dim = vector.size();
      double result = 0;
      for (int i = 0; i < dim; i++) {
         result += Math.pow(Math.abs(vector.get(i)), dim);
      }
      return Math.pow(result, 1d / dim);
   } // normLP


   /**
    * Normalises a vector (the LP norm is used).
    * 
    * @param vector the vector to be normalised.
    * @return the normalised vector.
    */
   public static DoubleVector normalize(DoubleVector vector) {
      final double norm = normLP(vector);
      final DoubleVector result = new DoubleVector(vector.size());
      
      for (int i = 0; i < vector.size(); i++) {
         result.set(i, vector.get(i) / norm);
      }
      
      return result;
   } // normalize

}
