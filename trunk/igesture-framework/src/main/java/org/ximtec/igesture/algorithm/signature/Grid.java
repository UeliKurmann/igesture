/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      : 	Implementation of the grid that is used to create the
 *                  signature.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 18, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.algorithm.signature;

import java.util.BitSet;
import java.util.HashMap;

import org.sigtec.util.Constant;


/**
 * Implementation of the grid that is used to create the signature.
 * 
 * @version 1.0 Dec 11, 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Grid {

   /**
    * Directions to populate the signatures.
    */
   private enum Direction {
      RIGHT, UP
   }

   /**
    * The lenght of the bit string (signature representation).
    */
   private int bitStringLength;

   /**
    * The size of the grid.
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
    * Constructs a new grid.
    * 
    * @param size the size of the grid (size*size).
    */
   public Grid(int size) {
      this.size = computeGridSize(size);
      bitStringLength = (int)Math.ceil(Math.log(this.size * this.size)
            / Math.log(2));
      grid = new BitSet[this.size][this.size];
      init();
   }


   /**
    * Computes the position of the bit.
    * 
    * @param width the width of the "subgrid" after mirroring.
    * @param height the height of the "subgrid" after mirroring.
    * @return the bit position.
    */
   private int getBitPosition(int width, int height) {
      final double pow = Math.log(width * height) / Math.log(2);
      return this.bitStringLength - (int)(Math.ceil(pow));
   } // getBitPosition


   public int getBitStringLength() {
      return bitStringLength;
   } // getBitStringLength


   /**
    * Computes the "mirror" position of the given field.
    * 
    * @param x the x coordinate of the field to mirror.
    * @param y the y coordinate of the field to mirror.
    * @param width total width of the current "subgrid".
    * @param height total height of the current "subgrid".
    * @param direction the direction to be mirrored.
    * @return the coordinates 0=x, 1=y).
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
   } // getMirrorPosition


   /**
    * Returns the signature of a given field of the grid.
    * 
    * @param x the x coordinate.
    * @param y the y coordinate.
    * @return the signature.
    */
   public BitSet getSignature(int x, int y) {
      if (!(x >= 0 && x < size && y >= 0 && y < size)) {
         throw new RuntimeException("size not allowed");
      }

      return grid[x][y];
   } // getSignature


   /**
    * Initialises the grid.
    */
   private void init() {
      final BitSet bitSet = new BitSet(size);
      bitSet.clear();
      grid[0][0] = bitSet;
      recursive(1, 1, Direction.UP);
   } // init


   /**
    * Pretty print for a given bit set.
    * 
    * @param bitSet the bit set to be formatted.
    * @return formatted bit set.
    */
   private String prettyPrint(BitSet bitSet) {
      final StringBuilder sb = new StringBuilder();

      for (int i = 0; i < this.bitStringLength; i++) {

         if (bitSet.get(i)) {
            sb.append(Constant.ONE);
         }
         else {
            sb.append(Constant.ZERO);
         }

      }

      return sb.toString();
   } // prettyPrint


   /**
    * Recursive method to populate the values in the grid.
    * 
    * @param x the x coordinate.
    * @param y the y coordinate.
    * @param direction the direction.
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
            final BitSet b = (BitSet)grid[i][j].clone();
            b.set(getBitPosition(newX, newY));
            this.setCell(coord[0], coord[1], b);
         }

      }

      recursive(newX, newY, newDirection);
   } // recursive


   /**
    * Sets the signature of a field.
    * 
    * @param x the x coordinate.
    * @param y the y coordinate.
    * @param bitSet the signature of the field.
    */
   public void setCell(int x, int y, BitSet bitSet) {
      if (!(x >= 0 && x < size && y >= 0 && y < size)) {
         throw new IndexOutOfBoundsException();
      }

      grid[x][y] = bitSet;
   } // setCell


   private static int computeGridSize(int gridSize) {
      return (int)Math
            .pow(2, (int)Math.ceil((Math.log(gridSize) / Math.log(2))));
   } // computeGridSize


   /**
    * Returns the grid with the desired size. If the grid is available in the
    * cache, the cached instance is returned. Otherwise a new grid instance is
    * created and added to the cache.
    * 
    * @param size the size of the grid to be returned.
    * @return grid with the specified size.
    */
   public static Grid createInstance(int size) {
      final int gridSize = computeGridSize(size);
      Grid result = gridCache.get(gridSize);

      if (result == null) {
         result = new Grid(gridSize);
         gridCache.put(gridSize, result);
      }

      return result;
   } // createInstance


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();

      for (int i = 0; i < size; i++) {

         for (int j = 0; j < size; j++) {
            sb.append(prettyPrint(grid[i][j]) + Constant.DOUBLE_BLANK);
         }

         sb.append(Constant.LF_STRING);
      }

      return sb.toString();
   } // toString

}