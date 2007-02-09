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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.sigtec.graphix.widget.BasicButton;
import org.ximtec.igesture.tool.GestureConstants;

public class JAboutDialog extends JDialog {

	public JAboutDialog(int width, int height, String path) {
		super();
		init(width, height, path);
	}

	private void init(int width, int height, String path) {
		this.setIconImage(IconLoader.getImage(IconLoader.INFORMATION));
		this.setSize(width, height);

		Box box = Box.createVerticalBox();

		JEditorPane aboutField = new JEditorPane();
		aboutField.setEditable(false);
		aboutField.setContentType("text/html");
		aboutField.setSize(width - 10, height - 50);
		aboutField.setText(loadText(path));
		JScrollPane scrollPane = new JScrollPane(aboutField);
		box.add(scrollPane);
		BasicButton okButton = new BasicButton(GestureConstants.COMMON_CLOSE,
				SwingTool.getGuiBundle());
		okButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				closeDialog();
			}
		});
		box.add(okButton);

		this.add(box);
	}
	
	private void closeDialog(){
		this.setVisible(false);
		this.dispose();
	}

	private String loadText(String path) {
		InputStream stream = JAboutDialog.class.getClassLoader()
				.getResourceAsStream(path);
		if (stream != null) {
			try {
				return org.apache.commons.io.IOUtils.toString(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		new JAboutDialog(300, 300, "about.html").setVisible(true);
	}

}
