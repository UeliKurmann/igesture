package org.ximtec.igesture.io.wiimote;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.storage.StorageManager;

public class Tester {

	;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		// The WiiReader to read from the WiiMote
		WiiReader reader = new WiiReader();
		// Storagemanager to keep database
		StorageManager storage = new StorageManager(StorageManager
				.createStorageEngine(new File("C:\\ali2.db")));

		// Create UI Frame
		JFrame frame = new TestUI(reader.getPanel());
		frame.setVisible(true);

		
		
		// Load list of gesturesets from database
		List<GestureSet> gestureSets = storage.load(GestureSet.class);
		
		
		
		
		// PANEL
		//Dimension dimension = new Dimension(150, 150);
		//JPanel panel = reader.getPanel(dimension);
		// panel.setSize(250, 250);
		//panel.setBounds(200, 10, 180, 180);
		//panel.setVisible(true);

		// ADD PANEL TO FRAME
		// frame.getContentPane().add(panel);
		reader.init();

		System.out.println("End of test main.");

	}

}
