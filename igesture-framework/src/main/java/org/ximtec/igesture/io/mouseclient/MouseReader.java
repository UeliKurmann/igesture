/*
 * @(#)$Id$
 *
 * Author       :   Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose      :   A mouse reader.
 *                  
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 10, 2006		ukurmann	Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 * Jan 17, 2008     crocimi     added dependency from MouseCallback
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.mouseclient;

import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.ink.Note;
import org.sigtec.ink.Point;
import org.sigtec.ink.Trace;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.io.AbstractGestureDevice;
import org.ximtec.igesture.io.mouse.MouseHandler;
import org.ximtec.igesture.io.mouse.MouseUtils;
import org.ximtec.igesture.io.mouse.MouseUtils.MouseButton;


/**
 * A mouse reader.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class MouseReader extends AbstractGestureDevice<Note, Point> implements MouseHandler{

   private static final Logger LOGGER = Logger.getLogger(MouseReader.class.getName());
   
   private MouseUtils mouseUtils;

   private Note note;
   private Trace trace;
   private boolean lastKeyState = false;


   public MouseReader() {
     super();
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.mouse.MouseHandler#handleMouseEvent(int, int, java.util.EnumSet)
    */
   @Override
   public synchronized void handleMouseEvent(int x, int y,
         EnumSet<MouseButton> buttons) {

      if (buttons.contains(MouseButton.MIDDLE)) {
         LOGGER.log(Level.FINEST, "Mouse Event...");

         long timestamp = System.currentTimeMillis();
         Point point = new Point(x, y, timestamp);
         trace.add(point);

         lastKeyState = true;
      }
      else {

         if (lastKeyState) {
            note.add(trace);
            trace = new Trace();

            lastKeyState = false;
            fireGestureEvent(getGesture());
            clear();
         }
      }
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.GestureDevice#getChunks()
    */
   @Override
   public List<Point> getChunks() {
      return trace.getPoints();
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.GestureDevice#getGesture()
    */
   @Override
   public Gesture<Note> getGesture() {

      return new GestureSample(Constant.EMPTY_STRING, note);
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.GestureDevice#init()
    */
   @Override
   public void init() {
      mouseUtils = new MouseUtils(this);
      Executors.newSingleThreadExecutor().execute(mouseUtils);
      note = new Note();
      trace = new Trace();
   }


   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.GestureDevice#dispose()
    */
   @Override
   public void dispose() {
      removeAllListener();
      clear();
   }

   /*
    * (non-Javadoc)
    * @see org.ximtec.igesture.io.GestureDevice#clear()
    */
   @Override
   public void clear() {
      note = new Note();
      trace = new Trace();
   }

}