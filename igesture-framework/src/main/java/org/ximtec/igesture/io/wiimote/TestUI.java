package org.ximtec.igesture.io.wiimote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;

public class TestUI extends JFrame {

	private JLabel gestureSetLabel;
	private JLabel gestureClassLabel;
	private JLabel gestureSampleLabel;

	private JComboBox gestureSetComboBox; // Combobox to pick the GestureSet
	private JComboBox gestureClassComboBox; // Combobox to pick the GestureClass
	// from
	// the GestureSet
	private WiiReaderPanel samplePanel;// Panel that displays the current
	// GestureSample3d
	private JButton sampleBackButton; // Button to go one GestureSample3D
	// backward
	private JButton sampleForwardButton; // Button to go one GestureSample3D
	// backward

	private JTextField addGestureSetTextField; // Field to type the name of a
	// new gesture set
	private JButton addGestureSetButton; // Button to add a gesture set

	private JTextField addGestureClassTextField; // Field to type the name of a
	// new gesture class
	private JButton addGestureClassButton; // Button to add a gesture class

	private JButton addGestureSampleButton; // Button to add a gesture sample

	private TestController controller; // controller

	public TestUI(TestController controller) {
		setSize(1100, 500);
		setTitle("WiiMote Test Frame");
		setLayout(null);

		this.controller = controller;

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
		gestureSetComboBox.addActionListener(new gestureSetComboBoxListener());
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
		sampleForwardButton.addActionListener(new sampleForwardButtonListener());
		getContentPane().add(sampleForwardButton);

		addGestureSetTextField = new JTextField("");
		addGestureSetTextField.setBounds(400, 35, 200, 20);
		getContentPane().add(addGestureSetTextField);

		addGestureSetButton = new JButton("Add new GestureSet");
		addGestureSetButton.setBounds(620, 35, 400, 20);
		addGestureSetButton
				.addActionListener(new addGestureSetButtonListener());
		getContentPane().add(addGestureSetButton);

		addGestureClassTextField = new JTextField("");
		addGestureClassTextField.setBounds(400, 70, 200, 20);
		getContentPane().add(addGestureClassTextField);

		addGestureClassButton = new JButton(
				"Add new GestureClass to current GestureSet");
		addGestureClassButton.setBounds(620, 70, 400, 20);
		addGestureClassButton
				.addActionListener(new addGestureClassButtonListener());
		getContentPane().add(addGestureClassButton);

		WiiReaderPanel wiiInputPanel = controller.getWiiReaderPanel();
		wiiInputPanel.setBounds(400, 120, 200, 200);
		getContentPane().add(wiiInputPanel);

		addGestureSampleButton = new JButton(
				"Add this sample to the current GestureClass");
		addGestureSampleButton.setBounds(620, 200, 400, 50);
		addGestureSampleButton
				.addActionListener(new addGestureSampleButtonListener());
		getContentPane().add(addGestureSampleButton);

		// Fill comboboxes
		setGestureSetsBox(controller.getGestureSets());
		setGestureClassesBox(controller
				.getGestureSet((String) gestureSetComboBox.getSelectedItem()));

	}

	/**
	 * 
	 * 
	 * @param gestureSets
	 */
	public void setGestureSetsBox(List<GestureSet> gestureSets) {
		// Clear GestureSets combobox
		gestureSetComboBox.removeAllItems();
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

	private void setGestureClassesBox(GestureSet gestureSet) {
		// Clear GestureClasses combobox
		gestureClassComboBox.removeAllItems();
		// Fill GestureClasses combobox
		if (gestureSet != null && gestureSet.getGestureClasses().size() > 0) {
			for (Iterator<GestureClass> gClassIter = gestureSet
					.getGestureClasses().iterator(); gClassIter.hasNext();) {
				GestureClass tempClass = gClassIter.next();
				gestureClassComboBox.addItem(tempClass.getName());
			}
		}
	}

	public void selectGestureSet(String setName) {
		gestureSetComboBox.setSelectedItem(setName);
	}

	public void selectGestureSet(int index) {
		gestureSetComboBox.setSelectedItem(index);
	}

	public void selectGestureSet(GestureSet set) {
		gestureSetComboBox.setSelectedItem(set.getName());
	}

	public void selectGestureClass(String className) {
		gestureClassComboBox.setSelectedItem(className);
	}

	public void selectGestureClass(int index) {
		gestureClassComboBox.setSelectedItem(index);
	}

	public void selectGestureClass(GestureClass gClass) {
		gestureClassComboBox.setSelectedItem(gClass.getName());
	}

	public void setSamplePanel(GestureSample3D sample) {
		samplePanel.setGesture(sample);
	}

	// ACTION LISTENERS

	private class gestureSetComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Determine name of selected set
			String setName = (String) gestureSetComboBox.getSelectedItem();
			// Fill gesture classes combobox with gesture class names from
			// selected set
			setGestureClassesBox(controller.getGestureSet(setName));
		}
	}

	private class addGestureSetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String setName = addGestureSetTextField.getText();
			controller.addGestureSet(setName);
			setGestureSetsBox(controller.getGestureSets());
		}
	}

	private class addGestureClassButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String setName = "";
			if (gestureSetComboBox.getSelectedItem() != null)
				setName = (String) gestureSetComboBox.getSelectedItem();
			else {
				System.err.println("No Gesture Set selected.");
				return;
			}
			String className = addGestureClassTextField.getText();
			controller.addGestureClass(setName, className);
			setGestureClassesBox(controller.getGestureSet(setName));
		}
	}

	private class addGestureSampleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String setName = "";
			String className = "";
			if (gestureSetComboBox.getSelectedItem() != null)
				setName = (String) gestureSetComboBox.getSelectedItem();
			else {
				System.err.println("No Gesture Set selected.");
				return;
			}
			if (gestureClassComboBox.getSelectedItem() != null)
				className = (String) gestureClassComboBox.getSelectedItem();
			else {
				System.err.println("No Gesture Class selected.");
				return;
			}
			controller
					.addCurrentGestureSampleToGestureClass(setName, className);
		}
	}

	private class sampleForwardButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Fill samplePanel if applicable
			String setName = (String) gestureSetComboBox.getSelectedItem();
			String className = (String) gestureClassComboBox.getSelectedItem();
			samplePanel.setGesture(controller.getFirstGestureSample(setName,
					className));
		}
	}

}
