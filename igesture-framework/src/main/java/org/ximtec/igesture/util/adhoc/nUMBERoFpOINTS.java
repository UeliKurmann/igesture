/*
 * @(#)$Id:$
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
 * 03.04.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.util.adhoc;

import java.io.File;

import org.ximtec.igesture.Recogniser;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.util.GestureTool;
import org.ximtec.igesture.util.XMLTool;



/**
 * Comment
 * @version 1.0 03.04.2008
 * @author Ueli Kurmann
 */
public class nUMBERoFpOINTS {
   
   public static void main(String[] args){
      GestureSet gestureSet = XMLTool.importGestureSet(new File("c:/gestureset.xml")).get(0);
      
      GestureTool.hasSampleEnoughPoints(gestureSet, 5);
      

      
     
      
      
   }

}
