/*
 * @(#)GestureKeyboard.java   1.0   March 09, 2007
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 09.03.2007       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.igesture.app.keyboard;

import java.io.File;

import org.sigtec.ink.Note;
import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.input.InputDeviceEventListener;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.util.XMLTools;
import static org.ximtec.igesture.io.Win32KeyboardProxy.*;

public class GestureKeyboard implements ButtonDeviceEventListener{

	private static final String RUBINE_CONFIGURATION = "rubineconfiguration.xml";

	private static final String GESTURE_SET = "gestureSets/ms_application_gestures.xml";

	private Recogniser recogniser;

	private InputDeviceClient client;

	private EventManager eventManager;

	public GestureKeyboard() throws AlgorithmException {
		initEventManager();
		initRecogniser();
		initDevice();
		
		System.out.println("initialised...");
	}

	private void initDevice() {
		InputDevice device = new MouseReader();
		InputDeviceEventListener listener = new BufferedInputDeviceEventListener(
				new MouseReaderEventListener(), 10000);

		client = new InputDeviceClient(device, listener);
		client.addButtonDeviceEventListener(this);
	}

	private void initRecogniser() throws AlgorithmException {
		Configuration configuration = XMLTools.importConfiguration(new File(
				ClassLoader.getSystemResource(RUBINE_CONFIGURATION).getFile()));

		GestureSet gestureSet = XMLTools.importGestureSet(
				new File(ClassLoader.getSystemResource(GESTURE_SET).getFile()))
				.get(0);

		configuration.addGestureSet(gestureSet);
		configuration.setEventManager(eventManager);

		recogniser = new Recogniser(configuration);
	}

	private void initEventManager() {
		
		eventManager = new EventManager();
		eventManager.registerEventHandler("Left", new PressKeystroke(new Integer[]{ALT,LEFT}));
		eventManager.registerEventHandler("Right", new PressKeystroke(new Integer[]{ALT,RIGHT}));
		eventManager.registerEventHandler("Curlicue", new PressKeystroke(new Integer[]{ALT,F4}));
		eventManager.registerEventHandler("Up-right", new PressKeystroke(new Integer[]{CONTROL,C}));
		eventManager.registerEventHandler("Up-left", new PressKeystroke(new Integer[]{CONTROL,V}));

	}

	public static void main(String[] args) throws AlgorithmException {
		new GestureKeyboard();
	}

	public void handleButtonPressedEvent(InputDeviceEvent event) {
		Note note = client.createNote(0, event.getTimestamp(), 70);
		if(note.getPoints().size() > 5){
			recogniser.recognise(note);	
		}
	}

}
