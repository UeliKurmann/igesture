/*
 * @(#)$Id: Gesture3DMain.java
 *
 * Author		:	Arthur Vogels, arthur.vogels@gmail.com
 *                  
 *
 * Purpose		:   Main for Gesture3DTool
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 15.01.2009		vogelsar	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.algorithm.rubine3d.tools;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Gesture3DMain {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		//Set System Look & Feel
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Create tool
		//Gesture3DTool tool = new Gesture3DTool("C:\\GestureDB\\demogestureset.db");
		//FIXME adapt path to where your project is
		//TODO use java.util.Properties
		Gesture3DTool tool = new Gesture3DTool("/home/bjorn/workspace/thesis/projects/wii.igz");
		// Create UI Frame
		Gesture3DToolUI ui = new Gesture3DToolUI(tool);
		ui.setVisible(true);
		tool.setUI(ui);	
	}

}
