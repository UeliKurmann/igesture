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
import org.ximtec.igesture.tool.GestureToolView;
import org.ximtec.igesture.tool.action.ActionAboutDialog;
import org.ximtec.igesture.tool.action.ActionExitApplication;
import org.ximtec.igesture.tool.action.ActionNewDataSouce;
import org.ximtec.igesture.tool.action.ActionOpenDataSouce;
import org.ximtec.igesture.tool.util.IconLoader;
import org.ximtec.igesture.tool.util.SwingTool;
import org.ximtec.igesture.util.XMLTool;

public class GestureMappingView extends JFrame{
	
	   private static final Logger LOGGER = Logger.getLogger(GestureMappingView.class
		         .getName());
	   
	   //private static final String GESTURE_SET = "gestureSets/ms_application_gestures.xml";
	   private static final String GESTURE_SET = "C:\\develop\\igesture\\igesture-framework\\src\\main\\data\\gestureSets\\ms_application_gestures.xml";
	   
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
		//this.getContentPane().setLayout( new FlowLayout());
		JPanel contentPanel = new JPanel();
		//contentPanel.setSize(WINDOW_WIDTH-10,WINDOW_HEIGHT-10);
		//contentPanel.setMinimumSize(new Dimension(WINDOW_WIDTH-10,WINDOW_HEIGHT-10));
		//this.getContentPane().add(contentPanel);
		
		//GridLayout grid1 = new GridLayout(1,2,10,30);
		//GridLayout grid2 = new GridLayout(2,1,10,30);

		//FlowLayout flowLayout = new FlowLayout();
		//flowLayout.setHgap(10);
		//flowLayout.setVgap(10);
		//grid.setHgap(10);
		//grid.setVgap(50);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		GridLayout grid = new GridLayout(2,1,10,10);
		GridLayout grid2 = new GridLayout(1,2,10,10);
		this.getContentPane().setLayout(gbl);
		//this.getContentPane().setLayout(grid);
		//contentPanel.setLayout(grid1);
		//leftPanel.setSize(WINDOW_WIDTH/2, WINDOW_HEIGHT);
		//rightPanel.setSize(WINDOW_WIDTH/2, WINDOW_HEIGHT);
		//leftPanel.setMinimumSize(new Dimension( WINDOW_WIDTH/2-5, WINDOW_HEIGHT-5));
		//rightPanel.setMinimumSize(new Dimension(WINDOW_WIDTH/2-5, WINDOW_HEIGHT-5));
		//leftPanel.setBorder(new TitledBorder(new LineBorder(Color.gray), "User defined mapping"));
		//rightPanel.setBorder(new TitledBorder(new LineBorder(Color.gray), "Gestures set"));

