/*
 * @(#)GestureSignature.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Represents the signature of a gestrue.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 17, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.signature;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.sigtec.ink.input.Location;
import org.sigtec.util.Constant;
import org.ximtec.igesture.algorithm.feature.FeatureTool;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.util.GestureTool;


/**
 * Interface for distance functions.
 * 
 * @version 1.0 Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureSignature {

   private BitSet signatures;

   private Note note;

   private int numberOfPoints;

   private int rasterSize;

   private int gridSize;

   private GestureClass gestureClass;

   private Position lastPosition;

   private Grid grid;

   public class Position {

      public int x;
      public int y;
      Location location;


      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }


      public boolean equals(Position p) {
         return (p.x == this.x && p.y == this.y);
      } // equals


      @Override
      public String toString() {
         return "X=" + x + " Y=" + y;
      } // toString

   }


   /**
    * Constructs a new gesture signature.
    * 
    * @param note the note.
    * @param gestureClass the gesture class.
    * @param rasterSize the raster's size.
    * @param gridSize the number of cells the grid have in a row.
    */
   public GestureSignature(Note note, GestureClass gestureClass, int rasterSize,
         int gridSize) {
      this.signatures = new BitSet();
      this.numberOfPoints = 0;
      this.rasterSize = rasterSize;
      this.gridSize = gridSize;
      this.grid = Grid.createInstance(gridSize);
      this.gestureClass = gestureClass;
      this.note = note;
      init();
   }


   private void init() {
      List<GestureSignature.Position> points = new ArrayList<GestureSignature.Position>();
      note.moveTo(0, 0);
      Trace trace = FeatureTool.createTrace((Note)note.clone());
      
      double scale = GestureTool.scaleTraceTo(trace, rasterSize, rasterSize);
      trace.scale(scale, scale);

      for (Point point : trace.getPoints()) {
         Position p = getPosition(point);

         if (lastPosition == null || !p.equals(lastPosition)) {
            addSignature(grid.getSignature(p.x, p.y));
            lastPosition = p;
         }

         points.add(lastPosition);
      }

   } // init


   /**
    * Computes the position in the grid.
    * 
    * @param point the point whose position has to be computed.
    * @return the position of the point within the grid.
    */
   private Position getPosition(Point point) {
      final Position result = new Position(0, 0);
      result.x = (int)(point.getX() / ((rasterSize / gridSize) + 1));
      result.y = (int)(point.getY() / ((rasterSize / gridSize) + 1));
      return result;
   } // getPosition


   /**
    * Appends a point (signature point) to the gesture signature.
    * 
    * @param bit the signature point to be added.
    */
   public void addSignature(BitSet bit) {
      for (int i = 0; i < grid.getBitStringLength(); i++) {
         this.signatures.set(grid.getBitStringLength() * numberOfPoints + i, bit
               .get(i));
      }

      numberOfPoints++;
   } // addSignature


   /**
    * Returns the signature for the point at position index.
    * 
    * @param index the position of the signature to be returned.
    * @return the signature for the point at position index.
    */
   public BitSet getPointSignature(int index) {
      signatures.length();
      return signatures.get(index * grid.getBitStringLength(), (index + 1)
            * grid.getBitStringLength());
   } // getPointSignature


   /**
    * Returns the number of points in the signature.
    * 
    * @return the number of points in the signature.
    */
   public int getNumberOfPoints() {
      return this.numberOfPoints;
   } // getNumberOfPoints


   /**
    * Returns the lenght of the bit string for a single point.
    * 
    * @return the lenght of the bit string for a single point.
    */
   public int getBitStringLength() {
      return grid.getBitStringLength();
   } // getBitStringLength


   /**
    * Returns the gesture class this signature belongs to.
    * 
    * @return the gesture class this signature belongs to.
    */
   public GestureClass getGestureClass() {
      return gestureClass;
   } // getGestureClass


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();

      for (int i = 0; i < getNumberOfPoints(); i++) {
         final BitSet bitSet = getPointSignature(i);

         for (int j = 0; j < grid.getBitStringLength(); j++) {
            sb.append(bitSet.get(j) ? Constant.ONE : Constant.ZERO);
         }

      }

      return sb.toString();
   } // toString

}
