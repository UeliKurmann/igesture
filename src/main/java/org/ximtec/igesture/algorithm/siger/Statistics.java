/*
 * @(#)Statistics.java	1.0   Dec 6, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Computes statistics of a textual gesture class description
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


package org.ximtec.igesture.algorithm.siger;

import java.util.List;

import org.sigtec.ink.Note;


/**
 * Comment
 * 
 * @version 1.0 Dec 6, 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 */
public class Statistics {

   // statistical information
   private double numOfLeft;

   private double numOfRight;

   private double numOfUp;

   private double numOfDown;

   private double numOfLeftUp;

   private double numOfLeftDown;

   private double numOfRightUp;

   private double numOfRightDown;

   // list of directions
   private List<Direction> directions;

   private Note note;


   public Statistics(List<Direction> directions, Note note) {
      this.directions = directions;
      this.note = note;
      numOfLeft = 0;
      numOfRight = 0;
      numOfUp = 0;
      numOfDown = 0;
      numOfLeftUp = 0;
      numOfRightUp = 0;
      numOfLeftDown = 0;
      numOfRightDown = 0;
      initialise(directions);
   }


   private void initialise(List<Direction> directions) {
      for (final Direction direction : directions) {
         switch (direction) {
            case W:
               numOfLeft++;
               break;
            case E:
               numOfRight++;
               break;
            case N:
               numOfUp++;
               break;
            case S:
               numOfDown++;
               break;
            case NW:
               numOfLeftUp++;
               break;
            case SW:
               numOfLeftDown++;
               break;
            case NE:
               numOfRightUp++;
               break;
            case SE:
               numOfRightDown++;
               break;
         }
      }
   }


   public int getTotalDirections() {
      return directions.size();
   }


   @SuppressWarnings("cast")
   public double getStraight() {
      return (double) (numOfLeft + numOfRight + numOfUp + numOfDown)
            / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getDiagonal() {
      return (double) (numOfRightDown + numOfRightUp + numOfLeftDown + numOfLeftUp)
            / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getProximity() {
      final double diag = Math.sqrt(Math.pow(note.getBounds2D().getWidth(), 2)
            + Math.pow(note.getBounds2D().getHeight(), 2));
      return (double) note.getStartPoint().distance(note.getEndPoint())
            / (double) diag;
   }


   @SuppressWarnings("cast")
   public double getEast() {

      return (double) numOfRight / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getWest() {
      return (double) numOfLeft / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getNorth() {
      return (double) numOfUp / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getSouth() {
      return (double) numOfDown / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getNorthWest() {
      return (double) numOfLeftUp / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getSouthWest() {
      return (double) numOfLeftDown / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getNorthEast() {
      return (double) numOfRightUp / getTotalDirections();
   }


   @SuppressWarnings("cast")
   public double getSouthEast() {
      return (double) numOfRightDown / getTotalDirections();
   }


   public int getTraces() {
      return note.getTraces().size();
   }


   public boolean isStraight(Direction direction) {
      switch (direction) {
         case N:
            return true;
         case E:
            return true;
         case S:
            return true;
         case W:
            return true;
         default:
            return false;
      }
   }


   public boolean isDiagonal(Direction direction) {
      return !isStraight(direction);
   }


   public int getCorners() {
      int corners = 0;
      for (int i = 1; i < directions.size(); i++) {
         if ((isStraight(directions.get(i - 1)) && isDiagonal(directions.get(i)))
               || isDiagonal(directions.get(i - 1))
               && isStraight(directions.get(i))) {
            corners++;
         }
      }
      return corners;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append("[");
      sb.append("PROXIMITY=" + getProximity());
      sb.append("]");
      return sb.toString();
   }

}
