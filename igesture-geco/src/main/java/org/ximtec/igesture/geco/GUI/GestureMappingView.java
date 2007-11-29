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
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.geco.GUI.action.GecoActionHandler;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;




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
	   private GecoActionHandler handler=  new GecoActionHandler(this);
	   
	   private static final String GESTURE_SET = "gestureSets/ms_application_gestures.xml";
	   private static final String XML_EXTENSION = "xml";
	   
	   private final int WINDOW_HEIGHT = 600;
	   private final int WINDOW_WIDTH = 800;
	   
	   //components of the window:
	   private JPanel leftPanel = new JPanel();
	   private JPanel rightPanel = new JPanel();
	   JPanel contentPanel = new JPanel();
	  
	   private ScrollableList gestureList;
	   private ScrollableList mappingList;
	   //private JList gestureList = new JList();
	   //private JList mappingList = new JList();
	   private BasicButton mapButton; 
	   private BasicButton saveButton;
	   private BasicButton exitButton;
	   private BasicButton editButton;
	   private BasicButton removeButton;
	   
	   private boolean initialized;

	   
	   
	   
	   /**
	    * Constructs a new main view.
	    * 
	    * @param model the model for this main view.
	    */
	   public GestureMappingView(GestureMappingModel model) {
	      super();
	      this.model = model;
	      createVoidDialog();
	      setVisible(true);
	   }
	
	
	   /**
	    * Initialises the main view.
	    */
	private void createVoidDialog(){
	   
	   try {
	         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	      }
	      catch (Exception e) {
	         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
	      }
	    
	    GridBagLayout gbl = new GridBagLayout();
	    this.getContentPane().setLayout(gbl);
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		setLocation(150, 100);
		this.setTitle(GestureMappingConstants.GECO);
		
		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		 this.getContentPane().add(contentPanel,
                new GridBagConstraints(0,1,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                         new Insets(20,20,20,20),0,0 ) );
    
		addMenu();
		
	}
	
	public void initProjectView(String projectName){
	   setTitle(GestureMappingConstants.GECO+" - "+projectName);
	   if(!initialized){
	   populateDialog();
	   setVisible(true);
	   }
	}
	
	
	private void populateDialog(){
		
		

		initialized= true;
		leftPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GestureMappingConstants.USER_DEFINED_MAPPING));
		rightPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GestureMappingConstants.GESTURE_SET));

		
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.add(leftPanel,
				new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				 new Insets(0,0,0,20),10,10 ) );
	
		contentPanel.add(rightPanel,
				new GridBagConstraints(1,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(0,0,0,0),0,0 ) );

		
        saveButton = SwingTool.createButton(GestureMappingConstants.SAVE);
        exitButton = SwingTool.createButton(GestureMappingConstants.EXIT);
        exitButton.setAction(handler.getExitApplicationAction());
        saveButton.setAction(handler.getSaveProjectAction());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        buttonPanel.setBackground(this.getBackground());
        
      this.getContentPane().add(buttonPanel,
                new GridBagConstraints(0,2,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                 new Insets(0,20,0,0),20,0 ) );
		
		
		initLeftPanel();
		initRightPanel();
		updateLists();
		
		
	}
	
	private void addMenu(){
       this.getContentPane().add(createMenuBar(),
             new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
              new Insets(0,0,0,0),5,5 ) );

	}
	
	   /**
	    * Initialises the left container.
	    */
	private void initLeftPanel(){

     
	   mappingList = SwingTool.createScrollableList(null,  0,   0);
	   mappingList.getList().setCellRenderer(new MappingCellRenderer());
	   
       //JScrollPane leftscroll = new JScrollPane();
       leftPanel.setLayout(new GridBagLayout());
	   //leftscroll.getViewport().add(mappingList);
	   //leftscroll.setBorder(null);
	   leftPanel.add(mappingList,
              new GridBagConstraints(0,0,2,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),0,0 ) );
	    
	    
	   editButton = SwingTool.createButton(GestureMappingConstants.EDIT);
	   editButton.setAction(handler.getEditMappingAction());
       editButton.setEnabled(false);
	   
	   removeButton = SwingTool.createButton(GestureMappingConstants.REMOVE);
	   
	   removeButton.setAction(handler.getRemoveMappingAction());
	   removeButton.setEnabled(false);
     
	   
       mappingList.getList().addListSelectionListener(new ListSelectionListener(){
          public void valueChanged(ListSelectionEvent e){
             GestureMappingView.this.editButton.setEnabled(true);
             GestureMappingView.this.removeButton.setEnabled(true);
          }
       });
	         
	   leftPanel.add(editButton,
	                    new GridBagConstraints(0,1,1,1,0.5,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
	                             new Insets(20,0,20,0),50,0 ) );
	        
	   leftPanel.add(removeButton,
	               new GridBagConstraints(1,1,1,1,0.5,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
	                     new Insets(20,0,20,0),50,0 ) );
    
		
	}
	
	
	   /**
	    * Initialises the right conatiner.
	    */
	private void initRightPanel(){
	     JScrollPane rightscroll = new JScrollPane();
	     
	    gestureList = SwingTool.createScrollableList(null,  0,   0);
	    rightPanel.setLayout(new GridBagLayout());
	    //gestureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	    gestureList.getList().setCellRenderer(new MyCellRenderer());
//	    gestureList.getList().setCellRenderer(new CustomCellRenderer());
	    gestureList.getList().addMouseListener(new MouseAdapter() {

	         @Override
	         public void mouseReleased(MouseEvent event) {
	            if (event.getButton() == MouseEvent.BUTTON1) {
	               GestureMappingView.this.mapButton.setEnabled(true);
	            }
	         }
	      });
	    /*
	     * old implementation:
	     * 
	    gestureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    gestureList.addListSelectionListener(new ListSelectionListener(){
	       public void valueChanged(ListSelectionEvent e){
	          GestureMappingView.this.mapButton.setEnabled(true);
	       }
	    });
	    
	    
	    rightscroll.getViewport().add(gestureList);
		rightscroll.setBorder(null);
		*/
	    
		rightPanel.add(gestureList,
              new GridBagConstraints(0,0,2,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),0,0 ) );
		
		//mapButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		mapButton = SwingTool.createButton(GestureMappingConstants.MAP_GESTURE);
		mapButton.setAction(handler.getAddMappingAction());
		mapButton.setEnabled(false);
		
	    BasicButton loadSetButton = SwingTool.createButton(GestureMappingConstants.LOAD_GESTURE_SET);
	    loadSetButton.setAction(handler.getLoadGestureSetAction());
	     
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
	     
	       menu.add(SwingTool.createMenuItem(handler.getNewProjectAction(),null));
	       menu.add(SwingTool.createMenuItem(handler.getOpenProjectAction(),null));
	       menu.add(SwingTool.createMenuItem(handler.getSaveProjectAction(),null));
	      
	 
	      menu.addSeparator();
	      menu.add(new JMenuItem(handler.getExitApplicationAction()));
	      
	      
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
        * Update the gesture Set
        * 
        */
       public void updateGestureList(){
           gestureList.setModel(model.getGestureListModel());
           mapButton.setEnabled(false);
       }
       
    /**
     * Update the mapping Set
     * 
     */
    public void updateMappingList(){
        mappingList.setModel(model.getMappingListModel());
        editButton.setEnabled(false);
        removeButton.setEnabled(false);
        mapButton.setEnabled(false);
    }
    
    public void updateLists(){
       updateGestureList();
       updateMappingList();
    }
       

    /**
     * Return the selected Gesture Class
     * 
     * @return the selected GestureClass
     * 
     */
    public GestureClass getSelectedClass(){
       return (GestureClass) gestureList.getSelectedValue();
  }
    
    
    /**
     * Return the selected GestureToAction mapping
     * 
     * @return the selected mapping
     * 
     */
    public GestureToActionMapping getSelectedMappping(){
       return (GestureToActionMapping) mappingList.getSelectedValue();
  }
  
    /**
     * Return the selected mapping
     * 
     * @return the selected GestureMapping
     * 
     */
    public GestureToActionMapping getSelectedMapping(){
       return (GestureToActionMapping) mappingList.getSelectedValue();
  }
  
    
    
    /*

    
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
    
    
    */
    
    
    
    
    public class MappingCellRenderer implements ListCellRenderer {

       public Component getListCellRendererComponent(JList list, Object value,
             int index, boolean isSelected, boolean cellHasFocus) {
          
          JLabel label = new JLabel();
          if(value instanceof GestureToActionMapping){
             GestureToActionMapping gm = (GestureToActionMapping)value;
             label.setText(gm.getGestureClass().getName()+"   -->   "+
                   gm.getAction().toString());
             label.setOpaque(true);
             label.setBackground(isSelected? Color.CYAN:list.getBackground());
          }
          return label;
       } // getListCellRendererComponent
    } 
    
    
    public class CustomCellRenderer implements ListCellRenderer {

       public Component getListCellRendererComponent(JList list, Object value,
             int index, boolean isSelected, boolean cellHasFocus) {
          final Component component = (Component)value;
          component.setBackground(isSelected ? Color.gray : Color.white);
          component.setForeground(isSelected ? Color.white : Color.gray);
          return component;
       } // getListCellRendererComponent
    }
    
    
    //LIST RENDERER

    public class MyCellRenderer extends DefaultListCellRenderer {
       
       private Graphics2D graphic;
       
       
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Let superclass deal with most of it...
           super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
           
           if(value instanceof GestureClass) {
              System.out.println("GestureMappingView.MyCellRenderer");
             GestureClass gestureClass = (GestureClass) value;
             
             SampleDescriptor sampleDes = gestureClass.getDescriptor(
                   SampleDescriptor.class);
             sampleDes.getSamples().get(0).getNote();

             
             if (sampleDes!=null)
                System.out.println("sample not null!");
             
             
              
             /*
              DigitalDescriptor descriptor = gestureClass.getDescriptor(
                    DigitalDescriptor.class);
              if (descriptor!=null)
                 System.out.println("digital not null!");
              List<Descriptor> desc = gestureClass.getDescriptors();
             TextDescriptor textDes = gestureClass.getDescriptor(
                    TextDescriptor.class);
              if (textDes!=null)
                 System.out.println("text not null!");
              */

              

              
              //Graphics2D graphics2D = (Graphics2D)list.getComponent(index).getGraphics();
               //JLabel label = new JLabel();
               //label.getGraphics()
               
               //Note note = sampleDes.getSamples().get(0).getNote();
              
               //descriptor.getDigitalObject(graphic, note);
               
               //final ImageIcon imageIcon=null;// ...; // add extra code here
               //setIcon(imageIcon);
               
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
    

    
 



		   
}
