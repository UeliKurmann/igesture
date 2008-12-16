package org.ximtec.igesture.io.wiimote;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Point;

import org.ximtec.igesture.io.mouseclient.SwingMouseReader;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		WiiReader reader = new WiiReader();
		
		//FRAME				
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("WiiMote Test Frame");
		frame.setVisible(true);		

		//PANEL
		Dimension dimension = new Dimension(frame.getHeight(), frame.getWidth());
		JPanel panel = reader.getPanel(dimension);
		panel.setSize(250, 250);
		panel.setVisible(true);
				
		//ADD PANEL TO FRAME
		frame.getContentPane().add(panel);	
		
		reader.init();
	
		System.out.println("End of test main.");
	
	}

}
