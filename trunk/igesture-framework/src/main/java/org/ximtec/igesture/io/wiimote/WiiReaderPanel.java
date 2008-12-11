package org.ximtec.igesture.io.wiimote;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;

public class WiiReaderPanel extends JPanel {

	private WiiReader reader;

	public WiiReaderPanel(){
		
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param reader
	 */
	public WiiReaderPanel(WiiReader reader) {
		this.reader = reader;
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	/**
	 * Draws a line on the panel from lastPoint to currentPoint
	 * 
	 * @param lastPoint
	 * @param currentPoint
	 */
	public void drawLine(Point lastPoint, Point currentPoint) {
		
		//System.err.println("drawLine()");
		//System.err.println("Graphics: " + getGraphics());
		System.err.println("PARENT = " + getParent() + ", GRAPHICS = " + getParent().getGraphics());
		
		if (getGraphics() != null && currentPoint != null && lastPoint != null
				&& lastPoint.distance(currentPoint) < 1000000) {
			getGraphics().drawLine((int) lastPoint.getX(),
					(int) lastPoint.getY(), (int) currentPoint.getX(),
					(int) currentPoint.getY());
		}
	}

	/**
	 * Clears the panel
	 */
	public void clear() {
		if (getGraphics() != null) {
			getGraphics().clearRect(0, 0, getWidth(), getHeight());
		}

		repaint();
	}

	public void paintComponent(Graphics g){
		System.out.println("PAINTING COMPONENT");

		g.clearRect(0,0,getWidth(),getHeight());
		
		if (reader != null) {
						
			//Get gesture to draw
			GestureSample3D gs = (GestureSample3D)reader.getGesture();
			//Split the gesture to three planes
			List<Gesture<Note>> notes = gs.splitToPlanes();
			//Get the XY plane gesture
			Gesture<Note> gestXY = notes.get(1);
			Note noteXY = gestXY.getGesture();

			
			//Note noteXY = (Note)((GestureSample3D)reader.getGesture()).splitToPlanes().get(0).getGesture(); 
			
			//For this moment we only draw the XY plane
			if (noteXY != null) {
				
				
				List<Point> buffer = new ArrayList<Point>();
				for (Trace trace : noteXY.getTraces()) {
					trace = scaleTrace(trace);
					for (org.sigtec.ink.Point point : trace.getPoints()) {
												
						//System.err.println("point: " + point.getX() + "," + point.getY() + " Time: " + point.getTimestamp());
						
						//g.drawLine(100, 100, (int)point.getX(), (int)point.getY());
						
						buffer.add(new Point((int) point.getX(), (int) point
								.getY()));
					}
				}

				for (int i = 0; i < buffer.size() - 1; i++) {
					
					//g.drawLine((int)(this.getWidth() * 0.5),(int)(this.getHeight() * 0.5), (int)buffer.get(i + 1).getX(),(int)buffer.get(i + 1).getY());
					
					g.drawLine((int)buffer.get(i).getX(),(int)buffer.get(i).getY(), (int)buffer.get(i + 1).getX(),(int)buffer.get(i + 1).getY());
				}
			}
		}
		
		
		
	}
	
	/**
	 * Scales a trace to match the size of this panel
	 * 
	 * @param trace
	 */
	private Trace scaleTrace(Trace trace) {
		//Define margins at the sides
		int margin = 10;		
		//Find extremes
		double maxFoundX = 0;
		double minFoundX = 0;
		double maxFoundY = 0;
		double minFoundY = 0;
		for(int i = 0; i < trace.getPoints().size(); i++){
			org.sigtec.ink.Point point = trace.getPoints().get(i);
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
		//Define the playing field
		double minAvailableX = 0 + margin;
		double minAvailableY = 0 + margin;
		double maxAvailableX = getWidth() - margin;
		double maxAvailableY = getHeight() - margin;
		double xSizeAvailable = maxAvailableX - minAvailableX;
		double ySizeAvailable = maxAvailableY - minAvailableY;
		//Calculate scaling factor
		double scalingFactor = xSizeAvailable / xSizeUsed;
		if((ySizeAvailable / ySizeUsed) < scalingFactor) //take the smallest factor so it will definately fit
			scalingFactor = ySizeAvailable / ySizeUsed;
		//Move every point inside the playing field
		List<org.sigtec.ink.Point> pointsNew = new Vector();
		Trace traceNew = new Trace();
		for(int i =0; i<trace.getPoints().size(); i++){
			double xNew = scalingFactor * (trace.getPoints().get(i).getX() - minFoundX);
			double yNew = scalingFactor * (trace.getPoints().get(i).getY() - minFoundY);
			traceNew.add(new org.sigtec.ink.Point(xNew, yNew));
		}
		return traceNew;
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//redrawGesture();
	}

	@Override
	public void repaint() {
		System.err.println("Repaint()");
		super.repaint();
		//redrawGesture();
	}

	public void redrawGesture() {
		System.err.println("redrawGesture()");
		System.err.println("Graphics: " + getGraphics());
		if (reader != null) {
			//Get gesture to draw
			GestureSample3D gs = (GestureSample3D)reader.getGesture();
			//Split the gesture to three planes
			List<Gesture<Note>> notes = gs.splitToPlanes();
			//Get the XY plane gesture
			Gesture<Note> gestXY = notes.get(2);
			Note noteXY = gestXY.getGesture();

			
			//Note noteXY = (Note)((GestureSample3D)reader.getGesture()).splitToPlanes().get(0).getGesture(); 
			
			//For this moment we only draw the XY plane
			if (noteXY != null) {
				
				List<Point> buffer = new ArrayList<Point>();
				for (Trace trace : noteXY.getTraces()) {
					for (org.sigtec.ink.Point point : trace.getPoints()) {
						buffer.add(new Point((int) point.getX(), (int) point
								.getY()));
					}
				}

				for (int i = 0; i < buffer.size() - 1; i++) {
					drawLine(buffer.get(i), buffer.get(i + 1));
				}
			}
		}
	}

}
