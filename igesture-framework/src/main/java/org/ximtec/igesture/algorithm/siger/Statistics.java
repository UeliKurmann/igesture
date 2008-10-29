/*
 * @(#)Statistics.java	1.0   Dec 6, 2006
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		: 	Computes statistics of a textual gesture class
 *                  description.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 6, 2006		ukurmann	Initial Release
 * Mar 16, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.siger;

import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.util.Constant;


/**
 * Computes statistics of a textual gesture class description.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Statistics {

   private static final String PROXIMITY = "PROXIMITY=";
   
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


   /**
    * Returns the number of direction elements
    * 
    * @return the number of direction elements
    */
   public int getTotalDirections() {
      return directions.size();
   }


   /**
    * Returns the number of straight elements
    * 
    * @return the number of straight elements
    */
   @SuppressWarnings("cast")
   public double getStraight() {
      return (double)(numOfLeft + numOfRight + numOfUp + numOfDown)
            / getTotalDirections();
   }


   /**
    * Returns the number of diagonal elements
    * 
    * @return the number of diagonal elements
    */
   @SuppressWarnings("cast")
   public double getDiagonal() {
      return (double)(numOfRightDown + numOfRightUp + numOfLeftDown + numOfLeftUp)
            / getTotalDirections();
   }


   /**
    * Returns the proportional distance between the first and last point
    * 
    * @return the proportional distance between the first and last point
    */
   @SuppressWarnings("cast")
   public double getProximity() {
      final double diag = Math.sqrt(Math.pow(note.getBounds2D().getWidth(), 2)
            + Math.pow(note.getBounds2D().getHeight(), 2));
      return (double)note.getStartPoint().distance(note.getEndPoint())
            / (double)diag;
   }


   /**
    * Returns the number of East directions
    * 
    * @return the number of East directions
    */
   @SuppressWarnings("cast")
   public double getEast() {

      return (double)numOfRight / getTotalDirections();
   }


   /**
    * Returns the number of west directions
    * 
    * @return the number of west directions
    */
   @SuppressWarnings("cast")
   public double getWest() {
      return (double)numOfLeft / getTotalDirections();
   }


   /**
    * Returns the number of north directions
    * 
    * @return the number of north directions
    */
   @SuppressWarnings("cast")
   public double getNorth() {
      return (double)numOfUp / getTotalDirections();
   }


   /**
    * Returns the number of south directions
    * 
    * @return the number of south directions
    */
   @SuppressWarnings("cast")
   public double getSouth() {
      return (double)numOfDown / getTotalDirections();
   }


   /**
    * Returns the number of north/west directions
    * 
    * @return the number of north/west directions
    */
   @SuppressWarnings("cast")
   public double getNorthWest() {
      return (double)numOfLeftUp / getTotalDirections();
   }


   /**
    * Returns the number of south/west directions
    * 
    * @return the number of south/west directions
    */
   @SuppressWarnings("cast")
   public double getSouthWest() {
      return (double)numOfLeftDown / getTotalDirections();
   }


   /**
    * Returns the number of north/east directions
    * 
    * @return the number of north/east directions
    */
   @SuppressWarnings("cast")
   public double getNorthEast() {
      return (double)numOfRightUp / getTotalDirections();
   }


   /**
    * Returns the number of south/east directions
    * 
    * @return the number of south/east directions
    */
   @SuppressWarnings("cast")
   public double getSouthEast() {
      return (double)numOfRightDown / getTotalDirections();
   }


   /**
    * Returns the number of traces
    * 
    * @return the number of traces
    */
   public int getTraces() {
      return note.getTraces().size();
   }


   /**
    * Returns true if the directions is straight
    * 
    * @param direction the direction
    * @return true if the directions is straight
    */
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


   /**
    * Returns true if the direction is diagonal
    * 
    * @param direction the direction
    * @return true if the direction is diagonal
    */
   public boolean isDiagonal(Direction direction) {
      return !isStraight(direction);
   }


   /**
    * Returns the number of corners
    * @return the number of corners
    */
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
      sb.append(Constant.OPEN_ANGULAR_BRACKET);
      sb.append(PROXIMITY + getProximity());
      sb.append(Constant.CLOSE_ANGULAR_BRACKET);
      return sb.toString();
   }

}
