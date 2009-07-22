/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
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
 * Dec 6, 2006		ukurmann	Initial Release
 * Mar 16, 2007     bsigner     Cleanup
 * Mar 19, 2007		ukurmann	Add Constants
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
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
import org.sigtec.util.Constant;
import org.ximtec.igesture.util.GestureTool;


/**
 * Analyses an input gesture and creates a string describing it.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class StrokeInfo {

   private static final int ANGLE_E0 = 0;

private static final double ANGLE_N_NE = 292.5;

private static final double ANGLE_NW_N = 247.5;

private static final double ANGLE_W_NW = 202.5;

private static final double ANGLE_SW_W = 157.5;

private static final double ANGLE_S_SW = 112.5;

private static final double ANGLE_SE_S = 67.5;

private static final double ANGLE_E_SE = 22.5;

private static final int ANGLE_E = 360;

private static final double ANGLE_NE_E = 337.5;

private List<Direction> directions;

   private Note note;

   private Statistics statistics;


   public StrokeInfo(Note note) {
      this.note = GestureTool.getCharacteristicNote((Note)note.clone(), 3, 10);
      directions = new ArrayList<Direction>();
      final List<Point> points = this.note.getPoints();

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
    * Returns a string representation of the stroke info.
    * 
    * @return string representation of the stroke info.
    */
   public String getString() {
      final StringBuilder sb = new StringBuilder();

      for (final Direction d : directions) {
         sb.append(d.name() + Constant.COMMA);
      }

      return sb.toString();
   } // getString


   /**
    * Returns the direction based on the gradient's angle.
    * 
    * @param angle the angle of the gradient.
    * @return the direction (8 cardial points).
    */
   private Direction getDirection(double angle) {
      if ((angle >= ANGLE_NE_E && angle < ANGLE_E)
				|| (angle >= ANGLE_E0 && angle < ANGLE_E_SE)) {
         return Direction.E;
      }
      else if (angle >= ANGLE_E_SE && angle < ANGLE_SE_S) {
         return Direction.SE;
      }
      else if (angle >= ANGLE_SE_S && angle < ANGLE_S_SW) {
         return Direction.S;
      }
      else if (angle >= ANGLE_S_SW && angle < ANGLE_SW_W) {
         return Direction.SW;
      }
      else if (angle >= ANGLE_SW_W && angle < ANGLE_W_NW) {
         return Direction.W;
      }
      else if (angle >= ANGLE_W_NW && angle < ANGLE_NW_N) {
         return Direction.NW;
      }
      else if (angle >= ANGLE_NW_N && angle < ANGLE_N_NE) {
         return Direction.N;
      }
      else if (angle >= ANGLE_N_NE && angle < ANGLE_NE_E) {
         return Direction.NE;
      }
      throw new IllegalStateException();
   } // getDirection


   /**
    * Returns the statistics object.
    * 
    * @return the statistic object.
    */
   public Statistics getStatistics() {
      return statistics;
   } // getStatistics


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append(Constant.OPEN_ANGULAR_BRACKET);
      
      for (final Direction d : directions) {
         sb.append(d.name() + Constant.COMMA);
      }
      
      sb.append(Constant.CLOSE_ANGULAR_BRACKET);
      return sb.toString();
   } // toString

}
