/*
 * @(#)GestureCaptureArea.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Captures a gesture
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.frame.capture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;

import org.sigtec.graphix.widget.BasicInternalFrame;
import org.sigtec.ink.Note;
import org.sigtec.ink.TraceTool;
import org.sigtec.ink.input.TimestampedLocation;
import org.sigtec.input.util.CaptureTool;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.event.CurrentGestureListener;
import org.ximtec.igesture.tool.frame.capture.action.ActionCaptureGesture;
import org.ximtec.igesture.tool.frame.capture.action.ActionCaptureGestureClear;
import org.ximtec.igesture.tool.utils.JNote;
import org.ximtec.igesture.tool.utils.SwingTool;

public class GestureCaptureArea extends BasicInternalFrame implements
		CurrentGestureListener {

	private GestureToolView mainView;

	private JNote image;

	/**
	 * This is the default constructor
	 */
	public GestureCaptureArea(GestureToolView mainView) {
		super(GestureConstants.DRAW_AREA_KEY, SwingTool.getGuiBundle());
		SwingTool.initFrame(this);

		this.mainView = mainView;
		mainView.getModel().addCurrentGestureListener(this);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		image = new JNote(200, 200);
		mainView.getModel().getPenClient().addInputHandler(image);

		this.addComponent(image, SwingTool.createGridBagConstraint(0, 0,2,1));

		
		
		this.addComponent(SwingTool
				.createButton(new ActionCaptureGestureClear(this)), SwingTool
				.createGridBagConstraint(0, 1));
		
		this.addComponent(SwingTool
				.createButton(new ActionCaptureGesture(this)), SwingTool
				.createGridBagConstraint(1, 1));

		if (mainView.getModel().getCurrentNote() != null) {
			drawCurrentGesture();
		}

		this.repaint();
	}

	public void updateCurrentGesture() {
		try {
			final InputDeviceClient client = mainView.getModel().getPenClient();
			final Collection<TimestampedLocation> locations = client
					.getTimestampedLocations(0, System.currentTimeMillis());

			client.clearBuffer();

			final Note note = new Note(TraceTool.detectTraces(CaptureTool
					.toPoints(new ArrayList<TimestampedLocation>(locations)),
					150));

			note.moveTo(0, 0);
			note.scaleTo(200, 200);

			mainView.getModel().setCurrentNote(note);
		} catch (final Exception e) {
			e.printStackTrace();
			mainView.getModel().setCurrentNote(new Note());
		}

	}

	public void clearCurrentGesture() {
		final InputDeviceClient client = mainView.getModel().getPenClient();
		client.clearBuffer();
		mainView.getModel().setCurrentNote(new Note());
		image.clear();
		this.repaint();
	}

	private void drawCurrentGesture() {
		image.freeze();
		this.repaint();
	}

	public void currentGestureChanged(EventObject event) {
		drawCurrentGesture();
	}

}
