/*
 * @(#)Win32MouseProxy.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, ueli@smartness.ch
 *
 * Purpose      : 	Reads the mouse position.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Dec 26, 2006     ukurmann    Initial Release
 * Mar 22, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sigtec.util.Constant;

import sun.misc.ServiceConfigurationError;

import com.eaio.nativecall.IntCall;
import com.eaio.nativecall.NativeCall;


/**
 * Reads the mouse position.
 * 
 * @version 1.0, Dec 2006
 * @author Ueli Kurmann, ueli@smartness.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class Win32MouseProxy {

   private static final Logger LOGGER = Logger.getLogger(Win32MouseProxy.class
         .getName());

   public static int LEFT = 0X01;

   public static int RIGHT = 0X02;

   public static int MIDDLE = 0X04;

   static {
      try {
         NativeCall.init();
      }
      catch (final SecurityException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final UnsatisfiedLinkError e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final UnsupportedOperationException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final IOException e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }
      catch (final ServiceConfigurationError e) {
         LOGGER.log(Level.SEVERE, Constant.EMPTY_STRING, e);
      }

   }


   /**
    * Returns true if the right mouse button is pressed.
    * 
    * @return true if the right mouse button is pressed.
    */
   public static boolean isRightButtonPressed() {
      return _GetAsyncKeyState(RIGHT) != 0 ? true : false;
   } // isRightButtonPressed


   /**
    * Returns true if the middle mouse button is pressed.
    * 
    * @return true if the middle mouse button is pressed.
    */
   public static boolean isMiddleButtonPressed() {
      return _GetAsyncKeyState(MIDDLE) != 0 ? true : false;
   } // isMiddleButtonPressed


   /**
    * Returns true if the left mouse button is pressed.
    * 
    * @return true if the left mouse button is pressed.
    */
   public static boolean isLeftButtonPressed() {
      return _GetAsyncKeyState(LEFT) != 0 ? true : false;
   } // isLeftButtonPressed


   /**
    * Returns the location of the mouse cursor on the screen.
    * 
    * @return the location of the mouse cursor on the screen.
    */
   public static Point getCursorLocation() {
      final PointerInfo pi = MouseInfo.getPointerInfo();
      return pi.getLocation();
   } // getCursorLocation


   /**
    * Calls the native user32.dll function GetAsyncKeyState. This functions
    * checks if the given key is pressed.
    * 
    * @param input the key to test.
    * @return true if the given key is pressed.
    */
   private static int _GetAsyncKeyState(int input) {
      final IntCall ic = new IntCall("user32", "GetAsyncKeyState");
      final int i = ic.executeCall(input);
      ic.destroy();
      return i;
   } // _GetAsyncKeyState

}
