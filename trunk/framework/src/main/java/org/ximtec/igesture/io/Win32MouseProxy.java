/*
 * @(#)Win32MouseProxy.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Reads the mouse position 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2006 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.io;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.IOException;

import sun.misc.ServiceConfigurationError;

import com.eaio.nativecall.IntCall;
import com.eaio.nativecall.NativeCall;


public class Win32MouseProxy {

   public static int LEFT = 0X01;

   public static int RIGHT = 0X02;

   public static int MIDDLE = 0X04;

   static {
      try {
         NativeCall.init();
      }
      catch (final SecurityException e) {
         e.printStackTrace();
      }
      catch (final UnsatisfiedLinkError e) {
         e.printStackTrace();
      }
      catch (final UnsupportedOperationException e) {
         e.printStackTrace();
      }
      catch (final IOException e) {
         e.printStackTrace();
      }
      catch (final ServiceConfigurationError e) {
         e.printStackTrace();
      }
   }


   /**
    * Returns true if the right mouse button is pressed
    * 
    * @return true if the right mouse button is pressed
    */
   public static boolean isRightButtonPressed() {
      return _GetAsyncKeyState(RIGHT) != 0 ? true : false;
   }


   /**
    * Returns true if the middle mouse button is pressed
    * 
    * @return true if the middle mouse button is pressed
    */
   public static boolean isMiddleButtonPressed() {
      return _GetAsyncKeyState(MIDDLE) != 0 ? true : false;
   }


   /**
    * Returns true if the left mouse button is pressed
    * 
    * @return true if the left mouse button is pressed
    */
   public static boolean isLeftButtonPressed() {
      return _GetAsyncKeyState(LEFT) != 0 ? true : false;
   }


   /**
    * Returns the location of the mouse cursor on the screen
    * 
    * @return Returns the location of the mouse cursor on the screen
    */
   public static Point getCursorLocation() {
      final PointerInfo pi = MouseInfo.getPointerInfo();
      return pi.getLocation();
   }


   /**
    * Calls the native user32.dll fucntion GetAsyncKeyState. This functions
    * checks if the given key is pressed.
    * 
    * @param input the key to test
    * @return true if the given key is pressed
    */
   private static int _GetAsyncKeyState(int input) {
      final IntCall ic = new IntCall("user32", "GetAsyncKeyState");
      final int i = ic.executeCall(input);
      ic.destroy();
      return i;
   }
}
