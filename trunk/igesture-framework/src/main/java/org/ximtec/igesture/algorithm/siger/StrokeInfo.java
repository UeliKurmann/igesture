/*
 * @(#)StrokeInfo.java	1.0   Dec 6, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		: 	Analyses an input gesture and creates a string 
 * 					describing it.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Dec 2006			ukurmann	Initial Release
 * Mar 16, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.siger;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.ximtec.igesture.util.GestureTool;


/**
 * Analyses an input gesture and creates a string describing it.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class StrokeInfo {

   private List<Direction> directions;

   private Note note;

   private Statistics statistics;


   public StrokeInfo(Note note) {
      this.note = GestureTool.getCharacteristicNote((Note)note.clone(), 3, 10);
      directions = new ArrayList<Direction>();
      final List<Point> points = GestureTool.getPoints(this.note);
      
      for (int i = 1; i < points.size(); i++) {
         final double angle = GestureTool.getAngle(points.get(i - 1), points
               .get(i));
         
         if (!Double.isNaN(angle)) {
            directions.add(getDirection(angle));
         }
      
      }
      
      this.statistics = new Statistics(directions, this.note);
   } // StrokeInfo


   /**
    * Creats
    * 
    * @return
    */
   public String getString() {
      final StringBuilder sb = new StringBuilder();
      for (final Direction d : directions) {
         sb.append(d.name() + ",");
      }
      return sb.toString();
   }


   /**
    * Returns the direction on the basis the gradient's angle
    * 
    * @param angle the angle of the gradient
    * @return the direction (8 cardial points)
    */
   private Direction getDirection(double angle) {
      if ((angle >= 337.5 && angle < 360) || (angle >= 0 && angle < 22.5)) {
         return Direction.E;
      }
      else if (angle >= 22.5 && angle < 67.5) {
         return Direction.SE;
      }
      else if (angle >= 67.5 && angle < 112.5) {
         return Direction.S;
      }
      else if (angle >= 112.5 && angle < 157.5) {
         return Direction.SW;
      }
      else if (angle >= 157.5 && angle < 202.5) {
         return Direction.W;
      }
      else if (angle >= 202.5 && angle < 247.5) {
         return Direction.NW;
      }
      else if (angle >= 247.5 && angle < 292.5) {
         return Direction.N;
      }
      else if (angle >= 292.5 && angle < 337.5) {
         return Direction.NE;
      }
      throw new IllegalStateException();
   }


   /**
    * Returns the statistic object
    * 
    * @return
    */
   public Statistics getStatistics() {
      return statistics;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append("[");
      for (final Direction d : directions) {
         sb.append(d.name() + ",");
      }
      sb.append("]");
      return sb.toString();
   }

}
