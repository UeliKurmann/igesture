/*
 * @(#)JAboutDialog.java	1.0   Feb 01, 2007
 *
 * Author		:	Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose		:   About Dialog
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 09.02.2007		ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.utils;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicHTML;

import org.sigtec.graphix.widget.BasicButton;
import org.sigtec.graphix.widget.BasicDialog;
import org.ximtec.igesture.tool.GestureConstants;

public class JAboutDialog extends BasicDialog {

	public JAboutDialog(int width, int height, URL path) {
		super(GestureConstants.COMMON_ABOUT,SwingTool.getGuiBundle());
		init(width, height, path);
	}

	private void init(int width, int height, URL path) {
		this.setIconImage(IconLoader.getImage(IconLoader.INFORMATION));
		this.setSize(width, height);

		JEditorPane aboutField;
		try {
			aboutField = new JEditorPane(path);
			aboutField.setEditable(false);
			aboutField.setContentType("text/html");
			aboutField.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
			aboutField.putClientProperty(BasicHTML.documentBaseKey, JAboutDialog.class.getResource("/"));
			aboutField.setSize(width - 10, height - 50);
			JScrollPane scrollPane = new JScrollPane(aboutField);
			scrollPane.setPreferredSize(new Dimension(width - 10, height - 50));
			scrollPane.setAutoscrolls(true);
			addComponent(scrollPane,SwingTool.createGridBagConstraint(0, 0));	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		BasicButton okButton = new BasicButton(GestureConstants.COMMON_CLOSE,
				SwingTool.getGuiBundle());
		okButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				closeDialog();
			}
		});
		addComponent(okButton,SwingTool.createGridBagConstraint(0, 1));
		this.pack();
	}
	
	private void closeDialog(){
		this.setVisible(false);
		this.dispose();
	}
}
