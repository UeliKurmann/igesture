/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :   GUI for the gesture controller application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 15, 2007     crocimi     Initial Release
 * Jan 14, 2008     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.geco.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.WindowConstants;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sigtec.graphix.GuiTool;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.geco.gui.action.ActionHandler;
import org.ximtec.igesture.geco.gui.action.MinimizeAction;
import org.ximtec.igesture.geco.mapping.GestureToActionMapping;
import org.ximtec.igesture.geco.util.Constant;
import org.ximtec.igesture.geco.util.GuiBundleTool;
import org.ximtec.igesture.graphics.ScrollableList;
import org.ximtec.igesture.graphics.SwingTool;


/**
 * GUI for the gesture controller application.
 * 
 * @version 0.9, Nov 2007
 * @author Michele Croci, mcroci@gmail.com
 * @author Beat Signer, bsigner@vub.ac.be
 */

public class MainView extends JFrame implements WindowListener {

   private static final Logger LOGGER = Logger.getLogger(MainView.class
         .getName());

   private MainModel model;
   private ActionHandler handler = new ActionHandler(this);
   private ComponentHandler compHandler = new ComponentHandler(this);
   private boolean initialized;

   private final int WINDOW_HEIGHT = 600;
   private final int WINDOW_WIDTH = 800;

   // GUI elements
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

   private boolean first = true;


   /**
    * Constructs a new main view.
    * 
    * @param model the model for this main view.
    */
   public MainView(MainModel model) {
      super();
      this.model = model;
      createVoidDialog();
      if ((!model.minimizeAsStartup())) {
         setVisible(true);
      }
   }


