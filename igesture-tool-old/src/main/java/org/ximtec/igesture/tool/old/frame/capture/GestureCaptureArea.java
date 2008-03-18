/*
 * @(#)GestureCaptureArea.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Captures a gesture.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.old.frame.capture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.sigtec.ink.Note;
import org.sigtec.ink.TraceTool;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.util.CaptureTool;
import org.sigtec.util.Constant;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.GestureCaptureArea;
import org.ximtec.igesture.tool.old.GestureConstants;
import org.ximtec.igesture.tool.old.GestureToolMain;
import org.ximtec.igesture.tool.old.GestureToolView;
import org.ximtec.igesture.tool.old.event.CurrentGestureListener;
import org.ximtec.igesture.tool.old.frame.capture.action.CaptureGestureAction;
import org.ximtec.igesture.tool.old.frame.capture.action.CaptureGestureClearAction;
import org.ximtec.igesture.tool.old.util.JNote;


/**
 * Captures a gesture.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureCaptureArea extends BasicInternalFrame implements
      CurrentGestureListener {

   private static final Logger LOGGER = Logger
         .getLogger(GestureCaptureArea.class.getName());

   private GestureToolView mainView;

   private JNote image;


   /**
    * Constructs a new gesture capture area.
    * 
    * @param mainView the main view.
    */
   public GestureCaptureArea(GestureToolView mainView) {
      super(GestureConstants.DRAW_AREA_KEY, GestureToolMain.getGuiBundle());
      SwingTool.initFrame(this);
      this.mainView = mainView;
      mainView.getModel().addCurrentGestureListener(this);
      init();
   }


   /**
    * Initialises the gesture capture area.
    */
   private void init() {
      image = new JNote(200, 200);
      mainView.getModel().getPenClient().addInputHandler(image);
      addComponent(image, SwingTool.createGridBagConstraint(0, 0, 2, 1));
      addComponent(GuiTool.createButton(new CaptureGestureClearAction(this)),
            SwingTool.createGridBagConstraint(0, 1));
      addComponent(GuiTool.createButton(new CaptureGestureAction(this)),
            SwingTool.createGridBagConstraint(1, 1));

      if (mainView.getModel().getCurrentNote() != null) {
         drawCurrentGesture();
      }

      this.repaint();
   } // init


   public void updateCurrentGesture() {
      try {
         final InputDeviceClient client = mainView.getModel().getPenClient();
         final Collection<TimestampedLocation> locations = client
               .getTimestampedLocations(0, System.currentTimeMillis());
         client.clearBuffer();
         final Note note = new Note(TraceTool.detectTraces(CaptureTool
               .toPoints(new ArrayList<TimestampedLocation>(locations)), 150));
         note.moveTo(0, 0);
         note.scaleTo(200, 200);
         mainView.getModel().setCurrentNote(note);
      }
      catch (final Exception e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
         mainView.getModel().setCurrentNote(new Note());
      }

   } // updateCurrentGesture


   public void clearCurrentGesture() {
      final InputDeviceClient client = mainView.getModel().getPenClient();
      client.clearBuffer();
      mainView.getModel().setCurrentNote(new Note());
      image.clear();
      this.repaint();
   } // clearCurrentGesture


   private void drawCurrentGesture() {
      image.freeze();
      this.repaint();
   } // drawCurrentGesture


   public void currentGestureChanged(EventObject event) {
      drawCurrentGesture();
   } // currentGestureChanged

}
