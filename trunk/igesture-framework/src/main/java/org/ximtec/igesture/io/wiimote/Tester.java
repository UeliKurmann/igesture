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
		//panel.add(new JButton("TEST"));
		panel.setVisible(true);
				
		//ADD PANEL TO FRAME
		frame.getContentPane().add(panel);	
		
		
		reader.init();
		
		//DRAW		
		//panel.getGraphics().setColor(Color.RED);
		//panel.getGraphics().drawLine(2, 2, 50, 50);
		
		
		//System.err.println("Panel Parent: " + panel.getParent().getClass() + ", Graphics: " + panel.getParent().getGraphics());
		//panel.repaint();
		//panel.getGraphics().drawLine(2, 2, 350, 350);
		
	
		//System.err.println("G: " + panel.getGraphics());
		//panel.getGraphics().drawLine(0, 0, 50, 50);
		//Point last = new Point(35,30);
		//Point current = new Point(10,10);
		//panel.drawLine(last, current);//.drawLine(new Point(10,10), new Point(30,30));
		
		//panel.redrawGesture();
		
		//reader.init();
		
		//WiiReader reader = new WiiReader();
		//reader.init();
		//Dimension dimension = new Dimension(frame.getHeight(), frame.getWidth());
		//panel = (WiiReaderPanel) reader.getPanel(dimension);		
		
		
		
		
		System.out.println("End of test main.");
	
	}

}
