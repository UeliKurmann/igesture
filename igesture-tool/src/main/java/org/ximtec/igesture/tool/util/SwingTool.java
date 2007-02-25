/*
 * @(#)SwingTool.java	1.0   Nov 15, 2006
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   Static helper methods used by the swing application
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 1.12.2006		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.util;

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

import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.sigtec.graphix.widget.BasicInternalFrame;
import org.sigtec.graphix.widget.BasicLabel;
import org.sigtec.graphix.widget.BasicTextField;
import org.sigtec.util.GuiBundle;

public class SwingTool {

	private static final String GUI_BUNDLE_FILE = "iserverMenu";

	public static final Dimension BUTTON_PREFERRED_SIZE = new java.awt.Dimension(
			80, 26);

	public static final Dimension TEXT_FIELD_PREFERRED_SIZE = new java.awt.Dimension(
			80, 26);

	public static final Dimension LABEL_PREFERRED_SIZE = new java.awt.Dimension(
			80, 26);

	public static final Dimension DIALOG_SIZE = new java.awt.Dimension(300, 300);

	public static final Dimension FRAME_SIZE = new java.awt.Dimension(240, 320);

	public static final Dimension FRAME_PREFERRED_SIZE = new java.awt.Dimension(
			240, 320);

	private static GuiBundle guiBundle;

	static {
		guiBundle = new GuiBundle(GUI_BUNDLE_FILE);
	}

	/**
	 * Creates a new GridBagConstraints instance
	 * 
	 * @param gridX
	 *            x position
	 * @param gridY
	 *            y position
	 * @param width
	 *            number of columns to span
	 * @param height
	 *            number of rows to span
	 * @return
	 */
	public static GridBagConstraints createGridBagConstraint(int gridX,
			int gridY, int width, int height, int alignment) {
		return new GridBagConstraints(gridX, gridY, width, height, 0, 0,
				alignment, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0,
				0);
	}

	public static GridBagConstraints createGridBagConstraint(int gridX,
			int gridY, int width, int height) {
		return createGridBagConstraint(gridX, gridY, width, height,
				GridBagConstraints.CENTER);
	}

	/**
	 * Creates a new GridBagConstraints instance
	 * 
	 * @param gridX
	 *            x position
	 * @param gridY
	 *            y position
	 * @return
	 */
	public static GridBagConstraints createGridBagConstraint(int gridX,
			int gridY) {
		return createGridBagConstraint(gridX, gridY, 1, 1);
	}

	public static GuiBundle getGuiBundle() {
		return guiBundle;
	} // getGuiBundle

	/**
	 * Creates a new button with some default settings.
	 * 
	 * @param key
	 *            the key of the button to be created.
	 * @return button configured with data from the GUI bundle.
	 */
	public static BasicButton createButton(String key) {
		final BasicButton button = new BasicButton(key, getGuiBundle());
		button.setPreferredSize(BUTTON_PREFERRED_SIZE);
		button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		return button;
	} // createButton

	/**
	 * Creates a new text field with some default settings.
	 * 
	 * @param key
	 *            the key of the text field to be created.
	 * @return text field configured with data from the GUI bundle.
	 */
	public static BasicTextField createTextField(String key) {
		final BasicTextField textField = new BasicTextField(key, getGuiBundle());
		textField.setPreferredSize(TEXT_FIELD_PREFERRED_SIZE);
		textField.setEditable(true);
		return textField;
	} // createTextField

	/**
	 * Creates a new dialogue with some default settings.
	 * 
	 * @param key
	 *            the key of the dialogue to be created.
	 * @return dialogue configured with data from the GUI bundle.
	 */
	public static BasicDialog createDialog(String key) {
		final BasicDialog dialog = new BasicDialog(key, getGuiBundle());
		dialog.setSize(DIALOG_SIZE);
		return dialog;
	} // createDialog

	/**
	 * Creates a new label with some default settings.
	 * 
	 * @param key
	 *            the key of the label to be created.
	 * @return label configured with data from the GUI bundle.
	 */
	public static BasicLabel createLabel(String key) {
		final BasicLabel label = new BasicLabel(key, getGuiBundle());
		label.setPreferredSize(LABEL_PREFERRED_SIZE);
		return label;
	} // createLabel

	/**
	 * Creates a new internal frame with some default settings.
	 * 
	 * @param key
	 *            the key of the internal frame to be created.
	 * @return internal frame configured with data from the GUI bundle.
	 */
	public static BasicInternalFrame createInternalFrame(String key) {
		final BasicInternalFrame frame = new BasicInternalFrame(key,
				getGuiBundle());
		initFrame(frame);
		return frame;
	} // createFrame

	/**
	 * Initialises a JFrame
	 * @param frame
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
	 * Creates a new menu item with the given action
	 * @param action the action of the item
	 * @return a new menu item
	 */
	public static JMenuItem createMenuItem(Action action) {
		final JMenuItem item = new JMenuItem();
		item.setAction(action);
		return item;
	}

	/**
	 * Creates a new menu item with the given action
	 * @param action the action
	 * @param icon the icon
	 * @return a new menu item
	 */
	public static JMenuItem createMenuItem(Action action, ImageIcon icon) {
		final JMenuItem item = new JMenuItem();
		item.setAction(action);
		item.setIcon(icon);
		return item;
	}

	/**
	 * Creates a JMenu
	 * @param name the name of the menu
	 * @param actions an array of actions
	 * @return a new JMenu with items
	 */
	public static JMenu createMenu(String name, Action[] actions) {
		final JMenu menu = new JMenu(name);
		if (actions != null) {
			for (final Action action : actions) {
				menu.add(createMenuItem(action));
			}
		}
		return menu;
	}

	/**
	 * Creates a popup menu
	 * @param actions
	 * @return a popup menu
	 */
	public static JPopupMenu createPopupMenu(Action[] actions) {
		final JPopupMenu menu = new JPopupMenu();
		for (final Action action : actions) {
			menu.add(createMenuItem(action));
		}
		return menu;
	}

	/**
	 * Returns a scrllable list
	 * @param model the list model
	 * @param height the height of the component
	 * @param width the width of the component
	 * @return a new scrollable list
	 */
	public static ScrollableList createScrollableList(ListModel model,
			int height, int width) {
		return new ScrollableList(model, height, width);
	}

	/**
	 * Creates a new JButton with an action
	 * @param action the action
	 * @return a new JButton
	 */
	public static JButton createButton(Action action) {
		final JButton button = new JButton();
		button.setPreferredSize(BUTTON_PREFERRED_SIZE);
		button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		button.setAction(action);
		return button;
	}

}
