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
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.GUI.action.ExitApplicationAction;
import org.ximtec.igesture.geco.GUI.action.LoadGestureSetAction;
import org.ximtec.igesture.geco.GUI.action.MapGestureAction;
import org.ximtec.igesture.geco.GUI.action.NewProjectAction;
import org.ximtec.igesture.geco.GUI.action.OpenProjectAction;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.util.IconLoader;




/**
 * GUI for the gesture mapping application.
 * 
 * @version 1.0, Nov 2007
 * @author Michele croci
 */

public class GestureMappingView extends JFrame{
	
	   private static final Logger LOGGER = Logger.getLogger(GestureMappingView.class
		         .getName());
	   
	   private GestureMappingModel model;
	   
	   private static final String GESTURE_SET = "gestureSets/ms_application_gestures.xml";
	   private static final String XML_EXTENSION = "xml";
	   
	   private final int WINDOW_HEIGHT = 600;
	   private final int WINDOW_WIDTH = 800;
	   
	   //components of the window:
	   private JPanel leftPanel = new JPanel();
	   private JPanel rightPanel = new JPanel();
	   private JList gestureList = new JList();
	   private BasicButton mapButton; 
	   private BasicButton saveButton;
	   private BasicButton exitButton;

	   
	   
	   
	   /**
	    * Constructs a new main view.
	    * 
	    * @param model the model for this main view.
	    */
	   public GestureMappingView(GestureMappingModel model) {
	      super();
	      this.model = model;
	      init();
	   }



	   
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
	   
	   try {
	         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	      }
	      catch (Exception e) {
	         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
	      }

		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		setLocation(150, 100);
		this.setTitle("Gestures Mapping");
		
		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		JPanel contentPanel = new JPanel();
		
		GridBagLayout gbl = new GridBagLayout();
		
		this.getContentPane().setLayout(gbl);
		
		leftPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GestureMappingConstants.USER_DEFINED_MAPPING));
		rightPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GestureMappingConstants.GESTURE_SET));

		
		contentPanel.setLayout(new GridBagLayout());
		//BasicButton loadSetButton = SwingTool.createButton(GestureMappingConstants.LOAD_GESTURE_SET);
		//loadSetButton.setAction(new ActionExitApplication(this));

		
		contentPanel.add(leftPanel,
				new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				 new Insets(0,0,0,20),10,10 ) );
	
		contentPanel.add(rightPanel,
				new GridBagConstraints(1,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(0,0,0,0),0,0 ) );

		//JPanel menuPanel = new JPanel();
		//menuPanel.setLayout(new GridLayout(1,1));
		//menuPanel.add(createMenuBar());
		
		this.getContentPane().add(createMenuBar(),
				new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				 new Insets(0,0,0,0),5,5 ) );
		this.getContentPane().add(contentPanel,
				new GridBagConstraints(0,1,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(20,20,20,20),0,0 ) );
		
        saveButton = SwingTool.createButton(GestureMappingConstants.SAVE);
        exitButton = SwingTool.createButton(GestureMappingConstants.EXIT);
        //saveButton.setText(GestureMappingConstants.SAVE);
        //exitButton.setText(GestureMappingConstants.EXIT);
        exitButton.setAction(new ExitApplicationAction(this));
        //saveButton.setAction();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        
      this.getContentPane().add(buttonPanel,
                new GridBagConstraints(0,2,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                 new Insets(0,20,0,0),20,0 ) );
		
		
		initLeftPanel();
		initRightPanel();
		setVisible(true);
		
	}
	
	   /**
	    * Initialises the left container.
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

		//GestureSet gestureSet = XMLTool.importGestureSet(
			//            new File(ClassLoader.getSystemResource(GESTURE_SET).getFile())).get(0);
		
	     
	    //GridLayout grid =  new GridLayout(1,2);

	    //rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
	    mapButton = SwingTool.createButton(GestureMappingConstants.MAP_GESTURE);
	    rightPanel.setLayout(new GridBagLayout());
	    gestureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    gestureList.setCellRenderer(new MyCellRenderer());
	    gestureList.addListSelectionListener(new ListSelectionListener(){
	       public void valueChanged(ListSelectionEvent e){
	          GestureMappingView.this.mapButton.setEnabled(true);
	       }
	    });
	    rightscroll.getViewport().add(gestureList);
		rightscroll.setBorder(null);
		rightPanel.add(rightscroll,
              new GridBagConstraints(0,0,2,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),0,0 ) );
		
		mapButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		mapButton.setAction(new MapGestureAction(this, model.mappingTable));
		//mapButton.setText(GestureMappingConstants.MAP_GESTURE);
		mapButton.setEnabled(false);
		
	    BasicButton loadSetButton = SwingTool.createButton(GestureMappingConstants.LOAD_GESTURE_SET);
	    
	    //loadSetButton.addActionListener(new LoadSetActionListener());
	    loadSetButton.setAction(new LoadGestureSetAction(this));
	    //loadSetButton.setText(GestureMappingConstants.LOAD_GESTURE_SET);

	     
	     rightPanel.add(loadSetButton,
	                new GridBagConstraints(0,1,1,1,0.5,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
	                         new Insets(20,0,20,0),50,0 ) );
		
	     rightPanel.add(mapButton,
               new GridBagConstraints(1,1,1,1,0.5,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                     new Insets(20,0,20,0),50,0 ) );

	
			 
			 
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
	      JMenu menu = GuiTool.getGuiBundle().createMenu(GestureMappingConstants.MENU_FILE);
	//      menu.add(SwingTool.createMenuItem(new ActionNewGestureMap(this), IconLoader
	  //          .getIcon(IconLoader.DOCUMENT_NEW)));
	//      menu.add(SwingTool.createMenuItem(new ActionOpenGestureMap(this),
	  //          IconLoader.getIcon(IconLoader.DOCUMENT_OPEN)));
	     
	       menu.add(SwingTool.createMenuItem( new NewProjectAction(this),null));
	       menu.add(SwingTool.createMenuItem(new OpenProjectAction(this),null));
	      
	 
	      menu.addSeparator();
	      menu.add(new JMenuItem(new ExitApplicationAction(this)));
	      
	      
	      return menu;
	   } // createFileMenu
	   
	   
	   
       /**
        * Returns the main model.
        * 
        * @return the main model.
        */
       public GestureMappingModel getModel() {
          return model;
       } // getModel
       
       
       /**
        * Display the gesture Set
        * 
        * 
        */
    public void showGestureSet(){
        //GestureSet gs= model.getGestureSet();

        List<GestureClass> classes = model.getGestureSet().getGestureClasses();

        DefaultListModel listModel = new DefaultListModel();
        
        for (GestureClass gc : classes){
                 listModel.addElement( gc);
        }
        gestureList.setModel(listModel);
    }
    

    
    
    public GestureClass getSelectedClass(){
         return (GestureClass) gestureList.getSelectedValue();
    }
    
    
    public void addActionExitApplication(AbstractAction a){
       exitButton.setAction(a);
    }
    
    
    public void addActionLoadGestureSet(AbstractAction a){
       exitButton.setAction(a);
    }
    
    public void addActionMapGesture(AbstractAction a){
       exitButton.setAction(a);
    }
    
    public void addActionNewGestureMap(AbstractAction a){
       exitButton.setAction(a);
    }
    
    public void addActionOpenGestureMap (AbstractAction a){
       exitButton.setAction(a);
    }
    
    
    
    
    
    
    
    //LIST RENDERER!
    
    

    
    
    public class MyCellRenderer extends DefaultListCellRenderer {
       
       private Graphics2D graphic;
       
       
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Let superclass deal with most of it...
           super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
           
           if(value instanceof GestureClass) {
     //         System.out.println("GestureMappingView.MyCellRenderer");
   /*           GestureClass gestureClass = (GestureClass) value;
              
              DigitalDescriptor descriptor = gestureClass.getDescriptor(
                    DigitalDescriptor.class);
              
              SampleDescriptor sampleDes = gestureClass.getDescriptor(
                    SampleDescriptor.class);
              
            Graphics2D test = (Graphics2D)list.getComponent(index).getGraphics();
               
               Note note = sampleDes.getSamples().get(0).getNote();
              
               descriptor.getDigitalObject(graphic, note);
               
               final ImageIcon imageIcon=null;// ...; // add extra code here
               setIcon(imageIcon);
    */           
               //Image image = imageIcon.getImage();
               //final Dimension dimension = this.getPreferredSize();
               //final double height = dimension.getHeight();
               //final double width = (height / imageIcon.getIconHeight()) * imageIcon.getIconWidth();
               //image = image.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH);
               //final ImageIcon finalIcon = new ImageIcon(image);
               //setIcon(finalIcon);
              
        }
           return this;
        }
    }
    

    
 
	   
	   /*
	   private class LoadSetActionListener implements ActionListener{
	      
	      public void actionPerformed(ActionEvent ae){
	         JFileChooser fileChooser = new JFileChooser();
	         fileChooser.setFileFilter(new ExtensionFileFilter(XML_EXTENSION,
	               new String[] {XML_EXTENSION}));
	         int status = fileChooser.showOpenDialog(null);
	         if (status == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            if(selectedFile != null){
	               String ext = selectedFile.getName().substring(selectedFile.getName().length()-3,
	                     selectedFile.getName().length());
	               if(ext.equals(XML_EXTENSION)){
	                   openGestureSet(selectedFile);
	                }
	            }
	         } else if (status == JFileChooser.CANCEL_OPTION) {
	           
	         }
	         
	     }
	         
	     public void openGestureSet(File file){
	        System.out.println("GestureMappingView.openGestureSet");
	       GestureSet gestureSet = XMLTool.importGestureSet(file).get(0);
           List<GestureClass> classes = gestureSet.getGestureClasses();

           DefaultListModel listModel = new DefaultListModel();
           
           for (GestureClass gc : classes){
                    listModel.addElement( gc.getName());
           }
           gestureList.setModel(listModel);


	     }
	         

	      
	   }
	   
	   */
	   


		   
}
