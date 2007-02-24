/*
 * @(#)Grid.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Implementation of the grid used to create the signature
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
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.signature;

import java.util.BitSet;
import java.util.HashMap;


public class Grid {

   /**
    * directions to populate the signatures
    * 
    * @author Ueli Kurmann
    * 
    */
   private enum Direction {
      RIGHT, UP
   };

   /**
    * the lenght of the bit string (signature representation)
    */
   private int bitStringLength;

   /**
    * the size of the grid
    */
   private int size;

   /**
    * the grid
    */
   private BitSet[][] grid;

   private static HashMap<Integer, Grid> gridCache;

   static {
      gridCache = new HashMap<Integer, Grid>();
   }


   /**
    * Constructor
    * 
    * @param size the size of the grid (size*size)
    */
   public Grid(int size) {
      this.size = computeGridSize(size);
      this.bitStringLength = (int) Math.ceil(Math.log(this.size * this.size)
            / Math.log(2));
      grid = new BitSet[this.size][this.size];
      initialize();
   }


   /**
    * computes the position of the bit
    * 
    * @param width the width of the "subgrid" after mirroring
    * @param height the height of the "subgrid" after mirroring
    * @return the bit position
    */
   private int getBitPosition(int width, int height) {
      final double pow = Math.log(width * height) / Math.log(2);
      return this.bitStringLength - (int) (Math.ceil(pow));
   }


   public int getBitStringLength() {
      return bitStringLength;
   }


   /**
    * computes the "mirror" position the given field
    * 
    * @param x x coordinate of the field to mirror
    * @param y y coordinate of the field to mirror
    * @param width total width of the current "subgrid"
    * @param height total height of the current "subgrid"
    * @param direction the direction we want to mirror
    * @return the coordinates. (0=x, 1=y)
    */
   private int[] getMirrorPosition(int x, int y, int width, int height,
         Direction direction) {
      final int[] coordinate = new int[2];
      if (direction.equals(Direction.UP)) {
         final int newHeight = 2 * height;
         coordinate[0] = x;
         coordinate[1] = newHeight - (y + 1);
      }
      else {
         final int newWidth = 2 * width;
         coordinate[0] = newWidth - (x + 1);
         coordinate[1] = y;
      }
      return coordinate;
   }


   /**
    * Returns the signature of a given field of the grid
    * 
    * @param x the x coordinate
    * @param y the y coordinate
    * @return the signature
    */
   public BitSet getSignature(int x, int y) {
      if (!(x >= 0 && x < size && y >= 0 && y < size)) {
         throw new RuntimeException("size not allowed");
      }
      return grid[x][y];
   }


   /**
    * Initializes the grid
    */
   private void initialize() {
      final BitSet bitSet = new BitSet(size);
      bitSet.clear();
      grid[0][0] = bitSet;
      recursive(1, 1, Direction.UP);
   }


   /**
    * Pretty print for BitSet
    * 
    * @param bitSet
    * @return
    */
   private String prettyPrint(BitSet bitSet) {
      final StringBuilder sb = new StringBuilder();
      for (int i = 0; i < this.bitStringLength; i++) {
         if (bitSet.get(i)) {
            sb.append("1");
         }
         else {
            sb.append("0");
         }
      }
      return sb.toString();
   }


   /**
    * Recursive method to populate the values in the grid
    * 
    * @param x the x coordinate
    * @param y the y coordinate
    * @param direction
    */
   private void recursive(int x, int y, Direction direction) {
      if (x >= this.size && y >= this.size) {
         return;
      }

      int newX;
      int newY;
      Direction newDirection;

      if (direction.equals(Direction.RIGHT)) {
         newX = x * 2;
         newY = y;
         newDirection = Direction.UP;
      }
      else {
         newX = x;
         newY = y * 2;
         newDirection = Direction.RIGHT;
      }

      for (int i = 0; i < x; i++) {
         for (int j = 0; j < y; j++) {
            final int[] coord = getMirrorPosition(i, j, x, y, direction);
            final BitSet b = (BitSet) grid[i][j].clone();
            b.set(getBitPosition(newX, newY));
            this.setCell(coord[0], coord[1], b);
         }
      }

      recursive(newX, newY, newDirection);
   }


   /**
    * sets the signature of a field
    * 
    * @param x
    * @param y
    * @param bitSet the signature of the field
    */
   public void setCell(int x, int y, BitSet bitSet) {
      if (!(x >= 0 && x < size && y >= 0 && y < size)) {
         throw new IndexOutOfBoundsException();
      }
      grid[x][y] = bitSet;
   }


   private static int computeGridSize(int gridSize) {
      return (int) Math.pow(2, (int) Math
            .ceil((Math.log(gridSize) / Math.log(2))));
   }


   /**
    * Returns the grid with the desired size. If the grid is available in the
    * cache, the cached instance is returned. Else a new grid instance is created
    * and added to the cache.
    * 
    * @param size
    * @return
    */
   public static Grid createInstance(int size) {
      final int gridSize = computeGridSize(size);
      Grid result = gridCache.get(gridSize);
      if (result == null) {
         result = new Grid(gridSize);
         gridCache.put(gridSize, result);
      }
      return result;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            sb.append(prettyPrint(grid[i][j]) + "  ");
         }
         sb.append("\n");
      }
      return sb.toString();
   }
}
