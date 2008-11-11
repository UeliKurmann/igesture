/*
 * @(#)$Id$
 *
 * Author		:	Michele Croci, mcroci@gmail.com
 *
 * Purpose		:  Test MouseUtils
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Jan 18, 2008		crocimi		Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.mouse;




/**
 * Comment
 * @version 1.0 Jan 18, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class MouseTest {

   /**
    * @param args
    */
   public static void main(String[] args) {
      MouseTest mt = new MouseTest();
      MyTestClass test = mt.new MyTestClass();

      MouseUtils mouseUtils = new MouseUtils(test);
      mouseUtils.start();
  
      
      

   }

   
   private class MyTestClass implements MouseCallback{

      public void callbackFunction(int x, int y, boolean buttonPressed){
         System.out.println("x: "+x+" - y: "+y);     
      }
      
      public int getMouseButton(){
         int MIDDLE= 0x04;
         return MIDDLE;
      }
      
   }
   
}