		leftscroll.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), "User defined mapping"));
		rightPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), "Gestures set"));

		
		//this.getContentPane().add(GridBagLayout.);
		contentPanel.setLayout(new GridBagLayout());
		BasicButton loadSetButton = new BasicButton();
		loadSetButton.setText(GestureMappingConstants.LOAD_GESTURE_SET);
		
		contentPanel.add(leftscroll,
				new GridBagConstraints(0,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				 new Insets(0,0,0,20),0,0 ) );
		contentPanel.add(loadSetButton,
				new GridBagConstraints(1,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
						 new Insets(0,0,0,0),0,0 ) );
		contentPanel.add(rightPanel,
				new GridBagConstraints(1,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(0,0,0,0),0,0 ) );


		
		//rightPanel.setLayout(flowLayout);

		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(1,1));
		menuPanel.add(createMenuBar());
		
		this.getContentPane().add(createMenuBar(),
				new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				 new Insets(0,0,0,0),0,0 ) );
		this.getContentPane().add(contentPanel,
				new GridBagConstraints(0,1,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(20,20,20,20),0,0 ) );
		
		/*
		this.getContentPane().add(menuPanel,
				new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				 new Insets(0,0,0,0),0,0 ) );
		this.getContentPane().add(contentPanel,
				new GridBagConstraints(0,1,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(20,20,20,20),0,0 ) );
*/

		
		
		
		/*
		this.getContentPane().add(createMenuBar(), 
				new GridBagConstraints(0,0,1,1,0.0,0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						 new Insets(0,0,0,0),0,0 ) );
		//this.getContentPane().add(contentPanel);
		this.getContentPane().add(leftPanel, new GridBagConstraints(0,1,1,1,0.5,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				 new Insets(10,10,10,5),0,0 ) );
		//this.getContentPane().add(leftPanel, new GridBagConstraints(1,1,1,1,0.5,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			//	 new Insets(10,10,10,5),0,0 ) );
		this.getContentPane().add(rightscroll,new GridBagConstraints(1,1,1,1,0.5,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				 new Insets(10,5,10,10),0,0 ) );
*/

		
		Icon gesture = new ImageIcon("C:\\develop\\gestures\\scratch.jpg");
		//JLabel gestureLabel = new JLabel("Scratch", gesture, SwingConstants.LEFT);
		//JPanel panel = new JPanel();
		//GridLayout grid = new GridLayout(2,2,10,10);
		//panel.setLayout(grid);
		//leftPanel.add(gestureLabel);
		
		/*
		Icon gesture = new ImageIcon("C:\\develop\\gestures\\scratch.jpg");
		JLabel gestureLabel = new JLabel("Scratch", gesture, SwingConstants.LEFT);
		JPanel panel = new JPanel();
		GridLayout grid = new GridLayout(2,2,10,10);
		panel.setLayout(grid);
		panel.add(gestureLabel);

		this.getContentPane().add(panel);
		*/
		
		
		initLeftPanel();
		initRightPanel();
		setVisible(true);
		
	}
	
	   /**
	    * Initialises the left conatiner.
	    */
	private void initLeftPanel(){
		leftscroll.getViewport().add(leftPanel);
			 
			 
		
	}
	
	
	   /**
	    * Initialises the right conatiner.
	    */
	private void initRightPanel(){
		//default: load Microsoft gesture set

		URL l = ClassLoader.getSystemResource(GESTURE_SET);
		GestureSet gestureSet = XMLTool.importGestureSet(
			            new File("C:\\develop\\igesture\\igesture-framework\\src\\main\\data\\gestureSets\\ms_application_gestures.xml")).get(0);
		List<GestureClass> classes = gestureSet.getGestureClasses();
		GridLayout grid =  new GridLayout(1,1);
		grid.setVgap(1);
		rightPanel.setLayout(grid);
		DefaultListModel listModel = new DefaultListModel();
		
			 
			 for (GestureClass gc : classes){
			        //System.out.println(gc.getName());
				 listModel.addElement( gc.getName());
			 		//rightPanel.add(new JLabel(gc.getName()));
			 }
			 JList gestureList = new JList(listModel);
			 gestureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			 //rightPanel.add(gestureList);
			 //rightscroll.getViewport().add(rightPanel);
			 
			 rightscroll.getViewport().add(gestureList);
			 
			 
	}
	
	
	   /**
	    * Creates the menu bar.
	    * 
	    * @return the newly created menu bar.
	    */
	   private JMenuBar createMenuBar() {
	      JMenuBar menuBar = new JMenuBar();
	      menuBar.add(createFileMenu());
	     // menuBar.add(createInfoMenu());
	      return menuBar;
	   } // createMenuBar
	   

	   private JMenu createFileMenu() {
	      JMenu menu = SwingTool.getGuiBundle().createMenu(GestureConstants.MENU_FILE);
	     /* menu.add(SwingTool.createMenuItem(new ActionNewDataSouce(this), IconLoader
	            .getIcon(IconLoader.DOCUMENT_NEW)));
	      menu.add(SwingTool.createMenuItem(new ActionOpenDataSouce(this),
	            IconLoader.getIcon(IconLoader.DOCUMENT_OPEN)));
	      menu.addSeparator();
	      menu.add(SwingTool.createMenuItem(new ActionExitApplication(this)));
	      
	      */
	      return menu;
	   } // createFileMenu
	

		   
}
