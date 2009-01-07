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
		

		//Create controller
		TestController controller = new TestController();
		controller.initWiiMote();
		
		// Create UI Frame
		JFrame frame = new TestUI(controller);
		frame.setVisible(true);
		
		System.out.println("End of test main.");

	}

}
