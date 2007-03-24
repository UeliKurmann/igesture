/*
 * @(#)ResultSet.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Holds a set of result objects.
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;


/**
 * Holds a set of result objects.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class ResultSet {

   private List<Result> results;

   private int maxSize;

   private Note note;


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
      results = new ArrayList<Result>();
      this.maxSize = maxSize;
   }


   /**
    * Adds a result object to the result set. The result set is always ordered by
    * the accuracy value of the result objects.
    * 
    * @param result the result object to be added.
    */
   public synchronized void addResult(Result result) {
      // FIXME: add the single result at the correct position -> we do not have
      // to sort the list anymore -> faster.
      results.add(result);

      /**
       * sorts the results by the accuracy (desc)
       */
      Collections.sort(results, new Comparator<Result>() {

         public int compare(Result result1, Result result2) {
            return Double.compare(result2.getAccuracy(), result1.getAccuracy());
         }
      });

   } // addResult


   /**
    * Returns a list containing all result objects.
    * 
    * @return the list of result objects.
    */
   public List<Result> getResults() {
      final List<Result> results = new ArrayList<Result>();

      for (int i = 0; i < maxSize && i < this.results.size(); i++) {
         results.add(this.results.get(i));
      }

      return results;
   } // getResults


   /**
    * Returns the result for a given index.
    * 
    * @param index the index of the result object to be returned.
    * @return the result object for the given index position.
    */
   public Result getResult(int index) {
      return results.get(index);
   } // getResult


   /**
    * Returns the first result object which is also the result object with the
    * highest recognition accuracy.
    * 
    * @return the first result object or null if the result set does not contain
    *         any result obejcts.
    */
   public Result getResult() {
      return !results.isEmpty() ? getResult(0) : null;
   } // getResult


   /**
    * Returns true if the result set contains the given gesture class.
    * 
    * @param gestureClass the gesture class to be tested.
    * @return true if the result set contains the specified gesture class.
    */
   public boolean contains(GestureClass gestureClass) {
      for (final Result result : results) {

         if (result.getGestureClass() == gestureClass) {
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
    * Sets the note containing the gesture.
    * 
    * @param note the note containing the gesture.
    */
   public void setNote(Note note) {
      this.note = note;
   } // setNote


   /**
    * Returns the note containing the gesture to be recognised.
    * 
    * @return the note containing the gesture to be recognised.
    */
   public Note getNote() {
      return note;
   } // getNote


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append("[ResultSet");

      for (final Result r : this.getResults()) {
         sb.append(Constant.DOUBLE_BLANK + Constant.OPEN_ANGULAR_BRACKET
               + r.getGestureClassName() + Constant.COMMA_BLANK
               + r.getAccuracy() + Constant.CLOSE_ANGULAR_BRACKET);
      }

      sb.append(Constant.CLOSE_ANGULAR_BRACKET);
      return sb.toString();
   } // toString

}