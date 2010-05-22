/**
 * 
 */
package org.ximtec.igesture.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.util.additions3d.AccelerationSample;
import org.ximtec.igesture.util.additions3d.Accelerations;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.util.additions3d.Point3D;

/**
 * @author Björn Puype, bpuype@gmail.com
 *
 */
public class RecordedGesture3DTool {

	public static void paintGesture(RecordedGesture3D gs, Graphics g, int width, int height, boolean drawFieldTitles)
	{		
	   List<Rectangle> fields = paintStructure(g,width,height, drawFieldTitles);

	   ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
	   
		Accelerations acc = gs.getAccelerations();
	
		// Split the gesture to three planes of type Gesture<Note>
		List<Gesture<Note>> notes = splitToPlanes(gs);
	
		// Get traces out of the note
		List<Trace> traces = new Vector<Trace>();
		for (int i = 0; i < notes.size(); i++) {
			traces.add(notes.get(i).getGesture().get(0));
		}
		traces = scaleTraces(traces, 10, fields.subList(0, 3));
		notes.clear();
		for (int i = 0; i < traces.size(); i++) {
			Note note = new Note();
			note.add(traces.get(i));
			Gesture<Note> gesture = new GestureSample("", note);
			notes.add(gesture);
		}
	
		// Get the planes from the list for convenience
		Gesture<Note> gest = notes.get(0);
		Note noteXY = gest.getGesture();
		gest = notes.get(1);
		Note noteYZ = gest.getGesture();
		gest = notes.get(2);
		Note noteZX = gest.getGesture();
		// Rectangle accelerationsField = fields.get(3);
		// Draw planes on g
		drawPlane(noteXY, fields.get(0), g);
		drawPlane(noteYZ, fields.get(1), g);
		drawPlane(noteZX, fields.get(2), g);
		// Draw acceleration graphs
		drawAccelerationsGraph(acc, fields.get(3), 0.02, g);
		
		if(drawFieldTitles)
			g.drawString(""+gs.size(), width/2-5, height/2+5);
		
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_OFF);
  }
  
  /**
   * Draw the fields for the different planes and the accelerations.
   */
  public static List<Rectangle> paintStructure(Graphics g, int width, int height, boolean drawFieldTitles)
  {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		
		// Calculate the drawing fields for the planes
		List<Rectangle> fields = calculateFieldSizes(0.02,width,height);
		Rectangle XYfield = fields.get(0);
		Rectangle YZfield = fields.get(1);
		Rectangle ZXfield = fields.get(2);
		Rectangle graphField = fields.get(3);
		//Draw rectangles around the fields
		g.drawRect((int) XYfield.getX(), (int) XYfield.getY(), (int) XYfield.getWidth(), (int) XYfield.getHeight());
		g.drawRect((int) YZfield.getX(), (int) YZfield.getY(), (int) YZfield.getWidth(), (int) YZfield.getHeight());
		g.drawRect((int) ZXfield.getX(), (int) ZXfield.getY(), (int) ZXfield.getWidth(), (int) ZXfield.getHeight());
		g.drawRect((int) graphField.getX(), (int) graphField.getY(), (int) graphField.getWidth(), (int) graphField.getHeight());
		if(drawFieldTitles)
		{
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		            RenderingHints.VALUE_ANTIALIAS_ON);
			
			//Draw titles in the fields
			g.setColor(Color.RED);
			g.drawString("XY-Plane", (int) (XYfield.getX() + 10),(int) (XYfield.getY() + 15));
			g.drawString("YZ-Plane", (int) (YZfield.getX() + 10),(int) (YZfield.getY() + 15));
			g.drawString("ZX-Plane", (int) (ZXfield.getX() + 10),(int) (ZXfield.getY() + 15));
			g.drawString("Accelerations", (int) (graphField.getX() + 10),(int) (graphField.getY() + 15));
			
			
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		            RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		return fields;
  }
  
  /**
	 * Calculates the sizes and positions of the four fields on this panel,
	 * using the size of the panel
	 * 
	 * @param spacePercentage
	 *            The margin percentage for the fields from the sides of the
	 *            panel
	 * @return A list of 4 Rectangles, the fields for this panel
	 */
	private static List<Rectangle> calculateFieldSizes(double spacePercentage, int width, int height) {
		// Keep a distance from the sides of the panel
		int spacerHorizontal = (int) (spacePercentage * width);
		int spacerVertical = (int) (spacePercentage * height);
		// Calculate width and height, they are the same for all four fields
		int fieldWidth = (int) ((width - (4 * spacerHorizontal)) * 0.5);
		int fieldHeight = (int) ((height - (4 * spacerVertical)) * 0.5);
		// Create field rectangles
		Rectangle field1 = new Rectangle(spacerHorizontal, spacerVertical,
				fieldWidth, fieldHeight);
		Rectangle field2 = new Rectangle((spacerHorizontal * 3) + fieldWidth,
				spacerVertical, fieldWidth, fieldHeight);
		Rectangle field3 = new Rectangle(spacerHorizontal, (spacerVertical * 3)
				+ fieldHeight, fieldWidth, fieldHeight);
		Rectangle field4 = new Rectangle((spacerHorizontal * 3) + fieldWidth,
				(spacerVertical * 3) + fieldHeight, fieldWidth, fieldHeight);
		// Add fields to return variable
		List<Rectangle> fields = new Vector<Rectangle>();
		fields.add(field1);
		fields.add(field2);
		fields.add(field3);
		fields.add(field4);
		// Return
		return fields;
	}
  
	/**
	 * Draws a graph with wiimote accelerations on g
	 * 
	 * @param acc
	 *            The WiiAccelerations object containing the data for the graphs
	 * @param field
	 *            The rectangle in which the graph should be drawn
	 * @param spacePercentage
	 *            The space marging percentage at the sides
	 * @param title
	 *            The title of the graph
	 * @param g
	 *            The Graphics object on which the graph should be drawn
	 */
	private static void drawAccelerationsGraph(Accelerations acc, Rectangle field,
			double spacePercentage, Graphics g) {
		g.setColor(Color.BLACK);

		if (acc != null) {

			// Calculate margin in pixels from spacePercentage
			int margin = (int) (spacePercentage * field.getWidth());

			// Define fields for X, Y and Z acceleration graphs
			Rectangle fieldX = new Rectangle((int) field.getX() + margin,
					(int) field.getY() + margin, (int) field.getWidth()
							- (2 * margin), (int) (field.getHeight() / 3)
							- (2 * margin));
			Rectangle fieldY = new Rectangle((int) field.getX() + margin,
					(int) (field.getY() + field.getHeight() / 3) + margin,
					(int) field.getWidth() - (2 * margin), (int) (field
							.getHeight() / 3)
							- (2 * margin));
			Rectangle fieldZ = new Rectangle((int) field.getX() + margin,
					(int) (field.getY() + field.getHeight() * 0.67) + margin,
					(int) field.getWidth() - (2 * margin), (int) (field
							.getHeight() / 3)
							- (2 * margin));

			// Create buffers of points to draw for X, Y and Z acceleration
			List<List<Point>> buffers = scaleAccelerations(acc, fieldX);

			// Draw the graphs
			drawGraph(buffers.get(0), fieldX, Color.GRAY, Color.RED, "X",//-Axis
					g);
			drawGraph(buffers.get(1), fieldY, Color.GRAY, Color.GREEN,
					"Y", g);//-Axis
			drawGraph(buffers.get(2), fieldZ, Color.GRAY, Color.BLUE, "Z",//-Axis
					g);

		}
	}

	/**
	 * Scales acceleration data from acc to fit into a rectangle of the size of
	 * fieldSize in order to be drawn. Returns a lists of 3 lists of points.
	 * 
	 * @param acc
	 *            the WiiAccelerations object containing the input data
	 * @param fieldSize
	 *            A rectangle from which the size is used to scale into
	 * @return A List of Lists of Points, cotaining the scaled data
	 */
	private static List<List<Point>> scaleAccelerations(Accelerations acc,
			Rectangle fieldSize) {
		// Retrieve timestamp of first and last sample
		long timeFirst = acc.getFirstSampleTime();
		long timeLast = acc.getLastSampleTime();
		// Retrieve maximum absolute acceleration value
		double maxAbsAcc = acc.getMaxAbsoluteAccelerationValue();
		// Find out how high and wide a graph can be
		double graphWidth = fieldSize.getWidth();
		double graphHeight = fieldSize.getHeight();
		// Calculate the vertical scaling factor
		double verticalScalingFactor = (0.5 * graphHeight) / maxAbsAcc;
		// Calculate the horizontal scaling factor
		long gestureLength = timeLast - timeFirst;
		double horizontalScalingFactor = graphWidth / gestureLength;
		// Calculate the positions to draw
		List<Point> bufferX = new Vector<Point>();
		List<Point> bufferY = new Vector<Point>();
		List<Point> bufferZ = new Vector<Point>();
		Iterator<AccelerationSample> i = acc.getSamples().iterator();
		while (i.hasNext()) {
			AccelerationSample s = i.next();
			Point point = new Point();
			// X position
			double xPos = horizontalScalingFactor
					* (s.getTimeStamp() - timeFirst);
			double yPos = (graphHeight / 2)
					- (verticalScalingFactor * (s.getXAcceleration()));
			point.setLocation(xPos, yPos);
			bufferX.add(point);
			// Y position
			point = new Point();
			yPos = (graphHeight / 2)
					- (verticalScalingFactor * (s.getYAcceleration()));
			point.setLocation(xPos, yPos);
			bufferY.add(point);
			// Z position
			point = new Point();
			yPos = (graphHeight / 2)
					- (verticalScalingFactor * (s.getZAcceleration()));
			point.setLocation(xPos, yPos);
			bufferZ.add(point);
		}
		List<List<Point>> buffers = new Vector<List<Point>>();
		buffers.add(bufferX);
		buffers.add(bufferY);
		buffers.add(bufferZ);
		return buffers;
	}

	/**
	 * Draws Note plane in Rectangle field on Graphics g
	 * 
	 * @param plane
	 *            the Note that should be drawn
	 * @param field
	 *            The rectangle in which the Note should be drawn
	 * @param title
	 *            The title of the plane
	 * @param g
	 *            The Graphics object onto which the Note should be drawn
	 */
	private static void drawPlane(Note plane, Rectangle field, Graphics g) {
		if (plane != null) {
			g.setColor(Color.BLACK);
			// Draw all traces from plane on g
			for (Trace trace : plane.getTraces()) {
				drawTrace(trace, 10, field, g);
			}
		}
	}

	/**
	 * Draws Trace trace on Graphics g in Rectangle field, while maintaining
	 * margin from the sides of the rectangle
	 * 
	 * @param trace
	 *            The Trace that should be drawn
	 * @param margin
	 *            The margin to the sides of the field in pixels
	 * @param field
	 *            The Rectangle into which the trace should be drawn
	 * @param g
	 *            The Graphics object onto which the Trace should be drawn
	 */
	private static void drawTrace(Trace trace, int margin, Rectangle field, Graphics g) {
		// Scale trace to match the field size, taking margin at the sides into
		// account
		// Create a buffer containing all the points of the trace
		List<Point> buffer = new ArrayList<Point>();
		for (org.sigtec.ink.Point point : trace.getPoints()) {
			buffer.add(new Point((int) point.getX(), (int) point.getY()));
		}
		// Draw lines on g from point to point within the field
		for (int i = 0; i < buffer.size() - 1; i++) {
			g.drawLine((int) (buffer.get(i).getX() + field.getX()),
					(int) (buffer.get(i).getY() + field.getY()), (int) (buffer
							.get(i + 1).getX() + field.getX()), (int) (buffer
							.get(i + 1).getY() + field.getY()));
		}
		// Draw a small circle to mark the startpoint of the gesture
		if (buffer.size() > 0)
			g.fillOval((int) (buffer.get(0).getX() + +field.getX()),
					(int) (buffer.get(0).getY() + field.getY()), 5, 5);
	}

	/**
	 * Scales Traces to fit into the given Rectangles
	 * 
	 * @param traces
	 *            List of traces that should be scaled
	 * @param margin
	 *            The margin to the sides of the fields in pixels
	 * @param fields
	 *            List of Rectangles into which the traces should be scaled
	 * @return A list of scaled traces
	 */
	private static List<Trace> scaleTraces(List<Trace> traces, int margin,
			List<Rectangle> fields) {
		List<Trace> tracesNew = new Vector<Trace>();
		double scalingFactor = 0;
		// find the scaling factor to use for all traces
		double maxXUsed = 0;
		double maxYUsed = 0;
		double minXAvailable = 100000;
		double minYAvailable = 100000;
		for (int i = 0; i < traces.size(); i++) {
			// Take trace and corresponding rectangle from lists
			Trace trace = traces.get(i);
			Rectangle field = fields.get(i);
			// Find extremes
			double maxFoundX = 0;
			double minFoundX = 0;
			double maxFoundY = 0;
			double minFoundY = 0;
			for (int j = 0; j < trace.getPoints().size(); j++) {
				org.sigtec.ink.Point point = trace.getPoints().get(j);
				if (point.getX() > maxFoundX)
					maxFoundX = point.getX();
				if (point.getY() > maxFoundY)
					maxFoundY = point.getY();
				if (point.getX() < minFoundX)
					minFoundX = point.getX();
				if (point.getY() < minFoundY)
					minFoundY = point.getY();
			}
			double xSizeUsed = maxFoundX - minFoundX;
			double ySizeUsed = maxFoundY - minFoundY;

			if (xSizeUsed > maxXUsed)
				maxXUsed = xSizeUsed;
			if (ySizeUsed > maxYUsed)
				maxYUsed = ySizeUsed;

			// Define the playing field
			double minAvailableX = field.getX() + margin;
			double minAvailableY = field.getY() + margin;
			double maxAvailableX = (field.getWidth() + field.getX()) - margin;
			double maxAvailableY = (field.getHeight() + field.getY()) - margin;
			double xSizeAvailable = maxAvailableX - minAvailableX;
			double ySizeAvailable = maxAvailableY - minAvailableY;

			if (xSizeAvailable < minXAvailable)
				minXAvailable = xSizeAvailable;
			if (ySizeAvailable < minYAvailable)
				minYAvailable = ySizeAvailable;

		}
		// Calculate scaling factor
		scalingFactor = findScalingFactor(minXAvailable, maxXUsed,
				minYAvailable, maxYUsed);
		// Scale every Trace using the found scalingfactor
		for (int i = 0; i < traces.size(); i++) {
			// Take trace and corresponding rectangle from lists
			Trace trace = traces.get(i);

			// Find extremes
			double maxFoundX = 0;
			double minFoundX = 0;
			double maxFoundY = 0;
			double minFoundY = 0;
			for (int j = 0; j < trace.getPoints().size(); j++) {
				org.sigtec.ink.Point point = trace.getPoints().get(j);
				if (point.getX() > maxFoundX)
					maxFoundX = point.getX();
				if (point.getY() > maxFoundY)
					maxFoundY = point.getY();
				if (point.getX() < minFoundX)
					minFoundX = point.getX();
				if (point.getY() < minFoundY)
					minFoundY = point.getY();
			}

			// Move every point inside the playing field
			Trace traceNew = new Trace();
			for (int k = 0; k < trace.getPoints().size(); k++) {
				double xNew = scalingFactor
						* (trace.getPoints().get(k).getX() - minFoundX)
						+ margin;
				double yNew = scalingFactor
						* (trace.getPoints().get(k).getY() - minFoundY)
						+ margin;
				traceNew.add(new org.sigtec.ink.Point(xNew, yNew));
			}
			tracesNew.add(traceNew);
		}
		return tracesNew;
	}

	/**
	 * Calculates scaling factor
	 * 
	 * @param xSizeAvailable
	 *            Available size in x direction in pixels
	 * @param xSizeUsed
	 *            Used size in x direction in source data
	 * @param ySizeAvailable
	 *            Available size in y direction in pixels
	 * @param ySizeUsed
	 *            Used size in y direction in source data
	 * @return The scaling factor
	 */
	private static double findScalingFactor(double xSizeAvailable, double xSizeUsed,
			double ySizeAvailable, double ySizeUsed) {
		double scalingFactor = xSizeAvailable / xSizeUsed;
		if ((ySizeAvailable / ySizeUsed) < scalingFactor) // take the smallest
			// factor so it will
			// definately fit
			scalingFactor = ySizeAvailable / ySizeUsed;
		return scalingFactor;
	}
	
	/**
	 * Draws a graph in color from data into rectangle on g
	 * 
	 * @param data
	 *            The source data for the graph
	 * @param field
	 *            The Rectangle into which the graph should be drawn
	 * @param axisColor
	 *            The color of the axis
	 * @param dataColor
	 *            The color of the data series in the graph
	 * @param title
	 *            The title of the graph
	 * @param g
	 *            The Graphics object onto which the graph should be drawn
	 */
	private static void drawGraph(List<Point> data, Rectangle field, Color axisColor,
			Color dataColor, String title, Graphics g) {
		//System.err.println("Field: " + field.getX() + "," + field.getY() + " dimensions " + field.getWidth() + "," + field.getHeight());
		// Save original color
		Color originalColor = g.getColor();
		// Draw axes
		g.setColor(axisColor);
		g.drawLine((int) field.getX(), (int) (field.getY() + (0.5 * field
				.getHeight())), (int) (field.getX() + field.getWidth()),
				(int) (field.getY() + (0.5 * field.getHeight())));
		g.drawLine((int) field.getX(), (int) field.getY(), (int) field.getX(),
				(int) (field.getY() + field.getHeight()));
		// g.drawRect((int)field.getX(), (int)field.getY(),
		// (int)field.getWidth(), (int)field.getHeight());
		// Set data color
		g.setColor(dataColor);
		// Draw title
		Font originalFont = g.getFont();
		Font font = new Font("Arial", Font.PLAIN, 10);
		g.setFont(font);
		g.drawString(title, (int) (field.getX() + (0.5 * field.getWidth())),
				(int) (field.getY() + 15));
		g.setFont(originalFont);
		// Draw data
		Point lastPoint = new Point();
		if (data.size() > 0)
			lastPoint.setLocation((int) data.get(0).getX(), (int) data.get(0)
					.getY()); // Startpoint
		Iterator<Point> it = data.iterator();
		while (it.hasNext()) {
			Point p = it.next();
			int oldX = (int) (field.getX() + lastPoint.getX());
			int oldY = (int) (field.getY() + lastPoint.getY());
			int newX = (int) (field.getX() + p.getX());
			int newY = (int) (field.getY() + p.getY());
			g.drawLine(oldX, oldY, newX, newY);
			lastPoint = p;
			//System.err.println("old: (" + oldX + "," + oldY + ") , new: " + newX + "," + newY + ")");
		}
		// Set color back to original
		g.setColor(originalColor);
	}
  
	/**
	 * Splits RecordedGesture3D gesture into three 2D planes. The XY-Plane is
	 * defined as the plane in 3D space where z=0. The YZ-Plane is defined as
	 * the plane in 3D space where x=0. The ZX-Plane is defined as the plane in
	 * 3D space where y=0.
	 * @param gs 
	 * 
	 * @return The list of 2D gestures that are the planes. First XY, then YZ
	 *         and then ZX.
	 */
	public static List<Gesture<Note>> splitToPlanes(RecordedGesture3D gs) {//comes from GestureSample3D
		Iterator<Point3D> iterator = gs.iterator(); // Iterator on the
		// list of
		// Point3D in
		// gesture
		Trace traceXY = new Trace(); // X plane Trace
		Trace traceYZ = new Trace(); // Y plane Trace
		Trace traceZX = new Trace(); // Z plane Trace
		Point3D point3d; // Working variable
		// Project all 3d points in gesture on planes
		while (iterator.hasNext()) {
			point3d = iterator.next();
			// Add points to 2d traces
			traceXY.add(new org.sigtec.ink.Point(point3d.getX(), point3d.getY(), point3d.getTimeStamp()));
			traceYZ.add(new org.sigtec.ink.Point(point3d.getY(), point3d.getZ(), point3d.getTimeStamp()));
			traceZX.add(new org.sigtec.ink.Point(point3d.getZ(), point3d.getX(), point3d.getTimeStamp()));
		}
		// Put traces into Notes
		Note noteXY = new Note(); // X plane Note
		Note noteYZ = new Note(); // Y plane Note
		Note noteZX = new Note(); // Z plane Note
		// Add planes to returnlist
		noteXY.add(traceXY);
		noteYZ.add(traceYZ);
		noteZX.add(traceZX);
		List<Gesture<Note>> returnList = new ArrayList<Gesture<Note>>(); // Return
		// variable
		returnList.add(new GestureSample("XY-plane", noteXY));
		returnList.add(new GestureSample("YZ-plane", noteYZ));
		returnList.add(new GestureSample("ZX-plane", noteZX));
		// Return list with three planes
		return returnList;
	}
}
