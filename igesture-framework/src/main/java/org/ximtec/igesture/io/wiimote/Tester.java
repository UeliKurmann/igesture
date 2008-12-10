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

		WiiReaderPanel panel;
				
		JFrame frame = new JFrame();
		frame.setSize(300, 300);
		frame.setTitle("WiiMote Test Frame");
		frame.setVisible(true);
		
		
		WiiReader reader = new WiiReader();
		
		//SwingMouseReader reader = new SwingMouseReader();
		//reader.init();
		
		Dimension dimension = new Dimension(100,100);
		panel = (WiiReaderPanel) reader.getPanel(dimension);

		frame.getContentPane().add(panel);	
		
		panel.setVisible(true);

		System.err.println("G: " + panel.getGraphics());
		//panel.getGraphics().drawLine(0, 0, 50, 50);
		Point last = new Point(30,30);
		Point current = new Point(10,10);
		panel.drawLine(last, current);//.drawLine(new Point(10,10), new Point(30,30));
		
		reader.init();
		
		System.out.println("End of test main.");
		
	
	}

}
