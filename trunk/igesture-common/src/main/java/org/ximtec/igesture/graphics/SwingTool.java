/*
 * @(#)SwingTool.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Static helper methods used by the swing application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;

import org.sigtec.graphix.GuiTool;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicInternalFrame;


/**
 * Static helper methods used by the swing application.
 * 
 * @version 1.0 Nov 2006
 * @author Ueli Kurmann, kurmannu@ethz.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class SwingTool {

   public static final Dimension BUTTON_PREFERRED_SIZE = new java.awt.Dimension(
         80, 26);

   public static final Dimension DIALOG_SIZE = new java.awt.Dimension(300, 300);

   public static final Dimension FRAME_SIZE = new java.awt.Dimension(240, 320);

   public static final Dimension FRAME_PREFERRED_SIZE = new java.awt.Dimension(
         240, 320);


   /**
    * Creates a new GridBagConstraints instance.
    * 
    * @param gridX the x position.
    * @param gridY the y position.
    * @param width number of columns to span.
    * @param height number of rows to span.
    * @return the newly created GridBagConstraints instance.
    */
   public static GridBagConstraints createGridBagConstraint(int gridX,
         int gridY, int width, int height, int alignment) {
      return new GridBagConstraints(gridX, gridY, width, height, 0, 0,
            alignment, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0);
   } // createGridBagConstraint


   public static GridBagConstraints createGridBagConstraint(int gridX,
         int gridY, int width, int height) {
      return createGridBagConstraint(gridX, gridY, width, height,
            GridBagConstraints.CENTER);
   } // createGridBagConstraint


   /**
    * Creates a new GridBagConstraints instance.
    * 
    * @param gridX the x position.
    * @param gridY the y position.
    * @return the newly created GridBagConstraints instance.
    */
   public static GridBagConstraints createGridBagConstraint(int gridX, int gridY) {
      return createGridBagConstraint(gridX, gridY, 1, 1);
   } // createGridBagConstraint


   /**
    * Creates a new button with some default settings.
    * 
    * @param key the key of the button to be created.
    * @return button configured with data from the GUI bundle.
    */
   public static BasicButton createButton(String key) {
      final BasicButton button = new BasicButton(key, GuiTool.getGuiBundle());
      button.setPreferredSize(BUTTON_PREFERRED_SIZE);
      button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
      return button;
   } // createButton


   /**
    * Creates a new dialog with some default settings.
    * 
    * @param key the key of the dialogue to be created.
    * @return dialogue configured with data from the GUI bundle.
    */
   public static BasicDialog createDialog(String key) {
      final BasicDialog dialog = new BasicDialog(key, GuiTool.getGuiBundle());
      dialog.setSize(DIALOG_SIZE);
      return dialog;
   } // createDialog


   /**
    * Creates a new internal frame with some default settings.
    * 
    * @param key the key of the internal frame to be created.
    * @return internal frame configured with data from the GUI bundle.
    */
   public static BasicInternalFrame createInternalFrame(String key) {
      final BasicInternalFrame frame = new BasicInternalFrame(key, GuiTool
            .getGuiBundle());
      initFrame(frame);
      return frame;
   } // createFrame


   /**
    * Initialises a frame..
    * @param frame the frame to be initialised.
    */
   public static void initFrame(BasicInternalFrame frame) {
      frame.setSize(FRAME_SIZE);
      frame.setLayout(new FlowLayout(FlowLayout.CENTER));
      frame.setResizable(true);
      frame.setVisible(true);
      frame.setPreferredSize(new java.awt.Dimension(FRAME_PREFERRED_SIZE));
      frame.setClosable(true);
      frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
   } // initFrame


   /**
    * Creates a new menu item with the given action.
    * @param action the action to be used.
    * @param icon the icon the icon to be used.
    * @return a newly created menu item.
    */
   public static JMenuItem createMenuItem(Action action, ImageIcon icon) {
      final JMenuItem item = new JMenuItem();
      item.setAction(action);
      item.setIcon(icon);
      return item;
   } // createMenuItem


   /**
    * Creates a JMenu
    * @param name the name of the menu.
    * @param actions an array of actions.
    * @return the newly created JMenu with the corresponding items.
    */
   public static JMenu createMenu(String name, Action[] actions) {
      final JMenu menu = new JMenu(name);

      if (actions != null) {

         for (final Action action : actions) {
            menu.add(new JMenuItem(action));
         }

      }

      return menu;
   } // createMenu


   /**
    * Creates a pop-up menu.
    * @param actions an array of actions.
    * @return the newly created pop-up menu.
    */
   public static JPopupMenu createPopupMenu(Action[] actions) {
      final JPopupMenu menu = new JPopupMenu();

      for (final Action action : actions) {
         menu.add(new JMenuItem(action));
      }

      return menu;
   } // createPopupMenu


   /**
    * Returns a scrollable list.
    * @param model the list model.
    * @param height the height of the component.
    * @param width the width of the component.
    * @return a new scrollable list.
    */
   public static ScrollableList createScrollableList(ListModel model,
         int height, int width) {
      return new ScrollableList(model, height, width);
   } // createScrollableList


   /**
    * Creates a new JButton with an action.
    * @param action the action to be used.
    * @return the newly created JButton.
    */
   public static JButton createButton(Action action) {
      final JButton button = new JButton();
      button.setPreferredSize(BUTTON_PREFERRED_SIZE);
      button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
      button.setAction(action);
      return button;
   } // createButton

}
