/*
 * @(#)GecoMainView.java 1.0   Nov 15, 2007
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.GuiTool;
import org.sigtec.util.Constant;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.GUI.action.GecoActionHandler;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;
import org.ximtec.igesture.tool.GestureConstants;




/**
 * GUI for the gesture mapping application.
 * 
 * @version 1.0, Nov 2007
 * @author Michele croci
 */

public class GecoMainView extends JFrame{
	
	   private static final Logger LOGGER = Logger.getLogger(GecoMainView.class
		         .getName());
	   
	   private GecoMainModel model;
	   private GecoActionHandler handler=  new GecoActionHandler(this);
	   private GecoComponentHandler compHandler = new GecoComponentHandler(this);
       private boolean initialized;
	   
	   private final int WINDOW_HEIGHT = 600;
	   private final int WINDOW_WIDTH = 800;

	   
	   //GUI elements
	   private JPanel leftPanel = new JPanel();
	   private JPanel rightPanel = new JPanel();
	   JPanel contentPanel = new JPanel();
	   private ScrollableList gestureList;
	   private ScrollableList mappingList;
	   private JButton mapButton; 
	   private JButton saveButton;
	   private JButton exitButton;
	   private JButton editButton;
	   private JButton removeButton;
	   private JMenuItem saveMenuItem;	   



