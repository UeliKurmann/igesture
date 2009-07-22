/*
 * @(#)$Id$
 *
 * Author       :   Michele Croci, mcroci@gmail.com
 *
 * Purpose      :  Test MouseUtils
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Jan 18, 2008     crocimi     Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io.wacom;

import org.ximtec.igesture.io.wacom.Wintab32.ORIENTATION;
import org.ximtec.igesture.io.wacom.Wintab32.ROTATION;




/**
 * Comment
 * @version 1.0 Jan 22, 2008
 * @author Michele Croci, mcroci@gmail.com
 */
public class WacomTest {

   /**
    * @param args
    */
   public static void main(String[] args) {
      WacomTest mt = new WacomTest();
      MyTestClass test = mt.new MyTestClass();

      WacomUtils wUtils = new WacomUtils(test);
      wUtils.start();
  
      
      

   }

   
   private class MyTestClass implements WacomCallback{
      @Override
      public void callbackFunction(int x, int y, int z, int pkstatus, int npress, int tpress,
            long timeStamp, ORIENTATION orientation, ROTATION rotation,
            int button) {
            System.out.println("x: "+x+" y: "+y+" z: "+z+""+" status: "+pkstatus+" button: "+button);
            System.out.println("Orientation: "+orientation.orTwist);
           
         
      }
      
   }
   
}

