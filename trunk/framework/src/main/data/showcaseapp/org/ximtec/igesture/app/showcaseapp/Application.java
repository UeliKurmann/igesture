package org.ximtec.igesture.app.showcaseapp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.sigtec.ink.Note;
import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.algorithm.AlgorithmException;
import org.ximtec.igesture.app.showcaseapp.descriptor.ArrowDescriptor;
import org.ximtec.igesture.app.showcaseapp.descriptor.LineDescriptor;
import org.ximtec.igesture.app.showcaseapp.descriptor.RectangleDescriptor;
import org.ximtec.igesture.app.showcaseapp.descriptor.TriangleDescriptor;
import org.ximtec.igesture.app.showcaseapp.eventhandler.DeleteEventHandler;
import org.ximtec.igesture.app.showcaseapp.eventhandler.DrawEventHandler;
import org.ximtec.igesture.app.showcaseapp.eventhandler.RejectEventHandler;
import org.ximtec.igesture.app.showcaseapp.eventhandler.StyleEventHandler;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.DigitalDescriptor;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.event.EventManager;
import org.ximtec.igesture.io.ButtonDeviceEventListener;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MagicommPenSocketModified;
import org.ximtec.igesture.tool.GestureConfiguration;
import org.ximtec.igesture.util.XMLTools;
import org.ximtec.ipaper.io.InputDevice;
import org.ximtec.ipaper.io.InputDeviceEvent;

public class Application implements ButtonDeviceEventListener {

	private Recogniser recogniser;

	private InputDeviceClient client;

	private JFrame frame;

	private BufferedImage bufferedImage;

	public Application() {
		initialiseGUI();
		
		
		
		initGestures();
		
		
	}

	private void initGestures() {
		GestureConfiguration appConfig = new GestureConfiguration("config.xml");
		Configuration configuration = XMLTools.importConfiguration(new File(
				ClassLoader.getSystemResource(
						"rubineconfiguration.xml").getFile()));

		GestureSet gestureSet = XMLTools.importGestureSet(
				new File(ClassLoader.getSystemResource(
						"demogestures.xml").getFile())).get(0);

		EventManager eventManager = new EventManager();
		eventManager.registerRejectEvent(new RejectEventHandler());

		Style style = new Style();
		StyleEventHandler styleEventHandler = new StyleEventHandler(style);

		Graphics2D graphic = (Graphics2D) bufferedImage.getGraphics();

		DrawEventHandler drawEventHandler = new DrawEventHandler(graphic, style);

		gestureSet.getGestureClass("Rectangle").addDescriptor(
				DigitalDescriptor.class, new RectangleDescriptor());
		gestureSet.getGestureClass("LeftRight").addDescriptor(
				DigitalDescriptor.class, new LineDescriptor());
		gestureSet.getGestureClass("Triangle").addDescriptor(
				DigitalDescriptor.class, new TriangleDescriptor());
		gestureSet.getGestureClass("Arrow").addDescriptor(
				DigitalDescriptor.class, new ArrowDescriptor());

		eventManager.registerEventHandler("Rectangle", drawEventHandler);
		eventManager.registerEventHandler("LeftRight", drawEventHandler);
		eventManager.registerEventHandler("Triangle", drawEventHandler);
		eventManager.registerEventHandler("Arrow", drawEventHandler);
		eventManager.registerEventHandler("Delete", new DeleteEventHandler(graphic));
		eventManager.registerEventHandler("Red", styleEventHandler);
		eventManager.registerEventHandler("Black", styleEventHandler);
		eventManager.registerEventHandler("Yellow", styleEventHandler);
		eventManager.registerEventHandler("Thin", styleEventHandler);
		eventManager.registerEventHandler("Fat", styleEventHandler);

		configuration.addGestureSet(gestureSet);
		configuration.setEventManager(eventManager);

		try {
			recogniser = new Recogniser(configuration);
		} catch (AlgorithmException e) {
			e.printStackTrace();
		}
		
		client = new InputDeviceClient(appConfig);
		client.addButtonDeviceEventListener(this);
	}

	private void initialiseGUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(440, 620);

		bufferedImage = new BufferedImage(420, 600, BufferedImage.TYPE_INT_ARGB);
		bufferedImage.getGraphics().setColor(Color.WHITE);
		bufferedImage.getGraphics().fillRect(0, 0, 420, 600);
		bufferedImage.getGraphics().setColor(Color.BLACK);

		JLabel label = new JLabel();
		label.setSize(420, 620);
		label.setIcon(new ImageIcon(bufferedImage));
		frame.add(label);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Application();
	}

	public void handleButtonPressedEvent(InputDeviceEvent event) {
		Note note = client.createNote(0, event.getTimestamp(), 70);

		Note clone = (Note) note.clone();
		clone.scale(2);

		recogniser.recognise(clone);
		frame.repaint();
	}

}
