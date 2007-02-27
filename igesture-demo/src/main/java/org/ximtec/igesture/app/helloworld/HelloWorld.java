package org.ximtec.igesture.app.helloworld;

import org.sigtec.input.BufferedInputDeviceEventListener;
import org.sigtec.input.InputDevice;
import org.sigtec.input.InputDeviceEvent;
import org.sigtec.input.InputDeviceEventListener;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.algorithm.siger.SigerRecogniser;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;

public class HelloWorld implements ButtonDeviceEventListener {

	private Recogniser recogniser;
	private InputDeviceClient client;

	public HelloWorld() throws AlgorithmException {

		GestureClass leftRightLine = new GestureClass("LeftRight");
		leftRightLine.addDescriptor(new TextDescriptor("E"));

		GestureClass downRight = new GestureClass("DownRight");
		downRight.addDescriptor(new TextDescriptor("S,E"));

		GestureClass upLeft = new GestureClass("UpLeft");
		upLeft.addDescriptor(new TextDescriptor("N,W"));

		GestureSet gestureSet = new GestureSet("GestureSet");
		gestureSet.addGestureClass(leftRightLine);
		gestureSet.addGestureClass(upLeft);
		gestureSet.addGestureClass(downRight);

		Configuration configuration = new Configuration();
		configuration.addGestureSet(gestureSet);
		configuration.addAlgorithm(SigerRecogniser.class.getName());

		recogniser = new Recogniser(configuration);

		InputDevice device = new MouseReader();
		InputDeviceEventListener listener = new BufferedInputDeviceEventListener(
				new MouseReaderEventListener(), 10000);

		client = new InputDeviceClient(device, listener);

		client.addButtonDeviceEventListener(this);

		System.out.println("initialised...");
	}

	public static void main(String[] args) throws AlgorithmException {
		new HelloWorld();
	}

	public void handleButtonPressedEvent(InputDeviceEvent event) {
		ResultSet result = recogniser.recognise(client.createNote(0, event
				.getTimestamp(), 70));
		client.clearBuffer();
		if (result.isEmpty()) {
			System.out.println("not recognised");
		} else {
			System.out.println(result.getResult().getGestureClassName());
		}
	}
}
