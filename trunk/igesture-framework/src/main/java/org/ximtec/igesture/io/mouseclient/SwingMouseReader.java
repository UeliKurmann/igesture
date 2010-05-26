/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 03.05.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.io.mouseclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.sigtec.ink.Note;
import org.sigtec.ink.Trace;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.io.AbstractGestureDevice;

/**
 * Comment
 * 
 * @version 1.0 03.05.2008
 * @author Ueli Kurmann
 */
public class SwingMouseReader extends AbstractGestureDevice<Note, Point> {

  private static final Logger LOGGER = Logger.getLogger(SwingMouseReader.class.getName());

  private org.sigtec.ink.Point translation;
  private SwingMouseReaderPanel currentPanel;

  private Note note;
  private Trace trace;
  private boolean lastKeyState = false;

  private Point lastPoint;

  public SwingMouseReader() {
	  
	  //MODIFY >
	  setName("SwingMouseReader");
	  setDeviceType(org.ximtec.igesture.util.Constant.TYPE_2D);
	  setDeviceID("System Mouse");
	  setConnectionType(org.ximtec.igesture.util.Constant.CONNECTION_USB);
	  setIsConnected(true);
	  setDefaultDevice(true);
	  //MODIFY <
  }
  
  public JPanel getPanel()
  {
	  return getPanel(new Dimension(200,200));
  }

  public JPanel getPanel(Dimension dimension) {

    SwingMouseReaderPanel panel = new SwingMouseReaderPanel(this);
    panel.setSize(dimension);
    panel.setPreferredSize(dimension);
    panel.setOpaque(true);
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

    panel.addMouseListener(new SwingMouseListener(panel));

    return panel;
  }

  public void clear() {
    note = new Note();
    trace = new Trace();
    if (currentPanel != null) {
      currentPanel.clear();
    }
  }

  private class SwingMouseListener extends MouseAdapter {

    SwingMouseReaderPanel owner;

    private SwingMouseListener(SwingMouseReaderPanel panel) {
      this.owner = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
      super.mousePressed(e);
      LOGGER.info("Mouse pressed...");
      currentPanel = owner;
      Point p1 = e.getPoint();
      Point p2 = MouseInfo.getPointerInfo().getLocation();
      long timestamp = System.currentTimeMillis();
      translation = new org.sigtec.ink.Point((int) (p1.getX() - p2.getX()), (int) (p1.getY() - p2.getY()), timestamp);

      lastKeyState = true;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
      super.mouseReleased(e);
      if (lastKeyState) {
        note.add(trace);
        trace = new Trace();

        lastKeyState = false;
        lastPoint = null;
        fireGestureEvent(getGesture());
      }
    }
  }

  @Override
  public void dispose() {
    removeAllListener();
    clear();

  }

  @Override
  public List<Point> getChunks() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Gesture<Note> getGesture() {
    return new GestureSample(this,Constant.EMPTY_STRING, note);
  }

  @Override
  public void init() {
    note = new Note();
    trace = new Trace();

    Executors.newCachedThreadPool().execute(new Worker());
  }

  private class Worker implements Runnable {

    @Override
    public void run() {
      while (true) {
        if (lastKeyState) {
          PointerInfo info = MouseInfo.getPointerInfo();
          Point currentPoint = info.getLocation();

          currentPoint.move((int) (currentPoint.getX() + translation.getX()), (int) (currentPoint.getY() + translation
              .getY()));

          trace.add(new org.sigtec.ink.Point(currentPoint.getX(), currentPoint.getY(), System.currentTimeMillis()));
          if (currentPanel != null && lastPoint != null) {
            currentPanel.drawLine(lastPoint, currentPoint);

          }

          lastPoint = currentPoint;
        }

        try {
          Thread.sleep(1000 / 100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  //MODIFY >
  
	@Override
	public void connect() {
		setIsConnected(true);
		LOGGER.log(Level.WARNING, "Still to implement");
	}
	
	@Override
	public void disconnect() {
		setIsConnected(false);
		LOGGER.log(Level.WARNING, "Still to implement");
	}

	@Override
	public boolean isConnectable() {
		return false;
	}
   	
   	@Override
	public boolean isDisconnectable() {
		return false;
	}
}
