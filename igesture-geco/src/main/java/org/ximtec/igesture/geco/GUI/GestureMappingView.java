package org.ximtec.igesture.geco.GUI;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.*;

import org.sigtec.graphix.widget.BasicButton;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.tool.GestureConstants;
//import org.ximtec.igesture.tool.util.SwingTool;
import org.ximtec.igesture.util.XMLTool;

public class GestureMappingView extends JFrame{
	
	   private static final Logger LOGGER = Logger.getLogger(GestureMappingView.class
		         .getName());
	   
	   private static final String GESTURE_SET = "gestureSets/ms_application_gestures.xml";
	   
	   private final int WINDOW_HEIGHT = 500;
	   private final int WINDOW_WIDTH = 800;
	   
	   //components of the window:
	   JPanel leftPanel = new JPanel();
	   JPanel rightPanel = new JPanel();
	   JScrollPane rightscroll = new JScrollPane();
	   JScrollPane leftscroll = new JScrollPane();
	   
	   

	   
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
		
		leftscroll.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), "User defined mapping"));
		rightPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), "Gestures set"));

		
		contentPanel.setLayout(new GridBagLayout());
		BasicButton loadSetButton = new BasicButton();
		loadSetButton.setText(GestureMappingConstants.LOAD_GESTURE_SET);
		
		contentPanel.add(leftPanel,
				new GridBagConstraints(0,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				 new Insets(0,0,0,20),0,0 ) );
		contentPanel.add(loadSetButton,
				new GridBagConstraints(1,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
						 new Insets(0,0,0,0),0,0 ) );
		contentPanel.add(rightPanel,
				new GridBagConstraints(1,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(0,0,0,0),0,0 ) );

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(1,1));
		menuPanel.add(createMenuBar());
		
		this.getContentPane().add(createMenuBar(),
				new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				 new Insets(0,0,0,0),10,10 ) );
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
	   leftPanel.setLayout(new GridLayout(1,1,0,0));
	   leftPanel.add(leftscroll);
	   
	   //leftPanel.setMinimumSize(new Dimension(this.WINDOW_HEIGHT, this.WINDOW_WIDTH/2));
	
		
	}
	
	
	   /**
	    * Initialises the right conatiner.
	    */
	private void initRightPanel(){
		//default: load Microsoft gesture set

		GestureSet gestureSet = XMLTool.importGestureSet(
			            new File(ClassLoader.getSystemResource(GESTURE_SET).getFile())).get(0);
		List<GestureClass> classes = gestureSet.getGestureClasses();
		GridLayout grid =  new GridLayout(1,1);
		grid.setVgap(1);
		rightPanel.setLayout(grid);
		DefaultListModel listModel = new DefaultListModel();
		
			 
		for (GestureClass gc : classes){
				 listModel.addElement( gc.getName());
		}
		JList gestureList = new JList(listModel);
		gestureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			 
		rightscroll.getViewport().add(gestureList);
			 
			 
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
	

		   
}
