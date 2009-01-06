package org.ximtec.igesture.io.wiimote;

import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;

public class TestUI extends JFrame {

	JLabel gestureSetLabel;
	JLabel gestureClassLabel;
	JLabel gestureSampleLabel;

	JComboBox gestureSetComboBox; // Combobox to pick the GestureSet
	JComboBox gestureClassComboBox; // Combobox to pick the GestureClass from
	// the GestureSet
	WiiReaderPanel samplePanel;// Panel that displays the current
	// GestureSample3d
	JButton sampleBackButton; // Button to go one GestureSample3D backward
	JButton sampleForwardButton; // Button to go one GestureSample3D backward
	
	JTextField addGestureSetTextField; // Field to type the name of a new gesture set
	JButton addGestureSetButton; // Button to add a gesture set
	
	JTextField addGestureClassTextField; // Field to type the name of a new gesture class
	JButton addGestureClassButton; // Button to add a gesture class
	
	JButton addGestureSampleButton; // Button to add a gesture sample
	

	public TestUI(WiiReaderPanel wiiInputPanel) {
		setSize(1100, 500);
		setTitle("WiiMote Test Frame");
		setLayout(null);

		// UI ELEMENTS
		gestureSetLabel = new JLabel("Choose Gesture Set");
		gestureSetLabel.setBounds(15, 15, 150, 20);
		getContentPane().add(gestureSetLabel);

		gestureClassLabel = new JLabel("Choose Gesture Class");
		gestureClassLabel.setBounds(190, 15, 150, 20);
		getContentPane().add(gestureClassLabel);

		gestureSampleLabel = new JLabel("Gesture Sample");
		gestureSampleLabel.setBounds(125, 90, 150, 20);
		getContentPane().add(gestureSampleLabel);

		gestureSetComboBox = new JComboBox();
		gestureSetComboBox.setBounds(15, 35, 150, 20);
		getContentPane().add(gestureSetComboBox);

		gestureClassComboBox = new JComboBox();
		gestureClassComboBox.setBounds(190, 35, 150, 20);
		getContentPane().add(gestureClassComboBox);

		samplePanel = new WiiReaderPanel(null);
		samplePanel.setBounds(80, 120, 200, 200);
		samplePanel.setVisible(true);
		getContentPane().add(samplePanel);

		sampleBackButton = new JButton("<");
		sampleBackButton.setBounds(20, 125, 45, 190);
		getContentPane().add(sampleBackButton);

		sampleForwardButton = new JButton(">");
		sampleForwardButton.setBounds(290, 125, 45, 190);
		getContentPane().add(sampleForwardButton);
		
		addGestureSetTextField = new JTextField("");
		addGestureSetTextField.setBounds(400, 35, 200, 20);
		getContentPane().add(addGestureSetTextField);
		
		addGestureSetButton = new JButton("Add new GestureSet");
		addGestureSetButton.setBounds(620,35,400,20);
		getContentPane().add(addGestureSetButton);

		addGestureClassTextField = new JTextField("");
		addGestureClassTextField.setBounds(400, 70, 200, 20);
		getContentPane().add(addGestureClassTextField);
		
		addGestureClassButton = new JButton("Add new GestureClass to current GestureSet");
		addGestureClassButton.setBounds(620,70,400,20);
		getContentPane().add(addGestureClassButton);
		
		wiiInputPanel.setBounds(400, 120, 200, 200);
		getContentPane().add(wiiInputPanel);
		
		addGestureSampleButton = new JButton("Add this sample to the current GestureClass");
		addGestureSampleButton.setBounds(620,200,400,50);
		getContentPane().add(addGestureSampleButton);
	}

	/**
	 * 
	 * 
	 * @param gestureSets
	 */
	public void setGestureSets(List<GestureSet> gestureSets) {
		// Fill GestureSets combobox
		if (gestureSets.size() > 0) {
			// Fill GestureSets combobox
			for (Iterator<GestureSet> gSetsIter = gestureSets.iterator(); gSetsIter
					.hasNext();) {
				GestureSet tempSet = gSetsIter.next();
				gestureSetComboBox.addItem(tempSet.getName());
			}
		}
	}

	private void setGestureClasses(GestureSet gestureSet) {
		// Fill GestureClasses combobox
		if (gestureSet.getGestureClasses().size() > 0) {
			for (Iterator<GestureClass> gClassIter = gestureSet
					.getGestureClasses().iterator(); gClassIter.hasNext();) {
				GestureClass tempClass = gClassIter.next();
				gestureClassComboBox.addItem(tempClass.getName());
			}
		}
	}

	
	
	
	public void selectGestureSet(String setName){
		gestureSetComboBox.setSelectedItem(setName);		
	}
	
	public void selectGestureSet(int index){
		gestureSetComboBox.setSelectedItem(index);		
	}
	
	public void selectGestureSet(GestureSet set){
		gestureSetComboBox.setSelectedItem(set.getName());
	}
	
	public void selectGestureClass(String className){
		gestureClassComboBox.setSelectedItem(className);		
	}
	
	public void selectGestureClass(int index){
		gestureClassComboBox.setSelectedItem(index);		
	}
	
	public void selectGestureClass(GestureClass gClass){
		gestureClassComboBox.setSelectedItem(gClass.getName());
	}
	
	
}
