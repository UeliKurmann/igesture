/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 04.05.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sigtec.graphix.GuiBundle;
import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicLabel;
import org.sigtec.graphix.widget.BasicTextField;
import org.sigtec.util.Constant;
import org.ximtec.igesture.tool.locator.Service;
import org.ximtec.igesture.tool.service.GuiBundleService;

/**
 * Comment
 * 
 * @version 1.0 04.05.2008
 * @author Ueli Kurmann
 */
public class ComponentFactory implements Service {

	

	private GuiBundleService guiBundleService;

  public ComponentFactory(GuiBundleService guiBundleService) {
		this.guiBundleService = guiBundleService;
	}

	public JLabel createLabel(String key) {
		return new BasicLabel(key, getGuiBundle(), Constant.COLON);
	}

	public JButton createButton(String key, Action action) {
		return new BasicButton(action, key, getGuiBundle());
	}

	public JTextField createTextField(String key) {
		return new BasicTextField(key, getGuiBundle());
	}

	public JTextArea createTextArea(String key) {
		return new JTextArea();
	}

	public GuiBundle getGuiBundle() {
	  return guiBundleService;
	}

	public JPopupMenu createPopupMenu(Action action) {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item = new JMenuItem();
		item.setAction(action);
		menu.add(item);
		return menu;
	}
	
	public static JPanel createBorderLayoutPanel(){
	  JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setOpaque(false);
    panel.setBackground(Color.white);
    return panel;
	}
	
	public static JPanel createGridBagLayoutPanel(){
	  JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    panel.setOpaque(true);
    panel.setBackground(Color.white);

    return panel;
	}

	@Override
	public String getIdentifier() {
		return ComponentFactory.class.getName();
	}

}
