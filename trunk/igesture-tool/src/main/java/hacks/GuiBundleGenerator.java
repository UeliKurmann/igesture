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
 * 04.05.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package hacks;

import java.lang.reflect.Field;

import org.ximtec.igesture.tool.GestureConstants;




/**
 * Comment
 * @version 1.0 04.05.2008
 * @author Ueli Kurmann
 */
public class GuiBundleGenerator {
   
   public static void main(String... args) throws IllegalArgumentException, IllegalAccessException{
      
      Field[] fields = GestureConstants.class.getDeclaredFields();
      for(Field field:fields){
         String prefix = (String)field.get(null);
         System.out.println(prefix+".Name=");
         System.out.println(prefix+".MnemonicKey=");
         System.out.println(prefix+".ShortDescription=");
         System.out.println(prefix+".LongDescription=");
         System.out.println(prefix+".AcceleratorKey=");
         System.out.println(prefix+".Icon=");
         System.out.println();
         
      }
   }

}
