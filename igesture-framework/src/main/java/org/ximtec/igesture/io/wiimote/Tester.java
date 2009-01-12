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
		TestUI ui = new TestUI(controller);
		ui.setVisible(true);
		
		controller.setUI(ui);
		
		System.out.println("End of test main.");

	}

}