   /**
    * Initialises the main view (create an empty frame).
    */
   private void createVoidDialog() {
	   try {
    	  /* see http://java.sun.com/docs/books/tutorial/uiswing/lookandfeel/nimbus.html */
    	  for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) {
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
      }
      catch (Exception e) {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         }
         catch (Exception e1) {
        	 LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e1);
         }
      }
	   
	  this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
      GridBagLayout gbl = new GridBagLayout();
      this.getContentPane().setLayout(gbl);
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      setLocation(150, 100);
      this.setTitle(GuiBundleTool.getBundle().getName(GuiBundleTool.KEY));
      GuiBundleTool.getBundle().getSmallIcon(GuiBundleTool.KEY);
      setIconImage(GuiBundleTool.getBundle().getSmallIcon(GuiBundleTool.KEY)
            .getImage());

      this.getContentPane().add(
            contentPanel,
            new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                  GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                  new Insets(20, 20, 20, 20), 0, 0));
      this.addWindowListener(this);
      addMenu();
   }


   /**
    * Initialises the view for a project
    */
   public void initProjectView(String projectName) {
      setTitle(GuiBundleTool.getBundle().getName(GuiBundleTool.KEY)
            + org.sigtec.util.Constant.DASH_S + projectName);

      if (!initialized) {
         populateDialog();
         if ((!first) || (!model.minimizeAsStartup())) {
            setVisible(true);
         }
         else {
            first = false;
         }
      }

   }


   /**
    * Populates the dialog.
    */
   private void populateDialog() {
      initialized = true;
      leftPanel.setBorder(new TitledBorder(new BevelBorder(0, Color.gray,
            Color.gray), Constant.USER_DEFINED_MAPPING));
      rightPanel.setBorder(new TitledBorder(new BevelBorder(0, Color.gray,
            Color.gray), Constant.GESTURE_SET));

      contentPanel.setLayout(new GridBagLayout());
      contentPanel.add(leftPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0,
                  0, 20), 10, 10));

      contentPanel.add(rightPanel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0,
                  0, 0), 0, 0));

      saveButton = GuiTool.createButton(handler.getSaveProjectAction());
      exitButton = GuiTool.createButton(handler.getExitApplicationAction());
      // JButton minimize = SwingTool.createButton(handler.getMinimizeAction());
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout());
      buttonPanel.add(saveButton);
      buttonPanel.add(exitButton);
      // buttonPanel.add(minimize);
      buttonPanel.setBackground(this.getBackground());
      this.getContentPane().add(
            buttonPanel,
            new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
                  GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 20, 0));

      initLeftPanel();
      initRightPanel();
      updateLists();
   }


   private void addMenu() {
      this.setJMenuBar(createMenuBar());
   }


   /**
    * Initialises the left panel.
    */
   private void initLeftPanel() {
      mappingList = SwingTool.createScrollableList(null, 0, 0);
      mappingList.getList().setCellRenderer(new MappingCellRenderer());

      leftPanel.setLayout(new GridBagLayout());
      leftPanel.add(mappingList, new GridBagConstraints(0, 0, 2, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0,
                  0, 0), 0, 0));

      editButton = GuiTool.createButton(handler.getEditMappingAction());
      editButton.setEnabled(false);
      removeButton = GuiTool.createButton(handler.getRemoveMappingAction());
      removeButton.setEnabled(false);

      mappingList.getList().addListSelectionListener(
            new ListSelectionListener() {

               public void valueChanged(ListSelectionEvent e) {
                  MainView.this.editButton.setEnabled(true);
                  MainView.this.removeButton.setEnabled(true);
               }
            });

      leftPanel.add(editButton, new GridBagConstraints(0, 1, 1, 1, 0.5, 0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20,
                  0, 20, 0), 50, 0));

      leftPanel.add(removeButton, new GridBagConstraints(1, 1, 1, 1, 0.5, 0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20,
                  0, 20, 0), 50, 0));

   }// initLeftPanel


   /**
    * Initialises the right panel.
    */
   private void initRightPanel() {
      gestureList = SwingTool.createScrollableList(null, 0, 0);
      rightPanel.setLayout(new GridBagLayout());
      gestureList.getList().addMouseListener(new MouseAdapter() {

         @Override
         public void mouseReleased(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {
               MainView.this.mapButton.setEnabled(true);
            }
         }
      });

      rightPanel.add(gestureList, new GridBagConstraints(0, 0, 2, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0,
                  0, 0), 0, 0));

      mapButton = GuiTool.createButton(handler.getAddMappingAction());
      mapButton.setEnabled(false);

      JButton loadSetButton = GuiTool.createButton(handler
            .getLoadGestureSetAction());

      rightPanel.add(loadSetButton, new GridBagConstraints(0, 1, 1, 1, 0.5, 0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20,
                  0, 20, 0), 50, 0));

      rightPanel.add(mapButton, new GridBagConstraints(1, 1, 1, 1, 0.5, 0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20,
                  0, 20, 0), 50, 0));
   }// initRightPanel


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
      JMenu menu = GuiTool.createMenu(Constant.FILE_MENU, GuiBundleTool.getBundle());
      menu.add(new JMenuItem(handler.getNewProjectAction()));
      menu.add(new JMenuItem(handler.getOpenProjectAction()));
      saveMenuItem = new JMenuItem(handler.getSaveProjectAction());
      saveMenuItem.setEnabled(false);
      menu.add(saveMenuItem);
      menu.add(new JMenuItem(handler.getSaveProjectAsAction()));
      menu.addSeparator();
      menu.add(new JMenuItem(handler.getOptionsAction()));
      menu.addSeparator();
      menu.add(new JMenuItem(handler.getExitApplicationAction()));
      return menu;
   } // createFileMenu


   private JMenu createInfoMenu() {
      JMenu menu = GuiTool.createMenu(Constant.HELP_MENU, GuiBundleTool.getBundle());
      menu.add(new JMenuItem(handler.getAboutAction()));
      return menu;
   } // createInfoMenu


   /**
    * Returns the main model.
    * 
    * @return the main model.
    */
   public MainModel getModel() {
      return model;
   } // getModel


   /**
    * Updates the gesture set.
    * 
    */
   public void updateGestureList() {
      gestureList.setModel(model.getGestureListModel());
      mapButton.setEnabled(false);
   }// updateGestureList


   /**
    * Update the mapping Set
    * 
    */
   public void updateMappingList() {
      mappingList.setModel(model.getMappingListModel());
      editButton.setEnabled(false);
      removeButton.setEnabled(false);
      mapButton.setEnabled(false);
   }// updateMappingList


   /**
    * Update the two lists
    * 
    */
   public void updateLists() {
      updateGestureList();
      updateMappingList();
   }// updateLists


   /**
    * Return the selected Gesture Class
    * 
    * @return the selected GestureClass
    * 
    */
   public GestureClass getSelectedClass() {
      return (GestureClass)gestureList.getSelectedValue();
   }// getSelectedClass


   /**
    * Return the selected GestureToAction mapping
    * 
    * @return the selected mapping
    * 
    */
   public GestureToActionMapping getSelectedMappping() {
      return (GestureToActionMapping)mappingList.getSelectedValue();
   }// getSelectedMappping


   /**
    * Return the selected mapping
    * 
    * @return the selected GestureMapping
    * 
    */
   public GestureToActionMapping getSelectedMapping() {
      return (GestureToActionMapping)mappingList.getSelectedValue();
   }// getSelectedMapping


   /**
    * Enable the save in the menu
    * 
    */
   public void enableMenuItem() {
      saveMenuItem.setEnabled(true);
   }// enableMenuItem


   /**
    * Returns the component handler of the application
    * 
    * @return the component Handler
    * 
    */
   public ComponentHandler getComponentHandler() {
      return compHandler;
   }// getComponentHandler


   /**
    * Returns the component handler of the application
    * 
    * @return the component Handler
    * 
    */
   public ActionHandler getActionHandler() {
      return handler;
   }// getActionHandler


   /**
    * Disable the save operation
    */
   public void disableSaveButton() {
      saveButton.setEnabled(false);
      saveMenuItem.setEnabled(false);
   }// disableSaveButton


   /**
    * Enable the save operation
    */
   public void enableSaveButton() {
      saveButton.setEnabled(true);
      saveMenuItem.setEnabled(true);
   }// enableSaveButton

   /**
    * Cell Renderer for the mapping list
    */
   public class MappingCellRenderer implements ListCellRenderer {

      public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

         JLabel label = new JLabel();
         if (value instanceof GestureToActionMapping) {
            GestureToActionMapping gm = (GestureToActionMapping)value;
            label.setText(gm.getGestureClass().getName() + Constant.DOUBLE_BLANK
                  + Constant.ARROW_RIGHT + Constant.DOUBLE_BLANK
                  + gm.getAction().toString());
            label.setOpaque(true);
            label.setBackground(isSelected ? Color.CYAN : list.getBackground());
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


   public void windowClosed(WindowEvent e) {
   }


   public void windowOpened(WindowEvent e) {
   }


   public void windowIconified(WindowEvent e) {
      ((MinimizeAction)handler.getMinimizeAction()).minimizeWindow();

   }


   public void windowDeiconified(WindowEvent e) {
   }


   public void windowActivated(WindowEvent e) {
   }


   public void windowDeactivated(WindowEvent e) {
   }


   public void windowClosing(WindowEvent e) {

   }

}
