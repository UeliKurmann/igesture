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

public class Gesture3DMain {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		//Create tool
		Gesture3DTool tool = new Gesture3DTool("C:\\GestureDB\\demogestureset.db");
		// Create UI Frame
		Gesture3DToolUI ui = new Gesture3DToolUI(tool);
		ui.setVisible(true);
		tool.setUI(ui);	
	}

}
