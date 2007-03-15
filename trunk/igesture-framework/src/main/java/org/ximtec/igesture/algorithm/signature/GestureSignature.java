/*
 * @(#)GestureSignature.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Represents the signature of a gestrue
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

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.sigtec.ink.input.Location;
import org.ximtec.igesture.algorithm.feature.FeatureTools;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.util.GestureTool;

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
		}

		@Override
		public String toString() {
			return "X=" + x + " Y=" + y;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param note the note
	 * @param gestureClass the gesture class
	 * @param rasterSize the raster's size
	 * @param gridSize the number of cells the grid have in a row
	 */
	public GestureSignature(Note note, GestureClass gestureClass,
			int rasterSize, int gridSize) {
		this.signatures = new BitSet();
		this.numberOfPoints = 0;
		this.rasterSize = rasterSize;
		this.gridSize = gridSize;

		this.grid = Grid.createInstance(gridSize);
		this.gestureClass = gestureClass;
		this.note = note;

		initialize();
	}

	private void initialize() {
		final List<GestureSignature.Position> points = new ArrayList<GestureSignature.Position>();
		final Trace trace = FeatureTools.createTrace((Note) note.clone());
		final double scale = GestureTool.scaleTraceTo(trace, rasterSize,
				rasterSize);
		trace.scale(scale, scale);

		for (final Point point : trace.getPoints()) {
			final Position p = getPosition(point);
			if (lastPosition == null || !p.equals(lastPosition)) {
				addSignature(grid.getSignature(p.x, p.y));
				lastPosition = p;
			}
			points.add(lastPosition);
		}
	}

	/**
	 * Computes the position in the grid
	 * 
	 * @param point
	 * @return
	 */
	private Position getPosition(Point point) {
		final Position result = new Position(0, 0);
		result.x = (int) (point.getX() / ((rasterSize / gridSize) + 1));
		result.y = (int) (point.getY() / ((rasterSize / gridSize) + 1));
		return result;
	}

	/**
	 * Appends a point (signature point) to the gesture signature
	 * 
	 * @param bit
	 */
	public void addSignature(BitSet bit) {
		for (int i = 0; i < grid.getBitStringLength(); i++) {
			this.signatures.set(grid.getBitStringLength() * numberOfPoints + i,
					bit.get(i));
		}
		numberOfPoints++;
	}

	/**
	 * returns the signature for the ith point
	 * 
	 * @param i
	 * @return
	 */
	public BitSet getPointSignature(int i) {
		signatures.length();
		return signatures.get(i * grid.getBitStringLength(), (i + 1)
				* grid.getBitStringLength());
	}

	/**
	 * Returns the number of points in the signature
	 * 
	 * @return
	 */
	public int getNumberOfPoints() {
		return this.numberOfPoints;
	}

	/**
	 * Returns the lenght of the bit string for one point
	 * 
	 * @return
	 */
	public int getBitStringLength() {
		return grid.getBitStringLength();
	}

	/**
	 * Returns the gesture class this signature belongs to
	 * 
	 * @return
	 */
	public GestureClass getGestureClass() {
		return gestureClass;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getNumberOfPoints(); i++) {
			final BitSet bitSet = getPointSignature(i);
			for (int j = 0; j < grid.getBitStringLength(); j++) {
				sb.append(bitSet.get(j) ? "1" : "0");
			}
			// sb.append(",");
		}

		return sb.toString();
	}

}
