package org.ximtec.igesture.io.wiimote;

import javax.swing.JFrame;



public class Tester {

	;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		

		//Create controller
		TestController controller = new TestController();
		
		// Create UI Frame
		JFrame frame = new TestUI(controller);
		frame.setVisible(true);
		
		System.out.println("End of test main.");

	}

}
