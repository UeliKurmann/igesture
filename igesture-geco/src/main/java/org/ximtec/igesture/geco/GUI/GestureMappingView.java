/*
 * @(#)GestureMappingView.java 1.0   Nov 15, 2007
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   GUI for the gesture mapping application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */



package org.ximtec.igesture.geco.GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.sigtec.graphix.widget.BasicButton;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.util.XMLTool;

import org.ximtec.igesture.geco.util.ExtensionFileFilter;




/**
 * GUI for the gesture mapping application.
 * 
 * @version 1.0, Nov 2007
 * @author Michele croci
 */

public class GestureMappingView extends JFrame{
	
	   private static final Logger LOGGER = Logger.getLogger(GestureMappingView.class
		         .getName());
	   
	   private static final String GESTURE_SET = "gestureSets/ms_application_gestures.xml";
	   private static final String XML_EXTENSION = "xml";
	   
	   private final int WINDOW_HEIGHT = 500;
	   private final int WINDOW_WIDTH = 800;
	   
	   //components of the window:
	   JPanel leftPanel = new JPanel();
	   JPanel rightPanel = new JPanel();

	   
	   

	   
	   /**
	    * Constructs a new main view.
	    * 
	    * @param model the model for this main view.
	    */
	public GestureMappingView(){
		init();
	}
	
	
	   /**
	    * Initialises the main view.
	    */
	private void init(){
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		setLocation(150, 100);
		this.setTitle("Gestures Mapping");
		
		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		JPanel contentPanel = new JPanel();
		
		GridBagLayout gbl = new GridBagLayout();
		
		GridLayout grid = new GridLayout(2,1,10,10);
		GridLayout grid2 = new GridLayout(1,2,10,10);
		this.getContentPane().setLayout(gbl);
		
		leftPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), "User defined mapping"));
		rightPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), "Gestures set"));

		
		contentPanel.setLayout(new GridBagLayout());
		BasicButton loadSetButton = new BasicButton();
		loadSetButton.setText(GestureMappingConstants.LOAD_GESTURE_SET);
		loadSetButton.addActionListener(new LoadSetActionListener());
		
		contentPanel.add(leftPanel,
				new GridBagConstraints(0,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				 new Insets(0,0,0,20),10,10 ) );
		contentPanel.add(loadSetButton,
				new GridBagConstraints(1,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
						 new Insets(0,0,0,0),0,0 ) );
		contentPanel.add(rightPanel,
				new GridBagConstraints(1,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(0,0,0,0),0,0 ) );

		//JPanel menuPanel = new JPanel();
		//menuPanel.setLayout(new GridLayout(1,1));
		//menuPanel.add(createMenuBar());
		
		this.getContentPane().add(createMenuBar(),
				new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				 new Insets(0,0,0,0),0,0 ) );
		this.getContentPane().add(contentPanel,
				new GridBagConstraints(0,1,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(20,20,20,20),0,0 ) );
		
		
		initLeftPanel();
		initRightPanel();
		setVisible(true);
		
	}
	
	   /**
	    * Initialises the left conatiner.
	    */
	private void initLeftPanel(){
       JScrollPane leftscroll = new JScrollPane();
	   leftPanel.setLayout(new GridLayout(1,1,0,0));
	   //leftPanel.add(leftscroll);
	   
	   //leftPanel.setMinimumSize(new Dimension(this.WINDOW_HEIGHT, this.WINDOW_WIDTH/2));
	
		
	}
	
	
	   /**
	    * Initialises the right conatiner.
	    */
	private void initRightPanel(){
	     JScrollPane rightscroll = new JScrollPane();

		GestureSet gestureSet = XMLTool.importGestureSet(
			            new File(ClassLoader.getSystemResource(GESTURE_SET).getFile())).get(0);
		List<GestureClass> classes = gestureSet.getGestureClasses();
		GridLayout grid =  new GridLayout(1,1);
		//grid.setVgap(1);
		rightPanel.setLayout(grid);
		DefaultListModel listModel = new DefaultListModel();
		
		int i = 0;
		for (GestureClass gc : classes){
		         i++;
		         if(i<10)
		            listModel.addElement( gc.getName());
				 System.out.println(gc.getName());
		}
		JList gestureList = new JList(listModel);
		gestureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			 
		rightscroll.getViewport().add(gestureList);
		rightscroll.setBorder(null);
		rightPanel.add(rightscroll);
			 
			 
	}
	
	
	   /**
	    * Creates the menu bar.
	    * 
	    * @return the newly created menu bar.
	    */
	   private JMenuBar createMenuBar() {
	      JMenuBar menuBar = new JMenuBar();
	      //menuBar.add(createFileMenu());
	     // menuBar.add(createInfoMenu());
	      return menuBar;
	   } // createMenuBar
	   

	   private JMenu createFileMenu() {
	     /* JMenu menu = SwingTool.getGuiBundle().createMenu(GestureMappingConstants.MENU_FILE);
	      menu.add(SwingTool.createMenuItem(new ActionNewDataSouce(this), IconLoader
	            .getIcon(IconLoader.DOCUMENT_NEW)));
	      menu.add(SwingTool.createMenuItem(new ActionOpenDataSouce(this),
	            IconLoader.getIcon(IconLoader.DOCUMENT_OPEN)));
	      menu.addSeparator();
	      menu.add(SwingTool.createMenuItem(new ActionExitApplication(this)));
	      
	      
	      return menu;
	      */
	      return null;
	   } // createFileMenu
	   
	   
	   private class LoadSetActionListener implements ActionListener{
	      
	      public void actionPerformed(ActionEvent ae){
	         JFileChooser fileChooser = new JFileChooser();
	         fileChooser.setFileFilter(new ExtensionFileFilter(XML_EXTENSION,
	               new String[] {XML_EXTENSION}));
	         System.out.println("FileChooser opened");
	         int status = fileChooser.showOpenDialog(null);
	         if (status == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            if(selectedFile != null){
	               String ext = selectedFile.getName().substring(selectedFile.getName().length()-3,
	                     selectedFile.getName().length());
	               if(ext==XML_EXTENSION){
	                   openGestureSet(selectedFile);
	                }
	            }
	         } else if (status == JFileChooser.CANCEL_OPTION) {
	           
	         }
	         
	     }
	         
	     public void openGestureSet(File file){
	            
	     }
	         

	      
	   }
	

		   
}
