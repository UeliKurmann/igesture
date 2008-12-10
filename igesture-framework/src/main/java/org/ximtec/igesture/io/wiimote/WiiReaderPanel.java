package org.ximtec.igesture.io.wiimote;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample3D;

public class WiiReaderPanel extends JPanel {

	private WiiReader reader;

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
		Graphics g = this.getGraphics();
		System.err.println("Graphics: " + g);
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

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		redrawGesture();
	}

	@Override
	public void repaint() {
		super.repaint();
		redrawGesture();
	}

	private void redrawGesture() {
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
