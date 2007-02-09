/*
 * @(#)Direction.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Holds a set of results
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

/**
 * This class groups a set of results. 
 * @author Ueli Kurmann
 */


package org.ximtec.igesture.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.sigtec.ink.Note;


public class ResultSet {

   private List<Result> results;

   private int maxSize;

   private Note note;


   /**
    * Constructor
    * 
    */
   public ResultSet() {
      this(Integer.MAX_VALUE);
   }


   /*****************************************************************************
    * Constructor
    * 
    * @param maxSize maximal size of the result set
    */
   public ResultSet(int maxSize) {
      results = new ArrayList<Result>();
      this.maxSize = maxSize;
   }


   /**
    * Returns the list of all results
    * 
    * @return the list of results
    */
   public List<Result> getResults() {
      final List<Result> results = new ArrayList<Result>();
      for (int i = 0; i < maxSize && i < this.results.size(); i++) {
         results.add(this.results.get(i));
      }
      return results;
   }


   /**
    * returns the ith result
    * 
    * @param i the index of the result
    * @return the ith result
    */
   public Result getResult(int i) {
      return results.get(i);
   }


   /**
    * returns the first, most suitable result
    * 
    * @return
    */
   public Result getResult() {
      return !results.isEmpty() ? getResult(0) : null;
   }


   /**
    * add a result to the result set. the result set is always ordered by the
    * accuracy of the results
    * 
    * @param result the result to add
    */
   public synchronized void add(Result result) {
      results.add(result);

      /**
       * sorts the results by the accuracy (desc)
       */
      Collections.sort(results, new Comparator<Result>() {

         public int compare(Result result1, Result result2) {
            return Double.compare(result2.getAccuracy(), result1.getAccuracy());
         }
      });
   }


   /**
    * Returns true if the result set contains the given gesture class
    * 
    * @param gestureClass
    * @return
    */
   public boolean contains(GestureClass gestureClass) {
      for (final Result result : results) {
         if (result.getGestureClass() == gestureClass) {
            return true;
         }
      }
      return false;
   }


   /**
    * Returns the number of results
    * 
    * @return the number of results in the result set
    */
   public int size() {
      return results.size() > maxSize ? maxSize : results.size();
   }


   /**
    * 
    * @return
    */
   public boolean isEmpty() {
      return results.isEmpty();
   }


   /**
    * Sets the note containing the gesture
    * 
    * @param note
    */
   public void setNote(Note note) {
      this.note = note;
   }


   /**
    * Returns the note containing the gesture to recognise
    * 
    * @return the note
    */
   public Note getNote() {
      return note;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append("[ResultSet");
      for (final Result r : this.getResults()) {
         sb.append("  [" + r.getName() + ", " + r.getAccuracy() + "]");
      }
      sb.append("]");
      return sb.toString();
   }
}
