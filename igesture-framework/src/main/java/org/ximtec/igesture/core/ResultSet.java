/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Class representing a set of result objects.
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
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;


/**
 * Class representing a set of result objects.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ResultSet {

   private static final String RESULT_SET = "ResultSet";

   private SortedSet<Result> results;

   private int maxSize;

   private Note gesture;


   /**
    * Constructs a new result set.
    */
   public ResultSet() {
      this(Integer.MAX_VALUE);
   }


   /**
    * Constructs a new result set with a specific maximal size.
    * 
    * @param maxSize the maximal size of the result set.
    */
   public ResultSet(int maxSize) {
      results = Collections.synchronizedSortedSet(new TreeSet<Result>(
            new Comparator<Result>() {

               public int compare(Result result1, Result result2) {
                  return Double.compare(result2.getAccuracy(), result1
                        .getAccuracy());
               }
            }));

      this.maxSize = maxSize;
   }


   /**
    * Adds a result object to the result set. The result set is always ordered by
    * the accuracy value of the result objects.
    * 
    * @param result the result object to be added.
    */
   public synchronized void addResult(Result result) {
      results.add(result);
   } // addResult


   /**
    * Adds a collection of result objects.
    * 
    * @param results the result objects to be added.
    */
   public void addResults(Collection<Result> results) {
      for (Result result : results) {
         addResult(result);
      }
      
   }// addResults


   /**
    * Returns a list containing all result objects.
    * 
    * @return the list of result objects.
    */
   public List<Result> getResults() {
      return new ArrayList<Result>(results).subList(0, Math.min(maxSize, results
            .size()));
   } // getResults


   /**
    * Returns the result for a given index position.
    * 
    * @param index the index of the result object to be returned.
    * @return the result object for the given index position.
    */
   public Result getResult(int index) {
      return new ArrayList<Result>(results).get(index);
   } // getResult


   /**
    * Returns the first result object which is also the result object with the
    * highest recognition accuracy.
    * 
    * @return the first result object or null if the result set does not contain
    *         any result objects.
    */
   public Result getResult() {
      return !results.isEmpty() ? getResult(0) : null;
   } // getResult


   /**
    * Returns true if the result set contains the given gesture class.
    * 
    * @param gestureClass the gesture class to be tested for.
    * @return true if the result set contains the specified gesture class.
    */
   public boolean contains(GestureClass gestureClass) {
      for (final Result result : results) {

         if (result.getGestureClass().getName().equals(gestureClass.getName())) {
            return true;
         }

      }

      return false;
   } // contains


   /**
    * Returns the number of results.
    * 
    * @return the number of results in the result set.
    */
   public int size() {
      return results.size() > maxSize ? maxSize : results.size();
   } // size


   /**
    * Return true if the result set is empty.
    * 
    * @return true if the result set is empty.
    */
   public boolean isEmpty() {
      return results.isEmpty();
   } // isEmpty


   /**
    * Sets the gesture to be recognised.
    * 
    * @param gesture the gesture to be recognised.
    */
   public void setGesture(Note gesture) {
      this.gesture = gesture;
   } // setGesture


   /**
    * Returns the gesture to be recognised.
    * 
    * @return the gesture to be recognised.
    */
   public Note getGesture() {
      return gesture;
   } // getGesture


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append(Constant.OPEN_ANGULAR_BRACKET);
      sb.append(RESULT_SET);

      for (final Result r : this.getResults()) {
         sb.append(Constant.DOUBLE_BLANK + Constant.OPEN_ANGULAR_BRACKET
               + r.getGestureClassName() + Constant.COMMA_BLANK
               + r.getAccuracy() + Constant.CLOSE_ANGULAR_BRACKET);
      }

      sb.append(Constant.CLOSE_ANGULAR_BRACKET);
      return sb.toString();
   } // toString 

}
