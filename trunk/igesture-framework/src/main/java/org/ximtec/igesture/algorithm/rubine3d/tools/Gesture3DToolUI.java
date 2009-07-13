/*
 * @(#)$Id: Gesture3DToolUI.java
 *
 * Author		:	Arthur Vogels, arthur.vogels@gmail.com
 *                  
 *
 * Purpose		:   User interface for the Gesture3DTool.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 15.01.2009		vogelsar	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.rubine3d.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.ximtec.igesture.core.Gesture;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample3D;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.io.wiimote.WiiReaderPanel;
import org.ximtec.igesture.util.additions3d.RecordedGesture3D;

public class Gesture3DToolUI extends JFrame {

	private String setName;
	private String className;
	private int currentSampleNumber;
	private List<Gesture<?>> samples;

	private boolean started;
	
	private JLabel gestureSetLabel;
	private JLabel gestureClassLabel;
	private JLabel gestureSampleLabel;

	private JComboBox gestureSetComboBox; // Combobox to pick the GestureSet
	private JComboBox gestureClassComboBox; // Combobox to pick the GestureClass
	// from
	// the GestureSet

	private JButton removeSetButton; // Button to remove a gesture set

	private JButton removeClassButton; // Button to remove a gesture class

	private WiiReaderPanel samplePanel;// Panel that displays the current
	// GestureSample3d
	private JButton sampleBackButton; // Button to go one GestureSample3D
	// backward
	private JButton sampleForwardButton; // Button to go one GestureSample3D
	// backward

	private JButton removeSampleButton; // Button to remove the current gesture
	// sample

	private JTextField addGestureSetTextField; // Field to type the name of a
	// new gesture set
	private JButton addGestureSetButton; // Button to add a gesture set

	private JTextField addGestureClassTextField; // Field to type the name of a
	// new gesture class
	private JButton addGestureClassButton; // Button to add a gesture class

	private JButton addGestureSampleButton; // Button to add a gesture sample

	private JButton startWiiMoteButton; // Button to start wiimote

	private JButton stopWiiMoteButton; // Button to start wiimote

	private JButton recogniseButton; // Button to start recognising

	private JTextArea resultTextArea; // Textfield to display the result

	private Gesture3DTool controller; // controller

	public Gesture3DToolUI(final Gesture3DTool controller) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(1100, 500);
		setTitle("WiiMote Test Frame");
		setLayout(null);

		this.controller = controller;
		
		started = false;

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
		gestureClassComboBox
				.addActionListener(new gestureClassComboBoxListener());
		getContentPane().add(gestureClassComboBox);

		removeSetButton = new JButton("Remove Set");
		removeSetButton.setBounds(15, 60, 150, 25);
		removeSetButton.addActionListener(new removeSetButtonListener());
		getContentPane().add(removeSetButton);

		removeClassButton = new JButton("Remove Class");
		removeClassButton.setBounds(190, 60, 150, 25);
		removeClassButton.addActionListener(new removeClassButtonListener());
		getContentPane().add(removeClassButton);

		samplePanel = new WiiReaderPanel(null);
		samplePanel.setBounds(80, 120, 200, 200);
		samplePanel.setVisible(true);
		getContentPane().add(samplePanel);

		sampleBackButton = new JButton("<");
		sampleBackButton.setBounds(20, 125, 45, 190);
		sampleBackButton.addActionListener(new sampleBackwardButtonListener());
		getContentPane().add(sampleBackButton);

		sampleForwardButton = new JButton(">");
		sampleForwardButton.setBounds(290, 125, 45, 190);
		sampleForwardButton
				.addActionListener(new sampleForwardButtonListener());
		getContentPane().add(sampleForwardButton);

		removeSampleButton = new JButton("Remove Sample");
		removeSampleButton.setBounds(100, 330, 150, 40);
		removeSampleButton.addActionListener(new removeSampleButtonListener());
		getContentPane().add(removeSampleButton);

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

		startWiiMoteButton = new JButton("Start WiiMote");
		startWiiMoteButton.setBounds(620, 125, 220, 50);
		startWiiMoteButton.addActionListener(new startWiiMoteButtonListener());
		getContentPane().add(startWiiMoteButton);

		stopWiiMoteButton = new JButton("Stop WiiMote");
		stopWiiMoteButton.setBounds(870, 125, 150, 50);
		stopWiiMoteButton.addActionListener(new stopWiiMoteButtonListener());
		getContentPane().add(stopWiiMoteButton);

		recogniseButton = new JButton("Recognise this gesture");
		recogniseButton.setBounds(400, 350, 200, 50);
		recogniseButton.addActionListener(new recogniseButtonListener());
		getContentPane().add(recogniseButton);

		resultTextArea = new JTextArea();
		resultTextArea.setBounds(620, 280, 400, 180);
		getContentPane().add(resultTextArea);

		// Fill comboboxes
		setGestureSetsBox(controller.getGestureSets());
		setGestureClassesBox(controller
				.getGestureSet((String) gestureSetComboBox.getSelectedItem()));

		// Get current names and numbers
		setName = (String) gestureSetComboBox.getSelectedItem();
		className = (String) gestureClassComboBox.getSelectedItem();
		currentSampleNumber = 0;

		// Get samples
		samples = controller.getGestureSamples(setName, className);

		// Set close operation
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Window Closing");
				controller.disconnectWiiMote();
			}

		});
		
		// Set sample panel when window is made visible
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				System.out.println("Window Opening, setting sample panel");
				started = true;
				setSamplePanel();
			}

		});
	}

	public void setNextSample() {
		System.err.print("setNextSample(): Samples size: " + samples.size());
		if (!(currentSampleNumber + 1 >= samples.size())) {
			currentSampleNumber = currentSampleNumber + 1;
			// sampleForwardButton.setEnabled(false);
		}
		if (samples.size() > 0) {
			samplePanel.setGesture((GestureSample3D) samples
					.get(currentSampleNumber));
			System.err.println(" Sample number displayed: "
					+ currentSampleNumber);
		}
	}

	public void setPreviousSample() {
		System.err
				.print("setPreviousSample(): Samples size: " + samples.size());
		if (currentSampleNumber > 0) {
			currentSampleNumber = currentSampleNumber - 1;
		} else{
			// sampleBackButton.setEnabled(false);
		}
		if (samples.size() > 0) {
			samplePanel.setGesture((GestureSample3D) samples
					.get(currentSampleNumber));
			System.err.println(" Sample number displayed: "
					+ currentSampleNumber);
		}
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

	public void setResultField(org.ximtec.igesture.core.ResultSet set) {
		String text = "Gesture Class:    ->    Accuracy:\n\n";
		for (int i = 0; i < set.getResults().size(); i++) {
			text = text + set.getResult(i).getGestureClass().getName() + " -> "
					+ set.getResult(i).getAccuracy() + "\n";
		}
		resultTextArea.setText(text);
	}

	private void setSamplePanel() {
		if (controller.getGestureSamples(setName, className).size() > 0
				&& currentSampleNumber < controller.getGestureSamples(setName,
						className).size()) {
			samplePanel.setGesture((GestureSample3D) controller
					.getGestureSamples(setName, className).get(
							currentSampleNumber));
		} else {
			System.err
					.println("Gesture3DToolUI.setSamplePanel(): currentSampleNumber out of range");
			samplePanel.setGesture(null);
		}
	}

	// ACTION LISTENERS

	private class gestureSetComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Determine name of selected set
			setName = (String) gestureSetComboBox.getSelectedItem();
			// Fill gesture classes combobox with gesture class names from
			// selected set
			setGestureClassesBox(controller.getGestureSet(setName));
			currentSampleNumber = 0;
			if(started)
				setSamplePanel();
		}
	}

	private class gestureClassComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Determine name of selected class
			className = (String) gestureClassComboBox.getSelectedItem();
			// Load gesture samples from this class
			samples = controller.getGestureSamples(setName, className);
			currentSampleNumber = 0;
			if(started)
				setSamplePanel();
		}
	}

	private class addGestureSetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String newSetName = addGestureSetTextField.getText();
			controller.addGestureSet(newSetName);
			setGestureSetsBox(controller.getGestureSets());
			addGestureSetTextField.setText("");
			gestureClassComboBox.removeAllItems();
			gestureSetComboBox.setSelectedItem(newSetName);
			currentSampleNumber = 0;
			if(started)
				setSamplePanel();
		}
	}

	private class addGestureClassButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (setName == null || setName.equals("")) {
				System.err.println("No Gesture Set selected.");
				return;
			}
			String newClassName = addGestureClassTextField.getText();
			controller.addGestureClass(setName, newClassName);
			setGestureClassesBox(controller.getGestureSet(setName));
			addGestureClassTextField.setText("");
			gestureClassComboBox.setSelectedItem(newClassName);
			currentSampleNumber = 0;
			if(started)
				setSamplePanel();
		}
	}

	private class addGestureSampleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (setName == null || setName.equals("")) {
				System.err.println("No Gesture Set selected.");
				return;
			}
			if (className == null || className.equals("")) {
				System.err.println("No Gesture Class selected.");
				return;
			}
			controller
					.addCurrentGestureSampleToGestureClass(setName, className);
			samples = controller.getGestureSamples(setName, className);
		}
	}

	private class sampleForwardButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Fill samplePanel if applicable
			setNextSample();
		}
	}

	private class sampleBackwardButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Fill samplePanel if applicable
			setPreviousSample();
		}
	}

	private class startWiiMoteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Start WiiMote
			controller.initWiiMote();
		}
	}

	private class stopWiiMoteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Disconnect WiiMote
			controller.disconnectWiiMote();
		}
	}

	private class recogniseButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Start Recognising
			controller.recognise(setName);
		}
	}

	private class removeSampleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Remove Sample
			controller.removeSample(setName, className, currentSampleNumber);
			currentSampleNumber = 0;
			// Reset sample panel
			if(started)
				setSamplePanel();
		}
	}

	private class removeSetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Remove Sample
			controller.removeSet(setName);
			// Refill comboboxes
			setGestureSetsBox(controller.getGestureSets());
			setGestureClassesBox(controller
					.getGestureSet((String) gestureSetComboBox
							.getSelectedItem()));
			if(started)
				setSamplePanel();
		}
	}

	private class removeClassButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Remove Sample
			controller.removeClass(setName, className);
			// Refill combobox
			setGestureClassesBox(controller
					.getGestureSet((String) gestureSetComboBox
							.getSelectedItem()));
			if(started)
				setSamplePanel();
		}
	}

}
