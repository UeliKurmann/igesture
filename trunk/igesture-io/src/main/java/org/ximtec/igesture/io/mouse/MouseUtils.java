/*
 * @(#)MouseUtils.java 1.0   Nov 14, 2007
 *
 * Author       :   Croci Michele, mcroci@gmail.com
 *
 * Purpose      :  Provide access to API for managing a mouse
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * Nov 14, 2007    crocimi     Initial Release
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

import org.ximtec.igesture.io.keyboard.KeyboardUtils.Kernel32;
import org.ximtec.igesture.io.win32.User32;
import org.ximtec.igesture.io.win32.User32.MSG;
import org.ximtec.igesture.io.win32.User32.POINT;
import org.ximtec.igesture.io.win32.W32API.HANDLE;
import org.ximtec.igesture.io.win32.W32API.HINSTANCE;
import org.ximtec.igesture.io.win32.W32API.LPARAM;
import org.ximtec.igesture.io.win32.W32API.LRESULT;
import org.ximtec.igesture.io.win32.W32API.WPARAM;

import com.sun.jna.win32.StdCallLibrary.StdCallCallback;


public class MouseUtils extends Thread{

   HINSTANCE hinst;
   MouseProc msListener;
   HANDLE handle;
   private final MouseCallback mouseCallback;


   public MouseUtils(MouseCallback mC) {
      mouseCallback = mC;
   }
   
   /**
    * Initialization
    */
 public void init(){
      hinst = Kernel32.INSTANCE.GetModuleHandle(null);
      msListener = new MouseProc() {

         public LRESULT callback(int code, WPARAM wParam, LPARAM lParam) {
            // getCoordinates!!
             POINT p = new POINT();
             User32.INSTANCE.GetCursorPos(p);

             boolean pressed = User32.INSTANCE.GetAsyncKeyState(mouseCallback.getMouseButton());
             mouseCallback.callbackFunction(p.x, p.y, pressed);

            return User32.INSTANCE.CallNextHookEx(handle, code, wParam, lParam);
         }
      };
       handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_MOUSE_LL, msListener, hinst,0);
   }
 
 


   /**
    * Finalize thread
    */
   protected void finalize() throws Throwable {
      unregisterHook();
      super.finalize();
   }

   /**
    * Finalize thread
    */
   public interface MouseProc extends StdCallCallback {
      LRESULT callback(int code, WPARAM wParam, LPARAM lParam);
   }
  
   
   /**
    * Register callback function
    */
   public void registerHook(){
      handle = User32.INSTANCE.SetWindowsHookExW(User32.WH_KEYBOARD_LL, msListener, hinst, 0);
  }
   
   
   
   public void run(){
      init();
      registerHook();
      MSG msg = new MSG();
      while((User32.INSTANCE.GetMessageW(msg,null,0,0)!=0)){
       //  User32.INSTANCE.DispatchMessage(msg);
      }
   }
  
   /**
    * Unregister callback function
    */
   public void unregisterHook(){
      User32.INSTANCE.UnhookWindowsHookExW(handle);
   }

}