	   /**
	    * Constructs a new main view.
	    * 
	    * @param model the model for this main view.
	    */
	   public GecoMainView(GecoMainModel model) {
	      super();
	      this.model = model;
	      createVoidDialog();
	      setVisible(true);
	   }
	
	
	   /**
	    * Initialises the main view (create an empty frame).
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
		this.setTitle(GecoConstants.GECO_TITLE);
		
		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		 this.getContentPane().add(contentPanel,
                new GridBagConstraints(0,1,1,1,1.0,1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                         new Insets(20,20,20,20),0,0 ) );
    
		addMenu();
		
	}
	
	
    /**
     * Initializes the view for a project
     */
	public void initProjectView(String projectName){
	   setTitle(GecoConstants.GECO_TITLE+ Constant.DASH_S +projectName);
	   if(!initialized){
	   populateDialog();
	   setVisible(true);
	   }
	}
	
	
    /**
     * Populates the dialog.
     */
	private void populateDialog(){
		initialized= true;
		leftPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.USER_DEFINED_MAPPING));
		rightPanel.setBorder(new TitledBorder(new BevelBorder(0,Color.gray,Color.gray), GecoConstants.GESTURE_SET));

		
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.add(leftPanel,
				new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				 new Insets(0,0,0,20),10,10 ) );
	
		contentPanel.add(rightPanel,
				new GridBagConstraints(1,0,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						 new Insets(0,0,0,0),0,0 ) );

		
        saveButton = SwingTool.createButton(GecoConstants.SAVE);
        exitButton = SwingTool.createButton(GecoConstants.EXIT);
    
        exitButton.setAction(handler.getExitApplicationAction());
        saveButton.setAction(handler.getSaveProjectAction());
        
        JButton minimize = SwingTool.createButton(GecoConstants.MINIMIZE);
        minimize.setAction(handler.getMinimizeAction());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(minimize);
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
	    * Initialises the left panel.
	    */
	private void initLeftPanel(){
	   mappingList = SwingTool.createScrollableList(null,  0,   0);
	   mappingList.getList().setCellRenderer(new MappingCellRenderer());

       leftPanel.setLayout(new GridBagLayout());
	   leftPanel.add(mappingList,
              new GridBagConstraints(0,0,2,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),0,0 ) );
	    
	    
	   editButton = SwingTool.createButton(GecoConstants.EDIT);
	   editButton.setAction(handler.getEditMappingAction());
       editButton.setEnabled(false);
	   removeButton = SwingTool.createButton(GecoConstants.REMOVE);
	   removeButton.setAction(handler.getRemoveMappingAction());
	   removeButton.setEnabled(false);
     
       mappingList.getList().addListSelectionListener(new ListSelectionListener(){
          public void valueChanged(ListSelectionEvent e){
             GecoMainView.this.editButton.setEnabled(true);
             GecoMainView.this.removeButton.setEnabled(true);
          }
       });
	         
	   leftPanel.add(editButton,
	                    new GridBagConstraints(0,1,1,1,0.5,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
	                             new Insets(20,0,20,0),50,0 ) );
	     
	   leftPanel.add(removeButton,
	               new GridBagConstraints(1,1,1,1,0.5,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
	                     new Insets(20,0,20,0),50,0 ) );
    
	}//initLeftPanel
	
	
	   /**
	    * Initialises the right panel.
	    */
	private void initRightPanel(){	     
	    gestureList = SwingTool.createScrollableList(null,  0,   0);
	    rightPanel.setLayout(new GridBagLayout());
	    gestureList.getList().addMouseListener(new MouseAdapter() {

	         @Override
	         public void mouseReleased(MouseEvent event) {
	            if (event.getButton() == MouseEvent.BUTTON1) {
	               GecoMainView.this.mapButton.setEnabled(true);
	            }
	         }
	      });

		rightPanel.add(gestureList,
              new GridBagConstraints(0,0,2,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0,0,0,0),0,0 ) );
		
		mapButton = SwingTool.createButton(GecoConstants.MAP_GESTURE);
		mapButton.setAction(handler.getAddMappingAction());
		mapButton.setEnabled(false);
		
	    JButton loadSetButton = SwingTool.createButton(GecoConstants.LOAD_GESTURE_SET);
	    loadSetButton.setAction(handler.getLoadGestureSetAction());
	     
	     rightPanel.add(loadSetButton,
	                new GridBagConstraints(0,1,1,1,0.5,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
	                         new Insets(20,0,20,0),50,0 ) );
		
	     rightPanel.add(mapButton,
               new GridBagConstraints(1,1,1,1,0.5,0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                     new Insets(20,0,20,0),50,0 ) );
	}//initRightPanel
	
	
	   /**
	    * Creates the menu bar.
	    * 
	    * @return the newly created menu bar.
	    */
	   private JMenuBar createMenuBar() {
	      JMenuBar menuBar = new JMenuBar();
	      menuBar.add(createFileMenu());
	      menuBar.add(createInfoMenu());
	      return menuBar;
	     
	   } // createMenuBar
	   

	   private JMenu createFileMenu() {
	      JMenu menu = GuiTool.getGuiBundle().createMenu(GecoConstants.MENU_FILE);
	     
	       menu.add(SwingTool.createMenuItem(handler.getNewProjectAction(),null));
	       menu.add(SwingTool.createMenuItem(handler.getOpenProjectAction(),null));
	       saveMenuItem = SwingTool.createMenuItem(handler.getSaveProjectAction(),null);
	       saveMenuItem.setEnabled(false);
	       menu.add(saveMenuItem);
	       menu.addSeparator();
	       menu.add(SwingTool.createMenuItem(handler.getOptionsAction(),null));
      	   menu.addSeparator();
      	   menu.add(new JMenuItem(handler.getExitApplicationAction()));
	  
	      return menu;
	   } // createFileMenu
	   

	   private JMenu createInfoMenu() {
	      JMenu menu =  GuiTool.getGuiBundle().createMenu(GestureConstants.COMMON_HELP);
	      menu.add(SwingTool.createMenuItem(handler.getAboutAction(),null));
	      return menu;
	   } // createInfoMenu
	   
	   
	   
       /**
        * Returns the main model.
        * 
        * @return the main model.
        */
       public GecoMainModel getModel() {
          return model;
       } // getModel
       
       
       /**
        * Update the gesture Set
        * 
        */
       public void updateGestureList(){
           gestureList.setModel(model.getGestureListModel());
           mapButton.setEnabled(false);
       }//updateGestureList
       
    /**
     * Update the mapping Set
     * 
     */
    public void updateMappingList(){
        mappingList.setModel(model.getMappingListModel());
        editButton.setEnabled(false);
        removeButton.setEnabled(false);
        mapButton.setEnabled(false);
    }//updateMappingList
    
    
    /**
     * Update the two lists
     * 
     */
    public void updateLists(){
       updateGestureList();
       updateMappingList();
    }//updateLists
       

    /**
     * Return the selected Gesture Class
     * 
     * @return the selected GestureClass
     * 
     */
    public GestureClass getSelectedClass(){
       return (GestureClass) gestureList.getSelectedValue();
  }//getSelectedClass
    
    
    /**
     * Return the selected GestureToAction mapping
     * 
     * @return the selected mapping
     * 
     */
    public GestureToActionMapping getSelectedMappping(){
       return (GestureToActionMapping) mappingList.getSelectedValue();
  }//getSelectedMappping
  
    /**
     * Return the selected mapping
     * 
     * @return the selected GestureMapping
     * 
     */
    public GestureToActionMapping getSelectedMapping(){
       return (GestureToActionMapping) mappingList.getSelectedValue();
  }//getSelectedMapping
    
    
    /**
     * Enable the save in the menu
     * 
     */
    public void enableMenuItem(){
         saveMenuItem.setEnabled(true);
  }//enableMenuItem
    
    /**
     * Returns the component handler of the applicatio
     * 
     * @return the component Handler
     * 
     */
    public GecoComponentHandler getComponentHandler(){
         return compHandler;
  }//getComponentHandler
    
    
    /**
     * Returns the component handler of the applicatio
     * 
     * @return the component Handler
     * 
     */
    public GecoActionHandler getActionHandler(){
         return handler;
  }//getActionHandler
    
    
    /**
     * Disable the save operation
     */
    public void disableSaveButton(){
       saveButton.setEnabled(false);
       saveMenuItem.setEnabled(false);
    }//disableSaveButton
    
    
    /**
     * Enable the save operation
     */
    public void enableSaveButton(){
       saveButton.setEnabled(true);
       saveMenuItem.setEnabled(true);
    }//enableSaveButton
  
    
    /**
     * Cell Renderer for the mapping list
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
    
    
    /**
     * Cell Renderer for the gestures list
     */  
    public class CustomCellRenderer implements ListCellRenderer {

       public Component getListCellRendererComponent(JList list, Object value,
             int index, boolean isSelected, boolean cellHasFocus) {
          final Component component = (Component)value;
          component.setBackground(isSelected ? Color.gray : Color.white);
          component.setForeground(isSelected ? Color.white : Color.gray);
          return component;
       } // getListCellRendererComponent
    }
    

		   
}
