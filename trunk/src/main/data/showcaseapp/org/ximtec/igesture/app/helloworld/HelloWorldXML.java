package org.ximtec.igesture.app.helloworld;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.ResultSet;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.tool.GestureConfiguration;
import org.ximtec.igesture.util.XMLTools;
import org.ximtec.ipaper.io.InputDevice;
import org.ximtec.ipaper.io.InputDeviceEvent;

public class HelloWorldXML implements ButtonDeviceEventListener {

	private Recogniser recogniser;

	private InputDeviceClient client;

	public HelloWorldXML() throws AlgorithmException {
		GestureConfiguration appConfig = new GestureConfiguration("config.xml");
		Configuration configuration = XMLTools.importConfiguration(new File(
				this.getClass().getClassLoader().getResource(
						"configuration.xml").getFile()));

		recogniser = new Recogniser(configuration);

		List<InputDevice> pens = new ArrayList<InputDevice>();
		pens.add(new MouseReader());
		client = new InputDeviceClient(appConfig);

		client.addButtonDeviceEventListener(this);

		System.out.println("initialised...");
	}

	public static void main(String[] args) throws AlgorithmException {
		new HelloWorldXML();
	}

	public void handleButtonPressedEvent(InputDeviceEvent event) {
		ResultSet result = recogniser.recognise(client.createNote(0, event
				.getTimestamp(), 70));
		client.clearBuffer();
		if (result.isEmpty()) {
			System.out.println("not recognised");
		} else {
			System.out.println(result.getResult().getName());
		}
	}
}
