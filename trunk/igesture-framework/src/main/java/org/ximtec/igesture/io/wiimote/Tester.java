package org.ximtec.igesture.io.wiimote;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.sigtec.ink.Note;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.Sample3DDescriptor;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.storage.StorageManager;
import org.ximtec.igesture.util.RecordedGesture3D;



public class Tester {

	;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		

		// ******************GESTURE RECOGNITION TOOL PART******************
		
		//Create controller
		TestController controller = new TestController();
		
		// Create UI Frame
		TestUI ui = new TestUI(controller);
		ui.setVisible(true);
		
		controller.setUI(ui);
		
		
		// Indicate end of code
		System.out.println("End of test main.");
		
	}

}
